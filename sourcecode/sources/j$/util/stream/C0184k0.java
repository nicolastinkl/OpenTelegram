package j$.util.stream;

import j$.util.OptionalInt;
import java.util.Objects;

/* renamed from: j$.util.stream.k0, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
final class C0184k0 extends AbstractC0202n0 implements InterfaceC0217p3 {
    C0184k0() {
    }

    @Override // j$.util.stream.AbstractC0202n0, j$.util.stream.InterfaceC0228r3
    public void accept(int i) {
        accept(Integer.valueOf(i));
    }

    @Override // j$.util.function.Supplier
    public Object get() {
        if (this.a) {
            return OptionalInt.of(((Integer) this.b).intValue());
        }
        return null;
    }

    @Override // j$.util.function.m
    public j$.util.function.m l(j$.util.function.m mVar) {
        Objects.requireNonNull(mVar);
        return new j$.util.function.l(this, mVar);
    }
}
