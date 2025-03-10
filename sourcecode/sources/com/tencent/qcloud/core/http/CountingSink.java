package com.tencent.qcloud.core.http;

import com.tencent.qcloud.core.common.QCloudProgressListener;
import java.io.IOException;
import okio.Buffer;
import okio.ForwardingSink;
import okio.Sink;

/* loaded from: classes.dex */
class CountingSink extends ForwardingSink {
    private long bytesTotal;
    private long bytesWritten;
    private long lastTimeBytesWritten;
    private QCloudProgressListener progressListener;
    private long recentReportBytes;

    public CountingSink(Sink sink, long j, QCloudProgressListener qCloudProgressListener) {
        super(sink);
        this.lastTimeBytesWritten = 0L;
        this.bytesWritten = 0L;
        this.bytesTotal = 0L;
        this.recentReportBytes = 0L;
        this.bytesTotal = j;
        this.progressListener = qCloudProgressListener;
    }

    public CountingSink(Sink sink, long j, long j2, QCloudProgressListener qCloudProgressListener) {
        super(sink);
        this.lastTimeBytesWritten = 0L;
        this.bytesWritten = 0L;
        this.bytesTotal = 0L;
        this.recentReportBytes = 0L;
        this.bytesTotal = j;
        this.lastTimeBytesWritten = j2;
        this.progressListener = qCloudProgressListener;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0026  */
    /* JADX WARN: Removed duplicated region for block: B:15:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void reportProgress() {
        /*
            r8 = this;
            com.tencent.qcloud.core.common.QCloudProgressListener r0 = r8.progressListener
            if (r0 != 0) goto L5
            return
        L5:
            long r1 = r8.bytesWritten
            long r3 = r8.recentReportBytes
            long r3 = r1 - r3
            r5 = 51200(0xc800, double:2.5296E-319)
            int r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r7 > 0) goto L23
            r5 = 10
            long r3 = r3 * r5
            long r5 = r8.bytesTotal
            int r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r7 > 0) goto L23
            int r3 = (r1 > r5 ? 1 : (r1 == r5 ? 0 : -1))
            if (r3 != 0) goto L21
            goto L23
        L21:
            r3 = 0
            goto L24
        L23:
            r3 = 1
        L24:
            if (r3 == 0) goto L31
            r8.recentReportBytes = r1
            long r3 = r8.lastTimeBytesWritten
            long r1 = r1 + r3
            long r5 = r8.bytesTotal
            long r3 = r3 + r5
            r0.onProgress(r1, r3)
        L31:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.qcloud.core.http.CountingSink.reportProgress():void");
    }

    void writeBytesInternal(long j) {
        this.bytesWritten += j;
        reportProgress();
    }

    long getTotalTransferred() {
        return this.bytesWritten + this.lastTimeBytesWritten;
    }

    @Override // okio.ForwardingSink, okio.Sink
    public void write(Buffer buffer, long j) throws IOException {
        super.write(buffer, j);
        writeBytesInternal(j);
    }
}
