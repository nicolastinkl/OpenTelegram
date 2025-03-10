package j$.util.stream;

import j$.util.r;

/* loaded from: classes2.dex */
abstract class G3 {
    static j$.util.r b(EnumC0182j4 enumC0182j4, j$.util.r rVar, long j, long j2) {
        long d = d(j, j2);
        int i = E3.a[enumC0182j4.ordinal()];
        if (i == 1) {
            return new H4(rVar, j, d);
        }
        if (i == 2) {
            return new B4((r.b) rVar, j, d);
        }
        if (i == 3) {
            return new D4((r.c) rVar, j, d);
        }
        if (i == 4) {
            return new z4((r.a) rVar, j, d);
        }
        throw new IllegalStateException("Unknown shape " + enumC0182j4);
    }

    static long c(long j, long j2, long j3) {
        if (j >= 0) {
            return Math.max(-1L, Math.min(j - j2, j3));
        }
        return -1L;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long d(long j, long j2) {
        long j3 = j2 >= 0 ? j + j2 : Long.MAX_VALUE;
        if (j3 >= 0) {
            return j3;
        }
        return Long.MAX_VALUE;
    }

    private static int e(long j) {
        return (j != -1 ? EnumC0176i4.u : 0) | EnumC0176i4.t;
    }

    public static Z f(AbstractC0135c abstractC0135c, long j, long j2) {
        if (j >= 0) {
            return new D3(abstractC0135c, EnumC0182j4.DOUBLE_VALUE, e(j2), j, j2);
        }
        throw new IllegalArgumentException("Skip must be non-negative: " + j);
    }

    public static IntStream g(AbstractC0135c abstractC0135c, long j, long j2) {
        if (j >= 0) {
            return new C0263x3(abstractC0135c, EnumC0182j4.INT_VALUE, e(j2), j, j2);
        }
        throw new IllegalArgumentException("Skip must be non-negative: " + j);
    }

    public static InterfaceC0179j1 h(AbstractC0135c abstractC0135c, long j, long j2) {
        if (j >= 0) {
            return new A3(abstractC0135c, EnumC0182j4.LONG_VALUE, e(j2), j, j2);
        }
        throw new IllegalArgumentException("Skip must be non-negative: " + j);
    }

    public static Stream i(AbstractC0135c abstractC0135c, long j, long j2) {
        if (j >= 0) {
            return new C0246u3(abstractC0135c, EnumC0182j4.REFERENCE, e(j2), j, j2);
        }
        throw new IllegalArgumentException("Skip must be non-negative: " + j);
    }
}
