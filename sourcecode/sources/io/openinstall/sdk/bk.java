package io.openinstall.sdk;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Random;

/* loaded from: classes.dex */
public class bk implements Cloneable {
    public static final Random a = new SecureRandom();
    private int b;
    private int c;
    private int[] d;

    public bk() {
        this(a.nextInt(65535));
    }

    bk(int i) {
        if (i >= 0 && i <= 65535) {
            this.b = i;
            this.c = 0;
            this.d = new int[4];
        } else {
            throw new IllegalArgumentException("DNS message ID " + i + " is out of range");
        }
    }

    bk(bh bhVar) throws IOException {
        this(bhVar.g());
        this.c = bhVar.g();
        int i = 0;
        while (true) {
            int[] iArr = this.d;
            if (i >= iArr.length) {
                return;
            }
            iArr[i] = bhVar.g();
            i++;
        }
    }

    static int a(int i, int i2, boolean z) {
        h(i2);
        int i3 = 1 << (15 - i2);
        return z ? i | i3 : i & (~i3);
    }

    private void a(StringBuilder sb) {
        for (int i = 0; i < 16; i++) {
            if (g(i) && b(i)) {
                sb.append(bj.a(i));
                sb.append(" ");
            }
        }
    }

    private static boolean g(int i) {
        return i >= 0 && i <= 15 && bj.b(i);
    }

    private static void h(int i) {
        if (g(i)) {
            return;
        }
        throw new IllegalArgumentException("invalid flag bit " + i);
    }

    public int a() {
        return this.b;
    }

    public void a(int i) {
        h(i);
        this.c = a(this.c, i, true);
    }

    void a(bi biVar) {
        biVar.c(a());
        biVar.c(this.c);
        for (int i : this.d) {
            biVar.c(i);
        }
    }

    public int b() {
        return this.c & 15;
    }

    public boolean b(int i) {
        h(i);
        return ((1 << (15 - i)) & this.c) != 0;
    }

    public int c() {
        return (this.c >> 11) & 15;
    }

    public void c(int i) {
        if (i >= 0 && i <= 15) {
            int i2 = this.c & 34815;
            this.c = i2;
            this.c = (i << 11) | i2;
        } else {
            throw new IllegalArgumentException("DNS Opcode " + i + "is out of range");
        }
    }

    int d() {
        return this.c;
    }

    void d(int i) {
        int[] iArr = this.d;
        if (iArr[i] == 65535) {
            throw new IllegalStateException("DNS section count cannot be incremented");
        }
        iArr[i] = iArr[i] + 1;
    }

    public int e(int i) {
        return this.d[i];
    }

    /* renamed from: e, reason: merged with bridge method [inline-methods] */
    public bk clone() throws CloneNotSupportedException {
        bk bkVar = (bk) super.clone();
        bkVar.b = this.b;
        bkVar.c = this.c;
        int[] iArr = new int[bkVar.d.length];
        bkVar.d = iArr;
        int[] iArr2 = this.d;
        System.arraycopy(iArr2, 0, iArr, 0, iArr2.length);
        return bkVar;
    }

    String f(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(";; ->>HEADER<<- ");
        sb.append("opcode: ");
        sb.append(bo.a(c()));
        sb.append(", status: ");
        sb.append(bp.a(i));
        sb.append(", id: ");
        sb.append(a());
        sb.append("\n");
        sb.append(";; flags: ");
        a(sb);
        sb.append("; ");
        for (int i2 = 0; i2 < 4; i2++) {
            sb.append(br.a(i2));
            sb.append(": ");
            sb.append(e(i2));
            sb.append(" ");
        }
        return sb.toString();
    }

    public String toString() {
        return f(b());
    }
}
