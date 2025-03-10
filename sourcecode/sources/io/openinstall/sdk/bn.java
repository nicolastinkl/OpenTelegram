package io.openinstall.sdk;

import java.io.Serializable;
import java.util.Arrays;

/* loaded from: classes.dex */
public class bn implements Serializable, Comparable<bn> {
    public static final bn a;
    public static final bn b;
    private static final byte[] g = {0};
    private static final byte[] h = {1, 42};
    private static final byte[] i = new byte[256];
    private static final bn j;
    private byte[] c;
    private long d;
    private transient int e;
    private int f;

    static {
        int i2 = 0;
        while (true) {
            byte[] bArr = i;
            if (i2 >= bArr.length) {
                bn bnVar = new bn();
                a = bnVar;
                bnVar.c = g;
                bnVar.f = 1;
                bn bnVar2 = new bn();
                b = bnVar2;
                bnVar2.c = new byte[0];
                bn bnVar3 = new bn();
                j = bnVar3;
                bnVar3.c = h;
                bnVar3.f = 1;
                return;
            }
            if (i2 < 65 || i2 > 90) {
                bArr[i2] = (byte) i2;
            } else {
                bArr[i2] = (byte) ((i2 - 65) + 97);
            }
            i2++;
        }
    }

    private bn() {
    }

    public bn(bh bhVar) throws cf {
        byte[] bArr = new byte[64];
        boolean z = false;
        boolean z2 = false;
        while (!z) {
            int f = bhVar.f();
            int i2 = f & 192;
            if (i2 != 0) {
                if (i2 != 192) {
                    throw new cf("bad label type");
                }
                int f2 = bhVar.f() + ((f & (-193)) << 8);
                if (f2 >= bhVar.a() - 2) {
                    throw new cf("bad compression");
                }
                if (!z2) {
                    bhVar.d();
                    z2 = true;
                }
                bhVar.b(f2);
            } else if (f == 0) {
                a(g, 0, 1);
                z = true;
            } else {
                bArr[0] = (byte) f;
                bhVar.a(bArr, 1, f);
                a(bArr, 0, 1);
            }
        }
        if (z2) {
            bhVar.e();
        }
    }

    public bn(bn bnVar, int i2) {
        int i3 = bnVar.f;
        if (i2 > i3) {
            throw new IllegalArgumentException("attempted to remove too many labels");
        }
        if (i2 == i3) {
            a(b, this);
            return;
        }
        this.f = i3 - i2;
        this.c = Arrays.copyOfRange(bnVar.c, bnVar.a(i2), bnVar.c.length);
        int a2 = bnVar.a(i2);
        for (int i4 = 1; i4 < 9 && i4 < this.f; i4++) {
            a(i4, bnVar.a(i4 + i2) - a2);
        }
    }

    public bn(String str, bn bnVar) throws ce {
        boolean z;
        if (str.equals("")) {
            throw new ce("empty name");
        }
        if (str.equals("@")) {
            if (bnVar == null) {
                a(b, this);
                return;
            } else {
                a(bnVar, this);
                return;
            }
        }
        if (str.equals(".")) {
            a(a, this);
            return;
        }
        char[] cArr = new char[63];
        int i2 = 0;
        boolean z2 = false;
        int i3 = -1;
        int i4 = 0;
        int i5 = 0;
        for (int i6 = 0; i6 < str.length(); i6++) {
            char charAt = str.charAt(i6);
            if (charAt > 255) {
                throw new ce(str, "Illegal character in name");
            }
            if (z2) {
                if (charAt >= '0' && charAt <= '9' && i2 < 3) {
                    i2++;
                    i5 = (i5 * 10) + (charAt - '0');
                    if (i5 > 255) {
                        throw new ce(str, "bad escape");
                    }
                    if (i2 >= 3) {
                        charAt = (char) i5;
                    }
                } else if (i2 > 0 && i2 < 3) {
                    throw new ce(str, "bad escape");
                }
                if (i4 >= 63) {
                    throw new ce(str, "label too long");
                }
                cArr[i4] = charAt;
                i3 = i4;
                z2 = false;
                i4++;
            } else if (charAt == '\\') {
                i2 = 0;
                z2 = true;
                i5 = 0;
            } else if (charAt != '.') {
                i3 = i3 == -1 ? i6 : i3;
                if (i4 >= 63) {
                    throw new ce(str, "label too long");
                }
                cArr[i4] = charAt;
                i4++;
            } else {
                if (i3 == -1) {
                    throw new ce(str, "invalid empty label");
                }
                a(str, cArr, i4);
                i3 = -1;
                i4 = 0;
            }
        }
        if ((i2 > 0 && i2 < 3) || z2) {
            throw new ce(str, "bad escape");
        }
        if (i3 == -1) {
            a(str, g, 1);
            z = true;
        } else {
            a(str, cArr, i4);
            z = false;
        }
        if (bnVar != null && !z) {
            z = bnVar.a();
            a(str, bnVar.c, bnVar.f);
        }
        if (!z && b() == 255) {
            throw new ce(str, "Name too long");
        }
    }

