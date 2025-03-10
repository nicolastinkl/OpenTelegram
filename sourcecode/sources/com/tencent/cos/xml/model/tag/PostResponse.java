package com.tencent.cos.xml.model.tag;

/* loaded from: classes.dex */
public class PostResponse {
    public String bucket;
    public String eTag;
    public String key;
    public String location;

    public String toString() {
        return "{PostResponse:\nLocation:" + this.location + "\nBucket:" + this.bucket + "\nKey:" + this.key + "\nETag:" + this.eTag + "\n}";
    }
}
