package com.tencent.cos.xml.crypto;

import java.util.Map;

/* loaded from: classes.dex */
final class KMSSecuredCEK extends SecuredCEK {
    static final String KEY_PROTECTION_MECHANISM = "KMS/TencentCloud";

    KMSSecuredCEK(byte[] bArr, Map<String, String> map) {
        super(bArr, KEY_PROTECTION_MECHANISM, map);
    }

    public static boolean isKMSKeyWrapped(String str) {
        return KEY_PROTECTION_MECHANISM.equals(str);
    }
}
