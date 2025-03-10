package j$.util.stream;

import j$.util.function.Consumer;

/* loaded from: classes2.dex */
final class V1 extends W1 implements D1 {
    V1(D1 d1, D1 d12) {
        super(d1, d12);
    }

    @Override // j$.util.stream.F1
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public /* synthetic */ void i(Long[] lArr, int i) {
        AbstractC0238t1.j(this, lArr, i);
    }

    @Override // j$.util.stream.E1
    /* renamed from: f, reason: merged with bridge method [inline-methods] */
    public long[] c(int i) {
        return new long[i];
    }

    @Override // j$.util.stream.F1
    public /* synthetic */ void forEach(Consumer consumer) {
        AbstractC0238t1.m(this, consumer);
    }

    @Override // j$.util.stream.F1
    /* renamed from: h, reason: merged with bridge method [inline-methods] */
    public /* synthetic */ D1 r(long j, long j2, j$.util.function.n nVar) {
        return AbstractC0238t1.p(this, j, j2, nVar);
    }

    @Override // j$.util.stream.F1
    public j$.util.s spliterator() {
        return new C0198m2(this);
    }

    @Override // j$.util.stream.F1
    public j$.util.r spliterator() {
        return new C0198m2(this);
    }
}
