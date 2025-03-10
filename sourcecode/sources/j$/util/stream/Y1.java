package j$.util.stream;

import j$.util.function.Consumer;
import java.util.Arrays;

/* loaded from: classes2.dex */
class Y1 implements InterfaceC0271z1 {
    final double[] a;
    int b;

    Y1(long j) {
        if (j >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        this.a = new double[(int) j];
        this.b = 0;
    }

    Y1(double[] dArr) {
        this.a = dArr;
        this.b = dArr.length;
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
        System.arraycopy(this.a, 0, (double[]) obj, i, this.b);
    }

    @Override // j$.util.stream.E1
    public Object e() {
        double[] dArr = this.a;
        int length = dArr.length;
        int i = this.b;
        return length == i ? dArr : Arrays.copyOf(dArr, i);
    }

    @Override // j$.util.stream.F1
    /* renamed from: f, reason: merged with bridge method [inline-methods] */
    public /* synthetic */ void i(Double[] dArr, int i) {
        AbstractC0238t1.h(this, dArr, i);
    }

    @Override // j$.util.stream.F1
    public /* synthetic */ void forEach(Consumer consumer) {
        AbstractC0238t1.k(this, consumer);
    }

    @Override // j$.util.stream.E1
    public void g(Object obj) {
        j$.util.function.f fVar = (j$.util.function.f) obj;
        for (int i = 0; i < this.b; i++) {
            fVar.accept(this.a[i]);
        }
    }

    @Override // j$.util.stream.F1
    /* renamed from: k, reason: merged with bridge method [inline-methods] */
    public /* synthetic */ InterfaceC0271z1 r(long j, long j2, j$.util.function.n nVar) {
        return AbstractC0238t1.n(this, j, j2, nVar);
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
        return j$.util.H.j(this.a, 0, this.b, 1040);
    }

    public String toString() {
        return String.format("DoubleArrayNode[%d][%s]", Integer.valueOf(this.a.length - this.b), Arrays.toString(this.a));
    }

    @Override // j$.util.stream.F1
    public /* bridge */ /* synthetic */ F1 b(int i) {
        b(i);
        throw null;
    }

    @Override // j$.util.stream.F1
    public j$.util.r spliterator() {
        return j$.util.H.j(this.a, 0, this.b, 1040);
    }
}
