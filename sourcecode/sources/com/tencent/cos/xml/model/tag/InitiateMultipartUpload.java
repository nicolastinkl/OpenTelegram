package com.tencent.cos.xml.model.tag;

/* loaded from: classes.dex */
public class InitiateMultipartUpload {
    public String bucket;
    public String key;
    public String uploadId;

    public String toString() {
        return "{InitiateMultipartUpload:\nBucket:" + this.bucket + "\nKey:" + this.key + "\nUploadId:" + this.uploadId + "\n}";
    }
}
