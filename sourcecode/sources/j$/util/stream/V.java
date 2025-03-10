package j$.util.stream;

import j$.util.r;
import java.util.Objects;

/* loaded from: classes2.dex */
class V extends Y {
    V(j$.util.r rVar, int i, boolean z) {
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

    @Override // j$.util.stream.Y, j$.util.stream.Z
    public void i0(j$.util.function.f fVar) {
        r.a J0;
        if (isParallel()) {
            Objects.requireNonNull(fVar);
            u0(new C0214p0(fVar, true));
        } else {
            J0 = Y.J0(G0());
            J0.e(fVar);
        }
    }

    @Override // j$.util.stream.Y, j$.util.stream.Z
    public void j(j$.util.function.f fVar) {
        r.a J0;
        if (isParallel()) {
            super.j(fVar);
        } else {
            J0 = Y.J0(G0());
            J0.e(fVar);
        }
    }

    @Override // j$.util.stream.AbstractC0135c, j$.util.stream.BaseStream
    public /* bridge */ /* synthetic */ Z parallel() {
        parallel();
        return this;
    }

    @Override // j$.util.stream.AbstractC0135c, j$.util.stream.BaseStream
    public /* bridge */ /* synthetic */ Z sequential() {
        sequential();
        return this;
    }
}
