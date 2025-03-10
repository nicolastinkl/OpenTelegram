package j$.util.stream;

import j$.util.function.Consumer;
import java.util.Arrays;

/* renamed from: j$.util.stream.h2, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
class C0168h2 implements B1 {
    final int[] a;
    int b;

    C0168h2(long j) {
        if (j >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        this.a = new int[(int) j];
        this.b = 0;
    }

    C0168h2(int[] iArr) {
        this.a = iArr;
        this.b = iArr.length;
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
        System.arraycopy(this.a, 0, (int[]) obj, i, this.b);
    }

    @Override // j$.util.stream.E1
    public Object e() {
        int[] iArr = this.a;
        int length = iArr.length;
        int i = this.b;
        return length == i ? iArr : Arrays.copyOf(iArr, i);
    }

    @Override // j$.util.stream.F1
    /* renamed from: f, reason: merged with bridge method [inline-methods] */
    public /* synthetic */ void i(Integer[] numArr, int i) {
        AbstractC0238t1.i(this, numArr, i);
    }

    @Override // j$.util.stream.F1
    public /* synthetic */ void forEach(Consumer consumer) {
        AbstractC0238t1.l(this, consumer);
    }

    @Override // j$.util.stream.E1
    public void g(Object obj) {
        j$.util.function.m mVar = (j$.util.function.m) obj;
        for (int i = 0; i < this.b; i++) {
            mVar.accept(this.a[i]);
        }
    }

    @Override // j$.util.stream.F1
    /* renamed from: j, reason: merged with bridge method [inline-methods] */
    public /* synthetic */ B1 r(long j, long j2, j$.util.function.n nVar) {
        return AbstractC0238t1.o(this, j, j2, nVar);
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
        return j$.util.H.k(this.a, 0, this.b, 1040);
    }

    public String toString() {
        return String.format("IntArrayNode[%d][%s]", Integer.valueOf(this.a.length - this.b), Arrays.toString(this.a));
    }

    @Override // j$.util.stream.F1
    public /* bridge */ /* synthetic */ F1 b(int i) {
        b(i);
        throw null;
    }

    @Override // j$.util.stream.F1
    public j$.util.r spliterator() {
        return j$.util.H.k(this.a, 0, this.b, 1040);
    }
}
