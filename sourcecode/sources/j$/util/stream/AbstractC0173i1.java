package j$.util.stream;

import j$.util.C0119h;
import j$.util.C0120i;
import j$.util.C0121j;
import j$.util.function.BiConsumer;
import j$.util.function.Supplier;
import j$.util.function.ToLongFunction;
import j$.util.r;
import j$.wrappers.C0287g0;
import j$.wrappers.C0291i0;
import j$.wrappers.C0295k0;
import java.util.Iterator;
import java.util.Objects;

/* renamed from: j$.util.stream.i1, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
abstract class AbstractC0173i1 extends AbstractC0135c implements InterfaceC0179j1 {
    AbstractC0173i1(j$.util.r rVar, int i, boolean z) {
        super(rVar, i, z);
    }

    AbstractC0173i1(AbstractC0135c abstractC0135c, int i) {
        super(abstractC0135c, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static r.c J0(j$.util.r rVar) {
        if (rVar instanceof r.c) {
            return (r.c) rVar;
        }
        if (!V4.a) {
            throw new UnsupportedOperationException("LongStream.adapt(Spliterator<Long> s)");
        }
        V4.a(AbstractC0135c.class, "using LongStream.adapt(Spliterator<Long> s)");
        throw null;
    }

    @Override // j$.util.stream.InterfaceC0179j1
    public final long C(long j, j$.util.function.q qVar) {
        Objects.requireNonNull(qVar);
        return ((Long) u0(new U2(EnumC0182j4.LONG_VALUE, qVar, j))).longValue();
    }

    @Override // j$.util.stream.AbstractC0135c
    final j$.util.r H0(D2 d2, Supplier supplier, boolean z) {
        return new x4(d2, supplier, z);
    }

    @Override // j$.util.stream.InterfaceC0179j1
    public final boolean K(C0287g0 c0287g0) {
        return ((Boolean) u0(AbstractC0238t1.w(c0287g0, EnumC0215p1.ALL))).booleanValue();
    }

    @Override // j$.util.stream.InterfaceC0179j1
    public final Z N(C0291i0 c0291i0) {
        Objects.requireNonNull(c0291i0);
        return new P(this, this, EnumC0182j4.LONG_VALUE, EnumC0176i4.p | EnumC0176i4.n, c0291i0);
    }

    @Override // j$.util.stream.InterfaceC0179j1
    public final Stream P(j$.util.function.t tVar) {
        Objects.requireNonNull(tVar);
        return new Q(this, this, EnumC0182j4.LONG_VALUE, EnumC0176i4.p | EnumC0176i4.n, tVar);
    }

    @Override // j$.util.stream.InterfaceC0179j1
    public final boolean R(C0287g0 c0287g0) {
        return ((Boolean) u0(AbstractC0238t1.w(c0287g0, EnumC0215p1.NONE))).booleanValue();
    }

    public void X(j$.util.function.s sVar) {
        Objects.requireNonNull(sVar);
        u0(new C0225r0(sVar, true));
    }

    @Override // j$.util.stream.InterfaceC0179j1
    public final Z asDoubleStream() {
        return new U(this, this, EnumC0182j4.LONG_VALUE, EnumC0176i4.p | EnumC0176i4.n);
    }

    @Override // j$.util.stream.InterfaceC0179j1
    public final C0120i average() {
        return ((long[]) c0(new Supplier() { // from class: j$.util.stream.U0
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new long[2];
            }
        }, new j$.util.function.y() { // from class: j$.util.stream.T0
            @Override // j$.util.function.y
            public final void accept(Object obj, long j) {
                long[] jArr = (long[]) obj;
                jArr[0] = jArr[0] + 1;
                jArr[1] = jArr[1] + j;
            }
        }, new BiConsumer() { // from class: j$.util.stream.W0
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

    @Override // j$.util.stream.InterfaceC0179j1
    public final IntStream b0(C0295k0 c0295k0) {
        Objects.requireNonNull(c0295k0);
        return new S(this, this, EnumC0182j4.LONG_VALUE, EnumC0176i4.p | EnumC0176i4.n, c0295k0);
    }

    @Override // j$.util.stream.InterfaceC0179j1
    public final Stream boxed() {
        return P(C0137c1.a);
    }

    @Override // j$.util.stream.InterfaceC0179j1
    public final Object c0(Supplier supplier, j$.util.function.y yVar, BiConsumer biConsumer) {
        H h = new H(biConsumer, 2);
        Objects.requireNonNull(supplier);
        Objects.requireNonNull(yVar);
        return u0(new E2(EnumC0182j4.LONG_VALUE, h, yVar, supplier));
    }

    @Override // j$.util.stream.InterfaceC0179j1
    public final long count() {
        return ((AbstractC0173i1) x(new j$.util.function.v() { // from class: j$.util.stream.d1
            @Override // j$.util.function.v
            public j$.util.function.v a(j$.util.function.v vVar) {
                Objects.requireNonNull(vVar);
                return new j$.util.function.u(this, vVar, 0);
            }

            @Override // j$.util.function.v
            public final long applyAsLong(long j) {
                return 1L;
            }

            @Override // j$.util.function.v
            public j$.util.function.v b(j$.util.function.v vVar) {
                Objects.requireNonNull(vVar);
                return new j$.util.function.u(this, vVar, 1);
            }
        })).sum();
    }

    public void d(j$.util.function.s sVar) {
        Objects.requireNonNull(sVar);
        u0(new C0225r0(sVar, false));
    }

    @Override // j$.util.stream.InterfaceC0179j1
    public final InterfaceC0179j1 distinct() {
        return ((AbstractC0181j3) P(C0137c1.a)).distinct().d0(new ToLongFunction() { // from class: j$.util.stream.V0
            @Override // j$.util.function.ToLongFunction
            public final long applyAsLong(Object obj) {
                return ((Long) obj).longValue();
            }
        });
    }

    @Override // j$.util.stream.InterfaceC0179j1
    public final C0121j findAny() {
        return (C0121j) u0(new C0172i0(false, EnumC0182j4.LONG_VALUE, C0121j.a(), C0142d0.a, C0160g0.a));
    }

    @Override // j$.util.stream.InterfaceC0179j1
    public final C0121j findFirst() {
        return (C0121j) u0(new C0172i0(true, EnumC0182j4.LONG_VALUE, C0121j.a(), C0142d0.a, C0160g0.a));
    }

    @Override // j$.util.stream.InterfaceC0179j1
    public final C0121j g(j$.util.function.q qVar) {
        Objects.requireNonNull(qVar);
        return (C0121j) u0(new I2(EnumC0182j4.LONG_VALUE, qVar));
    }

    @Override // j$.util.stream.BaseStream
    public final j$.util.p iterator() {
        return j$.util.H.h(spliterator());
    }

    @Override // j$.util.stream.BaseStream
    public Iterator iterator() {
        return j$.util.H.h(spliterator());
    }

    @Override // j$.util.stream.InterfaceC0179j1
    public final boolean k(C0287g0 c0287g0) {
        return ((Boolean) u0(AbstractC0238t1.w(c0287g0, EnumC0215p1.ANY))).booleanValue();
    }

    @Override // j$.util.stream.InterfaceC0179j1
    public final InterfaceC0179j1 limit(long j) {
        if (j >= 0) {
            return G3.h(this, 0L, j);
        }
        throw new IllegalArgumentException(Long.toString(j));
    }

    @Override // j$.util.stream.InterfaceC0179j1
    public final C0121j max() {
        return g(new j$.util.function.q() { // from class: j$.util.stream.Z0
            @Override // j$.util.function.q
            public final long applyAsLong(long j, long j2) {
                return Math.max(j, j2);
            }
        });
    }

    @Override // j$.util.stream.InterfaceC0179j1
    public final C0121j min() {
        return g(new j$.util.function.q() { // from class: j$.util.stream.a1
            @Override // j$.util.function.q
            public final long applyAsLong(long j, long j2) {
                return Math.min(j, j2);
            }
        });
    }

    @Override // j$.util.stream.InterfaceC0179j1
    public final InterfaceC0179j1 o(j$.util.function.s sVar) {
        Objects.requireNonNull(sVar);
        return new T(this, this, EnumC0182j4.LONG_VALUE, 0, sVar);
    }

    @Override // j$.util.stream.InterfaceC0179j1
    public final InterfaceC0179j1 q(j$.util.function.t tVar) {
        return new T(this, this, EnumC0182j4.LONG_VALUE, EnumC0176i4.p | EnumC0176i4.n | EnumC0176i4.t, tVar);
    }

    @Override // j$.util.stream.D2
    final InterfaceC0261x1 q0(long j, j$.util.function.n nVar) {
        return C2.q(j);
    }

    @Override // j$.util.stream.InterfaceC0179j1
    public final InterfaceC0179j1 s(C0287g0 c0287g0) {
        Objects.requireNonNull(c0287g0);
        return new T(this, this, EnumC0182j4.LONG_VALUE, EnumC0176i4.t, c0287g0);
    }

    @Override // j$.util.stream.InterfaceC0179j1
    public final InterfaceC0179j1 skip(long j) {
        if (j >= 0) {
            return j == 0 ? this : G3.h(this, j, -1L);
        }
        throw new IllegalArgumentException(Long.toString(j));
    }

    @Override // j$.util.stream.InterfaceC0179j1
    public final InterfaceC0179j1 sorted() {
        return new Q3(this);
    }

    @Override // j$.util.stream.AbstractC0135c, j$.util.stream.BaseStream
    public final r.c spliterator() {
        return J0(super.spliterator());
    }

    @Override // j$.util.stream.InterfaceC0179j1
    public final long sum() {
        return ((Long) u0(new U2(EnumC0182j4.LONG_VALUE, new j$.util.function.q() { // from class: j$.util.stream.Y0
            @Override // j$.util.function.q
            public final long applyAsLong(long j, long j2) {
                return j + j2;
            }
        }, 0L))).longValue();
    }

    @Override // j$.util.stream.InterfaceC0179j1
    public final C0119h summaryStatistics() {
        return (C0119h) c0(new Supplier() { // from class: j$.util.stream.q
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new C0119h();
            }
        }, new j$.util.function.y() { // from class: j$.util.stream.S0
            @Override // j$.util.function.y
            public final void accept(Object obj, long j) {
                ((C0119h) obj).accept(j);
            }
        }, new BiConsumer() { // from class: j$.util.stream.R0
            @Override // j$.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ((C0119h) obj).b((C0119h) obj2);
            }

            @Override // j$.util.function.BiConsumer
            public /* synthetic */ BiConsumer andThen(BiConsumer biConsumer) {
                return BiConsumer.CC.$default$andThen(this, biConsumer);
            }
        });
    }

    @Override // j$.util.stream.InterfaceC0179j1
    public final long[] toArray() {
        return (long[]) C2.o((D1) v0(new j$.util.function.n() { // from class: j$.util.stream.X0
            @Override // j$.util.function.n
            public final Object apply(int i) {
                return new Long[i];
            }
        })).e();
    }

    @Override // j$.util.stream.BaseStream
    public BaseStream unordered() {
        return !z0() ? this : new L0(this, this, EnumC0182j4.LONG_VALUE, EnumC0176i4.r);
    }

    @Override // j$.util.stream.AbstractC0135c
    final F1 w0(D2 d2, j$.util.r rVar, boolean z, j$.util.function.n nVar) {
        return C2.h(d2, rVar, z);
    }

    @Override // j$.util.stream.InterfaceC0179j1
    public final InterfaceC0179j1 x(j$.util.function.v vVar) {
        Objects.requireNonNull(vVar);
        return new T(this, this, EnumC0182j4.LONG_VALUE, EnumC0176i4.p | EnumC0176i4.n, vVar);
    }

    @Override // j$.util.stream.AbstractC0135c
    final void x0(j$.util.r rVar, InterfaceC0228r3 interfaceC0228r3) {
        j$.util.function.s c0131b1;
        r.c J0 = J0(rVar);
        if (interfaceC0228r3 instanceof j$.util.function.s) {
            c0131b1 = (j$.util.function.s) interfaceC0228r3;
        } else {
            if (V4.a) {
                V4.a(AbstractC0135c.class, "using LongStream.adapt(Sink<Long> s)");
                throw null;
            }
            c0131b1 = new C0131b1(interfaceC0228r3);
        }
        while (!interfaceC0228r3.o() && J0.k(c0131b1)) {
        }
    }

    @Override // j$.util.stream.AbstractC0135c
    final EnumC0182j4 y0() {
        return EnumC0182j4.LONG_VALUE;
    }
}
