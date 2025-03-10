package j$.util.stream;

import j$.util.C0117f;
import j$.util.C0120i;
import j$.util.function.BiConsumer;
import j$.util.function.Supplier;
import j$.util.function.ToDoubleFunction;
import j$.util.r;
import java.util.Iterator;
import java.util.Objects;

/* loaded from: classes2.dex */
abstract class Y extends AbstractC0135c implements Z {
    Y(j$.util.r rVar, int i, boolean z) {
        super(rVar, i, z);
    }

    Y(AbstractC0135c abstractC0135c, int i) {
        super(abstractC0135c, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static r.a J0(j$.util.r rVar) {
        if (rVar instanceof r.a) {
            return (r.a) rVar;
        }
        if (!V4.a) {
            throw new UnsupportedOperationException("DoubleStream.adapt(Spliterator<Double> s)");
        }
        V4.a(AbstractC0135c.class, "using DoubleStream.adapt(Spliterator<Double> s)");
        throw null;
    }

    @Override // j$.util.stream.Z
    public final C0120i F(j$.util.function.d dVar) {
        Objects.requireNonNull(dVar);
        return (C0120i) u0(new I2(EnumC0182j4.DOUBLE_VALUE, dVar));
    }

    @Override // j$.util.stream.Z
    public final Object G(Supplier supplier, j$.util.function.w wVar, BiConsumer biConsumer) {
        H h = new H(biConsumer, 0);
        Objects.requireNonNull(supplier);
        Objects.requireNonNull(wVar);
        return u0(new E2(EnumC0182j4.DOUBLE_VALUE, h, wVar, supplier));
    }

    @Override // j$.util.stream.AbstractC0135c
    final j$.util.r H0(D2 d2, Supplier supplier, boolean z) {
        return new C0241t4(d2, supplier, z);
    }

    @Override // j$.util.stream.Z
    public final double J(double d, j$.util.function.d dVar) {
        Objects.requireNonNull(dVar);
        return ((Double) u0(new G2(EnumC0182j4.DOUBLE_VALUE, dVar, d))).doubleValue();
    }

    @Override // j$.util.stream.Z
    public final Stream L(j$.util.function.g gVar) {
        Objects.requireNonNull(gVar);
        return new Q(this, this, EnumC0182j4.DOUBLE_VALUE, EnumC0176i4.p | EnumC0176i4.n, gVar);
    }

    @Override // j$.util.stream.Z
    public final IntStream Q(j$.wrappers.E e) {
        Objects.requireNonNull(e);
        return new S(this, this, EnumC0182j4.DOUBLE_VALUE, EnumC0176i4.p | EnumC0176i4.n, e);
    }

    @Override // j$.util.stream.Z
    public final boolean W(j$.wrappers.C c) {
        return ((Boolean) u0(AbstractC0238t1.u(c, EnumC0215p1.ALL))).booleanValue();
    }

    @Override // j$.util.stream.Z
    public final C0120i average() {
        double[] dArr = (double[]) G(new Supplier() { // from class: j$.util.stream.C
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new double[4];
            }
        }, new j$.util.function.w() { // from class: j$.util.stream.A
            @Override // j$.util.function.w
            public final void accept(Object obj, double d) {
                double[] dArr2 = (double[]) obj;
                dArr2[2] = dArr2[2] + 1.0d;
                Collectors.b(dArr2, d);
                dArr2[3] = dArr2[3] + d;
            }
        }, new BiConsumer() { // from class: j$.util.stream.F
            @Override // j$.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                double[] dArr2 = (double[]) obj;
                double[] dArr3 = (double[]) obj2;
                Collectors.b(dArr2, dArr3[0]);
                Collectors.b(dArr2, dArr3[1]);
                dArr2[2] = dArr2[2] + dArr3[2];
                dArr2[3] = dArr2[3] + dArr3[3];
            }

            @Override // j$.util.function.BiConsumer
            public /* synthetic */ BiConsumer andThen(BiConsumer biConsumer) {
                return BiConsumer.CC.$default$andThen(this, biConsumer);
            }
        });
        return dArr[2] > 0.0d ? C0120i.d(Collectors.a(dArr) / dArr[2]) : C0120i.a();
    }

