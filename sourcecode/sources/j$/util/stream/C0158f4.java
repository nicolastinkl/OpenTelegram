package j$.util.stream;

import j$.lang.Iterable$EL;
import j$.util.function.Consumer;
import j$.wrappers.C0288h;
import j$.wrappers.C0307v;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Spliterator;

/* renamed from: j$.util.stream.f4, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
class C0158f4 extends AbstractC0147e implements Consumer, Iterable, j$.lang.e {
    protected Object[] e = new Object[16];
    protected Object[][] f;

    C0158f4() {
    }

    private void v() {
        if (this.f == null) {
            Object[][] objArr = new Object[8][];
            this.f = objArr;
            this.d = new long[8];
            objArr[0] = this.e;
        }
    }

    @Override // j$.util.function.Consumer
    public void accept(Object obj) {
        if (this.b == this.e.length) {
            v();
            int i = this.c;
            int i2 = i + 1;
            Object[][] objArr = this.f;
            if (i2 >= objArr.length || objArr[i + 1] == null) {
                u(t() + 1);
            }
            this.b = 0;
            int i3 = this.c + 1;
            this.c = i3;
            this.e = this.f[i3];
        }
        Object[] objArr2 = this.e;
        int i4 = this.b;
        this.b = i4 + 1;
        objArr2[i4] = obj;
    }

    @Override // j$.util.function.Consumer
    public /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer.CC.$default$andThen(this, consumer);
    }

    @Override // j$.util.stream.AbstractC0147e
    public void clear() {
        Object[][] objArr = this.f;
        if (objArr != null) {
            this.e = objArr[0];
            int i = 0;
            while (true) {
                Object[] objArr2 = this.e;
                if (i >= objArr2.length) {
                    break;
                }
                objArr2[i] = null;
                i++;
            }
            this.f = null;
            this.d = null;
        } else {
            for (int i2 = 0; i2 < this.b; i2++) {
                this.e[i2] = null;
            }
        }
        this.b = 0;
        this.c = 0;
    }

    @Override // j$.lang.e
    public void forEach(Consumer consumer) {
        for (int i = 0; i < this.c; i++) {
            for (Object obj : this.f[i]) {
                consumer.accept(obj);
            }
        }
        for (int i2 = 0; i2 < this.b; i2++) {
            consumer.accept(this.e[i2]);
        }
    }

    @Override // java.lang.Iterable
    public /* synthetic */ void forEach(java.util.function.Consumer consumer) {
        forEach(C0307v.b(consumer));
    }

    public void i(Object[] objArr, int i) {
        long j = i;
        long count = count() + j;
        if (count > objArr.length || count < j) {
            throw new IndexOutOfBoundsException("does not fit");
        }
        if (this.c == 0) {
            System.arraycopy(this.e, 0, objArr, i, this.b);
            return;
        }
        for (int i2 = 0; i2 < this.c; i2++) {
            Object[][] objArr2 = this.f;
            System.arraycopy(objArr2[i2], 0, objArr, i, objArr2[i2].length);
            i += this.f[i2].length;
        }
        int i3 = this.b;
        if (i3 > 0) {
            System.arraycopy(this.e, 0, objArr, i, i3);
        }
    }

    @Override // java.lang.Iterable
    public Iterator iterator() {
        return j$.util.H.i(spliterator());
    }

    @Override // java.lang.Iterable, j$.lang.e
    public j$.util.r spliterator() {
        return new X3(this, 0, this.c, 0, this.b);
    }

    @Override // java.lang.Iterable
    public /* synthetic */ Spliterator spliterator() {
        return C0288h.a(spliterator());
    }

    protected long t() {
        int i = this.c;
        if (i == 0) {
            return this.e.length;
        }
        return this.f[i].length + this.d[i];
    }

    public String toString() {
        ArrayList arrayList = new ArrayList();
        Iterable$EL.forEach(this, new C0129b(arrayList));
        return "SpinedBuffer:" + arrayList.toString();
    }

    protected final void u(long j) {
        long t = t();
        if (j <= t) {
            return;
        }
        v();
        int i = this.c;
        while (true) {
            i++;
            if (j <= t) {
                return;
            }
            Object[][] objArr = this.f;
            if (i >= objArr.length) {
                int length = objArr.length * 2;
                this.f = (Object[][]) Arrays.copyOf(objArr, length);
                this.d = Arrays.copyOf(this.d, length);
            }
            int s = s(i);
            this.f[i] = new Object[s];
            long[] jArr = this.d;
            jArr[i] = jArr[i - 1] + r4[r6].length;
            t += s;
        }
    }
}
