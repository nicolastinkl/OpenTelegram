package j$.util.stream;

import j$.util.function.Function;
import j$.util.function.IntUnaryOperator;
import j$.util.function.ToIntFunction;
import j$.wrappers.C0295k0;

/* loaded from: classes2.dex */
class S extends P0 {
    public final /* synthetic */ int l = 2;
    final /* synthetic */ Object m;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public S(Y y, AbstractC0135c abstractC0135c, EnumC0182j4 enumC0182j4, int i, j$.wrappers.E e) {
        super(abstractC0135c, enumC0182j4, i);
        this.m = e;
    }

    @Override // j$.util.stream.AbstractC0135c
    InterfaceC0228r3 E0(int i, InterfaceC0228r3 interfaceC0228r3) {
        switch (this.l) {
            case 0:
                return new O(this, interfaceC0228r3);
            case 1:
                return new K0(this, interfaceC0228r3);
            case 2:
                return new K0(this, interfaceC0228r3, (j$.lang.a) null);
            case 3:
                return new K0(this, interfaceC0228r3, (j$.lang.b) null);
            case 4:
                return new K0(this, interfaceC0228r3, (j$.lang.c) null);
            case 5:
                return new C0149e1(this, interfaceC0228r3);
            case 6:
                return new C0145d3(this, interfaceC0228r3);
            default:
                return new C0254w(this, interfaceC0228r3);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public S(Q0 q0, AbstractC0135c abstractC0135c, EnumC0182j4 enumC0182j4, int i, j$.util.function.m mVar) {
        super(abstractC0135c, enumC0182j4, i);
        this.m = mVar;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public S(Q0 q0, AbstractC0135c abstractC0135c, EnumC0182j4 enumC0182j4, int i, j$.util.function.n nVar) {
        super(abstractC0135c, enumC0182j4, i);
        this.m = nVar;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public S(Q0 q0, AbstractC0135c abstractC0135c, EnumC0182j4 enumC0182j4, int i, IntUnaryOperator intUnaryOperator) {
        super(abstractC0135c, enumC0182j4, i);
        this.m = intUnaryOperator;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public S(Q0 q0, AbstractC0135c abstractC0135c, EnumC0182j4 enumC0182j4, int i, j$.wrappers.S s) {
        super(abstractC0135c, enumC0182j4, i);
        this.m = s;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public S(AbstractC0173i1 abstractC0173i1, AbstractC0135c abstractC0135c, EnumC0182j4 enumC0182j4, int i, C0295k0 c0295k0) {
        super(abstractC0135c, enumC0182j4, i);
        this.m = c0295k0;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public S(AbstractC0181j3 abstractC0181j3, AbstractC0135c abstractC0135c, EnumC0182j4 enumC0182j4, int i, Function function) {
        super(abstractC0135c, enumC0182j4, i);
        this.m = function;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public S(AbstractC0181j3 abstractC0181j3, AbstractC0135c abstractC0135c, EnumC0182j4 enumC0182j4, int i, ToIntFunction toIntFunction) {
        super(abstractC0135c, enumC0182j4, i);
        this.m = toIntFunction;
    }
}
