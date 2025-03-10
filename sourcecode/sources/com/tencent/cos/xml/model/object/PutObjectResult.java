package com.tencent.cos.xml.model.object;

import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.model.tag.pic.PicUploadResult;
import com.tencent.cos.xml.utils.QCloudXmlUtils;
import com.tencent.qcloud.core.http.HttpResponse;

/* loaded from: classes.dex */
public final class PutObjectResult extends BasePutObjectResult {
    public PicUploadResult picUploadResult;

    @Override // com.tencent.cos.xml.model.object.BasePutObjectResult, com.tencent.cos.xml.model.CosXmlResult
    public void parseResponseBody(HttpResponse httpResponse) throws CosXmlServiceException, CosXmlClientException {
        super.parseResponseBody(httpResponse);
        this.picUploadResult = (PicUploadResult) QCloudXmlUtils.fromXml(httpResponse.byteStream(), PicUploadResult.class);
    }

    public PicUploadResult picUploadResult() {
        return this.picUploadResult;
    }
}
