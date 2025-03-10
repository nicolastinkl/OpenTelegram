package j$.util.stream;

import j$.util.function.Consumer;
import java.util.Objects;

/* renamed from: j$.util.stream.g3, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
class C0163g3 extends AbstractC0181j3 {
    C0163g3(j$.util.r rVar, int i, boolean z) {
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

    @Override // j$.util.stream.AbstractC0181j3, j$.util.stream.Stream
    public void e(Consumer consumer) {
        if (!isParallel()) {
            G0().forEachRemaining(consumer);
        } else {
            Objects.requireNonNull(consumer);
            u0(new C0231s0(consumer, true));
        }
    }

    @Override // j$.util.stream.AbstractC0181j3, j$.util.stream.Stream
    public void forEach(Consumer consumer) {
        if (isParallel()) {
            super.forEach(consumer);
        } else {
            G0().forEachRemaining(consumer);
        }
    }
}
