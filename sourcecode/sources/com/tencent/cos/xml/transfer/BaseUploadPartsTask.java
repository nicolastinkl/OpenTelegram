package com.tencent.cos.xml.transfer;

import com.tencent.cos.xml.crypto.COSDirect;
import com.tencent.cos.xml.crypto.Headers;
import com.tencent.cos.xml.listener.CosXmlProgressListener;
import com.tencent.cos.xml.model.object.PutObjectRequest;
import com.tencent.cos.xml.transfer.COSUploadTask;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* compiled from: UploadPartsTask.java */
/* loaded from: classes.dex */
abstract class BaseUploadPartsTask {
    protected COSDirect mCosDirect;
    protected long mOffset;
    protected PutObjectRequest mPutObjectRequest;
    protected long mSize;
    protected int mStartNumber;
    protected String mUploadId;
    private CosXmlProgressListener progressListener;
    protected String taskId;
    protected Set<COSUploadTask.UploadPart> uploadParts = Collections.synchronizedSet(new HashSet());
    protected long mMaxPartSize = 1048576;
    protected final String TAG = COSUploadTask.TAG;

    public abstract void cancel();

    public abstract Set<COSUploadTask.UploadPart> upload() throws Exception;

    BaseUploadPartsTask(COSDirect cOSDirect, PutObjectRequest putObjectRequest, long j, long j2, int i, String str) {
        this.mCosDirect = cOSDirect;
        this.mPutObjectRequest = putObjectRequest;
        this.mOffset = j;
        this.mSize = j2;
        this.mStartNumber = i;
        this.mUploadId = str;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x004e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    com.tencent.cos.xml.model.object.UploadPartRequest getUploadRequest(int r14, long r15, long r17) {
        /*
            r13 = this;
            r0 = r13
            long r1 = r0.mOffset
            long r3 = r0.mSize
            long r1 = r1 + r3
            r3 = 0
            int r4 = (r15 > r1 ? 1 : (r15 == r1 ? 0 : -1))
            if (r4 < 0) goto Lc
            return r3
        Lc:
            com.tencent.cos.xml.model.object.PutObjectRequest r1 = r0.mPutObjectRequest
            java.lang.String r5 = r1.getSrcPath()
            com.tencent.cos.xml.model.object.PutObjectRequest r1 = r0.mPutObjectRequest
            android.net.Uri r6 = r1.getUri()
            com.tencent.cos.xml.model.object.PutObjectRequest r1 = r0.mPutObjectRequest
            java.lang.String r2 = r1.getBucket()
            com.tencent.cos.xml.model.object.PutObjectRequest r1 = r0.mPutObjectRequest
            java.lang.String r4 = r1.getCosPath()
            com.tencent.cos.xml.model.object.PutObjectRequest r1 = r0.mPutObjectRequest
            java.lang.String r11 = r1.getRegion()
            if (r5 == 0) goto L3b
            com.tencent.cos.xml.model.object.UploadPartRequest r12 = new com.tencent.cos.xml.model.object.UploadPartRequest
            java.lang.String r10 = r0.mUploadId
            r1 = r12
            r3 = r4
            r4 = r14
            r6 = r15
            r8 = r17
            r1.<init>(r2, r3, r4, r5, r6, r8, r10)
        L39:
            r3 = r12
            goto L4c
        L3b:
            if (r6 == 0) goto L4c
            com.tencent.cos.xml.model.object.UploadPartRequest r12 = new com.tencent.cos.xml.model.object.UploadPartRequest
            java.lang.String r10 = r0.mUploadId
            r1 = r12
            r3 = r4
            r4 = r14
            r5 = r6
            r6 = r15
            r8 = r17
            r1.<init>(r2, r3, r4, r5, r6, r8, r10)
            goto L39
        L4c:
            if (r3 == 0) goto L71
            boolean r1 = android.text.TextUtils.isEmpty(r11)
            if (r1 != 0) goto L57
            r3.setRegion(r11)
        L57:
            com.tencent.cos.xml.model.object.PutObjectRequest r1 = r0.mPutObjectRequest
            java.util.Map r1 = r13.getUploadPartHeaders(r1)
            r3.setRequestHeaders(r1)
            long r1 = r15 + r17
            long r4 = r0.mOffset
            long r6 = r0.mSize
            long r4 = r4 + r6
            int r6 = (r1 > r4 ? 1 : (r1 == r4 ? 0 : -1))
            if (r6 < 0) goto L6d
            r1 = 1
            goto L6e
        L6d:
            r1 = 0
        L6e:
            r3.setLastPart(r1)
        L71:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.cos.xml.transfer.BaseUploadPartsTask.getUploadRequest(int, long, long):com.tencent.cos.xml.model.object.UploadPartRequest");
    }

    private Map<String, List<String>> getUploadPartHeaders(PutObjectRequest putObjectRequest) {
        Map<String, List<String>> requestHeaders = putObjectRequest.getRequestHeaders();
        if (requestHeaders == null) {
            return new HashMap();
        }
        HashMap hashMap = new HashMap();
        for (String str : requestHeaders.keySet()) {
            if (str.startsWith(Headers.SERVER_SIDE_ENCRYPTION) || str.equals(Headers.COS_TRAFFIC_LIMIT)) {
                hashMap.put(str, requestHeaders.get(str));
            }
        }
        return hashMap;
    }

    void notifyProgressChange(long j, long j2) {
        CosXmlProgressListener cosXmlProgressListener = this.progressListener;
        if (cosXmlProgressListener != null) {
            cosXmlProgressListener.onProgress(j, j2);
        }
    }

    public void setProgressListener(CosXmlProgressListener cosXmlProgressListener) {
        this.progressListener = cosXmlProgressListener;
    }

    public void setTaskId(String str) {
        this.taskId = str;
    }
}
