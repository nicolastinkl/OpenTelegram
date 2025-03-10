package j$.util.stream;

import java.util.Objects;

/* loaded from: classes2.dex */
abstract class Z2 implements S4 {
    private final EnumC0182j4 a;

    Z2(EnumC0182j4 enumC0182j4) {
        this.a = enumC0182j4;
    }

    public abstract X2 a();

    @Override // j$.util.stream.S4
    public /* synthetic */ int b() {
        return 0;
    }

    @Override // j$.util.stream.S4
    public Object c(D2 d2, j$.util.r rVar) {
        return ((X2) new C0127a3(this, d2, rVar).invoke()).get();
    }

    @Override // j$.util.stream.S4
    public Object d(D2 d2, j$.util.r rVar) {
        X2 a = a();
        AbstractC0135c abstractC0135c = (AbstractC0135c) d2;
        Objects.requireNonNull(a);
        abstractC0135c.k0(abstractC0135c.s0(a), rVar);
        return a.get();
    }
}
