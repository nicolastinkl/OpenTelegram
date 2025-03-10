package j$.util.stream;

import j$.util.C0120i;
import java.util.Objects;

/* renamed from: j$.util.stream.j0, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
final class C0178j0 extends AbstractC0202n0 implements InterfaceC0211o3 {
    C0178j0() {
    }

    @Override // j$.util.stream.AbstractC0202n0, j$.util.stream.InterfaceC0228r3, j$.util.function.f
    public void accept(double d) {
        accept(Double.valueOf(d));
    }

    @Override // j$.util.function.Supplier
    public Object get() {
        if (this.a) {
            return C0120i.d(((Double) this.b).doubleValue());
        }
        return null;
    }

    @Override // j$.util.function.f
    public j$.util.function.f j(j$.util.function.f fVar) {
        Objects.requireNonNull(fVar);
        return new j$.util.function.e(this, fVar);
    }
}
