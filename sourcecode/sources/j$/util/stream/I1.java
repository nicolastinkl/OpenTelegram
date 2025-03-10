package j$.util.stream;

import j$.util.DesugarArrays;
import j$.util.function.Consumer;
import java.util.Arrays;

/* loaded from: classes2.dex */
class I1 implements F1 {
    final Object[] a;
    int b;

    I1(long j, j$.util.function.n nVar) {
        if (j >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        this.a = (Object[]) nVar.apply((int) j);
        this.b = 0;
    }

    I1(Object[] objArr) {
        this.a = objArr;
        this.b = objArr.length;
    }

    @Override // j$.util.stream.F1
    public F1 b(int i) {
        throw new IndexOutOfBoundsException();
    }

    @Override // j$.util.stream.F1
    public long count() {
        return this.b;
    }

    @Override // j$.util.stream.F1
    public void forEach(Consumer consumer) {
        for (int i = 0; i < this.b; i++) {
            consumer.accept(this.a[i]);
        }
    }

    @Override // j$.util.stream.F1
    public void i(Object[] objArr, int i) {
        System.arraycopy(this.a, 0, objArr, i, this.b);
    }

    @Override // j$.util.stream.F1
    public /* synthetic */ int p() {
        return 0;
    }

    @Override // j$.util.stream.F1
    public Object[] q(j$.util.function.n nVar) {
        Object[] objArr = this.a;
        if (objArr.length == this.b) {
            return objArr;
        }
        throw new IllegalStateException();
    }

    @Override // j$.util.stream.F1
    public /* synthetic */ F1 r(long j, long j2, j$.util.function.n nVar) {
        return AbstractC0238t1.q(this, j, j2, nVar);
    }

    @Override // j$.util.stream.F1
    public j$.util.r spliterator() {
        return DesugarArrays.a(this.a, 0, this.b);
    }

    public String toString() {
        return String.format("ArrayNode[%d][%s]", Integer.valueOf(this.a.length - this.b), Arrays.toString(this.a));
    }
}
