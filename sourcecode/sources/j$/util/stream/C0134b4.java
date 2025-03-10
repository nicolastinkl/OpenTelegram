package j$.util.stream;

import j$.util.function.Consumer;
import j$.util.r;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

/* renamed from: j$.util.stream.b4, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
class C0134b4 extends AbstractC0152e4 implements j$.util.function.m {
    C0134b4() {
    }

    C0134b4(int i) {
        super(i);
    }

    @Override // j$.util.stream.AbstractC0152e4, java.lang.Iterable, j$.lang.e
    /* renamed from: B, reason: merged with bridge method [inline-methods] */
    public r.b spliterator() {
        return new C0128a4(this, 0, this.c, 0, this.b);
    }

    @Override // j$.util.function.m
    public void accept(int i) {
        A();
        int[] iArr = (int[]) this.e;
        int i2 = this.b;
        this.b = i2 + 1;
        iArr[i2] = i;
    }

    @Override // j$.util.stream.AbstractC0152e4
    public Object c(int i) {
        return new int[i];
    }

    @Override // j$.lang.e
    public void forEach(Consumer consumer) {
        if (consumer instanceof j$.util.function.m) {
            g((j$.util.function.m) consumer);
        } else {
            if (V4.a) {
                V4.a(getClass(), "{0} calling SpinedBuffer.OfInt.forEach(Consumer)");
                throw null;
            }
            spliterator().forEachRemaining(consumer);
        }
    }

    @Override // java.lang.Iterable
    public Iterator iterator() {
        return j$.util.H.g(spliterator());
    }

    @Override // j$.util.function.m
    public j$.util.function.m l(j$.util.function.m mVar) {
        Objects.requireNonNull(mVar);
        return new j$.util.function.l(this, mVar);
    }

    @Override // j$.util.stream.AbstractC0152e4
    protected void t(Object obj, int i, int i2, Object obj2) {
        int[] iArr = (int[]) obj;
        j$.util.function.m mVar = (j$.util.function.m) obj2;
        while (i < i2) {
            mVar.accept(iArr[i]);
            i++;
        }
    }

    public String toString() {
        int[] iArr = (int[]) e();
        return iArr.length < 200 ? String.format("%s[length=%d, chunks=%d]%s", getClass().getSimpleName(), Integer.valueOf(iArr.length), Integer.valueOf(this.c), Arrays.toString(iArr)) : String.format("%s[length=%d, chunks=%d]%s...", getClass().getSimpleName(), Integer.valueOf(iArr.length), Integer.valueOf(this.c), Arrays.toString(Arrays.copyOf(iArr, 200)));
    }

    @Override // j$.util.stream.AbstractC0152e4
    protected int u(Object obj) {
        return ((int[]) obj).length;
    }

    @Override // j$.util.stream.AbstractC0152e4
    protected Object[] z(int i) {
        return new int[i][];
    }
}
