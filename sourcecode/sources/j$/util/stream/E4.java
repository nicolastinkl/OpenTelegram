package j$.util.stream;

import j$.util.AbstractC0112a;
import java.util.Comparator;
import java.util.Objects;

/* loaded from: classes2.dex */
abstract class E4 extends I4 implements j$.util.s {
    E4(j$.util.s sVar, long j, long j2) {
        super(sVar, j, j2, 0L, Math.min(sVar.estimateSize(), j2));
    }

    protected abstract Object f();

    @Override // j$.util.s
    /* renamed from: forEachRemaining, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public void e(Object obj) {
        Objects.requireNonNull(obj);
        long j = this.a;
        long j2 = this.e;
        if (j >= j2) {
            return;
        }
        long j3 = this.d;
        if (j3 >= j2) {
            return;
        }
        if (j3 >= j && ((j$.util.s) this.c).estimateSize() + j3 <= this.b) {
            ((j$.util.s) this.c).e(obj);
            this.d = this.e;
            return;
        }
        while (this.a > this.d) {
            ((j$.util.s) this.c).k(f());
            this.d++;
        }
        while (this.d < this.e) {
            ((j$.util.s) this.c).k(obj);
            this.d++;
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

    @Override // j$.util.r
    public /* synthetic */ boolean hasCharacteristics(int i) {
        return AbstractC0112a.f(this, i);
    }

    @Override // j$.util.s
    /* renamed from: tryAdvance, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public boolean k(Object obj) {
        long j;
        Objects.requireNonNull(obj);
        if (this.a >= this.e) {
            return false;
        }
        while (true) {
            long j2 = this.a;
            j = this.d;
            if (j2 <= j) {
                break;
            }
            ((j$.util.s) this.c).k(f());
            this.d++;
        }
        if (j >= this.e) {
            return false;
        }
        this.d = j + 1;
        return ((j$.util.s) this.c).k(obj);
    }

    E4(j$.util.s sVar, long j, long j2, long j3, long j4, AbstractC0238t1 abstractC0238t1) {
        super(sVar, j, j2, j3, j4);
    }
}
