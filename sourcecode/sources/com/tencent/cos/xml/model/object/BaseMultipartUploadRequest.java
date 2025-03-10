package com.tencent.cos.xml.model.object;

import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.qcloud.core.auth.STSCredentialScope;

/* loaded from: classes.dex */
public abstract class BaseMultipartUploadRequest extends UploadRequest {
    BaseMultipartUploadRequest(String str, String str2) {
        super(str, str2);
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public STSCredentialScope[] getSTSCredentialScope(CosXmlServiceConfig cosXmlServiceConfig) {
        String[] strArr = {"name/cos:InitiateMultipartUpload", "name/cos:ListParts", "name/cos:UploadPart", "name/cos:CompleteMultipartUpload", "name/cos:AbortMultipartUpload"};
        STSCredentialScope[] sTSCredentialScopeArr = new STSCredentialScope[5];
        int i = 0;
        int i2 = 0;
        while (i < 5) {
            sTSCredentialScopeArr[i2] = new STSCredentialScope(strArr[i], cosXmlServiceConfig.getBucket(this.bucket), cosXmlServiceConfig.getRegion(), getPath(cosXmlServiceConfig));
            i++;
            i2++;
        }
        return sTSCredentialScopeArr;
    }
}
