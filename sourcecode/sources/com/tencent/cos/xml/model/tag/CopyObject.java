package com.tencent.cos.xml.model.tag;

/* loaded from: classes.dex */
public class CopyObject {
    public String eTag;
    public String lastModified;

    public String toString() {
        return "{CopyObject:\nETag:" + this.eTag + "\nLastModified:" + this.lastModified + "\n}";
    }
}
