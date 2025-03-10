package com.tencent.cos.xml.model.object;

import com.tencent.cos.xml.common.COSACL;
import com.tencent.cos.xml.common.COSRequestHeaderKey;
import com.tencent.cos.xml.common.COSStorageClass;
import com.tencent.cos.xml.common.RequestMethod;
import com.tencent.cos.xml.crypto.ObjectMetadata;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.model.tag.ACLAccount;
import com.tencent.qcloud.core.http.RequestBodySerializer;
import java.util.Map;

/* loaded from: classes.dex */
public final class InitMultipartUploadRequest extends BaseMultipartUploadRequest {
    private ObjectMetadata metadata;

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public String getMethod() {
        return RequestMethod.POST;
    }

    public InitMultipartUploadRequest(String str, String str2) {
        super(str, str2);
    }

    public void setCacheControl(String str) {
        if (str == null) {
            return;
        }
        addHeader("Cache-Control", str);
    }

    public void setContentDisposition(String str) {
        if (str == null) {
            return;
        }
        addHeader("Content-Disposition", str);
    }

    public void setContentEncoding(String str) {
        if (str == null) {
            return;
        }
        addHeader("Content-Encoding", str);
    }

    @Override // com.tencent.cos.xml.model.object.ObjectRequest, com.tencent.cos.xml.model.CosXmlRequest
    public void checkParameters() throws CosXmlClientException {
        super.checkParameters();
        ObjectMetadata objectMetadata = this.metadata;
        if (objectMetadata != null) {
            Map<String, Object> rawMetadata = objectMetadata.getRawMetadata();
            Map<String, String> userMetadata = this.metadata.getUserMetadata();
            for (Map.Entry<String, Object> entry : rawMetadata.entrySet()) {
                addHeader(entry.getKey(), entry.getValue().toString());
            }
            for (Map.Entry<String, String> entry2 : userMetadata.entrySet()) {
                addHeader(entry2.getKey(), entry2.getValue());
            }
        }
    }

    public void setExpires(String str) {
        if (str == null) {
            return;
        }
        addHeader("Expires", str);
    }

    public void setXCOSMeta(String str, String str2) {
        if (str == null || str2 == null) {
            return;
        }
        addHeader(str, str2);
    }

    public void setXCOSACL(String str) {
        if (str != null) {
            addHeader("x-cos-acl", str);
        }
    }

    public void setMetadata(ObjectMetadata objectMetadata) {
        this.metadata = objectMetadata;
    }

    public ObjectMetadata getMetadata() {
        return this.metadata;
    }

    public void setXCOSACL(COSACL cosacl) {
        if (cosacl != null) {
            addHeader("x-cos-acl", cosacl.getAcl());
        }
    }

    public void setXCOSGrantRead(ACLAccount aCLAccount) {
        if (aCLAccount != null) {
            addHeader(COSRequestHeaderKey.X_COS_GRANT_READ, aCLAccount.getAccount());
        }
    }

    public void setXCOSGrantWrite(ACLAccount aCLAccount) {
        if (aCLAccount != null) {
            addHeader(COSRequestHeaderKey.X_COS_GRANT_WRITE, aCLAccount.getAccount());
        }
    }

    public void setXCOSReadWrite(ACLAccount aCLAccount) {
        if (aCLAccount != null) {
            addHeader(COSRequestHeaderKey.X_COS_GRANT_FULL_CONTROL, aCLAccount.getAccount());
        }
    }

    public void setStroageClass(COSStorageClass cOSStorageClass) {
        addHeader("x-cos-storage-class", cOSStorageClass.getStorageClass());
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public Map<String, String> getQueryString() {
        this.queryParameters.put("uploads", null);
        return this.queryParameters;
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public RequestBodySerializer getRequestBody() {
        return RequestBodySerializer.bytes(getContentType(), new byte[0]);
    }
}
