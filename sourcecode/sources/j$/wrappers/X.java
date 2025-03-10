package j$.wrappers;

import java.util.function.IntToLongFunction;

/* loaded from: classes2.dex */
public final /* synthetic */ class X implements IntToLongFunction {
    final /* synthetic */ j$.util.function.o a;

    private /* synthetic */ X(j$.util.function.o oVar) {
        this.a = oVar;
    }

    public static /* synthetic */ IntToLongFunction a(j$.util.function.o oVar) {
        if (oVar == null) {
            return null;
        }
        return oVar instanceof W ? ((W) oVar).a : new X(oVar);
    }

    @Override // java.util.function.IntToLongFunction
    public /* synthetic */ long applyAsLong(int i) {
        return this.a.applyAsLong(i);
    }
}
