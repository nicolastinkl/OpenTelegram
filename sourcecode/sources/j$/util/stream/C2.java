package j$.util.stream;

import org.telegram.messenger.LiteMode;

/* loaded from: classes2.dex */
abstract class C2 {
    private static final F1 a = new C0150e2(null);
    private static final B1 b = new C0138c2();
    private static final D1 c = new C0144d2();
    private static final InterfaceC0271z1 d = new C0132b2();
    private static final int[] e = new int[0];
    private static final long[] f = new long[0];
    private static final double[] g = new double[0];

    static InterfaceC0261x1 d(long j, j$.util.function.n nVar) {
        return (j < 0 || j >= 2147483639) ? new C0267y2() : new C0162g2(j, nVar);
    }

    public static F1 e(D2 d2, j$.util.r rVar, boolean z, j$.util.function.n nVar) {
        long n0 = d2.n0(rVar);
        if (n0 < 0 || !rVar.hasCharacteristics(LiteMode.FLAG_ANIMATED_EMOJI_KEYBOARD_NOT_PREMIUM)) {
            F1 f1 = (F1) new M1(d2, nVar, rVar).invoke();
            return z ? l(f1, nVar) : f1;
        }
        if (n0 >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        Object[] objArr = (Object[]) nVar.apply((int) n0);
        new C0257w2(rVar, d2, objArr).invoke();
        return new I1(objArr);
    }

    public static InterfaceC0271z1 f(D2 d2, j$.util.r rVar, boolean z) {
        long n0 = d2.n0(rVar);
        if (n0 < 0 || !rVar.hasCharacteristics(LiteMode.FLAG_ANIMATED_EMOJI_KEYBOARD_NOT_PREMIUM)) {
            InterfaceC0271z1 interfaceC0271z1 = (InterfaceC0271z1) new M1(d2, rVar, 0).invoke();
            return z ? m(interfaceC0271z1) : interfaceC0271z1;
        }
        if (n0 >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        double[] dArr = new double[(int) n0];
        new C0239t2(rVar, d2, dArr).invoke();
        return new Y1(dArr);
    }

    public static B1 g(D2 d2, j$.util.r rVar, boolean z) {
        long n0 = d2.n0(rVar);
        if (n0 < 0 || !rVar.hasCharacteristics(LiteMode.FLAG_ANIMATED_EMOJI_KEYBOARD_NOT_PREMIUM)) {
            B1 b1 = (B1) new M1(d2, rVar, 1).invoke();
            return z ? n(b1) : b1;
        }
        if (n0 >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        int[] iArr = new int[(int) n0];
        new C0245u2(rVar, d2, iArr).invoke();
        return new C0168h2(iArr);
    }

    public static D1 h(D2 d2, j$.util.r rVar, boolean z) {
        long n0 = d2.n0(rVar);
        if (n0 < 0 || !rVar.hasCharacteristics(LiteMode.FLAG_ANIMATED_EMOJI_KEYBOARD_NOT_PREMIUM)) {
            D1 d1 = (D1) new M1(d2, rVar, 2).invoke();
            return z ? o(d1) : d1;
        }
        if (n0 >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        long[] jArr = new long[(int) n0];
        new C0251v2(rVar, d2, jArr).invoke();
        return new C0222q2(jArr);
    }

    static F1 i(EnumC0182j4 enumC0182j4, F1 f1, F1 f12) {
        int i = G1.a[enumC0182j4.ordinal()];
        if (i == 1) {
            return new X1(f1, f12);
        }
        if (i == 2) {
            return new U1((B1) f1, (B1) f12);
        }
        if (i == 3) {
            return new V1((D1) f1, (D1) f12);
        }
        if (i == 4) {
            return new T1((InterfaceC0271z1) f1, (InterfaceC0271z1) f12);
        }
        throw new IllegalStateException("Unknown shape " + enumC0182j4);
    }

    static InterfaceC0244u1 j(long j) {
        return (j < 0 || j >= 2147483639) ? new C0126a2() : new Z1(j);
    }

    static F1 k(EnumC0182j4 enumC0182j4) {
        int i = G1.a[enumC0182j4.ordinal()];
        if (i == 1) {
            return a;
        }
        if (i == 2) {
            return b;
        }
        if (i == 3) {
            return c;
        }
        if (i == 4) {
            return d;
        }
        throw new IllegalStateException("Unknown shape " + enumC0182j4);
    }

    public static F1 l(F1 f1, j$.util.function.n nVar) {
        if (f1.p() <= 0) {
            return f1;
        }
        long count = f1.count();
        if (count >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        Object[] objArr = (Object[]) nVar.apply((int) count);
        new A2(f1, objArr, 0, (G1) null).invoke();
        return new I1(objArr);
    }

    public static InterfaceC0271z1 m(InterfaceC0271z1 interfaceC0271z1) {
        if (interfaceC0271z1.p() <= 0) {
            return interfaceC0271z1;
        }
        long count = interfaceC0271z1.count();
        if (count >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        double[] dArr = new double[(int) count];
        new C0272z2(interfaceC0271z1, dArr, 0).invoke();
        return new Y1(dArr);
    }

    public static B1 n(B1 b1) {
        if (b1.p() <= 0) {
            return b1;
        }
        long count = b1.count();
        if (count >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        int[] iArr = new int[(int) count];
        new C0272z2(b1, iArr, 0).invoke();
        return new C0168h2(iArr);
    }

    public static D1 o(D1 d1) {
        if (d1.p() <= 0) {
            return d1;
        }
        long count = d1.count();
        if (count >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        long[] jArr = new long[(int) count];
        new C0272z2(d1, jArr, 0).invoke();
        return new C0222q2(jArr);
    }

    static InterfaceC0250v1 p(long j) {
        return (j < 0 || j >= 2147483639) ? new C0180j2() : new C0174i2(j);
    }

    static InterfaceC0256w1 q(long j) {
        return (j < 0 || j >= 2147483639) ? new C0233s2() : new C0227r2(j);
    }
}
