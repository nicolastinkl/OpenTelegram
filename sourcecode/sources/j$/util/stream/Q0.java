package j$.util.stream;

import j$.util.C0118g;
import j$.util.C0120i;
import j$.util.OptionalInt;
import j$.util.function.BiConsumer;
import j$.util.function.IntUnaryOperator;
import j$.util.function.Supplier;
import j$.util.function.ToIntFunction;
import j$.util.r;
import java.util.Iterator;
import java.util.Objects;

/* loaded from: classes2.dex */
abstract class Q0 extends AbstractC0135c implements IntStream {
    Q0(j$.util.r rVar, int i, boolean z) {
        super(rVar, i, z);
    }

    Q0(AbstractC0135c abstractC0135c, int i) {
        super(abstractC0135c, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static r.b J0(j$.util.r rVar) {
        if (rVar instanceof r.b) {
            return (r.b) rVar;
        }
        if (!V4.a) {
            throw new UnsupportedOperationException("IntStream.adapt(Spliterator<Integer> s)");
        }
        V4.a(AbstractC0135c.class, "using IntStream.adapt(Spliterator<Integer> s)");
        throw null;
    }

    @Override // j$.util.stream.IntStream
    public final boolean B(j$.wrappers.S s) {
        return ((Boolean) u0(AbstractC0238t1.v(s, EnumC0215p1.ALL))).booleanValue();
    }

    @Override // j$.util.stream.IntStream
    public final boolean E(j$.wrappers.S s) {
        return ((Boolean) u0(AbstractC0238t1.v(s, EnumC0215p1.ANY))).booleanValue();
    }

    public void H(j$.util.function.m mVar) {
        Objects.requireNonNull(mVar);
        u0(new C0220q0(mVar, true));
    }

    @Override // j$.util.stream.AbstractC0135c
    final j$.util.r H0(D2 d2, Supplier supplier, boolean z) {
        return new C0253v4(d2, supplier, z);
    }

    @Override // j$.util.stream.IntStream
    public final Stream I(j$.util.function.n nVar) {
        Objects.requireNonNull(nVar);
        return new Q(this, this, EnumC0182j4.INT_VALUE, EnumC0176i4.p | EnumC0176i4.n, nVar);
    }

    @Override // j$.util.stream.IntStream
    public final int M(int i, j$.util.function.k kVar) {
        Objects.requireNonNull(kVar);
        return ((Integer) u0(new Q2(EnumC0182j4.INT_VALUE, kVar, i))).intValue();
    }

    @Override // j$.util.stream.IntStream
    public final IntStream O(j$.util.function.n nVar) {
        return new S(this, this, EnumC0182j4.INT_VALUE, EnumC0176i4.p | EnumC0176i4.n | EnumC0176i4.t, nVar);
    }

    public void S(j$.util.function.m mVar) {
        Objects.requireNonNull(mVar);
        u0(new C0220q0(mVar, false));
    }

    @Override // j$.util.stream.IntStream
    public final OptionalInt Y(j$.util.function.k kVar) {
        Objects.requireNonNull(kVar);
        return (OptionalInt) u0(new I2(EnumC0182j4.INT_VALUE, kVar));
    }

    @Override // j$.util.stream.IntStream
    public final IntStream Z(j$.util.function.m mVar) {
        Objects.requireNonNull(mVar);
        return new S(this, this, EnumC0182j4.INT_VALUE, 0, mVar);
    }

    @Override // j$.util.stream.IntStream
    public final Z asDoubleStream() {
        return new U(this, this, EnumC0182j4.INT_VALUE, EnumC0176i4.p | EnumC0176i4.n);
    }

    @Override // j$.util.stream.IntStream
    public final InterfaceC0179j1 asLongStream() {
        return new L0(this, this, EnumC0182j4.INT_VALUE, EnumC0176i4.p | EnumC0176i4.n);
    }

    @Override // j$.util.stream.IntStream
    public final C0120i average() {
        return ((long[]) h0(new Supplier() { // from class: j$.util.stream.A0
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new long[2];
            }
        }, new j$.util.function.x() { // from class: j$.util.stream.z0
            @Override // j$.util.function.x
            public final void accept(Object obj, int i) {
                long[] jArr = (long[]) obj;
                jArr[0] = jArr[0] + 1;
                jArr[1] = jArr[1] + i;
            }
        }, new BiConsumer() { // from class: j$.util.stream.C0
            @Override // j$.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                long[] jArr = (long[]) obj;
                long[] jArr2 = (long[]) obj2;
                jArr[0] = jArr[0] + jArr2[0];
                jArr[1] = jArr[1] + jArr2[1];
            }

            @Override // j$.util.function.BiConsumer
            public /* synthetic */ BiConsumer andThen(BiConsumer biConsumer) {
                return BiConsumer.CC.$default$andThen(this, biConsumer);
            }
        }))[0] > 0 ? C0120i.d(r0[1] / r0[0]) : C0120i.a();
    }

