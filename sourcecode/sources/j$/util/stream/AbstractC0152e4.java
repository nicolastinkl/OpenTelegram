package j$.util.stream;

import j$.util.AbstractC0112a;
import j$.util.r;
import j$.wrappers.C0288h;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.Spliterator;

/* renamed from: j$.util.stream.e4, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
abstract class AbstractC0152e4 extends AbstractC0147e implements Iterable, j$.lang.e {
    Object e;
    Object[] f;

    /* renamed from: j$.util.stream.e4$a */
    abstract class a implements j$.util.s {
        int a;
        final int b;
        int c;
        final int d;
        Object e;

        a(int i, int i2, int i3, int i4) {
            this.a = i;
            this.b = i2;
            this.c = i3;
            this.d = i4;
            Object[] objArr = AbstractC0152e4.this.f;
            this.e = objArr == null ? AbstractC0152e4.this.e : objArr[i];
        }

        abstract void a(Object obj, int i, Object obj2);

        @Override // j$.util.r
        public int characteristics() {
            return 16464;
        }

        @Override // j$.util.r
        public long estimateSize() {
            int i = this.a;
            int i2 = this.b;
            if (i == i2) {
                return this.d - this.c;
            }
            long[] jArr = AbstractC0152e4.this.d;
            return ((jArr[i2] + this.d) - jArr[i]) - this.c;
        }

        abstract j$.util.s f(Object obj, int i, int i2);

        @Override // j$.util.s
        /* renamed from: forEachRemaining, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
        public void e(Object obj) {
            int i;
            Objects.requireNonNull(obj);
            int i2 = this.a;
            int i3 = this.b;
            if (i2 < i3 || (i2 == i3 && this.c < this.d)) {
                int i4 = this.c;
                while (true) {
                    i = this.b;
                    if (i2 >= i) {
                        break;
                    }
                    AbstractC0152e4 abstractC0152e4 = AbstractC0152e4.this;
                    Object obj2 = abstractC0152e4.f[i2];
                    abstractC0152e4.t(obj2, i4, abstractC0152e4.u(obj2), obj);
                    i4 = 0;
                    i2++;
                }
                AbstractC0152e4.this.t(this.a == i ? this.e : AbstractC0152e4.this.f[i], i4, this.d, obj);
                this.a = this.b;
                this.c = this.d;
            }
        }

        @Override // j$.util.r
        public Comparator getComparator() {
            throw new IllegalStateException();
        }

        @Override // j$.util.r
        public /* synthetic */ long getExactSizeIfKnown() {
            return AbstractC0112a.e(this);
        }

        abstract j$.util.s h(int i, int i2, int i3, int i4);

        @Override // j$.util.r
        public /* synthetic */ boolean hasCharacteristics(int i) {
            return AbstractC0112a.f(this, i);
        }

        @Override // j$.util.s
        /* renamed from: tryAdvance, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
        public boolean k(Object obj) {
            Objects.requireNonNull(obj);
            int i = this.a;
            int i2 = this.b;
            if (i >= i2 && (i != i2 || this.c >= this.d)) {
                return false;
            }
            Object obj2 = this.e;
            int i3 = this.c;
            this.c = i3 + 1;
            a(obj2, i3, obj);
            if (this.c == AbstractC0152e4.this.u(this.e)) {
                this.c = 0;
                int i4 = this.a + 1;
                this.a = i4;
                Object[] objArr = AbstractC0152e4.this.f;
                if (objArr != null && i4 <= this.b) {
                    this.e = objArr[i4];
                }
            }
            return true;
        }

        @Override // j$.util.s, j$.util.r
        public /* bridge */ /* synthetic */ r.a trySplit() {
            return (r.a) trySplit();
        }

        @Override // j$.util.s, j$.util.r
        public /* bridge */ /* synthetic */ r.b trySplit() {
            return (r.b) trySplit();
        }

        @Override // j$.util.s, j$.util.r
        public /* bridge */ /* synthetic */ r.c trySplit() {
            return (r.c) trySplit();
        }

        @Override // j$.util.r
        public j$.util.s trySplit() {
            int i = this.a;
            int i2 = this.b;
            if (i < i2) {
                int i3 = this.c;
                AbstractC0152e4 abstractC0152e4 = AbstractC0152e4.this;
                j$.util.s h = h(i, i2 - 1, i3, abstractC0152e4.u(abstractC0152e4.f[i2 - 1]));
                int i4 = this.b;
                this.a = i4;
                this.c = 0;
                this.e = AbstractC0152e4.this.f[i4];
                return h;
            }
            if (i != i2) {
                return null;
            }
            int i5 = this.d;
            int i6 = this.c;
            int i7 = (i5 - i6) / 2;
            if (i7 == 0) {
                return null;
            }
            j$.util.s f = f(this.e, i6, i7);
            this.c += i7;
            return f;
        }
    }

    AbstractC0152e4() {
        this.e = c(16);
    }

    AbstractC0152e4(int i) {
        super(i);
        this.e = c(1 << this.a);
    }

    private void y() {
        if (this.f == null) {
            Object[] z = z(8);
            this.f = z;
            this.d = new long[8];
            z[0] = this.e;
        }
    }

    protected void A() {
        if (this.b == u(this.e)) {
            y();
            int i = this.c;
            int i2 = i + 1;
            Object[] objArr = this.f;
            if (i2 >= objArr.length || objArr[i + 1] == null) {
                x(v() + 1);
            }
            this.b = 0;
            int i3 = this.c + 1;
            this.c = i3;
            this.e = this.f[i3];
        }
    }

    public abstract Object c(int i);

    @Override // j$.util.stream.AbstractC0147e
    public void clear() {
        Object[] objArr = this.f;
        if (objArr != null) {
            this.e = objArr[0];
            this.f = null;
            this.d = null;
        }
        this.b = 0;
        this.c = 0;
    }

    public void d(Object obj, int i) {
        long j = i;
        long count = count() + j;
        if (count > u(obj) || count < j) {
            throw new IndexOutOfBoundsException("does not fit");
        }
        if (this.c == 0) {
            System.arraycopy(this.e, 0, obj, i, this.b);
            return;
        }
        for (int i2 = 0; i2 < this.c; i2++) {
            Object[] objArr = this.f;
            System.arraycopy(objArr[i2], 0, obj, i, u(objArr[i2]));
            i += u(this.f[i2]);
        }
        int i3 = this.b;
        if (i3 > 0) {
            System.arraycopy(this.e, 0, obj, i, i3);
        }
    }

    public Object e() {
        long count = count();
        if (count >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        Object c = c((int) count);
        d(c, 0);
        return c;
    }

    public void g(Object obj) {
        for (int i = 0; i < this.c; i++) {
            Object[] objArr = this.f;
            t(objArr[i], 0, u(objArr[i]), obj);
        }
        t(this.e, 0, this.b, obj);
    }

    public abstract j$.util.r spliterator();

    @Override // java.lang.Iterable
    public /* synthetic */ Spliterator spliterator() {
        return C0288h.a(spliterator());
    }

    protected abstract void t(Object obj, int i, int i2, Object obj2);

    protected abstract int u(Object obj);

    protected long v() {
        int i = this.c;
        if (i == 0) {
            return u(this.e);
        }
        return u(this.f[i]) + this.d[i];
    }

    protected int w(long j) {
        if (this.c == 0) {
            if (j < this.b) {
                return 0;
            }
            throw new IndexOutOfBoundsException(Long.toString(j));
        }
        if (j >= count()) {
            throw new IndexOutOfBoundsException(Long.toString(j));
        }
        for (int i = 0; i <= this.c; i++) {
            if (j < this.d[i] + u(this.f[i])) {
                return i;
            }
        }
        throw new IndexOutOfBoundsException(Long.toString(j));
    }

    protected final void x(long j) {
        long v = v();
        if (j <= v) {
            return;
        }
        y();
        int i = this.c;
        while (true) {
            i++;
            if (j <= v) {
                return;
            }
            Object[] objArr = this.f;
            if (i >= objArr.length) {
                int length = objArr.length * 2;
                this.f = Arrays.copyOf(objArr, length);
                this.d = Arrays.copyOf(this.d, length);
            }
            int s = s(i);
            this.f[i] = c(s);
            long[] jArr = this.d;
            jArr[i] = jArr[i - 1] + u(this.f[r5]);
            v += s;
        }
    }

    protected abstract Object[] z(int i);
}
