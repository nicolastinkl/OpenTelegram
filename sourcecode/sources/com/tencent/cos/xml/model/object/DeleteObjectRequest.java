package com.tencent.cos.xml.model.object;

import com.tencent.cos.xml.common.RequestMethod;
import com.tencent.qcloud.core.http.RequestBodySerializer;

/* loaded from: classes.dex */
public final class DeleteObjectRequest extends ObjectRequest {
    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public String getMethod() {
        return RequestMethod.DELETE;
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public RequestBodySerializer getRequestBody() {
        return null;
    }

    public DeleteObjectRequest(String str, String str2) {
        super(str, str2);
    }

    public void setVersionId(String str) {
        if (str != null) {
            this.queryParameters.put("versionId", str);
        }
    }
}
