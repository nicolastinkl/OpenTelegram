package j$.util.stream;

import j$.util.AbstractC0112a;
import j$.util.r;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;

/* renamed from: j$.util.stream.p2, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
abstract class AbstractC0216p2 implements j$.util.r {
    F1 a;
    int b;
    j$.util.r c;
    j$.util.r d;
    Deque e;

    AbstractC0216p2(F1 f1) {
        this.a = f1;
    }

    protected final F1 a(Deque deque) {
        while (true) {
            F1 f1 = (F1) deque.pollFirst();
            if (f1 == null) {
                return null;
            }
            if (f1.p() != 0) {
                for (int p = f1.p() - 1; p >= 0; p--) {
                    deque.addFirst(f1.b(p));
                }
            } else if (f1.count() > 0) {
                return f1;
            }
        }
    }

    @Override // j$.util.r
    public final int characteristics() {
        return 64;
    }

    @Override // j$.util.r
    public final long estimateSize() {
        long j = 0;
        if (this.a == null) {
            return 0L;
        }
        j$.util.r rVar = this.c;
        if (rVar != null) {
            return rVar.estimateSize();
        }
        for (int i = this.b; i < this.a.p(); i++) {
            j += this.a.b(i).count();
        }
        return j;
    }

    protected final Deque f() {
        ArrayDeque arrayDeque = new ArrayDeque(8);
        int p = this.a.p();
        while (true) {
            p--;
            if (p < this.b) {
                return arrayDeque;
            }
            arrayDeque.addFirst(this.a.b(p));
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

    protected final boolean h() {
        if (this.a == null) {
            return false;
        }
        if (this.d != null) {
            return true;
        }
        j$.util.r rVar = this.c;
        if (rVar == null) {
            Deque f = f();
            this.e = f;
            F1 a = a(f);
            if (a == null) {
                this.a = null;
                return false;
            }
            rVar = a.spliterator();
        }
        this.d = rVar;
        return true;
    }

    @Override // j$.util.r
    public /* synthetic */ boolean hasCharacteristics(int i) {
        return AbstractC0112a.f(this, i);
    }

    @Override // j$.util.r
    public /* bridge */ /* synthetic */ r.a trySplit() {
        return (r.a) trySplit();
    }

    @Override // j$.util.r
    public /* bridge */ /* synthetic */ r.b trySplit() {
        return (r.b) trySplit();
    }

    @Override // j$.util.r
    public /* bridge */ /* synthetic */ r.c trySplit() {
        return (r.c) trySplit();
    }

    @Override // j$.util.r
    public final j$.util.r trySplit() {
        F1 f1 = this.a;
        if (f1 == null || this.d != null) {
            return null;
        }
        j$.util.r rVar = this.c;
        if (rVar != null) {
            return rVar.trySplit();
        }
        if (this.b < f1.p() - 1) {
            F1 f12 = this.a;
            int i = this.b;
            this.b = i + 1;
            return f12.b(i).spliterator();
        }
        F1 b = this.a.b(this.b);
        this.a = b;
        if (b.p() == 0) {
            j$.util.r spliterator = this.a.spliterator();
            this.c = spliterator;
            return spliterator.trySplit();
        }
        this.b = 0;
        F1 f13 = this.a;
        this.b = 1;
        return f13.b(0).spliterator();
    }

    @Override // j$.util.r
    public /* bridge */ /* synthetic */ j$.util.s trySplit() {
        return (j$.util.s) trySplit();
    }
}
