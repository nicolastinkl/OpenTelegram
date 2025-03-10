package j$.util.stream;

import j$.util.function.Consumer;

/* renamed from: j$.util.stream.d2, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
final class C0144d2 extends AbstractC0156f2 implements D1 {
    C0144d2() {
    }

    @Override // j$.util.stream.F1
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public /* synthetic */ void i(Long[] lArr, int i) {
        AbstractC0238t1.j(this, lArr, i);
    }

    @Override // j$.util.stream.AbstractC0156f2, j$.util.stream.F1
    public E1 b(int i) {
        throw new IndexOutOfBoundsException();
    }

    @Override // j$.util.stream.E1
    public Object e() {
        long[] jArr;
        jArr = C2.f;
        return jArr;
    }

    @Override // j$.util.stream.AbstractC0156f2, j$.util.stream.F1
    /* renamed from: f, reason: merged with bridge method [inline-methods] */
    public /* synthetic */ D1 r(long j, long j2, j$.util.function.n nVar) {
        return AbstractC0238t1.p(this, j, j2, nVar);
    }

    @Override // j$.util.stream.F1
    public /* synthetic */ void forEach(Consumer consumer) {
        AbstractC0238t1.m(this, consumer);
    }

    @Override // j$.util.stream.F1
    public j$.util.s spliterator() {
        return j$.util.H.d();
    }

    @Override // j$.util.stream.AbstractC0156f2, j$.util.stream.F1
    public /* bridge */ /* synthetic */ F1 b(int i) {
        b(i);
        throw null;
    }

    @Override // j$.util.stream.F1
    public j$.util.r spliterator() {
        return j$.util.H.d();
    }
}
