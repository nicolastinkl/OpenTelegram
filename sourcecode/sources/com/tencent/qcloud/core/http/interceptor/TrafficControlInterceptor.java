package com.tencent.qcloud.core.http.interceptor;

import com.tencent.qcloud.core.http.HttpTask;
import com.tencent.qcloud.core.logger.QCloudLogger;
import java.io.IOException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/* loaded from: classes.dex */
public class TrafficControlInterceptor implements Interceptor {
    private TrafficStrategy uploadTrafficStrategy = new ModerateTrafficStrategy("UploadStrategy-", 2);
    private TrafficStrategy downloadTrafficStrategy = new AggressiveTrafficStrategy("DownloadStrategy-", 3);

    private static class ResizableSemaphore extends Semaphore {
        ResizableSemaphore(int i, boolean z) {
            super(i, z);
        }

        @Override // java.util.concurrent.Semaphore
        protected void reducePermits(int i) {
            super.reducePermits(i);
        }
    }

    private static abstract class TrafficStrategy {
        static final long BOOST_MODE_DURATION = TimeUnit.SECONDS.toNanos(3);
        private long boostModeExhaustedTime;
        private AtomicInteger concurrent;
        private ResizableSemaphore controller;
        private final int maxConcurrent;
        private final String name;

        TrafficStrategy(String str, int i, int i2) {
            this.name = str;
            this.maxConcurrent = i2;
            this.controller = new ResizableSemaphore(i, true);
            this.concurrent = new AtomicInteger(i);
            QCloudLogger.d("QCloudHttp", str + " init concurrent is " + i, new Object[0]);
        }

        void reportException(Request request, IOException iOException) {
            this.controller.release();
        }

        void reportTimeOut(Request request) {
            adjustConcurrent(1, true);
        }

        void reportSpeed(Request request, double d) {
            if (d > 0.0d) {
                QCloudLogger.d("QCloudHttp", this.name + " %s streaming speed is %1.3f KBps", request, Double.valueOf(d));
                int i = this.concurrent.get();
                if (d > 240.0d && i < this.maxConcurrent) {
                    this.boostModeExhaustedTime = System.nanoTime() + BOOST_MODE_DURATION;
                    adjustConcurrent(i + 1, true);
                    return;
                }
                if (d > 120.0d && this.boostModeExhaustedTime > 0) {
                    this.boostModeExhaustedTime = System.nanoTime() + BOOST_MODE_DURATION;
                    this.controller.release();
                    return;
                } else if (d > 0.0d && i > 1 && d < 70.0d) {
                    adjustConcurrent(i - 1, true);
                    return;
                } else {
                    this.controller.release();
                    return;
                }
            }
            this.controller.release();
        }

