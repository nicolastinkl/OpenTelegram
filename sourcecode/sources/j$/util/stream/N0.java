package j$.util.stream;

import j$.util.r;
import java.util.Objects;

/* loaded from: classes2.dex */
class N0 extends Q0 {
    N0(j$.util.r rVar, int i, boolean z) {
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

    @Override // j$.util.stream.Q0, j$.util.stream.IntStream
    public void H(j$.util.function.m mVar) {
        r.b J0;
        if (isParallel()) {
            Objects.requireNonNull(mVar);
            u0(new C0220q0(mVar, true));
        } else {
            J0 = Q0.J0(G0());
            J0.e(mVar);
        }
    }

    @Override // j$.util.stream.Q0, j$.util.stream.IntStream
    public void S(j$.util.function.m mVar) {
        r.b J0;
        if (isParallel()) {
            super.S(mVar);
        } else {
            J0 = Q0.J0(G0());
            J0.e(mVar);
        }
    }

    @Override // j$.util.stream.AbstractC0135c, j$.util.stream.BaseStream
    public /* bridge */ /* synthetic */ IntStream parallel() {
        parallel();
        return this;
    }

    @Override // j$.util.stream.AbstractC0135c, j$.util.stream.BaseStream
    public /* bridge */ /* synthetic */ IntStream sequential() {
        sequential();
        return this;
    }
}
