package j$.util.stream;

import j$.util.function.Consumer;

/* loaded from: classes2.dex */
final class T1 extends W1 implements InterfaceC0271z1 {
    T1(InterfaceC0271z1 interfaceC0271z1, InterfaceC0271z1 interfaceC0271z12) {
        super(interfaceC0271z1, interfaceC0271z12);
    }

    @Override // j$.util.stream.F1
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public /* synthetic */ void i(Double[] dArr, int i) {
        AbstractC0238t1.h(this, dArr, i);
    }

    @Override // j$.util.stream.E1
    /* renamed from: f, reason: merged with bridge method [inline-methods] */
    public double[] c(int i) {
        return new double[i];
    }

    @Override // j$.util.stream.F1
    public /* synthetic */ void forEach(Consumer consumer) {
        AbstractC0238t1.k(this, consumer);
    }

    @Override // j$.util.stream.F1
    /* renamed from: h, reason: merged with bridge method [inline-methods] */
    public /* synthetic */ InterfaceC0271z1 r(long j, long j2, j$.util.function.n nVar) {
        return AbstractC0238t1.n(this, j, j2, nVar);
    }

    @Override // j$.util.stream.F1
    public j$.util.s spliterator() {
        return new C0186k2(this);
    }

    @Override // j$.util.stream.F1
    public j$.util.r spliterator() {
        return new C0186k2(this);
    }
}
