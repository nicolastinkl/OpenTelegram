package com.tencent.cos.xml.crypto;

import java.io.Serializable;
import java.security.KeyPair;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;

/* loaded from: classes.dex */
public class EncryptionMaterials implements Serializable {
    private static final long serialVersionUID = 1;
    private final Map<String, String> desc;
    private final KeyPair keyPair;
    private final SecretKey symmetricKey;

    public EncryptionMaterialsAccessor getAccessor() {
        return null;
    }

    public boolean isKMSEnabled() {
        return false;
    }

    public EncryptionMaterials(KeyPair keyPair) {
        this(keyPair, null);
    }

    public EncryptionMaterials(SecretKey secretKey) {
        this(null, secretKey);
    }

    protected EncryptionMaterials(KeyPair keyPair, SecretKey secretKey) {
        this.desc = new HashMap();
        this.keyPair = keyPair;
        this.symmetricKey = secretKey;
    }

    public KeyPair getKeyPair() {
        return this.keyPair;
    }

    public SecretKey getSymmetricKey() {
        return this.symmetricKey;
    }

    public Map<String, String> getMaterialsDescription() {
        return new HashMap(this.desc);
    }

    public EncryptionMaterials addDescription(String str, String str2) {
        this.desc.put(str, str2);
        return this;
    }

    public EncryptionMaterials addDescriptions(Map<String, String> map) {
        this.desc.putAll(map);
        return this;
    }

    public String getCustomerMasterKeyId() {
        throw new UnsupportedOperationException();
    }

    protected String getDescription(String str) {
        return this.desc.get(str);
    }
}
