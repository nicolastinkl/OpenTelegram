package j$.util.stream;

import j$.util.Optional;
import j$.util.function.BiConsumer;
import j$.util.function.BiFunction;
import j$.util.function.Consumer;
import j$.util.function.Function;
import j$.util.function.Predicate;
import j$.util.function.Supplier;
import j$.util.function.ToDoubleFunction;
import j$.util.function.ToIntFunction;
import j$.util.function.ToLongFunction;
import j$.util.stream.Collector;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;

/* renamed from: j$.util.stream.j3, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
abstract class AbstractC0181j3 extends AbstractC0135c implements Stream {
    AbstractC0181j3(j$.util.r rVar, int i, boolean z) {
        super(rVar, i, z);
    }

    AbstractC0181j3(AbstractC0135c abstractC0135c, int i) {
        super(abstractC0135c, i);
    }

    @Override // j$.util.stream.Stream
    public final Object A(Object obj, BiFunction biFunction, j$.util.function.b bVar) {
        Objects.requireNonNull(biFunction);
        Objects.requireNonNull(bVar);
        return u0(new E2(EnumC0182j4.REFERENCE, bVar, biFunction, obj));
    }

    @Override // j$.util.stream.Stream
    public final Z D(Function function) {
        Objects.requireNonNull(function);
        return new P(this, this, EnumC0182j4.REFERENCE, EnumC0176i4.p | EnumC0176i4.n | EnumC0176i4.t, function);
    }

    @Override // j$.util.stream.AbstractC0135c
    final j$.util.r H0(D2 d2, Supplier supplier, boolean z) {
        return new Q4(d2, supplier, z);
    }

    @Override // j$.util.stream.Stream
    public final Stream T(Consumer consumer) {
        Objects.requireNonNull(consumer);
        return new Q(this, this, EnumC0182j4.REFERENCE, 0, consumer);
    }

    @Override // j$.util.stream.Stream
    public final boolean U(Predicate predicate) {
        return ((Boolean) u0(AbstractC0238t1.x(predicate, EnumC0215p1.ALL))).booleanValue();
    }

    @Override // j$.util.stream.Stream
    public final InterfaceC0179j1 V(Function function) {
        Objects.requireNonNull(function);
        return new T(this, this, EnumC0182j4.REFERENCE, EnumC0176i4.p | EnumC0176i4.n | EnumC0176i4.t, function);
    }

    @Override // j$.util.stream.Stream
    public final boolean a(Predicate predicate) {
        return ((Boolean) u0(AbstractC0238t1.x(predicate, EnumC0215p1.ANY))).booleanValue();
    }

    @Override // j$.util.stream.Stream
    public final boolean a0(Predicate predicate) {
        return ((Boolean) u0(AbstractC0238t1.x(predicate, EnumC0215p1.NONE))).booleanValue();
    }

    @Override // j$.util.stream.Stream
    public final IntStream c(Function function) {
        Objects.requireNonNull(function);
        return new S(this, this, EnumC0182j4.REFERENCE, EnumC0176i4.p | EnumC0176i4.n | EnumC0176i4.t, function);
    }

    @Override // j$.util.stream.Stream
    public final Object collect(Collector collector) {
        Object u0;
        if (isParallel() && collector.characteristics().contains(Collector.a.CONCURRENT) && (!z0() || collector.characteristics().contains(Collector.a.UNORDERED))) {
            u0 = collector.supplier().get();
            forEach(new C0236t(collector.accumulator(), u0));
        } else {
            Objects.requireNonNull(collector);
            Supplier supplier = collector.supplier();
            u0 = u0(new N2(EnumC0182j4.REFERENCE, collector.combiner(), collector.accumulator(), supplier, collector));
        }
        return collector.characteristics().contains(Collector.a.IDENTITY_FINISH) ? u0 : collector.finisher().apply(u0);
    }

    @Override // j$.util.stream.Stream
    public final long count() {
        return ((AbstractC0173i1) d0(new ToLongFunction() { // from class: j$.util.stream.c3
            @Override // j$.util.function.ToLongFunction
            public final long applyAsLong(Object obj) {
                return 1L;
            }
        })).sum();
    }

    @Override // j$.util.stream.Stream
    public final InterfaceC0179j1 d0(ToLongFunction toLongFunction) {
        Objects.requireNonNull(toLongFunction);
        return new T(this, this, EnumC0182j4.REFERENCE, EnumC0176i4.p | EnumC0176i4.n, toLongFunction);
    }

    @Override // j$.util.stream.Stream
    public final Stream distinct() {
        return new C0259x(this, EnumC0182j4.REFERENCE, EnumC0176i4.m | EnumC0176i4.t);
    }

    public void e(Consumer consumer) {
        Objects.requireNonNull(consumer);
        u0(new C0231s0(consumer, true));
    }

    @Override // j$.util.stream.Stream
    public final Stream filter(Predicate predicate) {
        Objects.requireNonNull(predicate);
        return new Q(this, this, EnumC0182j4.REFERENCE, EnumC0176i4.t, predicate);
    }

    @Override // j$.util.stream.Stream
    public final Optional findAny() {
        return (Optional) u0(new C0172i0(false, EnumC0182j4.REFERENCE, Optional.empty(), C0124a0.a, C0166h0.a));
    }

    @Override // j$.util.stream.Stream
    public final Optional findFirst() {
        return (Optional) u0(new C0172i0(true, EnumC0182j4.REFERENCE, Optional.empty(), C0124a0.a, C0166h0.a));
    }

    public void forEach(Consumer consumer) {
        Objects.requireNonNull(consumer);
        u0(new C0231s0(consumer, false));
    }

    @Override // j$.util.stream.Stream
    public final Z g0(ToDoubleFunction toDoubleFunction) {
        Objects.requireNonNull(toDoubleFunction);
        return new P(this, this, EnumC0182j4.REFERENCE, EnumC0176i4.p | EnumC0176i4.n, toDoubleFunction);
    }

    @Override // j$.util.stream.Stream
    public final Object i(Supplier supplier, BiConsumer biConsumer, BiConsumer biConsumer2) {
        Objects.requireNonNull(supplier);
        Objects.requireNonNull(biConsumer);
        Objects.requireNonNull(biConsumer2);
        return u0(new E2(EnumC0182j4.REFERENCE, biConsumer2, biConsumer, supplier));
    }

    @Override // j$.util.stream.BaseStream
    public final Iterator iterator() {
        return j$.util.H.i(spliterator());
    }

    @Override // j$.util.stream.Stream
    public final Object j0(Object obj, j$.util.function.b bVar) {
        Objects.requireNonNull(bVar);
        return u0(new E2(EnumC0182j4.REFERENCE, bVar, bVar, obj));
    }

    @Override // j$.util.stream.Stream
    public final Object[] l(j$.util.function.n nVar) {
        return C2.l(v0(nVar), nVar).q(nVar);
    }

    @Override // j$.util.stream.Stream
    public final Stream limit(long j) {
        if (j >= 0) {
            return G3.i(this, 0L, j);
        }
        throw new IllegalArgumentException(Long.toString(j));
    }

    @Override // j$.util.stream.Stream
    public final IntStream m(ToIntFunction toIntFunction) {
        Objects.requireNonNull(toIntFunction);
        return new S(this, this, EnumC0182j4.REFERENCE, EnumC0176i4.p | EnumC0176i4.n, toIntFunction);
    }

    @Override // j$.util.stream.Stream
    public final Stream map(Function function) {
        Objects.requireNonNull(function);
        return new C0157f3(this, this, EnumC0182j4.REFERENCE, EnumC0176i4.p | EnumC0176i4.n, function, 0);
    }

    @Override // j$.util.stream.Stream
    public final Optional max(Comparator comparator) {
        Objects.requireNonNull(comparator);
        return r(new j$.util.function.a(comparator, 0));
    }

    @Override // j$.util.stream.Stream
    public final Optional min(Comparator comparator) {
        Objects.requireNonNull(comparator);
        return r(new j$.util.function.a(comparator, 1));
    }

    @Override // j$.util.stream.Stream
    public final Stream n(Function function) {
        Objects.requireNonNull(function);
        return new C0157f3(this, this, EnumC0182j4.REFERENCE, EnumC0176i4.p | EnumC0176i4.n | EnumC0176i4.t, function, 1);
    }

    @Override // j$.util.stream.D2
    final InterfaceC0261x1 q0(long j, j$.util.function.n nVar) {
        return C2.d(j, nVar);
    }

    @Override // j$.util.stream.Stream
    public final Optional r(j$.util.function.b bVar) {
        Objects.requireNonNull(bVar);
        return (Optional) u0(new I2(EnumC0182j4.REFERENCE, bVar));
    }

    @Override // j$.util.stream.Stream
    public final Stream skip(long j) {
        if (j >= 0) {
            return j == 0 ? this : G3.i(this, j, -1L);
        }
        throw new IllegalArgumentException(Long.toString(j));
    }

    @Override // j$.util.stream.Stream
    public final Stream sorted() {
        return new R3(this);
    }

    @Override // j$.util.stream.Stream
    public final Object[] toArray() {
        C0133b3 c0133b3 = new j$.util.function.n() { // from class: j$.util.stream.b3
            @Override // j$.util.function.n
            public final Object apply(int i) {
                return new Object[i];
            }
        };
        return C2.l(v0(c0133b3), c0133b3).q(c0133b3);
    }

    @Override // j$.util.stream.BaseStream
    public BaseStream unordered() {
        return !z0() ? this : new C0151e3(this, this, EnumC0182j4.REFERENCE, EnumC0176i4.r);
    }

    @Override // j$.util.stream.AbstractC0135c
    final F1 w0(D2 d2, j$.util.r rVar, boolean z, j$.util.function.n nVar) {
        return C2.e(d2, rVar, z, nVar);
    }

    @Override // j$.util.stream.AbstractC0135c
    final void x0(j$.util.r rVar, InterfaceC0228r3 interfaceC0228r3) {
        while (!interfaceC0228r3.o() && rVar.b(interfaceC0228r3)) {
        }
    }

    @Override // j$.util.stream.AbstractC0135c
    final EnumC0182j4 y0() {
        return EnumC0182j4.REFERENCE;
    }

    @Override // j$.util.stream.Stream
    public final Stream sorted(Comparator comparator) {
        return new R3(this, comparator);
    }
}
