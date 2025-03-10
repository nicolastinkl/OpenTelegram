package com.tencent.cos.xml.crypto;

import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.InputStream;

/* loaded from: classes.dex */
public class ReleasableInputStream extends SdkFilterInputStream {
    private boolean closeDisabled;

    protected ReleasableInputStream(InputStream inputStream) {
        super(inputStream);
    }

    @Override // com.tencent.cos.xml.crypto.SdkFilterInputStream, java.io.FilterInputStream, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        if (this.closeDisabled) {
            return;
        }
        doRelease();
    }

    @Override // com.tencent.cos.xml.crypto.SdkFilterInputStream, com.tencent.cos.xml.crypto.Releasable
    public final void release() {
        doRelease();
    }

    private void doRelease() {
        try {
            ((FilterInputStream) this).in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (((FilterInputStream) this).in instanceof Releasable) {
            ((Releasable) ((FilterInputStream) this).in).release();
        }
        abortIfNeeded();
    }

    public final boolean isCloseDisabled() {
        return this.closeDisabled;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final <T extends ReleasableInputStream> T disableClose() {
        this.closeDisabled = true;
        return this;
    }

    public static ReleasableInputStream wrap(InputStream inputStream) {
        if (inputStream instanceof ReleasableInputStream) {
            return (ReleasableInputStream) inputStream;
        }
        if (inputStream instanceof FileInputStream) {
            return ResettableInputStream.newResettableInputStream((FileInputStream) inputStream);
        }
        return new ReleasableInputStream(inputStream);
    }
}
