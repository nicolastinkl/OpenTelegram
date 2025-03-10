package com.tencent.cos.xml.model.bucket;

import com.tencent.cos.xml.common.RequestMethod;
import com.tencent.qcloud.core.http.RequestBodySerializer;
import java.util.Map;

/* loaded from: classes.dex */
public final class ListMultiUploadsRequest extends BucketRequest {
    private String delimiter;
    private String encodingType;
    private String keyMarker;
    private String maxUploads;
    private String prefix;
    private String uploadIdMarker;

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public String getMethod() {
        return RequestMethod.GET;
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public RequestBodySerializer getRequestBody() {
        return null;
    }

    public ListMultiUploadsRequest(String str) {
        super(str);
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public Map<String, String> getQueryString() {
        this.queryParameters.put("uploads", null);
        String str = this.delimiter;
        if (str != null) {
            this.queryParameters.put("delimiter", str);
        }
        String str2 = this.encodingType;
        if (str2 != null) {
            this.queryParameters.put("encoding-type", str2);
        }
        String str3 = this.prefix;
        if (str3 != null) {
            this.queryParameters.put("prefix", str3);
        }
        String str4 = this.maxUploads;
        if (str4 != null) {
            this.queryParameters.put("max-uploads", str4);
        }
        String str5 = this.keyMarker;
        if (str5 != null) {
            this.queryParameters.put("key-marker", str5);
        }
        String str6 = this.uploadIdMarker;
        if (str6 != null) {
            this.queryParameters.put("upload-id-marker", str6);
        }
        return super.getQueryString();
    }

    public void setDelimiter(String str) {
        this.delimiter = str;
    }

    public String getDelimiter() {
        return this.delimiter;
    }

    public void setEncodingType(String str) {
        this.encodingType = str;
    }

    public String getEncodingType() {
        return this.encodingType;
    }

    public void setKeyMarker(String str) {
        this.keyMarker = str;
    }

    public String getKeyMarker() {
        return this.keyMarker;
    }

    public void setMaxUploads(String str) {
        this.maxUploads = str;
    }

    public String getMaxUploads() {
        return this.maxUploads;
    }

    public void setPrefix(String str) {
        this.prefix = str;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void setUploadIdMarker(String str) {
        this.uploadIdMarker = str;
    }

    public String getUploadIdMarker() {
        return this.uploadIdMarker;
    }
}
