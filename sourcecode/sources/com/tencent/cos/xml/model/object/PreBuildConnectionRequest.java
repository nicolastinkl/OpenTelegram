package com.tencent.cos.xml.model.object;

import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.cos.xml.common.ClientErrorCode;
import com.tencent.cos.xml.common.RequestMethod;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.model.CosXmlRequest;
import com.tencent.qcloud.core.http.RequestBodySerializer;

/* loaded from: classes.dex */
public final class PreBuildConnectionRequest extends CosXmlRequest {
    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public String getMethod() {
        return RequestMethod.HEAD;
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public RequestBodySerializer getRequestBody() {
        return null;
    }

    public PreBuildConnectionRequest(String str) {
        this.bucket = str;
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public String getPath(CosXmlServiceConfig cosXmlServiceConfig) {
        return cosXmlServiceConfig.getUrlPath(this.bucket, "/");
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public void checkParameters() throws CosXmlClientException {
        if (this.requestURL == null && this.bucket == null) {
            throw new CosXmlClientException(ClientErrorCode.INVALID_ARGUMENT.getCode(), "bucket must not be null");
        }
    }
}
