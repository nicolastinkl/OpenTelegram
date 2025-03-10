package j$.util.stream;

import j$.util.r;
import java.util.Objects;

/* renamed from: j$.util.stream.f1, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
class C0155f1 extends AbstractC0173i1 {
    C0155f1(j$.util.r rVar, int i, boolean z) {
        super(rVar, i, z);
    }

    @Override // j$.util.stream.AbstractC0135c
    final boolean D0() {
        throw new UnsupportedOperationException();
    }

    @Override // j$.util.stream.AbstractC0135c
    final InterfaceC0228r3 E0(int i, InterfaceC0228r3 interfaceC0228r3) {
        throw new UnsupportedOperationException();
    }

    @Override // j$.util.stream.AbstractC0173i1, j$.util.stream.InterfaceC0179j1
    public void X(j$.util.function.s sVar) {
        r.c J0;
        if (isParallel()) {
            Objects.requireNonNull(sVar);
            u0(new C0225r0(sVar, true));
        } else {
            J0 = AbstractC0173i1.J0(G0());
            J0.e(sVar);
        }
    }

    @Override // j$.util.stream.AbstractC0173i1, j$.util.stream.InterfaceC0179j1
    public void d(j$.util.function.s sVar) {
        r.c J0;
        if (isParallel()) {
            super.d(sVar);
        } else {
            J0 = AbstractC0173i1.J0(G0());
            J0.e(sVar);
        }
    }

    @Override // j$.util.stream.AbstractC0135c, j$.util.stream.BaseStream
    public /* bridge */ /* synthetic */ InterfaceC0179j1 parallel() {
        parallel();
        return this;
    }

    @Override // j$.util.stream.AbstractC0135c, j$.util.stream.BaseStream
    public /* bridge */ /* synthetic */ InterfaceC0179j1 sequential() {
        sequential();
        return this;
    }
}
