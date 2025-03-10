package io.openinstall.sdk;

import java.nio.ByteBuffer;

/* loaded from: classes.dex */
public class bh {
    private final ByteBuffer a;
    private final int b;
    private final int c;
    private int d;
    private int e;

    public bh(ByteBuffer byteBuffer) {
        this.a = byteBuffer;
        this.b = byteBuffer.position();
        this.c = byteBuffer.limit();
        this.d = -1;
        this.e = -1;
    }

    public bh(byte[] bArr) {
        this(ByteBuffer.wrap(bArr));
    }

    private void d(int i) throws cf {
        if (i > b()) {
            throw new cf("end of input");
        }
    }

    public int a() {
        return this.a.position() - this.b;
    }

    public void a(int i) {
        if (i > this.c - this.a.position()) {
            throw new IllegalArgumentException("cannot set active region past end of input");
        }
        ByteBuffer byteBuffer = this.a;
        byteBuffer.limit(byteBuffer.position() + i);
    }

    public void a(byte[] bArr, int i, int i2) throws cf {
        d(i2);
        this.a.get(bArr, i, i2);
    }

    public int b() {
        return this.a.remaining();
    }

    public void b(int i) {
        int i2 = this.b;
        if (i + i2 >= this.c) {
            throw new IllegalArgumentException("cannot jump past end of input");
        }
        this.a.position(i2 + i);
        this.a.limit(this.c);
    }

    public void c() {
        this.a.limit(this.c);
    }

    public byte[] c(int i) throws cf {
        d(i);
        byte[] bArr = new byte[i];
        this.a.get(bArr, 0, i);
        return bArr;
    }

    public void d() {
        this.d = this.a.position();
        this.e = this.a.limit();
    }

    public void e() {
        int i = this.d;
        if (i < 0) {
            throw new IllegalStateException("no previous state");
        }
        this.a.position(i);
        this.a.limit(this.e);
        this.d = -1;
        this.e = -1;
    }

    public int f() throws cf {
        d(1);
        return this.a.get() & 255;
    }

    public int g() throws cf {
        d(2);
        return this.a.getShort() & 65535;
    }

    public long h() throws cf {
        d(4);
        return this.a.getInt() & 4294967295L;
    }

    public byte[] i() {
        int b = b();
        byte[] bArr = new byte[b];
        this.a.get(bArr, 0, b);
        return bArr;
    }

    public byte[] j() throws cf {
        return c(f());
    }
}
