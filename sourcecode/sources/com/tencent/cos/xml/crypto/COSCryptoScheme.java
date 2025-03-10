package com.tencent.cos.xml.crypto;

import java.security.SecureRandom;

/* loaded from: classes.dex */
final class COSCryptoScheme {
    static final String AES = "AES";
    static final String RSA = "RSA";
    private static final SecureRandom srand = new SecureRandom();
    private final ContentCryptoScheme contentCryptoScheme;
    private final COSKeyWrapScheme kwScheme;

    private COSCryptoScheme(ContentCryptoScheme contentCryptoScheme, COSKeyWrapScheme cOSKeyWrapScheme) {
        this.contentCryptoScheme = contentCryptoScheme;
        this.kwScheme = cOSKeyWrapScheme;
    }

    SecureRandom getSecureRandom() {
        return srand;
    }

    ContentCryptoScheme getContentCryptoScheme() {
        return this.contentCryptoScheme;
    }

    COSKeyWrapScheme getKeyWrapScheme() {
        return this.kwScheme;
    }

    static boolean isAesGcm(String str) {
        return ContentCryptoScheme.AES_GCM.getCipherAlgorithm().equals(str);
    }

    static COSCryptoScheme from() {
        return new COSCryptoScheme(ContentCryptoScheme.AES_CTR, new COSKeyWrapScheme());
    }
}
