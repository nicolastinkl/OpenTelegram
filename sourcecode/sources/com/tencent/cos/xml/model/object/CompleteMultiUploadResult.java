package com.tencent.cos.xml.model.object;

import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.model.tag.CompleteMultipartUploadResult;
import com.tencent.cos.xml.utils.QCloudXmlUtils;
import com.tencent.qcloud.core.http.HttpResponse;

/* loaded from: classes.dex */
public final class CompleteMultiUploadResult extends CosXmlResult {
    public CompleteMultipartUploadResult completeMultipartUpload;

    @Override // com.tencent.cos.xml.model.CosXmlResult
    public void parseResponseBody(HttpResponse httpResponse) throws CosXmlServiceException, CosXmlClientException {
        super.parseResponseBody(httpResponse);
        this.completeMultipartUpload = new CompleteMultipartUploadResult();
        this.completeMultipartUpload = (CompleteMultipartUploadResult) QCloudXmlUtils.fromXml(httpResponse.byteStream(), CompleteMultipartUploadResult.class);
    }

    @Override // com.tencent.cos.xml.model.CosXmlResult
    public String printResult() {
        CompleteMultipartUploadResult completeMultipartUploadResult = this.completeMultipartUpload;
        return completeMultipartUploadResult != null ? completeMultipartUploadResult.toString() : super.printResult();
    }
}
