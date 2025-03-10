package j$.util.stream;

import j$.util.function.Consumer;
import j$.util.function.Predicate;
import j$.util.r;
import j$.wrappers.C0287g0;
import java.util.Objects;

/* renamed from: j$.util.stream.t1, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public abstract /* synthetic */ class AbstractC0238t1 {
    public static void a(InterfaceC0211o3 interfaceC0211o3, Double d) {
        if (V4.a) {
            V4.a(interfaceC0211o3.getClass(), "{0} calling Sink.OfDouble.accept(Double)");
            throw null;
        }
        interfaceC0211o3.accept(d.doubleValue());
    }

    public static void b(InterfaceC0217p3 interfaceC0217p3, Integer num) {
        if (V4.a) {
            V4.a(interfaceC0217p3.getClass(), "{0} calling Sink.OfInt.accept(Integer)");
            throw null;
        }
        interfaceC0217p3.accept(num.intValue());
    }

    public static void c(InterfaceC0223q3 interfaceC0223q3, Long l) {
        if (V4.a) {
            V4.a(interfaceC0223q3.getClass(), "{0} calling Sink.OfLong.accept(Long)");
            throw null;
        }
        interfaceC0223q3.accept(l.longValue());
    }

    public static void d(InterfaceC0228r3 interfaceC0228r3) {
        throw new IllegalStateException("called wrong accept method");
    }

    public static void e(InterfaceC0228r3 interfaceC0228r3) {
        throw new IllegalStateException("called wrong accept method");
    }

    public static void f(InterfaceC0228r3 interfaceC0228r3) {
        throw new IllegalStateException("called wrong accept method");
    }

    public static Object[] g(E1 e1, j$.util.function.n nVar) {
        if (V4.a) {
            V4.a(e1.getClass(), "{0} calling Node.OfPrimitive.asArray");
            throw null;
        }
        if (e1.count() >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        Object[] objArr = (Object[]) nVar.apply((int) e1.count());
        e1.i(objArr, 0);
        return objArr;
    }

    public static void h(InterfaceC0271z1 interfaceC0271z1, Double[] dArr, int i) {
        if (V4.a) {
            V4.a(interfaceC0271z1.getClass(), "{0} calling Node.OfDouble.copyInto(Double[], int)");
            throw null;
        }
        double[] dArr2 = (double[]) interfaceC0271z1.e();
        for (int i2 = 0; i2 < dArr2.length; i2++) {
            dArr[i + i2] = Double.valueOf(dArr2[i2]);
        }
    }

    public static void i(B1 b1, Integer[] numArr, int i) {
        if (V4.a) {
            V4.a(b1.getClass(), "{0} calling Node.OfInt.copyInto(Integer[], int)");
            throw null;
        }
        int[] iArr = (int[]) b1.e();
        for (int i2 = 0; i2 < iArr.length; i2++) {
            numArr[i + i2] = Integer.valueOf(iArr[i2]);
        }
    }

    public static void j(D1 d1, Long[] lArr, int i) {
        if (V4.a) {
            V4.a(d1.getClass(), "{0} calling Node.OfInt.copyInto(Long[], int)");
            throw null;
        }
        long[] jArr = (long[]) d1.e();
        for (int i2 = 0; i2 < jArr.length; i2++) {
            lArr[i + i2] = Long.valueOf(jArr[i2]);
        }
    }

    public static void k(InterfaceC0271z1 interfaceC0271z1, Consumer consumer) {
        if (consumer instanceof j$.util.function.f) {
            interfaceC0271z1.g((j$.util.function.f) consumer);
        } else {
            if (V4.a) {
                V4.a(interfaceC0271z1.getClass(), "{0} calling Node.OfLong.forEachRemaining(Consumer)");
                throw null;
            }
            ((r.a) interfaceC0271z1.spliterator()).forEachRemaining(consumer);
        }
    }

    public static void l(B1 b1, Consumer consumer) {
        if (consumer instanceof j$.util.function.m) {
            b1.g((j$.util.function.m) consumer);
        } else {
            if (V4.a) {
                V4.a(b1.getClass(), "{0} calling Node.OfInt.forEachRemaining(Consumer)");
                throw null;
            }
            ((r.b) b1.spliterator()).forEachRemaining(consumer);
        }
    }

    public static void m(D1 d1, Consumer consumer) {
        if (consumer instanceof j$.util.function.s) {
            d1.g((j$.util.function.s) consumer);
        } else {
            if (V4.a) {
                V4.a(d1.getClass(), "{0} calling Node.OfLong.forEachRemaining(Consumer)");
                throw null;
            }
            ((r.c) d1.spliterator()).forEachRemaining(consumer);
        }
    }

    public static InterfaceC0271z1 n(InterfaceC0271z1 interfaceC0271z1, long j, long j2, j$.util.function.n nVar) {
        if (j == 0 && j2 == interfaceC0271z1.count()) {
            return interfaceC0271z1;
        }
        long j3 = j2 - j;
        r.a aVar = (r.a) interfaceC0271z1.spliterator();
        InterfaceC0244u1 j4 = C2.j(j3);
        j4.n(j3);
        for (int i = 0; i < j && aVar.k(new j$.util.function.f() { // from class: j$.util.stream.y1
            @Override // j$.util.function.f
            public final void accept(double d) {
            }

            @Override // j$.util.function.f
            public j$.util.function.f j(j$.util.function.f fVar) {
                Objects.requireNonNull(fVar);
                return new j$.util.function.e(this, fVar);
            }
        }); i++) {
        }
        for (int i2 = 0; i2 < j3 && aVar.k(j4); i2++) {
        }
        j4.m();
        return j4.a();
    }

    public static B1 o(B1 b1, long j, long j2, j$.util.function.n nVar) {
        if (j == 0 && j2 == b1.count()) {
            return b1;
        }
        long j3 = j2 - j;
        r.b bVar = (r.b) b1.spliterator();
        InterfaceC0250v1 p = C2.p(j3);
        p.n(j3);
        for (int i = 0; i < j && bVar.k(new j$.util.function.m() { // from class: j$.util.stream.A1
            @Override // j$.util.function.m
            public final void accept(int i2) {
            }

            @Override // j$.util.function.m
            public j$.util.function.m l(j$.util.function.m mVar) {
                Objects.requireNonNull(mVar);
                return new j$.util.function.l(this, mVar);
            }
        }); i++) {
        }
        for (int i2 = 0; i2 < j3 && bVar.k(p); i2++) {
        }
        p.m();
        return p.a();
    }

    public static D1 p(D1 d1, long j, long j2, j$.util.function.n nVar) {
        if (j == 0 && j2 == d1.count()) {
            return d1;
        }
        long j3 = j2 - j;
        r.c cVar = (r.c) d1.spliterator();
        InterfaceC0256w1 q2 = C2.q(j3);
        q2.n(j3);
        for (int i = 0; i < j && cVar.k(new j$.util.function.s() { // from class: j$.util.stream.C1
            @Override // j$.util.function.s
            public final void accept(long j4) {
            }

            @Override // j$.util.function.s
            public j$.util.function.s f(j$.util.function.s sVar) {
                Objects.requireNonNull(sVar);
                return new j$.util.function.r(this, sVar);
            }
        }); i++) {
        }
        for (int i2 = 0; i2 < j3 && cVar.k(q2); i2++) {
        }
        q2.m();
        return q2.a();
    }

    public static F1 q(F1 f1, long j, long j2, j$.util.function.n nVar) {
        if (j == 0 && j2 == f1.count()) {
            return f1;
        }
        j$.util.r spliterator = f1.spliterator();
        long j3 = j2 - j;
        InterfaceC0261x1 d = C2.d(j3, nVar);
        d.n(j3);
        for (int i = 0; i < j && spliterator.b(new Consumer() { // from class: j$.util.stream.s1
            @Override // j$.util.function.Consumer
            public final void accept(Object obj) {
            }

            @Override // j$.util.function.Consumer
            public /* synthetic */ Consumer andThen(Consumer consumer) {
                return Consumer.CC.$default$andThen(this, consumer);
            }
        }); i++) {
        }
        for (int i2 = 0; i2 < j3 && spliterator.b(d); i2++) {
        }
        d.m();
        return d.a();
    }

    public static Z r(r.a aVar, boolean z) {
        return new V(aVar, EnumC0176i4.c(aVar), z);
    }

    public static IntStream s(r.b bVar, boolean z) {
        return new N0(bVar, EnumC0176i4.c(bVar), z);
    }

    public static InterfaceC0179j1 t(r.c cVar, boolean z) {
        return new C0155f1(cVar, EnumC0176i4.c(cVar), z);
    }

    public static S4 u(j$.wrappers.C c, EnumC0215p1 enumC0215p1) {
        Objects.requireNonNull(c);
        Objects.requireNonNull(enumC0215p1);
        return new C0221q1(EnumC0182j4.DOUBLE_VALUE, enumC0215p1, new C0236t(enumC0215p1, c));
    }

    public static S4 v(j$.wrappers.S s, EnumC0215p1 enumC0215p1) {
        Objects.requireNonNull(s);
        Objects.requireNonNull(enumC0215p1);
        return new C0221q1(EnumC0182j4.INT_VALUE, enumC0215p1, new C0236t(enumC0215p1, s));
    }

    public static S4 w(C0287g0 c0287g0, EnumC0215p1 enumC0215p1) {
        Objects.requireNonNull(c0287g0);
        Objects.requireNonNull(enumC0215p1);
        return new C0221q1(EnumC0182j4.LONG_VALUE, enumC0215p1, new C0236t(enumC0215p1, c0287g0));
    }

    public static S4 x(Predicate predicate, EnumC0215p1 enumC0215p1) {
        Objects.requireNonNull(predicate);
        Objects.requireNonNull(enumC0215p1);
        return new C0221q1(EnumC0182j4.REFERENCE, enumC0215p1, new C0236t(enumC0215p1, predicate));
    }

    public static Stream y(j$.util.r rVar, boolean z) {
        Objects.requireNonNull(rVar);
        return new C0163g3(rVar, EnumC0176i4.c(rVar), z);
    }
}