    @Override // j$.util.stream.Z
    public final Z b(j$.util.function.f fVar) {
        Objects.requireNonNull(fVar);
        return new P(this, this, EnumC0182j4.DOUBLE_VALUE, 0, fVar);
    }

    @Override // j$.util.stream.Z
    public final Stream boxed() {
        return L(L.a);
    }

    @Override // j$.util.stream.Z
    public final long count() {
        return ((AbstractC0173i1) v(new j$.util.function.h() { // from class: j$.util.stream.M
            @Override // j$.util.function.h
            public final long applyAsLong(double d) {
                return 1L;
            }
        })).sum();
    }

    @Override // j$.util.stream.Z
    public final Z distinct() {
        return ((AbstractC0181j3) L(L.a)).distinct().g0(new ToDoubleFunction() { // from class: j$.util.stream.E
            @Override // j$.util.function.ToDoubleFunction
            public final double applyAsDouble(Object obj) {
                return ((Double) obj).doubleValue();
            }
        });
    }

    @Override // j$.util.stream.Z
    public final boolean e0(j$.wrappers.C c) {
        return ((Boolean) u0(AbstractC0238t1.u(c, EnumC0215p1.ANY))).booleanValue();
    }

    @Override // j$.util.stream.Z
    public final boolean f0(j$.wrappers.C c) {
        return ((Boolean) u0(AbstractC0238t1.u(c, EnumC0215p1.NONE))).booleanValue();
    }

    @Override // j$.util.stream.Z
    public final C0120i findAny() {
        return (C0120i) u0(new C0172i0(false, EnumC0182j4.DOUBLE_VALUE, C0120i.a(), C0130b0.a, C0148e0.a));
    }

    @Override // j$.util.stream.Z
    public final C0120i findFirst() {
        return (C0120i) u0(new C0172i0(true, EnumC0182j4.DOUBLE_VALUE, C0120i.a(), C0130b0.a, C0148e0.a));
    }

    public void i0(j$.util.function.f fVar) {
        Objects.requireNonNull(fVar);
        u0(new C0214p0(fVar, true));
    }

    @Override // j$.util.stream.BaseStream
    public final j$.util.l iterator() {
        return j$.util.H.f(spliterator());
    }

    @Override // j$.util.stream.BaseStream
    public Iterator iterator() {
        return j$.util.H.f(spliterator());
    }

    public void j(j$.util.function.f fVar) {
        Objects.requireNonNull(fVar);
        u0(new C0214p0(fVar, false));
    }

    @Override // j$.util.stream.Z
    public final Z limit(long j) {
        if (j >= 0) {
            return G3.f(this, 0L, j);
        }
        throw new IllegalArgumentException(Long.toString(j));
    }

    @Override // j$.util.stream.Z
    public final C0120i max() {
        return F(new j$.util.function.d() { // from class: j$.util.stream.I
            @Override // j$.util.function.d
            public final double applyAsDouble(double d, double d2) {
                return Math.max(d, d2);
            }
        });
    }

    @Override // j$.util.stream.Z
    public final C0120i min() {
        return F(new j$.util.function.d() { // from class: j$.util.stream.J
            @Override // j$.util.function.d
            public final double applyAsDouble(double d, double d2) {
                return Math.min(d, d2);
            }
        });
    }

    @Override // j$.util.stream.Z
    public final Z p(j$.wrappers.C c) {
        Objects.requireNonNull(c);
        return new P(this, this, EnumC0182j4.DOUBLE_VALUE, EnumC0176i4.t, c);
    }

    @Override // j$.util.stream.D2
    final InterfaceC0261x1 q0(long j, j$.util.function.n nVar) {
        return C2.j(j);
    }

    @Override // j$.util.stream.Z
    public final Z skip(long j) {
        if (j >= 0) {
            return j == 0 ? this : G3.f(this, j, -1L);
        }
        throw new IllegalArgumentException(Long.toString(j));
    }

    @Override // j$.util.stream.Z
    public final Z sorted() {
        return new O3(this);
    }

    @Override // j$.util.stream.AbstractC0135c, j$.util.stream.BaseStream
    public final r.a spliterator() {
        return J0(super.spliterator());
    }

