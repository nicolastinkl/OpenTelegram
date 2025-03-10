package j$.util.stream;

import j$.util.function.Consumer;

/* renamed from: j$.util.stream.b2, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
final class C0132b2 extends AbstractC0156f2 implements InterfaceC0271z1 {
    C0132b2() {
    }

    @Override // j$.util.stream.F1
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public /* synthetic */ void i(Double[] dArr, int i) {
        AbstractC0238t1.h(this, dArr, i);
    }

    @Override // j$.util.stream.AbstractC0156f2, j$.util.stream.F1
    public E1 b(int i) {
        throw new IndexOutOfBoundsException();
    }

    @Override // j$.util.stream.E1
    public Object e() {
        double[] dArr;
        dArr = C2.g;
        return dArr;
    }

    @Override // j$.util.stream.AbstractC0156f2, j$.util.stream.F1
    /* renamed from: f, reason: merged with bridge method [inline-methods] */
    public /* synthetic */ InterfaceC0271z1 r(long j, long j2, j$.util.function.n nVar) {
        return AbstractC0238t1.n(this, j, j2, nVar);
    }

    @Override // j$.util.stream.F1
    public /* synthetic */ void forEach(Consumer consumer) {
        AbstractC0238t1.k(this, consumer);
    }

    @Override // j$.util.stream.F1
    public j$.util.s spliterator() {
        return j$.util.H.b();
    }

    @Override // j$.util.stream.AbstractC0156f2, j$.util.stream.F1
    public /* bridge */ /* synthetic */ F1 b(int i) {
        b(i);
        throw null;
    }

    @Override // j$.util.stream.F1
    public j$.util.r spliterator() {
        return j$.util.H.b();
    }
}
