package j$.util.stream;

import j$.util.C0119h;
import j$.util.C0120i;
import j$.util.C0121j;
import j$.util.function.BiConsumer;
import j$.util.function.Supplier;
import j$.util.r;
import j$.wrappers.C0287g0;
import j$.wrappers.C0291i0;
import j$.wrappers.C0295k0;

/* renamed from: j$.util.stream.j1, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public interface InterfaceC0179j1 extends BaseStream {
    long C(long j, j$.util.function.q qVar);

    boolean K(C0287g0 c0287g0);

    Z N(C0291i0 c0291i0);

    Stream P(j$.util.function.t tVar);

    boolean R(C0287g0 c0287g0);

    void X(j$.util.function.s sVar);

    Z asDoubleStream();

    C0120i average();

    IntStream b0(C0295k0 c0295k0);

    Stream boxed();

    Object c0(Supplier supplier, j$.util.function.y yVar, BiConsumer biConsumer);

    long count();

    void d(j$.util.function.s sVar);

    InterfaceC0179j1 distinct();

    C0121j findAny();

    C0121j findFirst();

    C0121j g(j$.util.function.q qVar);

    @Override // j$.util.stream.BaseStream
    j$.util.p iterator();

    boolean k(C0287g0 c0287g0);

    InterfaceC0179j1 limit(long j);

    C0121j max();

    C0121j min();

    InterfaceC0179j1 o(j$.util.function.s sVar);

    @Override // j$.util.stream.BaseStream
    InterfaceC0179j1 parallel();

    InterfaceC0179j1 q(j$.util.function.t tVar);

    InterfaceC0179j1 s(C0287g0 c0287g0);

    @Override // j$.util.stream.BaseStream
    InterfaceC0179j1 sequential();

    InterfaceC0179j1 skip(long j);

    InterfaceC0179j1 sorted();

    @Override // j$.util.stream.BaseStream
    r.c spliterator();

    long sum();

    C0119h summaryStatistics();

    long[] toArray();

    InterfaceC0179j1 x(j$.util.function.v vVar);
}
