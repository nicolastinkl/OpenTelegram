package j$.wrappers;

import j$.util.AbstractC0112a;
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
import j$.util.stream.BaseStream;
import j$.util.stream.Collector;
import j$.util.stream.IntStream;
import j$.util.stream.InterfaceC0179j1;
import j$.util.stream.Stream;
import java.util.Comparator;
import java.util.Iterator;

/* renamed from: j$.wrappers.$r8$wrapper$java$util$stream$Stream$-V-WRP, reason: invalid class name */
/* loaded from: classes2.dex */
public final /* synthetic */ class C$r8$wrapper$java$util$stream$Stream$VWRP implements Stream {
    final /* synthetic */ java.util.stream.Stream a;

    private /* synthetic */ C$r8$wrapper$java$util$stream$Stream$VWRP(java.util.stream.Stream stream) {
        this.a = stream;
    }

    public static /* synthetic */ Stream convert(java.util.stream.Stream stream) {
        if (stream == null) {
            return null;
        }
        return stream instanceof M0 ? ((M0) stream).a : new C$r8$wrapper$java$util$stream$Stream$VWRP(stream);
    }

    @Override // j$.util.stream.Stream
    public /* synthetic */ Object A(Object obj, BiFunction biFunction, j$.util.function.b bVar) {
        return this.a.reduce(obj, C$r8$wrapper$java$util$function$BiFunction$WRP.convert(biFunction), C0306u.a(bVar));
    }

    @Override // j$.util.stream.Stream
    public /* synthetic */ j$.util.stream.Z D(Function function) {
        return I0.k0(this.a.flatMapToDouble(C$r8$wrapper$java$util$function$Function$WRP.convert(function)));
    }

    @Override // j$.util.stream.Stream
    public /* synthetic */ Stream T(Consumer consumer) {
        return convert(this.a.peek(C$r8$wrapper$java$util$function$Consumer$WRP.convert(consumer)));
    }

    @Override // j$.util.stream.Stream
    public /* synthetic */ boolean U(Predicate predicate) {
        return this.a.allMatch(v0.a(predicate));
    }

    @Override // j$.util.stream.Stream
    public /* synthetic */ InterfaceC0179j1 V(Function function) {
        return K0.k0(this.a.flatMapToLong(C$r8$wrapper$java$util$function$Function$WRP.convert(function)));
    }

    @Override // j$.util.stream.Stream
    public /* synthetic */ boolean a(Predicate predicate) {
        return this.a.anyMatch(v0.a(predicate));
    }

    @Override // j$.util.stream.Stream
    public /* synthetic */ boolean a0(Predicate predicate) {
        return this.a.noneMatch(v0.a(predicate));
    }

    @Override // j$.util.stream.Stream
    public /* synthetic */ IntStream c(Function function) {
        return C$r8$wrapper$java$util$stream$IntStream$VWRP.convert(this.a.flatMapToInt(C$r8$wrapper$java$util$function$Function$WRP.convert(function)));
    }

    @Override // j$.util.stream.Stream, j$.util.stream.BaseStream, java.lang.AutoCloseable
    public /* synthetic */ void close() {
        this.a.close();
    }

    @Override // j$.util.stream.Stream
    public /* synthetic */ Object collect(Collector collector) {
        return this.a.collect(H0.a(collector));
    }

    @Override // j$.util.stream.Stream
    public /* synthetic */ long count() {
        return this.a.count();
    }

    @Override // j$.util.stream.Stream
    public /* synthetic */ InterfaceC0179j1 d0(ToLongFunction toLongFunction) {
        return K0.k0(this.a.mapToLong(D0.a(toLongFunction)));
    }

    @Override // j$.util.stream.Stream
    public /* synthetic */ Stream distinct() {
        return convert(this.a.distinct());
    }

    @Override // j$.util.stream.Stream
    public /* synthetic */ void e(Consumer consumer) {
        this.a.forEachOrdered(C$r8$wrapper$java$util$function$Consumer$WRP.convert(consumer));
    }

    @Override // j$.util.stream.Stream
    public /* synthetic */ Stream filter(Predicate predicate) {
        return convert(this.a.filter(v0.a(predicate)));
    }

    @Override // j$.util.stream.Stream
    public /* synthetic */ Optional findAny() {
        return AbstractC0112a.m(this.a.findAny());
    }

    @Override // j$.util.stream.Stream
    public /* synthetic */ Optional findFirst() {
        return AbstractC0112a.m(this.a.findFirst());
    }

    @Override // j$.util.stream.Stream
    public /* synthetic */ void forEach(Consumer consumer) {
        this.a.forEach(C$r8$wrapper$java$util$function$Consumer$WRP.convert(consumer));
    }

    @Override // j$.util.stream.Stream
    public /* synthetic */ j$.util.stream.Z g0(ToDoubleFunction toDoubleFunction) {
        return I0.k0(this.a.mapToDouble(z0.a(toDoubleFunction)));
    }

    @Override // j$.util.stream.Stream
    public /* synthetic */ Object i(Supplier supplier, BiConsumer biConsumer, BiConsumer biConsumer2) {
        return this.a.collect(x0.a(supplier), r.a(biConsumer), r.a(biConsumer2));
    }

    @Override // j$.util.stream.BaseStream
    public /* synthetic */ boolean isParallel() {
        return this.a.isParallel();
    }

    @Override // j$.util.stream.BaseStream
    public /* synthetic */ Iterator iterator() {
        return this.a.iterator();
    }

    @Override // j$.util.stream.Stream
    public /* synthetic */ Object j0(Object obj, j$.util.function.b bVar) {
        return this.a.reduce(obj, C0306u.a(bVar));
    }

    @Override // j$.util.stream.Stream
    public /* synthetic */ Object[] l(j$.util.function.n nVar) {
        return this.a.toArray(Q.a(nVar));
    }

    @Override // j$.util.stream.Stream
    public /* synthetic */ Stream limit(long j) {
        return convert(this.a.limit(j));
    }

    @Override // j$.util.stream.Stream
    public /* synthetic */ IntStream m(ToIntFunction toIntFunction) {
        return C$r8$wrapper$java$util$stream$IntStream$VWRP.convert(this.a.mapToInt(B0.a(toIntFunction)));
    }

    @Override // j$.util.stream.Stream
    public /* synthetic */ Stream map(Function function) {
        return convert(this.a.map(C$r8$wrapper$java$util$function$Function$WRP.convert(function)));
    }

    @Override // j$.util.stream.Stream
    public /* synthetic */ Optional max(Comparator comparator) {
        return AbstractC0112a.m(this.a.max(comparator));
    }

    @Override // j$.util.stream.Stream
    public /* synthetic */ Optional min(Comparator comparator) {
        return AbstractC0112a.m(this.a.min(comparator));
    }

    @Override // j$.util.stream.Stream
    public /* synthetic */ Stream n(Function function) {
        return convert(this.a.flatMap(C$r8$wrapper$java$util$function$Function$WRP.convert(function)));
    }

    @Override // j$.util.stream.BaseStream
    public /* synthetic */ BaseStream onClose(Runnable runnable) {
        return E0.k0(this.a.onClose(runnable));
    }

    @Override // j$.util.stream.BaseStream
    public /* synthetic */ BaseStream parallel() {
        return E0.k0(this.a.parallel());
    }

    @Override // j$.util.stream.Stream
    public /* synthetic */ Optional r(j$.util.function.b bVar) {
        return AbstractC0112a.m(this.a.reduce(C0306u.a(bVar)));
    }

    @Override // j$.util.stream.BaseStream
    public /* synthetic */ BaseStream sequential() {
        return E0.k0(this.a.sequential());
    }

    @Override // j$.util.stream.Stream
    public /* synthetic */ Stream skip(long j) {
        return convert(this.a.skip(j));
    }

    @Override // j$.util.stream.Stream
    public /* synthetic */ Stream sorted() {
        return convert(this.a.sorted());
    }

    @Override // j$.util.stream.Stream
    public /* synthetic */ Stream sorted(Comparator comparator) {
        return convert(this.a.sorted(comparator));
    }

    @Override // j$.util.stream.BaseStream
    public /* synthetic */ j$.util.r spliterator() {
        return C0286g.a(this.a.spliterator());
    }

    @Override // j$.util.stream.Stream
    public /* synthetic */ Object[] toArray() {
        return this.a.toArray();
    }

    @Override // j$.util.stream.BaseStream
    public /* synthetic */ BaseStream unordered() {
        return E0.k0(this.a.unordered());
    }
}
