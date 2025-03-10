package j$.util.stream;

import j$.util.function.Predicate;
import j$.util.function.Supplier;
import java.util.Objects;

/* renamed from: j$.util.stream.i0, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
final class C0172i0 implements S4 {
    private final EnumC0182j4 a;
    final boolean b;
    final Object c;
    final Predicate d;
    final Supplier e;

    C0172i0(boolean z, EnumC0182j4 enumC0182j4, Object obj, Predicate predicate, Supplier supplier) {
        this.b = z;
        this.a = enumC0182j4;
        this.c = obj;
        this.d = predicate;
        this.e = supplier;
    }

    @Override // j$.util.stream.S4
    public int b() {
        return EnumC0176i4.u | (this.b ? 0 : EnumC0176i4.r);
    }

    @Override // j$.util.stream.S4
    public Object c(D2 d2, j$.util.r rVar) {
        return new C0208o0(this, d2, rVar).invoke();
    }

    @Override // j$.util.stream.S4
    public Object d(D2 d2, j$.util.r rVar) {
        T4 t4 = (T4) this.e.get();
        AbstractC0135c abstractC0135c = (AbstractC0135c) d2;
        Objects.requireNonNull(t4);
        abstractC0135c.k0(abstractC0135c.s0(t4), rVar);
        Object obj = t4.get();
        return obj != null ? obj : this.c;
    }
}
