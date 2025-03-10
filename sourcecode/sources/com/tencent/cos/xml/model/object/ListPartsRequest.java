package com.tencent.cos.xml.model.object;

import com.tencent.cos.xml.common.ClientErrorCode;
import com.tencent.cos.xml.common.RequestMethod;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.qcloud.core.http.RequestBodySerializer;
import java.util.Map;

/* loaded from: classes.dex */
public final class ListPartsRequest extends BaseMultipartUploadRequest {
    private String encodingType;
    private String maxParts;
    private String partNumberMarker;
    private String uploadId;

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public String getMethod() {
        return RequestMethod.GET;
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public int getPriority() {
        return 3;
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public RequestBodySerializer getRequestBody() {
        return null;
    }

    public ListPartsRequest(String str, String str2, String str3) {
        super(str, str2);
        this.uploadId = str3;
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public Map<String, String> getQueryString() {
        String str = this.uploadId;
        if (str != null) {
            this.queryParameters.put("uploadId", str);
        }
        String str2 = this.maxParts;
        if (str2 != null) {
            this.queryParameters.put("max-parts", str2);
        }
        if (this.partNumberMarker != null) {
            this.queryParameters.put("part-number-marker", this.maxParts);
        }
        String str3 = this.encodingType;
        if (str3 != null) {
            this.queryParameters.put("Encoding-type", str3);
        }
        return this.queryParameters;
    }

    @Override // com.tencent.cos.xml.model.object.ObjectRequest, com.tencent.cos.xml.model.CosXmlRequest
    public void checkParameters() throws CosXmlClientException {
        super.checkParameters();
        if (this.requestURL == null && this.uploadId == null) {
            throw new CosXmlClientException(ClientErrorCode.INVALID_ARGUMENT.getCode(), "uploadID must not be null");
        }
    }

    public void setUploadId(String str) {
        this.uploadId = str;
    }

    public String getUploadId() {
        return this.uploadId;
    }

    public void setMaxParts(int i) {
        if (i <= 0) {
            i = 1;
        }
        this.maxParts = String.valueOf(i);
    }

    public int getMaxParts() {
        return Integer.parseInt(this.maxParts);
    }

    public void setPartNumberMarker(int i) {
        this.partNumberMarker = String.valueOf(i);
    }

    public int getPartNumberMarker() {
        return Integer.parseInt(this.partNumberMarker);
    }

    public void setEncodingType(String str) {
        this.encodingType = str;
    }

    public String getEncodingType() {
        return this.encodingType;
    }
}