    private int a(int i2) {
        if (i2 == 0) {
            return 0;
        }
        if (i2 < 1 || i2 >= this.f) {
            throw new IllegalArgumentException("label out of range");
        }
        if (i2 < 9) {
            return ((int) (this.d >>> ((i2 - 1) * 8))) & 255;
        }
        int i3 = ((int) (this.d >>> 56)) & 255;
        for (int i4 = 8; i4 < i2; i4++) {
            i3 += this.c[i3] + 1;
        }
        return i3;
    }

    public static bn a(String str) throws ce {
        return a(str, (bn) null);
    }

    public static bn a(String str, bn bnVar) throws ce {
        return str.equals("@") ? bnVar != null ? bnVar : b : str.equals(".") ? a : new bn(str, bnVar);
    }

    private String a(byte[] bArr, int i2) {
        StringBuilder sb = new StringBuilder();
        int i3 = i2 + 1;
        int i4 = bArr[i2];
        for (int i5 = i3; i5 < i3 + i4; i5++) {
            int i6 = bArr[i5] & 255;
            if (i6 <= 32 || i6 >= 127) {
                sb.append('\\');
                if (i6 < 10) {
                    sb.append("00");
                } else if (i6 < 100) {
                    sb.append('0');
                }
                sb.append(i6);
            } else {
                if (i6 == 34 || i6 == 40 || i6 == 41 || i6 == 46 || i6 == 59 || i6 == 92 || i6 == 64 || i6 == 36) {
                    sb.append('\\');
                }
                sb.append((char) i6);
            }
        }
        return sb.toString();
    }

    private void a(int i2, int i3) {
        if (i2 == 0 || i2 >= 9) {
            return;
        }
        int i4 = (i2 - 1) * 8;
        long j2 = this.d & (~(255 << i4));
        this.d = j2;
        this.d = (i3 << i4) | j2;
    }

    private static void a(bn bnVar, bn bnVar2) {
        bnVar2.c = bnVar.c;
        bnVar2.d = bnVar.d;
        bnVar2.f = bnVar.f;
    }

    private void a(String str, byte[] bArr, int i2) throws ce {
        try {
            a(bArr, 0, i2);
        } catch (cc unused) {
            throw new ce(str, "Name too long");
        }
    }

    private void a(String str, char[] cArr, int i2) throws ce {
        try {
            a(cArr, i2);
        } catch (cc e) {
            throw new ce(str, "Name too long", e);
        }
    }

    private void a(byte[] bArr, int i2, int i3) throws cc {
        byte[] bArr2 = this.c;
        int length = bArr2 == null ? 0 : bArr2.length;
        int i4 = i2;
        int i5 = 0;
        for (int i6 = 0; i6 < i3; i6++) {
            int i7 = bArr[i4] + 1;
            i4 += i7;
            i5 += i7;
        }
        int i8 = length + i5;
        if (i8 > 255) {
            throw new cc();
        }
        byte[] bArr3 = this.c;
        byte[] copyOf = bArr3 != null ? Arrays.copyOf(bArr3, i8) : new byte[i8];
        System.arraycopy(bArr, i2, copyOf, length, i5);
        this.c = copyOf;
        for (int i9 = 0; i9 < i3 && i9 < 9; i9++) {
            a(this.f + i9, length);
            length += copyOf[length] + 1;
        }
        this.f += i3;
    }

    private void a(char[] cArr, int i2) throws cc {
        int b2 = b(i2);
        for (int i3 = 0; i3 < i2; i3++) {
            this.c[b2 + i3] = (byte) cArr[i3];
        }
    }

    private int b(int i2) throws cc {
        byte[] bArr = this.c;
        int length = bArr == null ? 0 : bArr.length;
        int i3 = length + 1;
        int i4 = i3 + i2;
        if (i4 > 255) {
            throw new cc();
        }
        byte[] copyOf = bArr != null ? Arrays.copyOf(bArr, i4) : new byte[i4];
        copyOf[length] = (byte) i2;
        this.c = copyOf;
        a(this.f, length);
        this.f++;
        return i3;
    }

