package j$.util.stream;

import j$.util.function.Function;
import j$.util.function.ToLongFunction;
import j$.wrappers.C0287g0;

/* loaded from: classes2.dex */
class T extends AbstractC0167h1 {
    public final /* synthetic */ int l = 1;
    final /* synthetic */ Object m;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public T(Y y, AbstractC0135c abstractC0135c, EnumC0182j4 enumC0182j4, int i, j$.util.function.h hVar) {
        super(abstractC0135c, enumC0182j4, i);
        this.m = hVar;
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
                return new C0149e1(this, interfaceC0228r3, (j$.lang.a) null);
            case 4:
                return new C0149e1(this, interfaceC0228r3, (j$.lang.b) null);
            case 5:
                return new C0149e1(this, interfaceC0228r3, (j$.lang.c) null);
            case 6:
                return new C0254w(this, interfaceC0228r3);
            default:
                return new C0145d3(this, interfaceC0228r3);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public T(Q0 q0, AbstractC0135c abstractC0135c, EnumC0182j4 enumC0182j4, int i, j$.util.function.o oVar) {
        super(abstractC0135c, enumC0182j4, i);
        this.m = oVar;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public T(AbstractC0173i1 abstractC0173i1, AbstractC0135c abstractC0135c, EnumC0182j4 enumC0182j4, int i, j$.util.function.s sVar) {
        super(abstractC0135c, enumC0182j4, i);
        this.m = sVar;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public T(AbstractC0173i1 abstractC0173i1, AbstractC0135c abstractC0135c, EnumC0182j4 enumC0182j4, int i, j$.util.function.t tVar) {
        super(abstractC0135c, enumC0182j4, i);
        this.m = tVar;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public T(AbstractC0173i1 abstractC0173i1, AbstractC0135c abstractC0135c, EnumC0182j4 enumC0182j4, int i, j$.util.function.v vVar) {
        super(abstractC0135c, enumC0182j4, i);
        this.m = vVar;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public T(AbstractC0173i1 abstractC0173i1, AbstractC0135c abstractC0135c, EnumC0182j4 enumC0182j4, int i, C0287g0 c0287g0) {
        super(abstractC0135c, enumC0182j4, i);
        this.m = c0287g0;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public T(AbstractC0181j3 abstractC0181j3, AbstractC0135c abstractC0135c, EnumC0182j4 enumC0182j4, int i, Function function) {
        super(abstractC0135c, enumC0182j4, i);
        this.m = function;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public T(AbstractC0181j3 abstractC0181j3, AbstractC0135c abstractC0135c, EnumC0182j4 enumC0182j4, int i, ToLongFunction toLongFunction) {
        super(abstractC0135c, enumC0182j4, i);
        this.m = toLongFunction;
    }
}
