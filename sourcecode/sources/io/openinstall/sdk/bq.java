package io.openinstall.sdk;

import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Arrays;

/* loaded from: classes.dex */
public abstract class bq implements Serializable, Cloneable, Comparable<bq> {
    private static final DecimalFormat e;
    protected bn a;
    protected int b;
    protected int c;
    protected long d;

    static {
        DecimalFormat decimalFormat = new DecimalFormat();
        e = decimalFormat;
        decimalFormat.setMinimumIntegerDigits(3);
    }

    protected bq() {
    }

    static bq a(bh bhVar, int i, boolean z) throws IOException {
        bn bnVar = new bn(bhVar);
        int g = bhVar.g();
        int g2 = bhVar.g();
        if (i == 0) {
            return a(bnVar, g, g2);
        }
        long h = bhVar.h();
        int g3 = bhVar.g();
        return (g3 == 0 && z && (i == 1 || i == 2)) ? a(bnVar, g, g2, h) : a(bnVar, g, g2, h, g3, bhVar);
    }

    public static bq a(bn bnVar, int i, int i2) {
        return a(bnVar, i, i2, 0L);
    }

    public static bq a(bn bnVar, int i, int i2, long j) {
        if (!bnVar.a()) {
            throw new cd(bnVar);
        }
        bv.a(i);
        bg.a(i2);
        bu.a(j);
        return a(bnVar, i, i2, j, false);
    }

    private static bq a(bn bnVar, int i, int i2, long j, int i3, bh bhVar) throws IOException {
        bq a = a(bnVar, i, i2, j, bhVar != null);
        if (bhVar != null) {
            if (bhVar.b() < i3) {
                throw new cf("truncated record");
            }
            bhVar.a(i3);
            a.a(bhVar);
            if (bhVar.b() > 0) {
                throw new cf("invalid record length");
            }
            bhVar.c();
        }
        return a;
    }

    private static bq a(bn bnVar, int i, int i2, long j, boolean z) {
        bq cwVar;
        if (z) {
            dc<bq> c = bv.c(i);
            cwVar = c != null ? c.b() : new db();
        } else {
            cwVar = new cw();
        }
        cwVar.a = bnVar;
        cwVar.b = i;
        cwVar.c = i2;
        cwVar.d = j;
        return cwVar;
    }

    protected static String a(byte[] bArr) {
        return "\\# " + bArr.length + " " + dd.a(bArr);
    }

    protected static String a(byte[] bArr, boolean z) {
        StringBuilder sb = new StringBuilder();
        if (z) {
            sb.append('\"');
        }
        for (byte b : bArr) {
            int i = b & 255;
            if (i < 32 || i >= 127) {
                sb.append('\\');
                sb.append(e.format(i));
            } else {
                if (i == 34 || i == 92) {
                    sb.append('\\');
                }
                sb.append((char) i);
            }
        }
        if (z) {
            sb.append('\"');
        }
        return sb.toString();
    }

    private void a(bi biVar, boolean z) {
        this.a.a(biVar);
        biVar.c(this.b);
        biVar.c(this.c);
        biVar.a(z ? 0L : this.d);
        int a = biVar.a();
        biVar.c(0);
        a(biVar, (be) null, true);
        biVar.a((biVar.a() - a) - 2, a);
    }

    private byte[] a(boolean z) {
        bi biVar = new bi();
        a(biVar, z);
        return biVar.b();
    }

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(bq bqVar) {
        if (this == bqVar) {
            return 0;
        }
        int compareTo = this.a.compareTo(bqVar.a);
        if (compareTo != 0) {
            return compareTo;
        }
        int i = this.c - bqVar.c;
        if (i != 0) {
            return i;
        }
        int i2 = this.b - bqVar.b;
        if (i2 != 0) {
            return i2;
        }
        byte[] a = a();
        byte[] a2 = bqVar.a();
        int min = Math.min(a.length, a2.length);
        for (int i3 = 0; i3 < min; i3++) {
            if (a[i3] != a2[i3]) {
                return (a[i3] & 255) - (a2[i3] & 255);
            }
        }
        return a.length - a2.length;
    }

    protected abstract void a(bh bhVar) throws IOException;

    void a(bi biVar, int i, be beVar) {
        this.a.a(biVar, beVar);
        biVar.c(this.b);
        biVar.c(this.c);
        if (i == 0) {
            return;
        }
        biVar.a(this.d);
        int a = biVar.a();
        biVar.c(0);
        a(biVar, beVar, false);
        biVar.a((biVar.a() - a) - 2, a);
    }

    protected abstract void a(bi biVar, be beVar, boolean z);

    public byte[] a() {
        bi biVar = new bi();
        a(biVar, (be) null, true);
        return biVar.b();
    }

    protected abstract String b();

    public String c() {
        return b();
    }

    public bn d() {
        return this.a;
    }

    public int e() {
        return this.b;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof bq)) {
            return false;
        }
        bq bqVar = (bq) obj;
        if (this.b == bqVar.b && this.c == bqVar.c && this.a.equals(bqVar.a)) {
            return Arrays.equals(a(), bqVar.a());
        }
        return false;
    }

    public int f() {
        return this.c;
    }

    public int hashCode() {
        int i = 0;
        for (byte b : a(true)) {
            i += (i << 3) + (b & 255);
        }
        return i;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.a);
        if (sb.length() < 8) {
            sb.append("\t");
        }
        if (sb.length() < 16) {
            sb.append("\t");
        }
        sb.append("\t");
        sb.append(this.d);
        sb.append("\t");
        int i = this.c;
        if (i != 1) {
            sb.append(bg.b(i));
            sb.append("\t");
        }
        sb.append(bv.b(this.b));
        String b = b();
        if (!b.isEmpty()) {
            sb.append("\t");
            sb.append(b);
        }
        return sb.toString();
    }
}