    private boolean b(byte[] bArr, int i2) {
        int i3 = 0;
        int i4 = 0;
        while (i3 < this.f) {
            byte[] bArr2 = this.c;
            if (bArr2[i4] != bArr[i2]) {
                return false;
            }
            int i5 = i4 + 1;
            byte b2 = bArr2[i4];
            i2++;
            int i6 = 0;
            while (i6 < b2) {
                byte[] bArr3 = i;
                int i7 = i5 + 1;
                int i8 = i2 + 1;
                if (bArr3[this.c[i5] & 255] != bArr3[bArr[i2] & 255]) {
                    return false;
                }
                i6++;
                i2 = i8;
                i5 = i7;
            }
            i3++;
            i4 = i5;
        }
        return true;
    }

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(bn bnVar) {
        if (this == bnVar) {
            return 0;
        }
        int i2 = bnVar.f;
        int min = Math.min(this.f, i2);
        for (int i3 = 1; i3 <= min; i3++) {
            int a2 = a(this.f - i3);
            int a3 = bnVar.a(i2 - i3);
            byte b2 = this.c[a2];
            byte b3 = bnVar.c[a3];
            for (int i4 = 0; i4 < b2 && i4 < b3; i4++) {
                byte[] bArr = i;
                int i5 = (bArr[this.c[(i4 + a2) + 1] & 255] & 255) - (bArr[bnVar.c[(i4 + a3) + 1] & 255] & 255);
                if (i5 != 0) {
                    return i5;
                }
            }
            if (b2 != b3) {
                return b2 - b3;
            }
        }
        return this.f - i2;
    }

    public String a(boolean z) {
        int i2 = this.f;
        if (i2 == 0) {
            return "@";
        }
        int i3 = 0;
        if (i2 == 1 && this.c[0] == 0) {
            return ".";
        }
        StringBuilder sb = new StringBuilder();
        int i4 = 0;
        while (true) {
            if (i3 >= this.f) {
                break;
            }
            byte b2 = this.c[i4];
            if (b2 != 0) {
                if (i3 > 0) {
                    sb.append('.');
                }
                sb.append(a(this.c, i4));
                i4 += b2 + 1;
                i3++;
            } else if (!z) {
                sb.append('.');
            }
        }
        return sb.toString();
    }

    public void a(bi biVar) {
        biVar.a(c());
    }

    public void a(bi biVar, be beVar) {
        if (!a()) {
            throw new IllegalArgumentException("toWire() called on non-absolute name");
        }
        int i2 = 0;
        while (i2 < this.f - 1) {
            bn bnVar = i2 == 0 ? this : new bn(this, i2);
            int a2 = beVar != null ? beVar.a(bnVar) : -1;
            if (a2 >= 0) {
                biVar.c(49152 | a2);
                return;
            }
            if (beVar != null) {
                beVar.a(biVar.a(), bnVar);
            }
            int a3 = a(i2);
            byte[] bArr = this.c;
            biVar.a(bArr, a3, bArr[a3] + 1);
            i2++;
        }
        biVar.b(0);
    }

    public void a(bi biVar, be beVar, boolean z) {
        if (z) {
            a(biVar);
        } else {
            a(biVar, beVar);
        }
    }

    public boolean a() {
        int i2 = this.f;
        return i2 != 0 && this.c[a(i2 - 1)] == 0;
    }

    public short b() {
        if (this.f == 0) {
            return (short) 0;
        }
        return (short) this.c.length;
    }

    public byte[] c() {
        if (this.f == 0) {
            return new byte[0];
        }
        byte[] bArr = new byte[this.c.length];
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < this.f; i4++) {
            byte[] bArr2 = this.c;
            byte b2 = bArr2[i2];
            bArr[i3] = bArr2[i2];
            i3++;
            i2++;
            int i5 = 0;
            while (i5 < b2) {
                bArr[i3] = i[this.c[i2] & 255];
                i5++;
                i3++;
                i2++;
            }
        }
        return bArr;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof bn)) {
            return false;
        }
        bn bnVar = (bn) obj;
        if (bnVar.f == this.f && bnVar.hashCode() == hashCode()) {
            return b(bnVar.c, 0);
        }
        return false;
    }

    public int hashCode() {
        int i2 = this.e;
        if (i2 != 0) {
            return i2;
        }
        int i3 = 0;
        int a2 = a(0);
        while (true) {
            byte[] bArr = this.c;
            if (a2 >= bArr.length) {
                this.e = i3;
                return i3;
            }
            i3 += (i3 << 3) + (i[bArr[a2] & 255] & 255);
            a2++;
        }
    }

    public String toString() {
        return a(false);
    }
}
