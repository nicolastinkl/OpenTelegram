package io.openinstall.sdk;

import java.io.IOException;

/* loaded from: classes.dex */
public class cu extends bq {
    private int e;

    private static byte[] a(int i) {
        return new byte[]{(byte) ((i >>> 24) & 255), (byte) ((i >>> 16) & 255), (byte) ((i >>> 8) & 255), (byte) (i & 255)};
    }

    private static int b(byte[] bArr) {
        return (bArr[3] & 255) | ((bArr[0] & 255) << 24) | ((bArr[1] & 255) << 16) | ((bArr[2] & 255) << 8);
    }

    @Override // io.openinstall.sdk.bq
    protected void a(bh bhVar) throws IOException {
        this.e = b(bhVar.c(4));
    }

    @Override // io.openinstall.sdk.bq
    protected void a(bi biVar, be beVar, boolean z) {
        biVar.a(this.e & 4294967295L);
    }

    @Override // io.openinstall.sdk.bq
    protected String b() {
        return bd.a(a(this.e));
    }
}
