package j$.util.stream;

import j$.util.C0117f;
import j$.util.C0120i;
import j$.util.function.BiConsumer;
import j$.util.function.Supplier;
import j$.util.r;

/* loaded from: classes2.dex */
public interface Z extends BaseStream {
    C0120i F(j$.util.function.d dVar);

    Object G(Supplier supplier, j$.util.function.w wVar, BiConsumer biConsumer);

    double J(double d, j$.util.function.d dVar);

    Stream L(j$.util.function.g gVar);

    IntStream Q(j$.wrappers.E e);

    boolean W(j$.wrappers.C c);

    C0120i average();

    Z b(j$.util.function.f fVar);

    Stream boxed();

    long count();

    Z distinct();

    boolean e0(j$.wrappers.C c);

    boolean f0(j$.wrappers.C c);

    C0120i findAny();

    C0120i findFirst();

    void i0(j$.util.function.f fVar);

    @Override // j$.util.stream.BaseStream
    j$.util.l iterator();

    void j(j$.util.function.f fVar);

    Z limit(long j);

    C0120i max();

    C0120i min();

    Z p(j$.wrappers.C c);

    @Override // j$.util.stream.BaseStream
    Z parallel();

    @Override // j$.util.stream.BaseStream
    Z sequential();

    Z skip(long j);

    Z sorted();

    @Override // j$.util.stream.BaseStream
    r.a spliterator();

    double sum();

    C0117f summaryStatistics();

    double[] toArray();

    Z u(j$.util.function.g gVar);

    InterfaceC0179j1 v(j$.util.function.h hVar);

    Z w(j$.wrappers.I i);
}
