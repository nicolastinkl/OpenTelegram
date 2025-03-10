package com.tencent.cos.xml.crypto;

import com.tencent.cos.xml.exception.CosXmlClientException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class MultipartUploadCryptoContext {
    private final String bucketName;
    private final ContentCryptoMaterial cekMaterial;
    private boolean hasFinalPartBeenSeen;
    private final String key;
    private Map<String, String> materialsDescription;
    private int partNumber;
    private volatile boolean partUploadInProgress;

    protected MultipartUploadCryptoContext(String str, String str2, ContentCryptoMaterial contentCryptoMaterial) {
        this.bucketName = str;
        this.key = str2;
        this.cekMaterial = contentCryptoMaterial;
    }

    public final String getBucketName() {
        return this.bucketName;
    }

    public final String getKey() {
        return this.key;
    }

    public final boolean hasFinalPartBeenSeen() {
        return this.hasFinalPartBeenSeen;
    }

    public final void setHasFinalPartBeenSeen(boolean z) {
        this.hasFinalPartBeenSeen = z;
    }

    public final Map<String, String> getMaterialsDescription() {
        return this.materialsDescription;
    }

    public final void setMaterialsDescription(Map<String, String> map) {
        this.materialsDescription = map == null ? null : Collections.unmodifiableMap(new HashMap(map));
    }

    public CipherLite getCipherLite() {
        return this.cekMaterial.getCipherLite();
    }

    ContentCryptoMaterial getContentCryptoMaterial() {
        return this.cekMaterial;
    }

    void beginPartUpload(int i) throws CosXmlClientException {
        if (i < 1) {
            throw new IllegalArgumentException("part number must be at least 1");
        }
        if (this.partUploadInProgress) {
            throw CosXmlClientException.internalException("Parts are required to be uploaded in series");
        }
        synchronized (this) {
            if (this.partUploadInProgress) {
                throw CosXmlClientException.internalException("Parts are required to be uploaded in series");
            }
            if (i - this.partNumber <= 1) {
                this.partNumber = i;
                this.partUploadInProgress = true;
            } else {
                throw CosXmlClientException.internalException("Parts are required to be uploaded in series (partNumber=" + this.partNumber + ", nextPartNumber=" + i + ")");
            }
        }
    }

    void endPartUpload() {
        this.partUploadInProgress = false;
    }
}
