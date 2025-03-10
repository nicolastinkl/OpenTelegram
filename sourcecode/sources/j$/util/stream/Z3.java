package j$.util.stream;

import j$.util.function.Consumer;
import j$.util.r;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

/* loaded from: classes2.dex */
class Z3 extends AbstractC0152e4 implements j$.util.function.f {
    Z3() {
    }

    Z3(int i) {
        super(i);
    }

    @Override // j$.util.stream.AbstractC0152e4, java.lang.Iterable, j$.lang.e
    /* renamed from: B, reason: merged with bridge method [inline-methods] */
    public r.a spliterator() {
        return new Y3(this, 0, this.c, 0, this.b);
    }

    @Override // j$.util.function.f
    public void accept(double d) {
        A();
        double[] dArr = (double[]) this.e;
        int i = this.b;
        this.b = i + 1;
        dArr[i] = d;
    }

    @Override // j$.util.stream.AbstractC0152e4
    public Object c(int i) {
        return new double[i];
    }

    @Override // j$.lang.e
    public void forEach(Consumer consumer) {
        if (consumer instanceof j$.util.function.f) {
            g((j$.util.function.f) consumer);
        } else {
            if (V4.a) {
                V4.a(getClass(), "{0} calling SpinedBuffer.OfDouble.forEach(Consumer)");
                throw null;
            }
            spliterator().forEachRemaining(consumer);
        }
    }

    @Override // java.lang.Iterable
    public Iterator iterator() {
        return j$.util.H.f(spliterator());
    }

    @Override // j$.util.function.f
    public j$.util.function.f j(j$.util.function.f fVar) {
        Objects.requireNonNull(fVar);
        return new j$.util.function.e(this, fVar);
    }

    @Override // j$.util.stream.AbstractC0152e4
    protected void t(Object obj, int i, int i2, Object obj2) {
        double[] dArr = (double[]) obj;
        j$.util.function.f fVar = (j$.util.function.f) obj2;
        while (i < i2) {
            fVar.accept(dArr[i]);
            i++;
        }
    }

    public String toString() {
        double[] dArr = (double[]) e();
        return dArr.length < 200 ? String.format("%s[length=%d, chunks=%d]%s", getClass().getSimpleName(), Integer.valueOf(dArr.length), Integer.valueOf(this.c), Arrays.toString(dArr)) : String.format("%s[length=%d, chunks=%d]%s...", getClass().getSimpleName(), Integer.valueOf(dArr.length), Integer.valueOf(this.c), Arrays.toString(Arrays.copyOf(dArr, 200)));
    }

    @Override // j$.util.stream.AbstractC0152e4
    protected int u(Object obj) {
        return ((double[]) obj).length;
    }

    @Override // j$.util.stream.AbstractC0152e4
    protected Object[] z(int i) {
        return new double[i][];
    }
}
