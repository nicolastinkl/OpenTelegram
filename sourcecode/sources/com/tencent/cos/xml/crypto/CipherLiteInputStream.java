package com.tencent.cos.xml.crypto;

import com.tencent.cos.xml.exception.CosXmlClientException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

/* loaded from: classes.dex */
public class CipherLiteInputStream extends SdkFilterInputStream {
    private static final int DEFAULT_IN_BUFFER_SIZE = 512;
    private static final int MAX_RETRY = 1000;
    private byte[] bufin;
    private byte[] bufout;
    private CipherLite cipherLite;
    private int curr_pos;
    private boolean eof;
    private final boolean lastMultiPart;
    private int max_pos;
    private final boolean multipart;

    public CipherLiteInputStream(InputStream inputStream, CipherLite cipherLite) {
        this(inputStream, cipherLite, 512, false, false);
    }

    public CipherLiteInputStream(InputStream inputStream, CipherLite cipherLite, int i) {
        this(inputStream, cipherLite, i, false, false);
    }

    public CipherLiteInputStream(InputStream inputStream, CipherLite cipherLite, int i, boolean z, boolean z2) {
        super(inputStream);
        this.max_pos = -1;
        if (z2 && !z) {
            throw new IllegalArgumentException("lastMultiPart can only be true if multipart is true");
        }
        this.multipart = z;
        this.lastMultiPart = z2;
        this.cipherLite = cipherLite;
        if (i <= 0 || i % 512 != 0) {
            throw new IllegalArgumentException("buffsize (" + i + ") must be a positive multiple of 512");
        }
        this.bufin = new byte[i];
    }

    protected CipherLiteInputStream(InputStream inputStream) {
        this(inputStream, CipherLite.Null, 512, false, false);
    }

    @Override // com.tencent.cos.xml.crypto.SdkFilterInputStream, java.io.FilterInputStream, java.io.InputStream
    public int read() throws IOException {
        if (this.curr_pos >= this.max_pos) {
            if (this.eof) {
                return -1;
            }
            int i = 0;
            while (i <= MAX_RETRY) {
                int nextChunk = nextChunk();
                i++;
                if (nextChunk != 0) {
                    if (nextChunk == -1) {
                        return -1;
                    }
                }
            }
            throw new IOException("exceeded maximum number of attempts to read next chunk of data");
        }
        byte[] bArr = this.bufout;
        int i2 = this.curr_pos;
        this.curr_pos = i2 + 1;
        return bArr[i2] & 255;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr) throws IOException {
        return read(bArr, 0, bArr.length);
    }

    @Override // com.tencent.cos.xml.crypto.SdkFilterInputStream, java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (this.curr_pos >= this.max_pos) {
            if (this.eof) {
                return -1;
            }
            int i3 = 0;
            while (i3 <= MAX_RETRY) {
                int nextChunk = nextChunk();
                i3++;
                if (nextChunk != 0) {
                    if (nextChunk == -1) {
                        return -1;
                    }
                }
            }
            throw new IOException("exceeded maximum number of attempts to read next chunk of data");
        }
        if (i2 <= 0) {
            return 0;
        }
        int i4 = this.max_pos;
        int i5 = this.curr_pos;
        int i6 = i4 - i5;
        if (i2 >= i6) {
            i2 = i6;
        }
        System.arraycopy(this.bufout, i5, bArr, i, i2);
        this.curr_pos += i2;
        return i2;
    }

    @Override // com.tencent.cos.xml.crypto.SdkFilterInputStream, java.io.FilterInputStream, java.io.InputStream
    public long skip(long j) throws IOException {
        abortIfNeeded();
        int i = this.max_pos;
        int i2 = this.curr_pos;
        long j2 = i - i2;
        if (j > j2) {
            j = j2;
        }
        if (j < 0) {
            return 0L;
        }
        this.curr_pos = (int) (i2 + j);
        return j;
    }

    @Override // com.tencent.cos.xml.crypto.SdkFilterInputStream, java.io.FilterInputStream, java.io.InputStream
    public int available() {
        abortIfNeeded();
        return this.max_pos - this.curr_pos;
    }

    @Override // com.tencent.cos.xml.crypto.SdkFilterInputStream, java.io.FilterInputStream, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        ((FilterInputStream) this).in.close();
        if (!this.multipart && !COSCryptoScheme.isAesGcm(this.cipherLite.getCipherAlgorithm())) {
            try {
                this.cipherLite.doFinal();
            } catch (BadPaddingException | IllegalBlockSizeException unused) {
            }
        }
        this.max_pos = 0;
        this.curr_pos = 0;
        abortIfNeeded();
    }

    @Override // com.tencent.cos.xml.crypto.SdkFilterInputStream, java.io.FilterInputStream, java.io.InputStream
    public boolean markSupported() {
        abortIfNeeded();
        return ((FilterInputStream) this).in.markSupported() && this.cipherLite.markSupported();
    }

    @Override // com.tencent.cos.xml.crypto.SdkFilterInputStream, java.io.FilterInputStream, java.io.InputStream
    public void mark(int i) {
        abortIfNeeded();
        ((FilterInputStream) this).in.mark(i);
        this.cipherLite.mark();
    }

    @Override // com.tencent.cos.xml.crypto.SdkFilterInputStream, java.io.FilterInputStream, java.io.InputStream
    public void reset() throws IOException {
        abortIfNeeded();
        ((FilterInputStream) this).in.reset();
        this.cipherLite.reset();
        resetInternal();
    }

    final void resetInternal() {
        this.max_pos = 0;
        this.curr_pos = 0;
        this.eof = false;
    }

    private int nextChunk() throws IOException {
        abortIfNeeded();
        if (this.eof) {
            return -1;
        }
        this.bufout = null;
        int read = ((FilterInputStream) this).in.read(this.bufin);
        if (read == -1) {
            this.eof = true;
            if (!this.multipart || this.lastMultiPart) {
                try {
                    byte[] doFinal = this.cipherLite.doFinal();
                    this.bufout = doFinal;
                    if (doFinal == null) {
                        return -1;
                    }
                    this.curr_pos = 0;
                    int length = doFinal.length;
                    this.max_pos = length;
                    return length;
                } catch (BadPaddingException e) {
                    if (COSCryptoScheme.isAesGcm(this.cipherLite.getCipherAlgorithm())) {
                        throw new SecurityException(e);
                    }
                } catch (IllegalBlockSizeException unused) {
                }
            }
            return -1;
        }
        byte[] update = this.cipherLite.update(this.bufin, 0, read);
        this.bufout = update;
        this.curr_pos = 0;
        int length2 = update != null ? update.length : 0;
        this.max_pos = length2;
        return length2;
    }

    void renewCipherLite() throws CosXmlClientException {
        this.cipherLite = this.cipherLite.recreate();
    }
}
