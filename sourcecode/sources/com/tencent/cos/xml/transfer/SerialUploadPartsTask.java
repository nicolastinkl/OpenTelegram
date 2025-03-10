package com.tencent.cos.xml.transfer;

import android.text.TextUtils;
import com.tencent.cos.xml.common.ClientErrorCode;
import com.tencent.cos.xml.crypto.COSDirect;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.listener.CosXmlProgressListener;
import com.tencent.cos.xml.model.object.PutObjectRequest;
import com.tencent.cos.xml.model.object.UploadPartRequest;
import com.tencent.cos.xml.transfer.COSUploadTask;
import com.tencent.qcloud.core.http.HttpTaskMetrics;
import java.util.Set;

/* compiled from: UploadPartsTask.java */
/* loaded from: classes.dex */
class SerialUploadPartsTask extends BaseUploadPartsTask {
    private UploadPartRequest currentUploadPartRequest;
    private volatile HttpTaskMetrics httpTaskMetrics;
    private int mPartNumber;
    private long mStartPointer;

    public SerialUploadPartsTask(COSDirect cOSDirect, PutObjectRequest putObjectRequest, long j, long j2, int i, String str) {
        super(cOSDirect, putObjectRequest, j, j2, i, str);
    }

    @Override // com.tencent.cos.xml.transfer.BaseUploadPartsTask
    public Set<COSUploadTask.UploadPart> upload() throws CosXmlClientException, CosXmlServiceException {
        this.mStartPointer = this.mOffset;
        this.mPartNumber = this.mStartNumber;
        while (true) {
            long j = this.mStartPointer;
            long j2 = this.mOffset;
            long j3 = this.mSize;
            if (j < j2 + j3) {
                long min = Math.min(this.mMaxPartSize, (j2 + j3) - j);
                UploadPartRequest uploadRequest = getUploadRequest(this.mPartNumber, this.mStartPointer, min);
                this.currentUploadPartRequest = uploadRequest;
                uploadRequest.setProgressListener(new CosXmlProgressListener() { // from class: com.tencent.cos.xml.transfer.SerialUploadPartsTask.1
                    @Override // com.tencent.cos.xml.listener.CosXmlProgressListener, com.tencent.qcloud.core.common.QCloudProgressListener
                    public void onProgress(long j4, long j5) {
                        SerialUploadPartsTask serialUploadPartsTask = SerialUploadPartsTask.this;
                        long j6 = serialUploadPartsTask.mStartPointer + j4;
                        SerialUploadPartsTask serialUploadPartsTask2 = SerialUploadPartsTask.this;
                        serialUploadPartsTask.notifyProgressChange(j6, serialUploadPartsTask2.mOffset + serialUploadPartsTask2.mSize);
                    }
                });
                String str = this.mCosDirect.uploadPart(this.currentUploadPartRequest).eTag;
                COSTransferTask.loggerInfo(COSUploadTask.TAG, this.taskId, "upload part %d, etag=%s", Integer.valueOf(this.mPartNumber), str);
                if (TextUtils.isEmpty(str)) {
                    throw new CosXmlClientException(ClientErrorCode.ETAG_NOT_FOUND);
                }
                COSUploadTask.UploadPart uploadPart = new COSUploadTask.UploadPart(str, this.mPartNumber, this.mStartPointer, min);
                this.mStartPointer += min;
                this.mPartNumber++;
                this.uploadParts.add(uploadPart);
            } else {
                return this.uploadParts;
            }
        }
    }

    @Override // com.tencent.cos.xml.transfer.BaseUploadPartsTask
    public void cancel() {
        UploadPartRequest uploadPartRequest = this.currentUploadPartRequest;
        if (uploadPartRequest != null) {
            this.mCosDirect.cancel(uploadPartRequest);
        }
    }

    public void setHttpTaskMetrics(HttpTaskMetrics httpTaskMetrics) {
        this.httpTaskMetrics = httpTaskMetrics;
    }

    private synchronized void mergeTaskMetrics(HttpTaskMetrics httpTaskMetrics) {
        if (httpTaskMetrics != null) {
            if (this.httpTaskMetrics != null) {
                this.httpTaskMetrics.merge(httpTaskMetrics);
            }
        }
    }
}
