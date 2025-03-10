package j$.util;

import j$.util.function.Consumer;

/* loaded from: classes2.dex */
public interface r {

    public interface a extends s {
        @Override // j$.util.r
        boolean b(Consumer consumer);

        void e(j$.util.function.f fVar);

        @Override // j$.util.r
        void forEachRemaining(Consumer consumer);

        boolean k(j$.util.function.f fVar);

        @Override // j$.util.s, j$.util.r
        a trySplit();
    }

    public interface b extends s {
        @Override // j$.util.r
        boolean b(Consumer consumer);

        void c(j$.util.function.m mVar);

        @Override // j$.util.r
        void forEachRemaining(Consumer consumer);

        boolean g(j$.util.function.m mVar);

        @Override // j$.util.s, j$.util.r
        b trySplit();
    }

    public interface c extends s {
        @Override // j$.util.r
        boolean b(Consumer consumer);

        void d(j$.util.function.s sVar);

        @Override // j$.util.r
        void forEachRemaining(Consumer consumer);

        boolean i(j$.util.function.s sVar);

        @Override // j$.util.s, j$.util.r
        c trySplit();
    }

    boolean b(Consumer consumer);

    int characteristics();

    long estimateSize();

    void forEachRemaining(Consumer consumer);

    java.util.Comparator getComparator();

    long getExactSizeIfKnown();

    boolean hasCharacteristics(int i);

    r trySplit();
}
