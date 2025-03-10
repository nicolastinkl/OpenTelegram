package com.tencent.cos.xml.crypto;

import java.io.Serializable;
import java.util.Map;

/* loaded from: classes.dex */
public class StaticEncryptionMaterialsProvider implements EncryptionMaterialsProvider, Serializable {
    private final EncryptionMaterials materials;

    @Override // com.tencent.cos.xml.crypto.EncryptionMaterialsProvider
    public void refresh() {
    }

    public StaticEncryptionMaterialsProvider(EncryptionMaterials encryptionMaterials) {
        this.materials = encryptionMaterials;
    }

    @Override // com.tencent.cos.xml.crypto.EncryptionMaterialsFactory
    public EncryptionMaterials getEncryptionMaterials() {
        return this.materials;
    }

    @Override // com.tencent.cos.xml.crypto.EncryptionMaterialsAccessor
    public EncryptionMaterials getEncryptionMaterials(Map<String, String> map) {
        EncryptionMaterials encryptionMaterials;
        EncryptionMaterials encryptionMaterials2 = this.materials;
        if (encryptionMaterials2 == null) {
            return null;
        }
        Map<String, String> materialsDescription = encryptionMaterials2.getMaterialsDescription();
        if (map != null && map.equals(materialsDescription)) {
            return this.materials;
        }
        EncryptionMaterialsAccessor accessor = this.materials.getAccessor();
        if (accessor != null && (encryptionMaterials = accessor.getEncryptionMaterials(map)) != null) {
            return encryptionMaterials;
        }
        boolean z = map == null || map.size() == 0;
        boolean z2 = materialsDescription == null || materialsDescription.size() == 0;
        if (z && z2) {
            return this.materials;
        }
        return null;
    }
}