    @Override // j$.util.stream.IntStream
    public final Stream boxed() {
        return I(H0.a);
    }

    @Override // j$.util.stream.IntStream
    public final long count() {
        return ((AbstractC0173i1) f(new j$.util.function.o() { // from class: j$.util.stream.J0
            @Override // j$.util.function.o
            public final long applyAsLong(int i) {
                return 1L;
            }
        })).sum();
    }

    @Override // j$.util.stream.IntStream
    public final IntStream distinct() {
        return ((AbstractC0181j3) I(H0.a)).distinct().m(new ToIntFunction() { // from class: j$.util.stream.B0
            @Override // j$.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((Integer) obj).intValue();
            }
        });
    }

    @Override // j$.util.stream.IntStream
    public final InterfaceC0179j1 f(j$.util.function.o oVar) {
        Objects.requireNonNull(oVar);
        return new T(this, this, EnumC0182j4.INT_VALUE, EnumC0176i4.p | EnumC0176i4.n, oVar);
    }

    @Override // j$.util.stream.IntStream
    public final OptionalInt findAny() {
        return (OptionalInt) u0(new C0172i0(false, EnumC0182j4.INT_VALUE, OptionalInt.empty(), C0136c0.a, C0154f0.a));
    }

    @Override // j$.util.stream.IntStream
    public final OptionalInt findFirst() {
        return (OptionalInt) u0(new C0172i0(true, EnumC0182j4.INT_VALUE, OptionalInt.empty(), C0136c0.a, C0154f0.a));
    }

    @Override // j$.util.stream.IntStream
    public final IntStream h(j$.wrappers.S s) {
        Objects.requireNonNull(s);
        return new S(this, this, EnumC0182j4.INT_VALUE, EnumC0176i4.t, s);
    }

    @Override // j$.util.stream.IntStream
    public final Object h0(Supplier supplier, j$.util.function.x xVar, BiConsumer biConsumer) {
        H h = new H(biConsumer, 1);
        Objects.requireNonNull(supplier);
        Objects.requireNonNull(xVar);
        return u0(new E2(EnumC0182j4.INT_VALUE, h, xVar, supplier));
    }

    @Override // j$.util.stream.BaseStream
    public final Iterator<Integer> iterator() {
        return j$.util.H.g(spliterator());
    }

    @Override // j$.util.stream.BaseStream
    /* renamed from: iterator, reason: avoid collision after fix types in other method */
    public Iterator<Integer> iterator2() {
        return j$.util.H.g(spliterator());
    }

    @Override // j$.util.stream.IntStream
    public final IntStream limit(long j) {
        if (j >= 0) {
            return G3.g(this, 0L, j);
        }
        throw new IllegalArgumentException(Long.toString(j));
    }

    @Override // j$.util.stream.IntStream
    public final OptionalInt max() {
        return Y(new j$.util.function.k() { // from class: j$.util.stream.E0
            @Override // j$.util.function.k
            public final int applyAsInt(int i, int i2) {
                return Math.max(i, i2);
            }
        });
    }

    @Override // j$.util.stream.IntStream
    public final OptionalInt min() {
        return Y(new j$.util.function.k() { // from class: j$.util.stream.F0
            @Override // j$.util.function.k
            public final int applyAsInt(int i, int i2) {
                return Math.min(i, i2);
            }
        });
    }

    @Override // j$.util.stream.D2
    final InterfaceC0261x1 q0(long j, j$.util.function.n nVar) {
        return C2.p(j);
    }

    @Override // j$.util.stream.IntStream
    public final IntStream skip(long j) {
        if (j >= 0) {
            return j == 0 ? this : G3.g(this, j, -1L);
        }
        throw new IllegalArgumentException(Long.toString(j));
    }

    @Override // j$.util.stream.IntStream
    public final IntStream sorted() {
        return new P3(this);
    }

    @Override // j$.util.stream.AbstractC0135c, j$.util.stream.BaseStream
    public final r.b spliterator() {
        return J0(super.spliterator());
    }

    @Override // j$.util.stream.IntStream
    public final int sum() {
        return ((Integer) u0(new Q2(EnumC0182j4.INT_VALUE, new j$.util.function.k() { // from class: j$.util.stream.D0
            @Override // j$.util.function.k
            public final int applyAsInt(int i, int i2) {
                return i + i2;
            }
        }, 0))).intValue();
    }

    @Override // j$.util.stream.IntStream
    public final C0118g summaryStatistics() {
        return (C0118g) h0(new Supplier() { // from class: j$.util.stream.p
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new C0118g();
            }
        }, new j$.util.function.x() { // from class: j$.util.stream.y0
            @Override // j$.util.function.x
            public final void accept(Object obj, int i) {
                ((C0118g) obj).accept(i);
            }
        }, new BiConsumer() { // from class: j$.util.stream.x0
            @Override // j$.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ((C0118g) obj).b((C0118g) obj2);
            }

            @Override // j$.util.function.BiConsumer
            public /* synthetic */ BiConsumer andThen(BiConsumer biConsumer) {
                return BiConsumer.CC.$default$andThen(this, biConsumer);
            }
        });
    }

    @Override // j$.util.stream.IntStream
    public final boolean t(j$.wrappers.S s) {
        return ((Boolean) u0(AbstractC0238t1.v(s, EnumC0215p1.NONE))).booleanValue();
    }

    @Override // j$.util.stream.IntStream
    public final int[] toArray() {
        return (int[]) C2.n((B1) v0(new j$.util.function.n() { // from class: j$.util.stream.I0
            @Override // j$.util.function.n
            public final Object apply(int i) {
                return new Integer[i];
            }
        })).e();
    }

    @Override // j$.util.stream.BaseStream
    public BaseStream unordered() {
        return !z0() ? this : new M0(this, this, EnumC0182j4.INT_VALUE, EnumC0176i4.r);
    }

    @Override // j$.util.stream.AbstractC0135c
    final F1 w0(D2 d2, j$.util.r rVar, boolean z, j$.util.function.n nVar) {
        return C2.g(d2, rVar, z);
    }

    @Override // j$.util.stream.AbstractC0135c
    final void x0(j$.util.r rVar, InterfaceC0228r3 interfaceC0228r3) {
        j$.util.function.m g0;
        r.b J0 = J0(rVar);
        if (interfaceC0228r3 instanceof j$.util.function.m) {
            g0 = (j$.util.function.m) interfaceC0228r3;
        } else {
            if (V4.a) {
                V4.a(AbstractC0135c.class, "using IntStream.adapt(Sink<Integer> s)");
                throw null;
            }
            g0 = new G0(interfaceC0228r3);
        }
        while (!interfaceC0228r3.o() && J0.k(g0)) {
        }
    }

    @Override // j$.util.stream.IntStream
    public final Z y(j$.wrappers.U u) {
        Objects.requireNonNull(u);
        return new P(this, this, EnumC0182j4.INT_VALUE, EnumC0176i4.p | EnumC0176i4.n, u);
    }

    @Override // j$.util.stream.AbstractC0135c
    final EnumC0182j4 y0() {
        return EnumC0182j4.INT_VALUE;
    }

    @Override // j$.util.stream.IntStream
    public final IntStream z(IntUnaryOperator intUnaryOperator) {
        Objects.requireNonNull(intUnaryOperator);
        return new S(this, this, EnumC0182j4.INT_VALUE, EnumC0176i4.p | EnumC0176i4.n, intUnaryOperator);
    }
}
