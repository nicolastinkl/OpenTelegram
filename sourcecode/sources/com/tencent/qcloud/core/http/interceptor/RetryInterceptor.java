package com.tencent.qcloud.core.http.interceptor;

import com.tencent.cos.xml.common.RequestMethod;
import com.tencent.qcloud.core.common.QCloudServiceException;
import com.tencent.qcloud.core.http.HttpTask;
import com.tencent.qcloud.core.logger.QCloudLogger;
import com.tencent.qcloud.core.task.RetryStrategy;
import com.tencent.qcloud.core.task.TaskManager;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;
import okhttp3.Interceptor;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSource;

/* loaded from: classes.dex */
public class RetryInterceptor implements Interceptor {
    private static volatile Map<String, HostReliable> hostReliables = new HashMap();
    private RetryStrategy.WeightAndReliableAddition additionComputer = new RetryStrategy.WeightAndReliableAddition();
    private RetryStrategy retryStrategy;

    private static class HostReliable {
        private int reliable;

        private HostReliable(String str) {
            this.reliable = 2;
            new Timer(str + "reliable").schedule(new TimerTask(this) { // from class: com.tencent.qcloud.core.http.interceptor.RetryInterceptor.HostReliable.1
                @Override // java.util.TimerTask, java.lang.Runnable
                public void run() {
                }
            }, 300000L, 300000L);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public synchronized void increaseReliable() {
            int i = this.reliable;
            if (i < 4) {
                this.reliable = i + 1;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public synchronized void decreaseReliable() {
            int i = this.reliable;
            if (i > 0) {
                this.reliable = i - 1;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public synchronized int getReliable() {
            return this.reliable;
        }
    }

    public RetryInterceptor(RetryStrategy retryStrategy) {
        this.retryStrategy = retryStrategy;
    }

    @Override // okhttp3.Interceptor
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        return processRequest(chain, request, (HttpTask) TaskManager.getInstance().get((String) request.tag()));
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x00e7, code lost:
    
        r17 = r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x013b, code lost:
    
        r2 = r17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x012a, code lost:
    
        com.tencent.qcloud.core.logger.QCloudLogger.i("QCloudHttp", "%s ends for %s, code is %d", r22, r0, java.lang.Integer.valueOf(r18));
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    okhttp3.Response processRequest(okhttp3.Interceptor.Chain r21, okhttp3.Request r22, com.tencent.qcloud.core.http.HttpTask r23) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 345
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.qcloud.core.http.interceptor.RetryInterceptor.processRequest(okhttp3.Interceptor$Chain, okhttp3.Request, com.tencent.qcloud.core.http.HttpTask):okhttp3.Response");
    }

    private Response executeTaskOnce(Interceptor.Chain chain, Request request, HttpTask httpTask) throws IOException {
        try {
            if (httpTask.isCanceled()) {
                throw new IOException("CANCELED");
            }
            if (httpTask.isResponseFilePathConverter()) {
                long transferBodySize = httpTask.getTransferBodySize();
                if (transferBodySize > 0) {
                    request = buildNewRangeRequest(request, transferBodySize);
                }
            }
            return processSingleRequest(chain, request);
        } catch (ProtocolException e) {
            if (e.getMessage() != null && e.getMessage().contains("HTTP 204 had non-zero Content-Length: ")) {
                return new Response.Builder().request(request).message(e.toString()).code(204).protocol(Protocol.HTTP_1_1).build();
            }
            e.printStackTrace();
            throw e;
        } catch (IOException e2) {
            e2.printStackTrace();
            throw e2;
        }
    }

    private boolean isUserCancelled(IOException iOException) {
        return (iOException == null || iOException.getMessage() == null || !iOException.getMessage().toLowerCase(Locale.ROOT).equals("canceled")) ? false : true;
    }

    Response processSingleRequest(Interceptor.Chain chain, Request request) throws IOException {
        return chain.proceed(request);
    }

    String getClockSkewError(Response response, int i) {
        if (response == null || i != 403) {
            return null;
        }
        if (response.request().method().toUpperCase(Locale.ROOT).equals(RequestMethod.HEAD)) {
            return QCloudServiceException.ERR0R_REQUEST_IS_EXPIRED;
        }
        ResponseBody body = response.body();
        if (body == null) {
            return null;
        }
        try {
            BufferedSource source = body.source();
            source.request(Long.MAX_VALUE);
            String readString = source.buffer().clone().readString(Charset.forName("UTF-8"));
            Pattern compile = Pattern.compile("<Code>(RequestTimeTooSkewed|AccessDenied)</Code>");
            Pattern compile2 = Pattern.compile("<Message>Request has expired</Message>");
            Matcher matcher = compile.matcher(readString);
            Matcher matcher2 = compile2.matcher(readString);
            if (!matcher.find()) {
                return null;
            }
            String group = matcher.group(1);
            if (QCloudServiceException.ERR0R_REQUEST_TIME_TOO_SKEWED.equals(group)) {
                return QCloudServiceException.ERR0R_REQUEST_TIME_TOO_SKEWED;
            }
            if (!"AccessDenied".equals(group)) {
                return null;
            }
            if (matcher2.find()) {
                return QCloudServiceException.ERR0R_REQUEST_IS_EXPIRED;
            }
            return null;
        } catch (IOException unused) {
            return null;
        }
    }

    private void increaseHostReliable(String str) {
        HostReliable hostReliable = hostReliables.get(str);
        if (hostReliable != null) {
            hostReliable.increaseReliable();
        } else {
            hostReliables.put(str, new HostReliable(str));
        }
    }

    private void decreaseHostAccess(String str) {
        HostReliable hostReliable = hostReliables.get(str);
        if (hostReliable != null) {
            hostReliable.decreaseReliable();
        } else {
            hostReliables.put(str, new HostReliable(str));
        }
    }

    private int getHostReliable(String str) {
        HostReliable hostReliable = hostReliables.get(str);
        if (hostReliable != null) {
            return hostReliable.getReliable();
        }
        return 2;
    }

    private boolean shouldRetry(Request request, Response response, int i, int i2, long j, IOException iOException, int i3) {
        if (isUserCancelled(iOException)) {
            return false;
        }
        int hostReliable = getHostReliable(request.url().host());
        int retryAddition = this.additionComputer.getRetryAddition(i2, hostReliable);
        QCloudLogger.i("QCloudHttp", String.format(Locale.ENGLISH, "attempts = %d, weight = %d, reliable = %d, addition = %d", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(hostReliable), Integer.valueOf(retryAddition)), new Object[0]);
        if (this.retryStrategy.shouldRetry(i, System.nanoTime() - j, retryAddition) && this.retryStrategy.getQCloudHttpRetryHandler().shouldRetry(request, response, iOException)) {
            return (iOException != null && isRecoverable(iOException)) || i3 == 500 || i3 == 502 || i3 == 503 || i3 == 504;
        }
        return false;
    }

    private boolean isRecoverable(IOException iOException) {
        if (iOException instanceof ProtocolException) {
            return false;
        }
        if (iOException instanceof InterruptedIOException) {
            return iOException instanceof SocketTimeoutException;
        }
        return (((iOException instanceof SSLHandshakeException) && (iOException.getCause() instanceof CertificateException)) || (iOException instanceof SSLPeerUnverifiedException)) ? false : true;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x002c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private okhttp3.Request buildNewRangeRequest(okhttp3.Request r12, long r13) {
        /*
            r11 = this;
            java.lang.String r0 = "Range"
            java.lang.String r1 = r12.header(r0)
            r2 = 0
            java.lang.String r3 = ""
            r4 = 1
            r5 = -1
            if (r1 == 0) goto L39
            java.lang.String r7 = "bytes="
            java.lang.String r1 = r1.replace(r7, r3)
            java.lang.String r7 = "-"
            java.lang.String[] r1 = r1.split(r7)
            int r7 = r1.length
            if (r7 <= 0) goto L28
            r7 = r1[r2]     // Catch: java.lang.NumberFormatException -> L24
            long r7 = java.lang.Long.parseLong(r7)     // Catch: java.lang.NumberFormatException -> L24
            goto L29
        L24:
            r7 = move-exception
            r7.printStackTrace()
        L28:
            r7 = r5
        L29:
            int r9 = r1.length
            if (r9 <= r4) goto L37
            r1 = r1[r4]     // Catch: java.lang.NumberFormatException -> L33
            long r9 = java.lang.Long.parseLong(r1)     // Catch: java.lang.NumberFormatException -> L33
            goto L3b
        L33:
            r1 = move-exception
            r1.printStackTrace()
        L37:
            r9 = r5
            goto L3b
        L39:
            r7 = r5
            r9 = r7
        L3b:
            int r1 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
            if (r1 == 0) goto L40
            long r13 = r13 + r7
        L40:
            okhttp3.Request$Builder r1 = r12.newBuilder()
            okhttp3.Headers r12 = r12.headers()
            okhttp3.Headers$Builder r12 = r12.newBuilder()
            r7 = 2
            java.lang.Object[] r7 = new java.lang.Object[r7]
            java.lang.Long r13 = java.lang.Long.valueOf(r13)
            r7[r2] = r13
            int r13 = (r9 > r5 ? 1 : (r9 == r5 ? 0 : -1))
            if (r13 != 0) goto L5a
            goto L5e
        L5a:
            java.lang.String r3 = java.lang.String.valueOf(r9)
        L5e:
            r7[r4] = r3
            java.lang.String r13 = "bytes=%s-%s"
            java.lang.String r13 = java.lang.String.format(r13, r7)
            r12.set(r0, r13)
            okhttp3.Headers r12 = r12.build()
            r1.headers(r12)
            okhttp3.Request r12 = r1.build()
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.qcloud.core.http.interceptor.RetryInterceptor.buildNewRangeRequest(okhttp3.Request, long):okhttp3.Request");
    }
}