    @Override // j$.util.stream.Z
    public final double sum() {
        return Collectors.a((double[]) G(new Supplier() { // from class: j$.util.stream.D
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new double[3];
            }
        }, new j$.util.function.w() { // from class: j$.util.stream.B
            @Override // j$.util.function.w
            public final void accept(Object obj, double d) {
                double[] dArr = (double[]) obj;
                Collectors.b(dArr, d);
                dArr[2] = dArr[2] + d;
            }
        }, new BiConsumer() { // from class: j$.util.stream.G
            @Override // j$.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                double[] dArr = (double[]) obj;
                double[] dArr2 = (double[]) obj2;
                Collectors.b(dArr, dArr2[0]);
                Collectors.b(dArr, dArr2[1]);
                dArr[2] = dArr[2] + dArr2[2];
            }

            @Override // j$.util.function.BiConsumer
            public /* synthetic */ BiConsumer andThen(BiConsumer biConsumer) {
                return BiConsumer.CC.$default$andThen(this, biConsumer);
            }
        }));
    }

    @Override // j$.util.stream.Z
    public final C0117f summaryStatistics() {
        return (C0117f) G(new Supplier() { // from class: j$.util.stream.o
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new C0117f();
            }
        }, new j$.util.function.w() { // from class: j$.util.stream.z
            @Override // j$.util.function.w
            public final void accept(Object obj, double d) {
                ((C0117f) obj).accept(d);
            }
        }, new BiConsumer() { // from class: j$.util.stream.y
            @Override // j$.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ((C0117f) obj).b((C0117f) obj2);
            }

            @Override // j$.util.function.BiConsumer
            public /* synthetic */ BiConsumer andThen(BiConsumer biConsumer) {
                return BiConsumer.CC.$default$andThen(this, biConsumer);
            }
        });
    }

    @Override // j$.util.stream.Z
    public final double[] toArray() {
        return (double[]) C2.m((InterfaceC0271z1) v0(new j$.util.function.n() { // from class: j$.util.stream.N
            @Override // j$.util.function.n
            public final Object apply(int i) {
                return new Double[i];
            }
        })).e();
    }

    @Override // j$.util.stream.Z
    public final Z u(j$.util.function.g gVar) {
        return new P(this, this, EnumC0182j4.DOUBLE_VALUE, EnumC0176i4.p | EnumC0176i4.n | EnumC0176i4.t, gVar);
    }

    @Override // j$.util.stream.BaseStream
    public BaseStream unordered() {
        return !z0() ? this : new U(this, this, EnumC0182j4.DOUBLE_VALUE, EnumC0176i4.r);
    }

    @Override // j$.util.stream.Z
    public final InterfaceC0179j1 v(j$.util.function.h hVar) {
        Objects.requireNonNull(hVar);
        return new T(this, this, EnumC0182j4.DOUBLE_VALUE, EnumC0176i4.p | EnumC0176i4.n, hVar);
    }

    @Override // j$.util.stream.Z
    public final Z w(j$.wrappers.I i) {
        Objects.requireNonNull(i);
        return new P(this, this, EnumC0182j4.DOUBLE_VALUE, EnumC0176i4.p | EnumC0176i4.n, i);
    }

    @Override // j$.util.stream.AbstractC0135c
    final F1 w0(D2 d2, j$.util.r rVar, boolean z, j$.util.function.n nVar) {
        return C2.f(d2, rVar, z);
    }

    @Override // j$.util.stream.AbstractC0135c
    final void x0(j$.util.r rVar, InterfaceC0228r3 interfaceC0228r3) {
        j$.util.function.f k;
        r.a J0 = J0(rVar);
        if (interfaceC0228r3 instanceof j$.util.function.f) {
            k = (j$.util.function.f) interfaceC0228r3;
        } else {
            if (V4.a) {
                V4.a(AbstractC0135c.class, "using DoubleStream.adapt(Sink<Double> s)");
                throw null;
            }
            k = new K(interfaceC0228r3);
        }
        while (!interfaceC0228r3.o() && J0.k(k)) {
        }
    }

    @Override // j$.util.stream.AbstractC0135c
    final EnumC0182j4 y0() {
        return EnumC0182j4.DOUBLE_VALUE;
    }
}
