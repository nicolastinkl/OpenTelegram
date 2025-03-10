package com.tencent.cos.xml.crypto;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/* loaded from: classes.dex */
public class ResettableInputStream extends ReleasableInputStream {
    private final File file;
    private FileChannel fileChannel;
    private FileInputStream fis;
    private long markPos;

    @Override // com.tencent.cos.xml.crypto.SdkFilterInputStream, java.io.FilterInputStream, java.io.InputStream
    public final boolean markSupported() {
        return true;
    }

    public ResettableInputStream(File file) throws IOException {
        this(new FileInputStream(file), file);
    }

    public ResettableInputStream(String str) throws IOException {
        this(new FileInputStream(str));
    }

    public ResettableInputStream(FileInputStream fileInputStream) throws IOException {
        this(fileInputStream, null);
    }

    private ResettableInputStream(FileInputStream fileInputStream, File file) throws IOException {
        super(fileInputStream);
        this.file = file;
        this.fis = fileInputStream;
        FileChannel channel = fileInputStream.getChannel();
        this.fileChannel = channel;
        this.markPos = channel.position();
    }

    @Override // com.tencent.cos.xml.crypto.SdkFilterInputStream, java.io.FilterInputStream, java.io.InputStream
    public void mark(int i) {
        abortIfNeeded();
        try {
            this.markPos = this.fileChannel.position();
        } catch (IOException unused) {
            throw new RuntimeException("Failed to mark the file position");
        }
    }

    @Override // com.tencent.cos.xml.crypto.SdkFilterInputStream, java.io.FilterInputStream, java.io.InputStream
    public void reset() throws IOException {
        abortIfNeeded();
        this.fileChannel.position(this.markPos);
    }

    @Override // com.tencent.cos.xml.crypto.SdkFilterInputStream, java.io.FilterInputStream, java.io.InputStream
    public int available() throws IOException {
        abortIfNeeded();
        return this.fis.available();
    }

    @Override // com.tencent.cos.xml.crypto.SdkFilterInputStream, java.io.FilterInputStream, java.io.InputStream
    public int read() throws IOException {
        abortIfNeeded();
        return this.fis.read();
    }

    @Override // com.tencent.cos.xml.crypto.SdkFilterInputStream, java.io.FilterInputStream, java.io.InputStream
    public long skip(long j) throws IOException {
        abortIfNeeded();
        return this.fis.skip(j);
    }

    @Override // com.tencent.cos.xml.crypto.SdkFilterInputStream, java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        abortIfNeeded();
        return this.fis.read(bArr, i, i2);
    }

    public File getFile() {
        return this.file;
    }

    public static ResettableInputStream newResettableInputStream(File file) {
        return newResettableInputStream(file, (String) null);
    }

    public static ResettableInputStream newResettableInputStream(File file, String str) {
        try {
            return new ResettableInputStream(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ResettableInputStream newResettableInputStream(FileInputStream fileInputStream) {
        return newResettableInputStream(fileInputStream, (String) null);
    }

    public static ResettableInputStream newResettableInputStream(FileInputStream fileInputStream, String str) {
        try {
            return new ResettableInputStream(fileInputStream);
        } catch (IOException unused) {
            throw new RuntimeException(str);
        }
    }
}
