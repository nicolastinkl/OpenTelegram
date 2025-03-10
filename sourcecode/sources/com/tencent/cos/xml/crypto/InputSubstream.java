package com.tencent.cos.xml.crypto;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes.dex */
public final class InputSubstream extends SdkFilterInputStream {
    private static final int MAX_SKIPS = 100;
    private final boolean closeSourceStream;
    private long currentPosition;
    private long markedPosition;
    private final long requestedLength;
    private final long requestedOffset;

    public InputSubstream(InputStream inputStream, long j, long j2, boolean z) {
        super(inputStream);
        this.markedPosition = 0L;
        this.currentPosition = 0L;
        this.requestedLength = j2;
        this.requestedOffset = j;
        this.closeSourceStream = z;
    }

    @Override // com.tencent.cos.xml.crypto.SdkFilterInputStream, java.io.FilterInputStream, java.io.InputStream
    public int read() throws IOException {
        byte[] bArr = new byte[1];
        int read = read(bArr, 0, 1);
        return read == -1 ? read : bArr[0];
    }

    @Override // com.tencent.cos.xml.crypto.SdkFilterInputStream, java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        int i3 = 0;
        while (true) {
            long j = this.currentPosition;
            long j2 = this.requestedOffset;
            if (j < j2) {
                long skip = super.skip(j2 - j);
                if (skip == 0 && (i3 = i3 + 1) > 100) {
                    throw new IOException("Unable to position the currentPosition from " + this.currentPosition + " to " + this.requestedOffset);
                }
                this.currentPosition += skip;
            } else {
                long j3 = (this.requestedLength + j2) - j;
                if (j3 <= 0) {
                    return -1;
                }
                int read = super.read(bArr, i, (int) Math.min(i2, j3));
                this.currentPosition += read;
                return read;
            }
        }
    }

    @Override // com.tencent.cos.xml.crypto.SdkFilterInputStream, java.io.FilterInputStream, java.io.InputStream
    public synchronized void mark(int i) {
        this.markedPosition = this.currentPosition;
        super.mark(i);
    }

    @Override // com.tencent.cos.xml.crypto.SdkFilterInputStream, java.io.FilterInputStream, java.io.InputStream
    public synchronized void reset() throws IOException {
        this.currentPosition = this.markedPosition;
        super.reset();
    }

    @Override // com.tencent.cos.xml.crypto.SdkFilterInputStream, java.io.FilterInputStream, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.closeSourceStream) {
            super.close();
        }
    }

    @Override // com.tencent.cos.xml.crypto.SdkFilterInputStream, java.io.FilterInputStream, java.io.InputStream
    public int available() throws IOException {
        long j;
        long j2 = this.currentPosition;
        long j3 = this.requestedOffset;
        if (j2 < j3) {
            j = this.requestedLength;
        } else {
            j = (this.requestedLength + j3) - j2;
        }
        return (int) Math.min(j, super.available());
    }

    InputStream getWrappedInputStream() {
        return ((FilterInputStream) this).in;
    }
}
