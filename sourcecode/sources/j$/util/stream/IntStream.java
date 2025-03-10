package j$.util.stream;

import j$.util.C0118g;
import j$.util.C0120i;
import j$.util.OptionalInt;
import j$.util.function.BiConsumer;
import j$.util.function.IntUnaryOperator;
import j$.util.function.Supplier;
import j$.util.r;
import java.util.Iterator;

/* loaded from: classes2.dex */
public interface IntStream extends BaseStream<Integer, IntStream> {
    boolean B(j$.wrappers.S s);

    boolean E(j$.wrappers.S s);

    void H(j$.util.function.m mVar);

    Stream I(j$.util.function.n nVar);

    int M(int i, j$.util.function.k kVar);

    IntStream O(j$.util.function.n nVar);

    void S(j$.util.function.m mVar);

    OptionalInt Y(j$.util.function.k kVar);

    IntStream Z(j$.util.function.m mVar);

    Z asDoubleStream();

    InterfaceC0179j1 asLongStream();

    C0120i average();

    Stream boxed();

    long count();

    IntStream distinct();

    InterfaceC0179j1 f(j$.util.function.o oVar);

    OptionalInt findAny();

    OptionalInt findFirst();

    IntStream h(j$.wrappers.S s);

    Object h0(Supplier supplier, j$.util.function.x xVar, BiConsumer biConsumer);

    @Override // j$.util.stream.BaseStream
    Iterator<Integer> iterator();

    IntStream limit(long j);

    OptionalInt max();

    OptionalInt min();

    @Override // j$.util.stream.BaseStream
    IntStream parallel();

    @Override // j$.util.stream.BaseStream
    IntStream sequential();

    IntStream skip(long j);

    IntStream sorted();

    @Override // j$.util.stream.BaseStream
    r.b spliterator();

    int sum();

    C0118g summaryStatistics();

    boolean t(j$.wrappers.S s);

    int[] toArray();

    Z y(j$.wrappers.U u);

    IntStream z(IntUnaryOperator intUnaryOperator);
}
