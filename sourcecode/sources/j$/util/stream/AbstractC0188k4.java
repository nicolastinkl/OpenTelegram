package j$.util.stream;

import j$.util.AbstractC0112a;
import j$.util.function.Supplier;
import java.util.Comparator;

/* renamed from: j$.util.stream.k4, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
abstract class AbstractC0188k4 implements j$.util.r {
    final boolean a;
    final D2 b;
    private Supplier c;
    j$.util.r d;
    InterfaceC0228r3 e;
    j$.util.function.c f;
    long g;
    AbstractC0147e h;
    boolean i;

    AbstractC0188k4(D2 d2, Supplier supplier, boolean z) {
        this.b = d2;
        this.c = supplier;
        this.d = null;
        this.a = z;
    }

    AbstractC0188k4(D2 d2, j$.util.r rVar, boolean z) {
        this.b = d2;
        this.c = null;
        this.d = rVar;
        this.a = z;
    }

    private boolean f() {
        boolean b;
        while (this.h.count() == 0) {
            if (!this.e.o()) {
                C0129b c0129b = (C0129b) this.f;
                switch (c0129b.a) {
                    case 4:
                        C0241t4 c0241t4 = (C0241t4) c0129b.b;
                        b = c0241t4.d.b(c0241t4.e);
                        break;
                    case 5:
                        C0253v4 c0253v4 = (C0253v4) c0129b.b;
                        b = c0253v4.d.b(c0253v4.e);
                        break;
                    case 6:
                        x4 x4Var = (x4) c0129b.b;
                        b = x4Var.d.b(x4Var.e);
                        break;
                    default:
                        Q4 q4 = (Q4) c0129b.b;
                        b = q4.d.b(q4.e);
                        break;
                }
                if (b) {
                    continue;
                }
            }
            if (this.i) {
                return false;
            }
            this.e.m();
            this.i = true;
        }
        return true;
    }

    final boolean a() {
        AbstractC0147e abstractC0147e = this.h;
        if (abstractC0147e == null) {
            if (this.i) {
                return false;
            }
            h();
            j();
            this.g = 0L;
            this.e.n(this.d.getExactSizeIfKnown());
            return f();
        }
        long j = this.g + 1;
        this.g = j;
        boolean z = j < abstractC0147e.count();
        if (z) {
            return z;
        }
        this.g = 0L;
        this.h.clear();
        return f();
    }

    @Override // j$.util.r
    public final int characteristics() {
        h();
        int g = EnumC0176i4.g(this.b.p0()) & EnumC0176i4.f;
        return (g & 64) != 0 ? (g & (-16449)) | (this.d.characteristics() & 16448) : g;
    }

    @Override // j$.util.r
    public final long estimateSize() {
        h();
        return this.d.estimateSize();
    }

    @Override // j$.util.r
    public Comparator getComparator() {
        if (AbstractC0112a.f(this, 4)) {
            return null;
        }
        throw new IllegalStateException();
    }

    @Override // j$.util.r
    public final long getExactSizeIfKnown() {
        h();
        if (EnumC0176i4.SIZED.d(this.b.p0())) {
            return this.d.getExactSizeIfKnown();
        }
        return -1L;
    }

    final void h() {
        if (this.d == null) {
            this.d = (j$.util.r) this.c.get();
            this.c = null;
        }
    }

    @Override // j$.util.r
    public /* synthetic */ boolean hasCharacteristics(int i) {
        return AbstractC0112a.f(this, i);
    }

    abstract void j();

    abstract AbstractC0188k4 l(j$.util.r rVar);

    public final String toString() {
        return String.format("%s[%s]", getClass().getName(), this.d);
    }

    @Override // j$.util.r
    public j$.util.r trySplit() {
        if (!this.a || this.i) {
            return null;
        }
        h();
        j$.util.r trySplit = this.d.trySplit();
        if (trySplit == null) {
            return null;
        }
        return l(trySplit);
    }
}
