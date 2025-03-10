package j$.util.stream;

import j$.util.function.Consumer;
import java.util.Arrays;

/* renamed from: j$.util.stream.q2, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
class C0222q2 implements D1 {
    final long[] a;
    int b;

    C0222q2(long j) {
        if (j >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        this.a = new long[(int) j];
        this.b = 0;
    }

    C0222q2(long[] jArr) {
        this.a = jArr;
        this.b = jArr.length;
    }

    @Override // j$.util.stream.E1, j$.util.stream.F1
    public E1 b(int i) {
        throw new IndexOutOfBoundsException();
    }

    @Override // j$.util.stream.F1
    public long count() {
        return this.b;
    }

    @Override // j$.util.stream.E1
    public void d(Object obj, int i) {
        System.arraycopy(this.a, 0, (long[]) obj, i, this.b);
    }

    @Override // j$.util.stream.E1
    public Object e() {
        long[] jArr = this.a;
        int length = jArr.length;
        int i = this.b;
        return length == i ? jArr : Arrays.copyOf(jArr, i);
    }

    @Override // j$.util.stream.F1
    public /* synthetic */ void forEach(Consumer consumer) {
        AbstractC0238t1.m(this, consumer);
    }

    @Override // j$.util.stream.E1
    public void g(Object obj) {
        j$.util.function.s sVar = (j$.util.function.s) obj;
        for (int i = 0; i < this.b; i++) {
            sVar.accept(this.a[i]);
        }
    }

    @Override // j$.util.stream.F1
    /* renamed from: j, reason: merged with bridge method [inline-methods] */
    public /* synthetic */ void i(Long[] lArr, int i) {
        AbstractC0238t1.j(this, lArr, i);
    }

    @Override // j$.util.stream.F1
    /* renamed from: k, reason: merged with bridge method [inline-methods] */
    public /* synthetic */ D1 r(long j, long j2, j$.util.function.n nVar) {
        return AbstractC0238t1.p(this, j, j2, nVar);
    }

    @Override // j$.util.stream.F1
    public /* synthetic */ int p() {
        return 0;
    }

    @Override // j$.util.stream.F1
    public /* synthetic */ Object[] q(j$.util.function.n nVar) {
        return AbstractC0238t1.g(this, nVar);
    }

    @Override // j$.util.stream.E1, j$.util.stream.F1
    public j$.util.s spliterator() {
        return j$.util.H.l(this.a, 0, this.b, 1040);
    }

    public String toString() {
        return String.format("LongArrayNode[%d][%s]", Integer.valueOf(this.a.length - this.b), Arrays.toString(this.a));
    }

    @Override // j$.util.stream.F1
    public /* bridge */ /* synthetic */ F1 b(int i) {
        b(i);
        throw null;
    }

    @Override // j$.util.stream.F1
    public j$.util.r spliterator() {
        return j$.util.H.l(this.a, 0, this.b, 1040);
    }
}
