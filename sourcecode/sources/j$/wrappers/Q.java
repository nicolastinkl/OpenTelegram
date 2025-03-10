package j$.wrappers;

import java.util.function.IntFunction;

/* loaded from: classes2.dex */
public final /* synthetic */ class Q implements IntFunction {
    final /* synthetic */ j$.util.function.n a;

    private /* synthetic */ Q(j$.util.function.n nVar) {
        this.a = nVar;
    }

    public static /* synthetic */ IntFunction a(j$.util.function.n nVar) {
        if (nVar == null) {
            return null;
        }
        return nVar instanceof P ? ((P) nVar).a : new Q(nVar);
    }

    @Override // java.util.function.IntFunction
    public /* synthetic */ Object apply(int i) {
        return this.a.apply(i);
    }
}
