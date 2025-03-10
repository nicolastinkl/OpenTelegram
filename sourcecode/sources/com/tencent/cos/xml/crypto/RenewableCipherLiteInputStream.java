package com.tencent.cos.xml.crypto;

import com.tencent.cos.xml.exception.CosXmlClientException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes.dex */
public final class RenewableCipherLiteInputStream extends CipherLiteInputStream {
    private boolean hasBeenAccessed;

    public RenewableCipherLiteInputStream(InputStream inputStream, CipherLite cipherLite) {
        super(inputStream, cipherLite);
    }

    public RenewableCipherLiteInputStream(InputStream inputStream, CipherLite cipherLite, int i) {
        super(inputStream, cipherLite, i);
    }

    public RenewableCipherLiteInputStream(InputStream inputStream, CipherLite cipherLite, int i, boolean z, boolean z2) {
        super(inputStream, cipherLite, i, z, z2);
    }

    protected RenewableCipherLiteInputStream(InputStream inputStream) {
        super(inputStream);
    }

    @Override // com.tencent.cos.xml.crypto.CipherLiteInputStream, com.tencent.cos.xml.crypto.SdkFilterInputStream, java.io.FilterInputStream, java.io.InputStream
    public boolean markSupported() {
        abortIfNeeded();
        return ((FilterInputStream) this).in.markSupported();
    }

    @Override // com.tencent.cos.xml.crypto.CipherLiteInputStream, com.tencent.cos.xml.crypto.SdkFilterInputStream, java.io.FilterInputStream, java.io.InputStream
    public void mark(int i) {
        abortIfNeeded();
        if (this.hasBeenAccessed) {
            throw new UnsupportedOperationException("Marking is only supported before your first call to read or skip.");
        }
        ((FilterInputStream) this).in.mark(i);
    }

    @Override // com.tencent.cos.xml.crypto.CipherLiteInputStream, com.tencent.cos.xml.crypto.SdkFilterInputStream, java.io.FilterInputStream, java.io.InputStream
    public void reset() throws IOException {
        abortIfNeeded();
        ((FilterInputStream) this).in.reset();
        try {
            renewCipherLite();
            resetInternal();
            this.hasBeenAccessed = false;
        } catch (CosXmlClientException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
    }

    @Override // com.tencent.cos.xml.crypto.CipherLiteInputStream, com.tencent.cos.xml.crypto.SdkFilterInputStream, java.io.FilterInputStream, java.io.InputStream
    public int read() throws IOException {
        this.hasBeenAccessed = true;
        return super.read();
    }

    @Override // com.tencent.cos.xml.crypto.CipherLiteInputStream, java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr) throws IOException {
        this.hasBeenAccessed = true;
        return super.read(bArr);
    }

    @Override // com.tencent.cos.xml.crypto.CipherLiteInputStream, com.tencent.cos.xml.crypto.SdkFilterInputStream, java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        this.hasBeenAccessed = true;
        return super.read(bArr, i, i2);
    }

    @Override // com.tencent.cos.xml.crypto.CipherLiteInputStream, com.tencent.cos.xml.crypto.SdkFilterInputStream, java.io.FilterInputStream, java.io.InputStream
    public long skip(long j) throws IOException {
        this.hasBeenAccessed = true;
        return super.skip(j);
    }
}
