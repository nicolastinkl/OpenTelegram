package j$.util.stream;

import j$.util.function.Consumer;

/* loaded from: classes2.dex */
final class U1 extends W1 implements B1 {
    U1(B1 b1, B1 b12) {
        super(b1, b12);
    }

    @Override // j$.util.stream.F1
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public /* synthetic */ void i(Integer[] numArr, int i) {
        AbstractC0238t1.i(this, numArr, i);
    }

    @Override // j$.util.stream.E1
    /* renamed from: f, reason: merged with bridge method [inline-methods] */
    public int[] c(int i) {
        return new int[i];
    }

    @Override // j$.util.stream.F1
    public /* synthetic */ void forEach(Consumer consumer) {
        AbstractC0238t1.l(this, consumer);
    }

    @Override // j$.util.stream.F1
    /* renamed from: h, reason: merged with bridge method [inline-methods] */
    public /* synthetic */ B1 r(long j, long j2, j$.util.function.n nVar) {
        return AbstractC0238t1.o(this, j, j2, nVar);
    }

    @Override // j$.util.stream.F1
    public j$.util.s spliterator() {
        return new C0192l2(this);
    }

    @Override // j$.util.stream.F1
    public j$.util.r spliterator() {
        return new C0192l2(this);
    }
}
