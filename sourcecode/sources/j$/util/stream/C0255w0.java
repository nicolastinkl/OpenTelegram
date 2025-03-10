package j$.util.stream;

import java.util.concurrent.CountedCompleter;

/* renamed from: j$.util.stream.w0, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
final class C0255w0 extends CountedCompleter {
    private j$.util.r a;
    private final InterfaceC0228r3 b;
    private final D2 c;
    private long d;

    C0255w0(D2 d2, j$.util.r rVar, InterfaceC0228r3 interfaceC0228r3) {
        super(null);
        this.b = interfaceC0228r3;
        this.c = d2;
        this.a = rVar;
        this.d = 0L;
    }

    C0255w0(C0255w0 c0255w0, j$.util.r rVar) {
        super(c0255w0);
        this.a = rVar;
        this.b = c0255w0.b;
        this.d = c0255w0.d;
        this.c = c0255w0.c;
    }

    @Override // java.util.concurrent.CountedCompleter
    public void compute() {
        j$.util.r trySplit;
        j$.util.r rVar = this.a;
        long estimateSize = rVar.estimateSize();
        long j = this.d;
        if (j == 0) {
            j = AbstractC0153f.h(estimateSize);
            this.d = j;
        }
        boolean d = EnumC0176i4.SHORT_CIRCUIT.d(this.c.p0());
        boolean z = false;
        InterfaceC0228r3 interfaceC0228r3 = this.b;
        C0255w0 c0255w0 = this;
        while (true) {
            if (d && interfaceC0228r3.o()) {
                break;
            }
            if (estimateSize <= j || (trySplit = rVar.trySplit()) == null) {
                break;
            }
            C0255w0 c0255w02 = new C0255w0(c0255w0, trySplit);
            c0255w0.addToPendingCount(1);
            if (z) {
                rVar = trySplit;
            } else {
                C0255w0 c0255w03 = c0255w0;
                c0255w0 = c0255w02;
                c0255w02 = c0255w03;
            }
            z = !z;
            c0255w0.fork();
            c0255w0 = c0255w02;
            estimateSize = rVar.estimateSize();
        }
        c0255w0.c.k0(interfaceC0228r3, rVar);
        c0255w0.a = null;
        c0255w0.propagateCompletion();
    }
}
