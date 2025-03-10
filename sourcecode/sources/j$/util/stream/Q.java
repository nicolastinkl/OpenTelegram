package j$.util.stream;

import j$.util.function.Consumer;
import j$.util.function.Predicate;

/* loaded from: classes2.dex */
class Q extends AbstractC0175i3 {
    public final /* synthetic */ int l = 1;
    final /* synthetic */ Object m;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Q(Y y, AbstractC0135c abstractC0135c, EnumC0182j4 enumC0182j4, int i, j$.util.function.g gVar) {
        super(abstractC0135c, enumC0182j4, i);
        this.m = gVar;
    }

    @Override // j$.util.stream.AbstractC0135c
    InterfaceC0228r3 E0(int i, InterfaceC0228r3 interfaceC0228r3) {
        switch (this.l) {
            case 0:
                return new O(this, interfaceC0228r3);
            case 1:
                return new K0(this, interfaceC0228r3);
            case 2:
                return new C0149e1(this, interfaceC0228r3);
            case 3:
                return new C0145d3(this, interfaceC0228r3);
            default:
                return new C0145d3(this, interfaceC0228r3, (j$.lang.a) null);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Q(Q0 q0, AbstractC0135c abstractC0135c, EnumC0182j4 enumC0182j4, int i, j$.util.function.n nVar) {
        super(abstractC0135c, enumC0182j4, i);
        this.m = nVar;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Q(AbstractC0173i1 abstractC0173i1, AbstractC0135c abstractC0135c, EnumC0182j4 enumC0182j4, int i, j$.util.function.t tVar) {
        super(abstractC0135c, enumC0182j4, i);
        this.m = tVar;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Q(AbstractC0181j3 abstractC0181j3, AbstractC0135c abstractC0135c, EnumC0182j4 enumC0182j4, int i, Consumer consumer) {
        super(abstractC0135c, enumC0182j4, i);
        this.m = consumer;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Q(AbstractC0181j3 abstractC0181j3, AbstractC0135c abstractC0135c, EnumC0182j4 enumC0182j4, int i, Predicate predicate) {
        super(abstractC0135c, enumC0182j4, i);
        this.m = predicate;
    }
}
