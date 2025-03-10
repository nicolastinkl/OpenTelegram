package j$.util.stream;

import j$.util.function.Consumer;
import j$.util.r;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

/* renamed from: j$.util.stream.d4, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
class C0146d4 extends AbstractC0152e4 implements j$.util.function.s {
    C0146d4() {
    }

    C0146d4(int i) {
        super(i);
    }

    @Override // j$.util.stream.AbstractC0152e4, java.lang.Iterable, j$.lang.e
    /* renamed from: B, reason: merged with bridge method [inline-methods] */
    public r.c spliterator() {
        return new C0140c4(this, 0, this.c, 0, this.b);
    }

    @Override // j$.util.function.s
    public void accept(long j) {
        A();
        long[] jArr = (long[]) this.e;
        int i = this.b;
        this.b = i + 1;
        jArr[i] = j;
    }

    @Override // j$.util.stream.AbstractC0152e4
    public Object c(int i) {
        return new long[i];
    }

    @Override // j$.util.function.s
    public j$.util.function.s f(j$.util.function.s sVar) {
        Objects.requireNonNull(sVar);
        return new j$.util.function.r(this, sVar);
    }

    @Override // j$.lang.e
    public void forEach(Consumer consumer) {
        if (consumer instanceof j$.util.function.s) {
            g((j$.util.function.s) consumer);
        } else {
            if (V4.a) {
                V4.a(getClass(), "{0} calling SpinedBuffer.OfLong.forEach(Consumer)");
                throw null;
            }
            spliterator().forEachRemaining(consumer);
        }
    }

    @Override // java.lang.Iterable
    public Iterator iterator() {
        return j$.util.H.h(spliterator());
    }

    @Override // j$.util.stream.AbstractC0152e4
    protected void t(Object obj, int i, int i2, Object obj2) {
        long[] jArr = (long[]) obj;
        j$.util.function.s sVar = (j$.util.function.s) obj2;
        while (i < i2) {
            sVar.accept(jArr[i]);
            i++;
        }
    }

    public String toString() {
        long[] jArr = (long[]) e();
        return jArr.length < 200 ? String.format("%s[length=%d, chunks=%d]%s", getClass().getSimpleName(), Integer.valueOf(jArr.length), Integer.valueOf(this.c), Arrays.toString(jArr)) : String.format("%s[length=%d, chunks=%d]%s...", getClass().getSimpleName(), Integer.valueOf(jArr.length), Integer.valueOf(this.c), Arrays.toString(Arrays.copyOf(jArr, 200)));
    }

    @Override // j$.util.stream.AbstractC0152e4
    protected int u(Object obj) {
        return ((long[]) obj).length;
    }

    @Override // j$.util.stream.AbstractC0152e4
    protected Object[] z(int i) {
        return new long[i][];
    }
}
