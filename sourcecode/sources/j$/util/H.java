package j$.util;

import j$.util.n;
import j$.util.r;
import java.util.Objects;

/* loaded from: classes2.dex */
public abstract class H {
    private static final r a = new C();
    private static final r.b b = new A();
    private static final r.c c = new B();
    private static final r.a d = new z();

    private static void a(int i, int i2, int i3) {
        if (i2 <= i3) {
            if (i2 < 0) {
                throw new ArrayIndexOutOfBoundsException(i2);
            }
            if (i3 > i) {
                throw new ArrayIndexOutOfBoundsException(i3);
            }
            return;
        }
        throw new ArrayIndexOutOfBoundsException("origin(" + i2 + ") > fence(" + i3 + ")");
    }

    public static r.a b() {
        return d;
    }

    public static r.b c() {
        return b;
    }

    public static r.c d() {
        return c;
    }

    public static r e() {
        return a;
    }

    public static l f(r.a aVar) {
        Objects.requireNonNull(aVar);
        return new w(aVar);
    }

    public static n.a g(r.b bVar) {
        Objects.requireNonNull(bVar);
        return new u(bVar);
    }

    public static p h(r.c cVar) {
        Objects.requireNonNull(cVar);
        return new v(cVar);
    }

    public static java.util.Iterator i(r rVar) {
        Objects.requireNonNull(rVar);
        return new t(rVar);
    }

    public static r.a j(double[] dArr, int i, int i2, int i3) {
        Objects.requireNonNull(dArr);
        a(dArr.length, i, i2);
        return new y(dArr, i, i2, i3);
    }

    public static r.b k(int[] iArr, int i, int i2, int i3) {
        Objects.requireNonNull(iArr);
        a(iArr.length, i, i2);
        return new E(iArr, i, i2, i3);
    }

    public static r.c l(long[] jArr, int i, int i2, int i3) {
        Objects.requireNonNull(jArr);
        a(jArr.length, i, i2);
        return new G(jArr, i, i2, i3);
    }

    public static r m(Object[] objArr, int i, int i2, int i3) {
        Objects.requireNonNull(objArr);
        a(objArr.length, i, i2);
        return new x(objArr, i, i2, i3);
    }
}
