package j$.util.stream;

import j$.util.function.Function;
import j$.util.function.ToDoubleFunction;
import j$.wrappers.C0291i0;

/* loaded from: classes2.dex */
class P extends X {
    public final /* synthetic */ int l = 4;
    final /* synthetic */ Object m;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public P(Y y, AbstractC0135c abstractC0135c, EnumC0182j4 enumC0182j4, int i, j$.util.function.f fVar) {
        super(abstractC0135c, enumC0182j4, i);
        this.m = fVar;
    }

    @Override // j$.util.stream.AbstractC0135c
    InterfaceC0228r3 E0(int i, InterfaceC0228r3 interfaceC0228r3) {
        switch (this.l) {
            case 0:
                return new O(this, interfaceC0228r3);
            case 1:
                return new O(this, interfaceC0228r3, (j$.lang.a) null);
            case 2:
                return new O(this, interfaceC0228r3, (j$.lang.b) null);
            case 3:
                return new O(this, interfaceC0228r3, (j$.lang.c) null);
            case 4:
                return new K0(this, interfaceC0228r3);
            case 5:
                return new C0149e1(this, interfaceC0228r3);
            case 6:
                return new C0145d3(this, interfaceC0228r3);
            default:
                return new C0254w(this, interfaceC0228r3);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public P(Y y, AbstractC0135c abstractC0135c, EnumC0182j4 enumC0182j4, int i, j$.util.function.g gVar) {
        super(abstractC0135c, enumC0182j4, i);
        this.m = gVar;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public P(Y y, AbstractC0135c abstractC0135c, EnumC0182j4 enumC0182j4, int i, j$.wrappers.C c) {
        super(abstractC0135c, enumC0182j4, i);
        this.m = c;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public P(Y y, AbstractC0135c abstractC0135c, EnumC0182j4 enumC0182j4, int i, j$.wrappers.I i2) {
        super(abstractC0135c, enumC0182j4, i);
        this.m = i2;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public P(Q0 q0, AbstractC0135c abstractC0135c, EnumC0182j4 enumC0182j4, int i, j$.wrappers.U u) {
        super(abstractC0135c, enumC0182j4, i);
        this.m = u;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public P(AbstractC0173i1 abstractC0173i1, AbstractC0135c abstractC0135c, EnumC0182j4 enumC0182j4, int i, C0291i0 c0291i0) {
        super(abstractC0135c, enumC0182j4, i);
        this.m = c0291i0;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public P(AbstractC0181j3 abstractC0181j3, AbstractC0135c abstractC0135c, EnumC0182j4 enumC0182j4, int i, Function function) {
        super(abstractC0135c, enumC0182j4, i);
        this.m = function;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public P(AbstractC0181j3 abstractC0181j3, AbstractC0135c abstractC0135c, EnumC0182j4 enumC0182j4, int i, ToDoubleFunction toDoubleFunction) {
        super(abstractC0135c, enumC0182j4, i);
        this.m = toDoubleFunction;
    }
}
