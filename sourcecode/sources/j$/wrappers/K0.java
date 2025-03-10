package j$.wrappers;

import j$.util.AbstractC0112a;
import j$.util.C0119h;
import j$.util.C0120i;
import j$.util.C0121j;
import j$.util.function.BiConsumer;
import j$.util.function.Supplier;
import j$.util.r;
import j$.util.stream.BaseStream;
import j$.util.stream.IntStream;
import j$.util.stream.InterfaceC0179j1;
import j$.util.stream.Stream;
import java.util.Iterator;
import java.util.stream.LongStream;

/* loaded from: classes2.dex */
public final /* synthetic */ class K0 implements InterfaceC0179j1 {
    final /* synthetic */ LongStream a;

    private /* synthetic */ K0(LongStream longStream) {
        this.a = longStream;
    }

    public static /* synthetic */ InterfaceC0179j1 k0(LongStream longStream) {
        if (longStream == null) {
            return null;
        }
        return longStream instanceof L0 ? ((L0) longStream).a : new K0(longStream);
    }

    @Override // j$.util.stream.InterfaceC0179j1
    public /* synthetic */ long C(long j, j$.util.function.q qVar) {
        return this.a.reduce(j, C0277b0.a(qVar));
    }

    @Override // j$.util.stream.InterfaceC0179j1
    public /* synthetic */ boolean K(C0287g0 c0287g0) {
        return this.a.allMatch(AbstractC0289h0.a(c0287g0));
    }

    @Override // j$.util.stream.InterfaceC0179j1
    public /* synthetic */ j$.util.stream.Z N(C0291i0 c0291i0) {
        return I0.k0(this.a.mapToDouble(c0291i0 == null ? null : c0291i0.a));
    }

    @Override // j$.util.stream.InterfaceC0179j1
    public /* synthetic */ Stream P(j$.util.function.t tVar) {
        return C$r8$wrapper$java$util$stream$Stream$VWRP.convert(this.a.mapToObj(C0285f0.a(tVar)));
    }

    @Override // j$.util.stream.InterfaceC0179j1
    public /* synthetic */ boolean R(C0287g0 c0287g0) {
        return this.a.noneMatch(AbstractC0289h0.a(c0287g0));
    }

    @Override // j$.util.stream.InterfaceC0179j1
    public /* synthetic */ void X(j$.util.function.s sVar) {
        this.a.forEachOrdered(C0281d0.a(sVar));
    }

    @Override // j$.util.stream.InterfaceC0179j1
    public /* synthetic */ j$.util.stream.Z asDoubleStream() {
        return I0.k0(this.a.asDoubleStream());
    }

    @Override // j$.util.stream.InterfaceC0179j1
    public /* synthetic */ C0120i average() {
        return AbstractC0112a.n(this.a.average());
    }

    @Override // j$.util.stream.InterfaceC0179j1
    public /* synthetic */ IntStream b0(C0295k0 c0295k0) {
        return C$r8$wrapper$java$util$stream$IntStream$VWRP.convert(this.a.mapToInt(c0295k0 == null ? null : c0295k0.a));
    }

    @Override // j$.util.stream.InterfaceC0179j1
    public /* synthetic */ Stream boxed() {
        return C$r8$wrapper$java$util$stream$Stream$VWRP.convert(this.a.boxed());
    }

    @Override // j$.util.stream.InterfaceC0179j1
    public /* synthetic */ Object c0(Supplier supplier, j$.util.function.y yVar, BiConsumer biConsumer) {
        return this.a.collect(x0.a(supplier), t0.a(yVar), r.a(biConsumer));
    }

    @Override // j$.util.stream.BaseStream, java.lang.AutoCloseable
    public /* synthetic */ void close() {
        this.a.close();
    }

    @Override // j$.util.stream.InterfaceC0179j1
    public /* synthetic */ long count() {
        return this.a.count();
    }

    @Override // j$.util.stream.InterfaceC0179j1
    public /* synthetic */ void d(j$.util.function.s sVar) {
        this.a.forEach(C0281d0.a(sVar));
    }

    @Override // j$.util.stream.InterfaceC0179j1
    public /* synthetic */ InterfaceC0179j1 distinct() {
        return k0(this.a.distinct());
    }

    @Override // j$.util.stream.InterfaceC0179j1
    public /* synthetic */ C0121j findAny() {
        return AbstractC0112a.p(this.a.findAny());
    }

    @Override // j$.util.stream.InterfaceC0179j1
    public /* synthetic */ C0121j findFirst() {
        return AbstractC0112a.p(this.a.findFirst());
    }

    @Override // j$.util.stream.InterfaceC0179j1
    public /* synthetic */ C0121j g(j$.util.function.q qVar) {
        return AbstractC0112a.p(this.a.reduce(C0277b0.a(qVar)));
    }

    @Override // j$.util.stream.BaseStream
    public /* synthetic */ boolean isParallel() {
        return this.a.isParallel();
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [java.util.PrimitiveIterator$OfLong] */
    @Override // j$.util.stream.InterfaceC0179j1, j$.util.stream.BaseStream
    public /* synthetic */ j$.util.p iterator() {
        return C0282e.a(this.a.iterator());
    }

    @Override // j$.util.stream.BaseStream
    public /* synthetic */ Iterator iterator() {
        return this.a.iterator();
    }

    @Override // j$.util.stream.InterfaceC0179j1
    public /* synthetic */ boolean k(C0287g0 c0287g0) {
        return this.a.anyMatch(AbstractC0289h0.a(c0287g0));
    }

    @Override // j$.util.stream.InterfaceC0179j1
    public /* synthetic */ InterfaceC0179j1 limit(long j) {
        return k0(this.a.limit(j));
    }

    @Override // j$.util.stream.InterfaceC0179j1
    public /* synthetic */ C0121j max() {
        return AbstractC0112a.p(this.a.max());
    }

    @Override // j$.util.stream.InterfaceC0179j1
    public /* synthetic */ C0121j min() {
        return AbstractC0112a.p(this.a.min());
    }

    @Override // j$.util.stream.InterfaceC0179j1
    public /* synthetic */ InterfaceC0179j1 o(j$.util.function.s sVar) {
        return k0(this.a.peek(C0281d0.a(sVar)));
    }

    @Override // j$.util.stream.BaseStream
    public /* synthetic */ BaseStream onClose(Runnable runnable) {
        return E0.k0(this.a.onClose(runnable));
    }

    @Override // j$.util.stream.BaseStream
    public /* synthetic */ BaseStream parallel() {
        return E0.k0(this.a.parallel());
    }

    @Override // j$.util.stream.InterfaceC0179j1, j$.util.stream.BaseStream
    public /* synthetic */ InterfaceC0179j1 parallel() {
        return k0(this.a.parallel());
    }

    @Override // j$.util.stream.InterfaceC0179j1
    public /* synthetic */ InterfaceC0179j1 q(j$.util.function.t tVar) {
        return k0(this.a.flatMap(C0285f0.a(tVar)));
    }

    @Override // j$.util.stream.InterfaceC0179j1
    public /* synthetic */ InterfaceC0179j1 s(C0287g0 c0287g0) {
        return k0(this.a.filter(AbstractC0289h0.a(c0287g0)));
    }

    @Override // j$.util.stream.BaseStream
    public /* synthetic */ BaseStream sequential() {
        return E0.k0(this.a.sequential());
    }

    @Override // j$.util.stream.InterfaceC0179j1, j$.util.stream.BaseStream
    public /* synthetic */ InterfaceC0179j1 sequential() {
        return k0(this.a.sequential());
    }

    @Override // j$.util.stream.InterfaceC0179j1
    public /* synthetic */ InterfaceC0179j1 skip(long j) {
        return k0(this.a.skip(j));
    }

    @Override // j$.util.stream.InterfaceC0179j1
    public /* synthetic */ InterfaceC0179j1 sorted() {
        return k0(this.a.sorted());
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [java.util.Spliterator$OfLong] */
    @Override // j$.util.stream.InterfaceC0179j1, j$.util.stream.BaseStream
    public /* synthetic */ r.c spliterator() {
        return C0298m.a(this.a.spliterator());
    }

    @Override // j$.util.stream.BaseStream
    public /* synthetic */ j$.util.r spliterator() {
        return C0286g.a(this.a.spliterator());
    }

    @Override // j$.util.stream.InterfaceC0179j1
    public /* synthetic */ long sum() {
        return this.a.sum();
    }

    @Override // j$.util.stream.InterfaceC0179j1
    public C0119h summaryStatistics() {
        this.a.summaryStatistics();
        throw new Error("Java 8+ API desugaring (library desugaring) cannot convert from java.util.LongSummaryStatistics");
    }

    @Override // j$.util.stream.InterfaceC0179j1
    public /* synthetic */ long[] toArray() {
        return this.a.toArray();
    }

    @Override // j$.util.stream.BaseStream
    public /* synthetic */ BaseStream unordered() {
        return E0.k0(this.a.unordered());
    }

    @Override // j$.util.stream.InterfaceC0179j1
    public /* synthetic */ InterfaceC0179j1 x(j$.util.function.v vVar) {
        return k0(this.a.map(n0.a(vVar)));
    }
}
