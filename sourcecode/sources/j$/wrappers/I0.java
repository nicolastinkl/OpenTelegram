package j$.wrappers;

import j$.util.AbstractC0112a;
import j$.util.C0117f;
import j$.util.C0120i;
import j$.util.function.BiConsumer;
import j$.util.function.Supplier;
import j$.util.r;
import j$.util.stream.BaseStream;
import j$.util.stream.IntStream;
import j$.util.stream.InterfaceC0179j1;
import j$.util.stream.Stream;
import java.util.Iterator;
import java.util.stream.DoubleStream;

/* loaded from: classes2.dex */
public final /* synthetic */ class I0 implements j$.util.stream.Z {
    final /* synthetic */ DoubleStream a;

    private /* synthetic */ I0(DoubleStream doubleStream) {
        this.a = doubleStream;
    }

    public static /* synthetic */ j$.util.stream.Z k0(DoubleStream doubleStream) {
        if (doubleStream == null) {
            return null;
        }
        return doubleStream instanceof J0 ? ((J0) doubleStream).a : new I0(doubleStream);
    }

    @Override // j$.util.stream.Z
    public /* synthetic */ C0120i F(j$.util.function.d dVar) {
        return AbstractC0112a.n(this.a.reduce(C0309x.a(dVar)));
    }

    @Override // j$.util.stream.Z
    public /* synthetic */ Object G(Supplier supplier, j$.util.function.w wVar, BiConsumer biConsumer) {
        return this.a.collect(x0.a(supplier), p0.a(wVar), r.a(biConsumer));
    }

    @Override // j$.util.stream.Z
    public /* synthetic */ double J(double d, j$.util.function.d dVar) {
        return this.a.reduce(d, C0309x.a(dVar));
    }

    @Override // j$.util.stream.Z
    public /* synthetic */ Stream L(j$.util.function.g gVar) {
        return C$r8$wrapper$java$util$stream$Stream$VWRP.convert(this.a.mapToObj(B.a(gVar)));
    }

    @Override // j$.util.stream.Z
    public /* synthetic */ IntStream Q(E e) {
        return C$r8$wrapper$java$util$stream$IntStream$VWRP.convert(this.a.mapToInt(e == null ? null : e.a));
    }

    @Override // j$.util.stream.Z
    public /* synthetic */ boolean W(C c) {
        return this.a.allMatch(D.a(c));
    }

    @Override // j$.util.stream.Z
    public /* synthetic */ C0120i average() {
        return AbstractC0112a.n(this.a.average());
    }

    @Override // j$.util.stream.Z
    public /* synthetic */ j$.util.stream.Z b(j$.util.function.f fVar) {
        return k0(this.a.peek(C0311z.a(fVar)));
    }

    @Override // j$.util.stream.Z
    public /* synthetic */ Stream boxed() {
        return C$r8$wrapper$java$util$stream$Stream$VWRP.convert(this.a.boxed());
    }

    @Override // j$.util.stream.BaseStream, java.lang.AutoCloseable
    public /* synthetic */ void close() {
        this.a.close();
    }

    @Override // j$.util.stream.Z
    public /* synthetic */ long count() {
        return this.a.count();
    }

    @Override // j$.util.stream.Z
    public /* synthetic */ j$.util.stream.Z distinct() {
        return k0(this.a.distinct());
    }

    @Override // j$.util.stream.Z
    public /* synthetic */ boolean e0(C c) {
        return this.a.anyMatch(D.a(c));
    }

    @Override // j$.util.stream.Z
    public /* synthetic */ boolean f0(C c) {
        return this.a.noneMatch(D.a(c));
    }

    @Override // j$.util.stream.Z
    public /* synthetic */ C0120i findAny() {
        return AbstractC0112a.n(this.a.findAny());
    }

    @Override // j$.util.stream.Z
    public /* synthetic */ C0120i findFirst() {
        return AbstractC0112a.n(this.a.findFirst());
    }

    @Override // j$.util.stream.Z
    public /* synthetic */ void i0(j$.util.function.f fVar) {
        this.a.forEachOrdered(C0311z.a(fVar));
    }

    @Override // j$.util.stream.BaseStream
    public /* synthetic */ boolean isParallel() {
        return this.a.isParallel();
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [java.util.PrimitiveIterator$OfDouble] */
    @Override // j$.util.stream.Z, j$.util.stream.BaseStream
    public /* synthetic */ j$.util.l iterator() {
        return C0274a.a(this.a.iterator());
    }

    @Override // j$.util.stream.BaseStream
    public /* synthetic */ Iterator iterator() {
        return this.a.iterator();
    }

    @Override // j$.util.stream.Z
    public /* synthetic */ void j(j$.util.function.f fVar) {
        this.a.forEach(C0311z.a(fVar));
    }

    @Override // j$.util.stream.Z
    public /* synthetic */ j$.util.stream.Z limit(long j) {
        return k0(this.a.limit(j));
    }

    @Override // j$.util.stream.Z
    public /* synthetic */ C0120i max() {
        return AbstractC0112a.n(this.a.max());
    }

    @Override // j$.util.stream.Z
    public /* synthetic */ C0120i min() {
        return AbstractC0112a.n(this.a.min());
    }

    @Override // j$.util.stream.BaseStream
    public /* synthetic */ BaseStream onClose(Runnable runnable) {
        return E0.k0(this.a.onClose(runnable));
    }

    @Override // j$.util.stream.Z
    public /* synthetic */ j$.util.stream.Z p(C c) {
        return k0(this.a.filter(D.a(c)));
    }

    @Override // j$.util.stream.BaseStream
    public /* synthetic */ BaseStream parallel() {
        return E0.k0(this.a.parallel());
    }

    @Override // j$.util.stream.Z, j$.util.stream.BaseStream
    public /* synthetic */ j$.util.stream.Z parallel() {
        return k0(this.a.parallel());
    }

    @Override // j$.util.stream.BaseStream
    public /* synthetic */ BaseStream sequential() {
        return E0.k0(this.a.sequential());
    }

    @Override // j$.util.stream.Z, j$.util.stream.BaseStream
    public /* synthetic */ j$.util.stream.Z sequential() {
        return k0(this.a.sequential());
    }

    @Override // j$.util.stream.Z
    public /* synthetic */ j$.util.stream.Z skip(long j) {
        return k0(this.a.skip(j));
    }

    @Override // j$.util.stream.Z
    public /* synthetic */ j$.util.stream.Z sorted() {
        return k0(this.a.sorted());
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [java.util.Spliterator$OfDouble] */
    @Override // j$.util.stream.Z, j$.util.stream.BaseStream
    public /* synthetic */ r.a spliterator() {
        return C0290i.a(this.a.spliterator());
    }

    @Override // j$.util.stream.BaseStream
    public /* synthetic */ j$.util.r spliterator() {
        return C0286g.a(this.a.spliterator());
    }

    @Override // j$.util.stream.Z
    public /* synthetic */ double sum() {
        return this.a.sum();
    }

    @Override // j$.util.stream.Z
    public C0117f summaryStatistics() {
        this.a.summaryStatistics();
        throw new Error("Java 8+ API desugaring (library desugaring) cannot convert from java.util.DoubleSummaryStatistics");
    }

    @Override // j$.util.stream.Z
    public /* synthetic */ double[] toArray() {
        return this.a.toArray();
    }

    @Override // j$.util.stream.Z
    public /* synthetic */ j$.util.stream.Z u(j$.util.function.g gVar) {
        return k0(this.a.flatMap(B.a(gVar)));
    }

    @Override // j$.util.stream.BaseStream
    public /* synthetic */ BaseStream unordered() {
        return E0.k0(this.a.unordered());
    }

    @Override // j$.util.stream.Z
    public /* synthetic */ InterfaceC0179j1 v(j$.util.function.h hVar) {
        return K0.k0(this.a.mapToLong(H.a(hVar)));
    }

    @Override // j$.util.stream.Z
    public /* synthetic */ j$.util.stream.Z w(I i) {
        return k0(this.a.map(J.a(i)));
    }
}