        void waitForPermit() {
            try {
                if (this.concurrent.get() > 1 && System.nanoTime() > this.boostModeExhaustedTime) {
                    adjustConcurrent(1, false);
                }
                this.controller.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private synchronized void adjustConcurrent(int i, boolean z) {
            int i2 = i - this.concurrent.get();
            if (i2 != 0) {
                this.concurrent.set(i);
                if (i2 <= 0) {
                    this.controller.reducePermits(i2 * (-1));
                    if (z) {
                        this.controller.release();
                    }
                } else if (z) {
                    this.controller.release(i2 + 1);
                }
                QCloudLogger.i("QCloudHttp", this.name + "set concurrent to " + i, new Object[0]);
            } else if (z) {
                this.controller.release();
            }
        }
    }

    private static class AggressiveTrafficStrategy extends TrafficStrategy {
        AggressiveTrafficStrategy(String str, int i) {
            super(str, i, i);
        }
    }

    private static class ModerateTrafficStrategy extends TrafficStrategy {
        ModerateTrafficStrategy(String str, int i) {
            super(str, 1, i);
        }
    }

    private TrafficStrategy getSuitableStrategy(HttpTask httpTask) {
        if (!httpTask.isEnableTraffic()) {
            return null;
        }
        if (httpTask.isDownloadTask()) {
            return this.downloadTrafficStrategy;
        }
        if (httpTask.isUploadTask()) {
            return this.uploadTrafficStrategy;
        }
        return null;
    }

    private double getAverageStreamingSpeed(HttpTask httpTask, long j) {
        if (j == 0) {
            return 0.0d;
        }
        return (httpTask.getTransferBodySize() / 1024.0d) / (j / 1000.0d);
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x008c  */
    @Override // okhttp3.Interceptor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public okhttp3.Response intercept(okhttp3.Interceptor.Chain r9) throws java.io.IOException {
        /*
            r8 = this;
            okhttp3.Request r0 = r9.request()
            com.tencent.qcloud.core.task.TaskManager r1 = com.tencent.qcloud.core.task.TaskManager.getInstance()
            java.lang.Object r2 = r0.tag()
            java.lang.String r2 = (java.lang.String) r2
            com.tencent.qcloud.core.task.QCloudTask r1 = r1.get(r2)
            com.tencent.qcloud.core.http.HttpTask r1 = (com.tencent.qcloud.core.http.HttpTask) r1
            com.tencent.qcloud.core.http.interceptor.TrafficControlInterceptor$TrafficStrategy r2 = r8.getSuitableStrategy(r1)
            if (r2 == 0) goto L1d
            r2.waitForPermit()
        L1d:
            r3 = 1
            java.lang.Object[] r3 = new java.lang.Object[r3]
            r4 = 0
            r3[r4] = r0
            java.lang.String r4 = "QCloudHttp"
            java.lang.String r5 = " %s begin to execute"
            com.tencent.qcloud.core.logger.QCloudLogger.i(r4, r5, r3)
            long r3 = java.lang.System.nanoTime()     // Catch: java.io.IOException -> L5b com.tencent.qcloud.core.common.QCloudServiceException -> L5d com.tencent.qcloud.core.common.QCloudClientException -> L74
            okhttp3.Response r9 = r8.processRequest(r9, r0)     // Catch: java.io.IOException -> L5b com.tencent.qcloud.core.common.QCloudServiceException -> L5d com.tencent.qcloud.core.common.QCloudClientException -> L74
            boolean r5 = r1.isDownloadTask()     // Catch: java.io.IOException -> L5b com.tencent.qcloud.core.common.QCloudServiceException -> L5d com.tencent.qcloud.core.common.QCloudClientException -> L74
            if (r5 == 0) goto L3b
            r1.convertResponse(r9)     // Catch: java.io.IOException -> L5b com.tencent.qcloud.core.common.QCloudServiceException -> L5d com.tencent.qcloud.core.common.QCloudClientException -> L74
        L3b:
            if (r2 == 0) goto L5a
            boolean r5 = r9.isSuccessful()     // Catch: java.io.IOException -> L5b com.tencent.qcloud.core.common.QCloudServiceException -> L5d com.tencent.qcloud.core.common.QCloudClientException -> L74
            if (r5 == 0) goto L56
            java.util.concurrent.TimeUnit r5 = java.util.concurrent.TimeUnit.NANOSECONDS     // Catch: java.io.IOException -> L5b com.tencent.qcloud.core.common.QCloudServiceException -> L5d com.tencent.qcloud.core.common.QCloudClientException -> L74
            long r6 = java.lang.System.nanoTime()     // Catch: java.io.IOException -> L5b com.tencent.qcloud.core.common.QCloudServiceException -> L5d com.tencent.qcloud.core.common.QCloudClientException -> L74
            long r6 = r6 - r3
            long r3 = r5.toMillis(r6)     // Catch: java.io.IOException -> L5b com.tencent.qcloud.core.common.QCloudServiceException -> L5d com.tencent.qcloud.core.common.QCloudClientException -> L74
            double r3 = r8.getAverageStreamingSpeed(r1, r3)     // Catch: java.io.IOException -> L5b com.tencent.qcloud.core.common.QCloudServiceException -> L5d com.tencent.qcloud.core.common.QCloudClientException -> L74
            r2.reportSpeed(r0, r3)     // Catch: java.io.IOException -> L5b com.tencent.qcloud.core.common.QCloudServiceException -> L5d com.tencent.qcloud.core.common.QCloudClientException -> L74
            goto L5a
        L56:
            r1 = 0
            r2.reportException(r0, r1)     // Catch: java.io.IOException -> L5b com.tencent.qcloud.core.common.QCloudServiceException -> L5d com.tencent.qcloud.core.common.QCloudClientException -> L74
        L5a:
            return r9
        L5b:
            r9 = move-exception
            goto L8a
        L5d:
            r9 = move-exception
            java.lang.Throwable r1 = r9.getCause()
            boolean r1 = r1 instanceof java.io.IOException
            if (r1 == 0) goto L6d
            java.lang.Throwable r9 = r9.getCause()
            java.io.IOException r9 = (java.io.IOException) r9
            goto L8a
        L6d:
            java.io.IOException r1 = new java.io.IOException
            r1.<init>(r9)
        L72:
            r9 = r1
            goto L8a
        L74:
            r9 = move-exception
            java.lang.Throwable r1 = r9.getCause()
            boolean r1 = r1 instanceof java.io.IOException
            if (r1 == 0) goto L84
            java.lang.Throwable r9 = r9.getCause()
            java.io.IOException r9 = (java.io.IOException) r9
            goto L8a
        L84:
            java.io.IOException r1 = new java.io.IOException
            r1.<init>(r9)
            goto L72
        L8a:
            if (r2 == 0) goto L99
            boolean r1 = com.tencent.qcloud.core.http.HttpUtil.isNetworkTimeoutError(r9)
            if (r1 == 0) goto L96
            r2.reportTimeOut(r0)
            goto L99
        L96:
            r2.reportException(r0, r9)
        L99:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.qcloud.core.http.interceptor.TrafficControlInterceptor.intercept(okhttp3.Interceptor$Chain):okhttp3.Response");
    }

    private Response processRequest(Interceptor.Chain chain, Request request) throws IOException {
        return chain.proceed(request);
    }
}
