package io.openinstall.sdk;

/* loaded from: classes.dex */
public class bi {
    private byte[] a;
    private int b;
    private int c;

    public bi() {
        this(32);
    }

    public bi(int i) {
        this.a = new byte[i];
        this.b = 0;
        this.c = -1;
    }

    private void a(long j, int i) {
        long j2 = 1 << i;
        if (j < 0 || j > j2) {
            throw new IllegalArgumentException(j + " out of range for " + i + " bit value");
        }
    }

    private void d(int i) {
        byte[] bArr = this.a;
        int length = bArr.length;
        int i2 = this.b;
        if (length - i2 >= i) {
            return;
        }
        int length2 = bArr.length * 2;
        if (length2 < i2 + i) {
            length2 = i2 + i;
        }
        byte[] bArr2 = new byte[length2];
        System.arraycopy(bArr, 0, bArr2, 0, i2);
        this.a = bArr2;
    }

    public int a() {
        return this.b;
    }

    public void a(int i) {
        if (i > this.b) {
            throw new IllegalArgumentException("cannot jump past end of data");
        }
        this.b = i;
    }

    public void a(int i, int i2) {
        a(i, 16);
        if (i2 > this.b - 2) {
            throw new IllegalArgumentException("cannot write past end of data");
        }
        byte[] bArr = this.a;
        bArr[i2] = (byte) ((i >>> 8) & 255);
        bArr[i2 + 1] = (byte) (i & 255);
    }

    public void a(long j) {
        a(j, 32);
        d(4);
        byte[] bArr = this.a;
        int i = this.b;
        int i2 = i + 1;
        this.b = i2;
        bArr[i] = (byte) ((j >>> 24) & 255);
        int i3 = i2 + 1;
        this.b = i3;
        bArr[i2] = (byte) ((j >>> 16) & 255);
        int i4 = i3 + 1;
        this.b = i4;
        bArr[i3] = (byte) ((j >>> 8) & 255);
        this.b = i4 + 1;
        bArr[i4] = (byte) (j & 255);
    }

    public void a(byte[] bArr) {
        a(bArr, 0, bArr.length);
    }

    public void a(byte[] bArr, int i, int i2) {
        d(i2);
        System.arraycopy(bArr, i, this.a, this.b, i2);
        this.b += i2;
    }

    public void b(int i) {
        a(i, 8);
        d(1);
        byte[] bArr = this.a;
        int i2 = this.b;
        this.b = i2 + 1;
        bArr[i2] = (byte) (i & 255);
    }

    public void b(byte[] bArr) {
        if (bArr.length > 255) {
            throw new IllegalArgumentException("Invalid counted string");
        }
        d(bArr.length + 1);
        byte[] bArr2 = this.a;
        int i = this.b;
        this.b = i + 1;
        bArr2[i] = (byte) (255 & bArr.length);
        a(bArr, 0, bArr.length);
    }

    public byte[] b() {
        int i = this.b;
        byte[] bArr = new byte[i];
        System.arraycopy(this.a, 0, bArr, 0, i);
        return bArr;
    }

    public void c(int i) {
        a(i, 16);
        d(2);
        byte[] bArr = this.a;
        int i2 = this.b;
        int i3 = i2 + 1;
        this.b = i3;
        bArr[i2] = (byte) ((i >>> 8) & 255);
        this.b = i3 + 1;
        bArr[i3] = (byte) (i & 255);
    }
}
