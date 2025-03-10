package com.tencent.cos.xml.model.object;

import com.tencent.cos.xml.common.RequestMethod;
import com.tencent.qcloud.core.http.RequestBodySerializer;

/* loaded from: classes.dex */
public final class HeadObjectRequest extends ObjectRequest {
    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public String getMethod() {
        return RequestMethod.HEAD;
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public RequestBodySerializer getRequestBody() {
        return null;
    }

    public HeadObjectRequest(String str, String str2) {
        super(str, str2);
    }

    public void setVersionId(String str) {
        if (str != null) {
            this.queryParameters.put("versionId", str);
        }
    }

    public void setIfModifiedSince(String str) {
        if (str != null) {
            addHeader("If-Modified-Since", str);
        }
    }
}
