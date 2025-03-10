package j$.util.stream;

import j$.util.function.Supplier;
import java.util.Objects;

/* renamed from: j$.util.stream.c, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
abstract class AbstractC0135c extends D2 implements BaseStream {
    private final AbstractC0135c a;
    private final AbstractC0135c b;
    protected final int c;
    private AbstractC0135c d;
    private int e;
    private int f;
    private j$.util.r g;
    private boolean h;
    private boolean i;
    private Runnable j;
    private boolean k;

    AbstractC0135c(j$.util.r rVar, int i, boolean z) {
        this.b = null;
        this.g = rVar;
        this.a = this;
        int i2 = EnumC0176i4.g & i;
        this.c = i2;
        this.f = (~(i2 << 1)) & EnumC0176i4.l;
        this.e = 0;
        this.k = z;
    }

    AbstractC0135c(AbstractC0135c abstractC0135c, int i) {
        if (abstractC0135c.h) {
            throw new IllegalStateException("stream has already been operated upon or closed");
        }
        abstractC0135c.h = true;
        abstractC0135c.d = this;
        this.b = abstractC0135c;
        this.c = EnumC0176i4.h & i;
        this.f = EnumC0176i4.a(i, abstractC0135c.f);
        AbstractC0135c abstractC0135c2 = abstractC0135c.a;
        this.a = abstractC0135c2;
        if (D0()) {
            abstractC0135c2.i = true;
        }
        this.e = abstractC0135c.e + 1;
    }

    private j$.util.r F0(int i) {
        int i2;
        int i3;
        AbstractC0135c abstractC0135c = this.a;
        j$.util.r rVar = abstractC0135c.g;
        if (rVar == null) {
            throw new IllegalStateException("source already consumed or closed");
        }
        abstractC0135c.g = null;
        if (abstractC0135c.k && abstractC0135c.i) {
            AbstractC0135c abstractC0135c2 = abstractC0135c.d;
            int i4 = 1;
            while (abstractC0135c != this) {
                int i5 = abstractC0135c2.c;
                if (abstractC0135c2.D0()) {
                    i4 = 0;
                    if (EnumC0176i4.SHORT_CIRCUIT.d(i5)) {
                        i5 &= ~EnumC0176i4.u;
                    }
                    rVar = abstractC0135c2.C0(abstractC0135c, rVar);
                    if (rVar.hasCharacteristics(64)) {
                        i2 = i5 & (~EnumC0176i4.t);
                        i3 = EnumC0176i4.s;
                    } else {
                        i2 = i5 & (~EnumC0176i4.s);
                        i3 = EnumC0176i4.t;
                    }
                    i5 = i2 | i3;
                }
                abstractC0135c2.e = i4;
                abstractC0135c2.f = EnumC0176i4.a(i5, abstractC0135c.f);
                i4++;
                AbstractC0135c abstractC0135c3 = abstractC0135c2;
                abstractC0135c2 = abstractC0135c2.d;
                abstractC0135c = abstractC0135c3;
            }
        }
        if (i != 0) {
            this.f = EnumC0176i4.a(i, this.f);
        }
        return rVar;
    }

    public /* synthetic */ j$.util.r A0() {
        return F0(0);
    }

    F1 B0(D2 d2, j$.util.r rVar, j$.util.function.n nVar) {
        throw new UnsupportedOperationException("Parallel evaluation is not supported");
    }

    j$.util.r C0(D2 d2, j$.util.r rVar) {
        return B0(d2, rVar, new j$.util.function.n() { // from class: j$.util.stream.a
            @Override // j$.util.function.n
            public final Object apply(int i) {
                return new Object[i];
            }
        }).spliterator();
    }

    abstract boolean D0();

    abstract InterfaceC0228r3 E0(int i, InterfaceC0228r3 interfaceC0228r3);

    final j$.util.r G0() {
        AbstractC0135c abstractC0135c = this.a;
        if (this != abstractC0135c) {
            throw new IllegalStateException();
        }
        if (this.h) {
            throw new IllegalStateException("stream has already been operated upon or closed");
        }
        this.h = true;
        j$.util.r rVar = abstractC0135c.g;
        if (rVar == null) {
            throw new IllegalStateException("source already consumed or closed");
        }
        abstractC0135c.g = null;
        return rVar;
    }

    abstract j$.util.r H0(D2 d2, Supplier supplier, boolean z);

    @Override // j$.util.stream.BaseStream, java.lang.AutoCloseable
    public void close() {
        this.h = true;
        this.g = null;
        AbstractC0135c abstractC0135c = this.a;
        Runnable runnable = abstractC0135c.j;
        if (runnable != null) {
            abstractC0135c.j = null;
            runnable.run();
        }
    }

    @Override // j$.util.stream.BaseStream
    public final boolean isParallel() {
        return this.a.k;
    }

    @Override // j$.util.stream.D2
    final void k0(InterfaceC0228r3 interfaceC0228r3, j$.util.r rVar) {
        Objects.requireNonNull(interfaceC0228r3);
        if (EnumC0176i4.SHORT_CIRCUIT.d(this.f)) {
            l0(interfaceC0228r3, rVar);
            return;
        }
        interfaceC0228r3.n(rVar.getExactSizeIfKnown());
        rVar.forEachRemaining(interfaceC0228r3);
        interfaceC0228r3.m();
    }

    @Override // j$.util.stream.D2
    final void l0(InterfaceC0228r3 interfaceC0228r3, j$.util.r rVar) {
        AbstractC0135c abstractC0135c = this;
        while (abstractC0135c.e > 0) {
            abstractC0135c = abstractC0135c.b;
        }
        interfaceC0228r3.n(rVar.getExactSizeIfKnown());
        abstractC0135c.x0(rVar, interfaceC0228r3);
        interfaceC0228r3.m();
    }

    @Override // j$.util.stream.D2
    final F1 m0(j$.util.r rVar, boolean z, j$.util.function.n nVar) {
        if (this.a.k) {
            return w0(this, rVar, z, nVar);
        }
        InterfaceC0261x1 q0 = q0(n0(rVar), nVar);
        Objects.requireNonNull(q0);
        k0(s0(q0), rVar);
        return q0.a();
    }

    @Override // j$.util.stream.D2
    final long n0(j$.util.r rVar) {
        if (EnumC0176i4.SIZED.d(this.f)) {
            return rVar.getExactSizeIfKnown();
        }
        return -1L;
    }

    @Override // j$.util.stream.D2
    final EnumC0182j4 o0() {
        AbstractC0135c abstractC0135c = this;
        while (abstractC0135c.e > 0) {
            abstractC0135c = abstractC0135c.b;
        }
        return abstractC0135c.y0();
    }

    @Override // j$.util.stream.BaseStream
    public BaseStream onClose(Runnable runnable) {
        AbstractC0135c abstractC0135c = this.a;
        Runnable runnable2 = abstractC0135c.j;
        if (runnable2 != null) {
            runnable = new R4(runnable2, runnable);
        }
        abstractC0135c.j = runnable;
        return this;
    }

    @Override // j$.util.stream.D2
    final int p0() {
        return this.f;
    }

    public final BaseStream parallel() {
        this.a.k = true;
        return this;
    }

    @Override // j$.util.stream.D2
    final InterfaceC0228r3 r0(InterfaceC0228r3 interfaceC0228r3, j$.util.r rVar) {
        Objects.requireNonNull(interfaceC0228r3);
        k0(s0(interfaceC0228r3), rVar);
        return interfaceC0228r3;
    }

    @Override // j$.util.stream.D2
    final InterfaceC0228r3 s0(InterfaceC0228r3 interfaceC0228r3) {
        Objects.requireNonNull(interfaceC0228r3);
        for (AbstractC0135c abstractC0135c = this; abstractC0135c.e > 0; abstractC0135c = abstractC0135c.b) {
            interfaceC0228r3 = abstractC0135c.E0(abstractC0135c.b.f, interfaceC0228r3);
        }
        return interfaceC0228r3;
    }

    public final BaseStream sequential() {
        this.a.k = false;
        return this;
    }

    public j$.util.r spliterator() {
        if (this.h) {
            throw new IllegalStateException("stream has already been operated upon or closed");
        }
        this.h = true;
        AbstractC0135c abstractC0135c = this.a;
        if (this != abstractC0135c) {
            return H0(this, new C0129b(this), abstractC0135c.k);
        }
        j$.util.r rVar = abstractC0135c.g;
        if (rVar == null) {
            throw new IllegalStateException("source already consumed or closed");
        }
        abstractC0135c.g = null;
        return rVar;
    }

    @Override // j$.util.stream.D2
    final j$.util.r t0(j$.util.r rVar) {
        return this.e == 0 ? rVar : H0(this, new C0129b(rVar), this.a.k);
    }

    final Object u0(S4 s4) {
        if (this.h) {
            throw new IllegalStateException("stream has already been operated upon or closed");
        }
        this.h = true;
        return this.a.k ? s4.c(this, F0(s4.b())) : s4.d(this, F0(s4.b()));
    }

    final F1 v0(j$.util.function.n nVar) {
        if (this.h) {
            throw new IllegalStateException("stream has already been operated upon or closed");
        }
        this.h = true;
        if (!this.a.k || this.b == null || !D0()) {
            return m0(F0(0), true, nVar);
        }
        this.e = 0;
        AbstractC0135c abstractC0135c = this.b;
        return B0(abstractC0135c, abstractC0135c.F0(0), nVar);
    }

    abstract F1 w0(D2 d2, j$.util.r rVar, boolean z, j$.util.function.n nVar);

    abstract void x0(j$.util.r rVar, InterfaceC0228r3 interfaceC0228r3);

    abstract EnumC0182j4 y0();

    final boolean z0() {
        return EnumC0176i4.ORDERED.d(this.f);
    }
}
