package j$.util.stream;

import j$.util.C0121j;
import java.util.Objects;

/* renamed from: j$.util.stream.l0, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
final class C0190l0 extends AbstractC0202n0 implements InterfaceC0223q3 {
    C0190l0() {
    }

    @Override // j$.util.stream.AbstractC0202n0, j$.util.stream.InterfaceC0228r3, j$.util.stream.InterfaceC0223q3, j$.util.function.s
    public void accept(long j) {
        accept(Long.valueOf(j));
    }

    @Override // j$.util.function.s
    public j$.util.function.s f(j$.util.function.s sVar) {
        Objects.requireNonNull(sVar);
        return new j$.util.function.r(this, sVar);
    }

    @Override // j$.util.function.Supplier
    public Object get() {
        if (this.a) {
            return C0121j.d(((Long) this.b).longValue());
        }
        return null;
    }
}
