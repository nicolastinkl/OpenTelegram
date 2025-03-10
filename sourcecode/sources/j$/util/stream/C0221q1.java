package j$.util.stream;

import j$.util.function.Supplier;
import java.util.Objects;

/* renamed from: j$.util.stream.q1, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
final class C0221q1 implements S4 {
    private final EnumC0182j4 a;
    final EnumC0215p1 b;
    final Supplier c;

    C0221q1(EnumC0182j4 enumC0182j4, EnumC0215p1 enumC0215p1, Supplier supplier) {
        this.a = enumC0182j4;
        this.b = enumC0215p1;
        this.c = supplier;
    }

    @Override // j$.util.stream.S4
    public int b() {
        return EnumC0176i4.u | EnumC0176i4.r;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // j$.util.stream.S4
    public Object c(D2 d2, j$.util.r rVar) {
        return (Boolean) new C0226r1(this, d2, rVar).invoke();
    }

    @Override // j$.util.stream.S4
    public Object d(D2 d2, j$.util.r rVar) {
        AbstractC0209o1 abstractC0209o1 = (AbstractC0209o1) this.c.get();
        AbstractC0135c abstractC0135c = (AbstractC0135c) d2;
        Objects.requireNonNull(abstractC0209o1);
        abstractC0135c.k0(abstractC0135c.s0(abstractC0209o1), rVar);
        return Boolean.valueOf(abstractC0209o1.b);
    }
}
