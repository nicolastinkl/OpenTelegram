package j$.util.stream;

import j$.util.concurrent.ConcurrentHashMap;
import java.util.Objects;
import java.util.concurrent.CountedCompleter;

/* renamed from: j$.util.stream.v0, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
final class C0249v0 extends CountedCompleter {
    public static final /* synthetic */ int h = 0;
    private final D2 a;
    private j$.util.r b;
    private final long c;
    private final ConcurrentHashMap d;
    private final InterfaceC0228r3 e;
    private final C0249v0 f;
    private F1 g;

    protected C0249v0(D2 d2, j$.util.r rVar, InterfaceC0228r3 interfaceC0228r3) {
        super(null);
        this.a = d2;
        this.b = rVar;
        this.c = AbstractC0153f.h(rVar.estimateSize());
        this.d = new ConcurrentHashMap(Math.max(16, AbstractC0153f.g << 1));
        this.e = interfaceC0228r3;
        this.f = null;
    }

    C0249v0(C0249v0 c0249v0, j$.util.r rVar, C0249v0 c0249v02) {
        super(c0249v0);
        this.a = c0249v0.a;
        this.b = rVar;
        this.c = c0249v0.c;
        this.d = c0249v0.d;
        this.e = c0249v0.e;
        this.f = c0249v02;
    }

    @Override // java.util.concurrent.CountedCompleter
    public final void compute() {
        j$.util.r trySplit;
        j$.util.r rVar = this.b;
        long j = this.c;
        boolean z = false;
        C0249v0 c0249v0 = this;
        while (rVar.estimateSize() > j && (trySplit = rVar.trySplit()) != null) {
            C0249v0 c0249v02 = new C0249v0(c0249v0, trySplit, c0249v0.f);
            C0249v0 c0249v03 = new C0249v0(c0249v0, rVar, c0249v02);
            c0249v0.addToPendingCount(1);
            c0249v03.addToPendingCount(1);
            c0249v0.d.put(c0249v02, c0249v03);
            if (c0249v0.f != null) {
                c0249v02.addToPendingCount(1);
                if (c0249v0.d.replace(c0249v0.f, c0249v0, c0249v02)) {
                    c0249v0.addToPendingCount(-1);
                } else {
                    c0249v02.addToPendingCount(-1);
                }
            }
            if (z) {
                rVar = trySplit;
                c0249v0 = c0249v02;
                c0249v02 = c0249v03;
            } else {
                c0249v0 = c0249v03;
            }
            z = !z;
            c0249v02.fork();
        }
        if (c0249v0.getPendingCount() > 0) {
            C0243u0 c0243u0 = new j$.util.function.n() { // from class: j$.util.stream.u0
                @Override // j$.util.function.n
                public final Object apply(int i) {
                    int i2 = C0249v0.h;
                    return new Object[i];
                }
            };
            D2 d2 = c0249v0.a;
            InterfaceC0261x1 q0 = d2.q0(d2.n0(rVar), c0243u0);
            AbstractC0135c abstractC0135c = (AbstractC0135c) c0249v0.a;
            Objects.requireNonNull(abstractC0135c);
            Objects.requireNonNull(q0);
            abstractC0135c.k0(abstractC0135c.s0(q0), rVar);
            c0249v0.g = q0.a();
            c0249v0.b = null;
        }
        c0249v0.tryComplete();
    }

    @Override // java.util.concurrent.CountedCompleter
    public void onCompletion(CountedCompleter countedCompleter) {
        F1 f1 = this.g;
        if (f1 != null) {
            f1.forEach(this.e);
            this.g = null;
        } else {
            j$.util.r rVar = this.b;
            if (rVar != null) {
                D2 d2 = this.a;
                InterfaceC0228r3 interfaceC0228r3 = this.e;
                AbstractC0135c abstractC0135c = (AbstractC0135c) d2;
                Objects.requireNonNull(abstractC0135c);
                Objects.requireNonNull(interfaceC0228r3);
                abstractC0135c.k0(abstractC0135c.s0(interfaceC0228r3), rVar);
                this.b = null;
            }
        }
        C0249v0 c0249v0 = (C0249v0) this.d.remove(this);
        if (c0249v0 != null) {
            c0249v0.tryComplete();
        }
    }
}
