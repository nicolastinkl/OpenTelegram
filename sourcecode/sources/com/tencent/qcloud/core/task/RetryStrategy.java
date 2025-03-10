package com.tencent.qcloud.core.task;

import androidx.recyclerview.widget.LinearLayoutManager;
import com.tencent.qcloud.core.http.QCloudHttpRetryHandler;

/* loaded from: classes.dex */
public class RetryStrategy {
    public static RetryStrategy DEFAULT = new RetryStrategy(1000, 2000, 0);
    protected final int baseAttempts;
    protected final int initBackoff;
    protected final int maxBackoff;
    private QCloudHttpRetryHandler qCloudHttpRetryHandler = QCloudHttpRetryHandler.DEFAULT;

    public void onTaskEnd(boolean z, Exception exc) {
    }

    static {
        new RetryStrategy(0, 0, LinearLayoutManager.INVALID_OFFSET);
    }

    public RetryStrategy(int i, int i2, int i3) {
        this.initBackoff = i;
        this.maxBackoff = i2;
        this.baseAttempts = i3;
    }

    public long getNextDelay(int i) {
        if (i < 1) {
            return 0L;
        }
        return Math.min(this.maxBackoff, this.initBackoff * ((int) Math.pow(2.0d, i - 1)));
    }

    public boolean shouldRetry(int i, long j, int i2) {
        return i < this.baseAttempts + i2;
    }

    public void setRetryHandler(QCloudHttpRetryHandler qCloudHttpRetryHandler) {
        this.qCloudHttpRetryHandler = qCloudHttpRetryHandler;
    }

    public QCloudHttpRetryHandler getQCloudHttpRetryHandler() {
        return this.qCloudHttpRetryHandler;
    }

    public static class WeightAndReliableAddition {
        private final int[][] addTable = {new int[]{0, 1, 2, 2, 2}, new int[]{0, 1, 2, 3, 3}, new int[]{0, 1, 2, 3, 4}};

        private int regular(int i, int i2, int i3) {
            return i > i2 ? i2 : i < i3 ? i3 : i;
        }

        public int getRetryAddition(int i, int i2) {
            int regular = regular(i, 2, 0);
            return this.addTable[regular][regular(i2, 4, 0)] + 1;
        }
    }
}
