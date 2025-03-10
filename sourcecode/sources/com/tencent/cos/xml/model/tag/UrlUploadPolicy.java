package com.tencent.cos.xml.model.tag;

/* loaded from: classes.dex */
public class UrlUploadPolicy {
    private final Type downloadType;
    private final long fileLength;

    public enum Type {
        NOTSUPPORT,
        RANGE,
        ENTIRETY
    }

    public UrlUploadPolicy(Type type, long j) {
        this.downloadType = type;
        this.fileLength = j;
    }

    public Type getDownloadType() {
        return this.downloadType;
    }

    public long getFileLength() {
        return this.fileLength;
    }
}
