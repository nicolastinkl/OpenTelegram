package org.telegram.messenger;

/* loaded from: classes3.dex */
public class SecureDocumentKey {
    public byte[] file_iv;
    public byte[] file_key;

    public SecureDocumentKey(byte[] bArr, byte[] bArr2) {
        this.file_key = bArr;
        this.file_iv = bArr2;
    }
}
