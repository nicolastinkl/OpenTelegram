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
import java.util.Comparator;

/* loaded from: classes2.dex */
public interface Stream<T> extends BaseStream<T, Stream<T>> {
    Object A(Object obj, BiFunction biFunction, j$.util.function.b bVar);

    Z D(Function function);

    Stream T(Consumer consumer);

    boolean U(Predicate predicate);

    InterfaceC0179j1 V(Function function);

    boolean a(Predicate predicate);

    boolean a0(Predicate predicate);

    IntStream c(Function function);

    @Override // j$.util.stream.BaseStream, java.lang.AutoCloseable
    /* synthetic */ void close();

    <R, A> R collect(Collector<? super T, A, R> collector);

    long count();

    InterfaceC0179j1 d0(ToLongFunction toLongFunction);

    Stream distinct();

    void e(Consumer consumer);

    Stream<T> filter(Predicate<? super T> predicate);

    Optional findAny();

    Optional findFirst();

    void forEach(Consumer<? super T> consumer);

    Z g0(ToDoubleFunction toDoubleFunction);

    Object i(Supplier supplier, BiConsumer biConsumer, BiConsumer biConsumer2);

    Object j0(Object obj, j$.util.function.b bVar);

    Object[] l(j$.util.function.n nVar);

    Stream limit(long j);

    IntStream m(ToIntFunction toIntFunction);

    <R> Stream<R> map(Function<? super T, ? extends R> function);

    Optional max(Comparator comparator);

    Optional min(Comparator comparator);

    Stream n(Function function);

    Optional r(j$.util.function.b bVar);

    Stream<T> skip(long j);

    Stream sorted();

    Stream<T> sorted(Comparator<? super T> comparator);

    Object[] toArray();
}
