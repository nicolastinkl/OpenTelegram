package j$.util.stream;

import j$.util.function.Consumer;
import java.util.Objects;

/* loaded from: classes2.dex */
final class X1 extends H1 {
    X1(F1 f1, F1 f12) {
        super(f1, f12);
    }

    @Override // j$.util.stream.F1
    public void forEach(Consumer consumer) {
        this.a.forEach(consumer);
        this.b.forEach(consumer);
    }

    @Override // j$.util.stream.F1
    public void i(Object[] objArr, int i) {
        Objects.requireNonNull(objArr);
        this.a.i(objArr, i);
        this.b.i(objArr, i + ((int) this.a.count()));
    }

    @Override // j$.util.stream.F1
    public Object[] q(j$.util.function.n nVar) {
        long count = count();
        if (count >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        Object[] objArr = (Object[]) nVar.apply((int) count);
        i(objArr, 0);
        return objArr;
    }

    @Override // j$.util.stream.F1
    public F1 r(long j, long j2, j$.util.function.n nVar) {
        if (j == 0 && j2 == count()) {
            return this;
        }
        long count = this.a.count();
        return j >= count ? this.b.r(j - count, j2 - count, nVar) : j2 <= count ? this.a.r(j, j2, nVar) : C2.i(EnumC0182j4.REFERENCE, this.a.r(j, count, nVar), this.b.r(0L, j2 - count, nVar));
    }

    @Override // j$.util.stream.F1
    public j$.util.r spliterator() {
        return new C0210o2(this);
    }

    public String toString() {
        return count() < 32 ? String.format("ConcNode[%s.%s]", this.a, this.b) : String.format("ConcNode[size=%d]", Long.valueOf(count()));
    }
}
