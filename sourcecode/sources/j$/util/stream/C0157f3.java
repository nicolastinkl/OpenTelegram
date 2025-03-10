package j$.util.stream;

import j$.util.function.Function;

/* renamed from: j$.util.stream.f3, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
class C0157f3 extends AbstractC0175i3 {
    public final /* synthetic */ int l;
    final /* synthetic */ Function m;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0157f3(AbstractC0181j3 abstractC0181j3, AbstractC0135c abstractC0135c, EnumC0182j4 enumC0182j4, int i, Function function, int i2) {
        super(abstractC0135c, enumC0182j4, i);
        this.l = i2;
        if (i2 != 1) {
            this.m = function;
        } else {
            this.m = function;
            super(abstractC0135c, enumC0182j4, i);
        }
    }

    @Override // j$.util.stream.AbstractC0135c
    InterfaceC0228r3 E0(int i, InterfaceC0228r3 interfaceC0228r3) {
        switch (this.l) {
            case 0:
                return new C0145d3(this, interfaceC0228r3);
            default:
                return new C0145d3(this, interfaceC0228r3, (j$.lang.a) null);
        }
    }
}
