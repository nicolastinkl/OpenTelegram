package com.tencent.cos.xml.model.object;

import com.tencent.cos.xml.crypto.Headers;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.qcloud.core.http.HttpResponse;

/* loaded from: classes.dex */
public final class UploadPartResult extends CosXmlResult {
    public String eTag;

    @Override // com.tencent.cos.xml.model.CosXmlResult
    public void parseResponseBody(HttpResponse httpResponse) throws CosXmlServiceException, CosXmlClientException {
        super.parseResponseBody(httpResponse);
        this.eTag = httpResponse.header(Headers.ETAG);
    }

    @Override // com.tencent.cos.xml.model.CosXmlResult
    public String printResult() {
        return this.eTag;
    }
}
