package com.tencent.cos.xml.crypto;

/* loaded from: classes.dex */
public class KMSEncryptionMaterialsProvider extends StaticEncryptionMaterialsProvider {
    public KMSEncryptionMaterialsProvider(String str) {
        this(new KMSEncryptionMaterials(str));
    }

    public KMSEncryptionMaterialsProvider(KMSEncryptionMaterials kMSEncryptionMaterials) {
        super(kMSEncryptionMaterials);
    }
}
