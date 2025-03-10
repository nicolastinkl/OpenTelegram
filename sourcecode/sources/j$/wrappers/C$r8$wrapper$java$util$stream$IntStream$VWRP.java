package j$.wrappers;

import j$.util.AbstractC0112a;
import j$.util.C0118g;
import j$.util.C0120i;
import j$.util.OptionalInt;
import j$.util.function.BiConsumer;
import j$.util.function.IntUnaryOperator;
import j$.util.function.Supplier;
import j$.util.r;
import j$.util.stream.BaseStream;
import j$.util.stream.IntStream;
import j$.util.stream.InterfaceC0179j1;
import j$.util.stream.Stream;
import java.util.Iterator;

/* renamed from: j$.wrappers.$r8$wrapper$java$util$stream$IntStream$-V-WRP, reason: invalid class name */
/* loaded from: classes2.dex */
public final /* synthetic */ class C$r8$wrapper$java$util$stream$IntStream$VWRP implements IntStream {
    final /* synthetic */ java.util.stream.IntStream a;

    private /* synthetic */ C$r8$wrapper$java$util$stream$IntStream$VWRP(java.util.stream.IntStream intStream) {
        this.a = intStream;
    }

    public static /* synthetic */ IntStream convert(java.util.stream.IntStream intStream) {
        if (intStream == null) {
            return null;
        }
        return intStream instanceof C$r8$wrapper$java$util$stream$IntStream$WRP ? ((C$r8$wrapper$java$util$stream$IntStream$WRP) intStream).a : new C$r8$wrapper$java$util$stream$IntStream$VWRP(intStream);
    }

    @Override // j$.util.stream.IntStream
    public /* synthetic */ boolean B(S s) {
        return this.a.allMatch(T.a(s));
    }

    @Override // j$.util.stream.IntStream
    public /* synthetic */ boolean E(S s) {
        return this.a.anyMatch(T.a(s));
    }

    @Override // j$.util.stream.IntStream
    public /* synthetic */ void H(j$.util.function.m mVar) {
        this.a.forEachOrdered(O.a(mVar));
    }

    @Override // j$.util.stream.IntStream
    public /* synthetic */ Stream I(j$.util.function.n nVar) {
        return C$r8$wrapper$java$util$stream$Stream$VWRP.convert(this.a.mapToObj(Q.a(nVar)));
    }

    @Override // j$.util.stream.IntStream
    public /* synthetic */ int M(int i, j$.util.function.k kVar) {
        return this.a.reduce(i, M.a(kVar));
    }

    @Override // j$.util.stream.IntStream
    public /* synthetic */ IntStream O(j$.util.function.n nVar) {
        return convert(this.a.flatMap(Q.a(nVar)));
    }

    @Override // j$.util.stream.IntStream
    public /* synthetic */ void S(j$.util.function.m mVar) {
        this.a.forEach(O.a(mVar));
    }

    @Override // j$.util.stream.IntStream
    public /* synthetic */ OptionalInt Y(j$.util.function.k kVar) {
        return AbstractC0112a.o(this.a.reduce(M.a(kVar)));
    }

    @Override // j$.util.stream.IntStream
    public /* synthetic */ IntStream Z(j$.util.function.m mVar) {
        return convert(this.a.peek(O.a(mVar)));
    }

    @Override // j$.util.stream.IntStream
    public /* synthetic */ j$.util.stream.Z asDoubleStream() {
        return I0.k0(this.a.asDoubleStream());
    }

    @Override // j$.util.stream.IntStream
    public /* synthetic */ InterfaceC0179j1 asLongStream() {
        return K0.k0(this.a.asLongStream());
    }

    @Override // j$.util.stream.IntStream
    public /* synthetic */ C0120i average() {
        return AbstractC0112a.n(this.a.average());
    }

    @Override // j$.util.stream.IntStream
    public /* synthetic */ Stream boxed() {
        return C$r8$wrapper$java$util$stream$Stream$VWRP.convert(this.a.boxed());
    }

    @Override // j$.util.stream.BaseStream, java.lang.AutoCloseable
    public /* synthetic */ void close() {
        this.a.close();
    }

    @Override // j$.util.stream.IntStream
    public /* synthetic */ long count() {
        return this.a.count();
    }

    @Override // j$.util.stream.IntStream
    public /* synthetic */ IntStream distinct() {
        return convert(this.a.distinct());
    }

    @Override // j$.util.stream.IntStream
    public /* synthetic */ InterfaceC0179j1 f(j$.util.function.o oVar) {
        return K0.k0(this.a.mapToLong(X.a(oVar)));
    }

    @Override // j$.util.stream.IntStream
    public /* synthetic */ OptionalInt findAny() {
        return AbstractC0112a.o(this.a.findAny());
    }

    @Override // j$.util.stream.IntStream
    public /* synthetic */ OptionalInt findFirst() {
        return AbstractC0112a.o(this.a.findFirst());
    }

    @Override // j$.util.stream.IntStream
    public /* synthetic */ IntStream h(S s) {
        return convert(this.a.filter(T.a(s)));
    }

    @Override // j$.util.stream.IntStream
    public /* synthetic */ Object h0(Supplier supplier, j$.util.function.x xVar, BiConsumer biConsumer) {
        return this.a.collect(x0.a(supplier), r0.a(xVar), r.a(biConsumer));
    }

    @Override // j$.util.stream.BaseStream
    public /* synthetic */ boolean isParallel() {
        return this.a.isParallel();
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [java.util.PrimitiveIterator$OfInt] */
    @Override // j$.util.stream.IntStream, j$.util.stream.BaseStream
    public /* synthetic */ Iterator<Integer> iterator() {
        return C0278c.a(this.a.iterator());
    }

    @Override // j$.util.stream.BaseStream
    /* renamed from: iterator, reason: avoid collision after fix types in other method */
    public /* synthetic */ Iterator<Integer> iterator2() {
        return this.a.iterator();
    }

    @Override // j$.util.stream.IntStream
    public /* synthetic */ IntStream limit(long j) {
        return convert(this.a.limit(j));
    }

    @Override // j$.util.stream.IntStream
    public /* synthetic */ OptionalInt max() {
        return AbstractC0112a.o(this.a.max());
    }

    @Override // j$.util.stream.IntStream
    public /* synthetic */ OptionalInt min() {
        return AbstractC0112a.o(this.a.min());
    }

    @Override // j$.util.stream.BaseStream
    public /* synthetic */ BaseStream onClose(Runnable runnable) {
        return E0.k0(this.a.onClose(runnable));
    }

    @Override // j$.util.stream.BaseStream
    public /* synthetic */ BaseStream parallel() {
        return E0.k0(this.a.parallel());
    }

    @Override // j$.util.stream.IntStream, j$.util.stream.BaseStream
    public /* synthetic */ IntStream parallel() {
        return convert(this.a.parallel());
    }

    @Override // j$.util.stream.BaseStream
    public /* synthetic */ BaseStream sequential() {
        return E0.k0(this.a.sequential());
    }

    @Override // j$.util.stream.IntStream, j$.util.stream.BaseStream
    public /* synthetic */ IntStream sequential() {
        return convert(this.a.sequential());
    }

    @Override // j$.util.stream.IntStream
    public /* synthetic */ IntStream skip(long j) {
        return convert(this.a.skip(j));
    }

    @Override // j$.util.stream.IntStream
    public /* synthetic */ IntStream sorted() {
        return convert(this.a.sorted());
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [java.util.Spliterator$OfInt] */
    @Override // j$.util.stream.IntStream, j$.util.stream.BaseStream
    public /* synthetic */ r.b spliterator() {
        return C0294k.a(this.a.spliterator());
    }

    @Override // j$.util.stream.BaseStream
    public /* synthetic */ j$.util.r spliterator() {
        return C0286g.a(this.a.spliterator());
    }

    @Override // j$.util.stream.IntStream
    public /* synthetic */ int sum() {
        return this.a.sum();
    }

    @Override // j$.util.stream.IntStream
    public C0118g summaryStatistics() {
        this.a.summaryStatistics();
        throw new Error("Java 8+ API desugaring (library desugaring) cannot convert from java.util.IntSummaryStatistics");
    }

    @Override // j$.util.stream.IntStream
    public /* synthetic */ boolean t(S s) {
        return this.a.noneMatch(T.a(s));
    }

    @Override // j$.util.stream.IntStream
    public /* synthetic */ int[] toArray() {
        return this.a.toArray();
    }

    @Override // j$.util.stream.BaseStream
    public /* synthetic */ BaseStream unordered() {
        return E0.k0(this.a.unordered());
    }

    @Override // j$.util.stream.IntStream
    public /* synthetic */ j$.util.stream.Z y(U u) {
        return I0.k0(this.a.mapToDouble(u == null ? null : u.a));
    }

    @Override // j$.util.stream.IntStream
    public /* synthetic */ IntStream z(IntUnaryOperator intUnaryOperator) {
        return convert(this.a.map(Z.a(intUnaryOperator)));
    }
}
