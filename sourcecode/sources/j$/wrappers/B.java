package j$.wrappers;

import java.util.function.DoubleFunction;

/* loaded from: classes2.dex */
public final /* synthetic */ class B implements DoubleFunction {
    final /* synthetic */ j$.util.function.g a;

    private /* synthetic */ B(j$.util.function.g gVar) {
        this.a = gVar;
    }

    public static /* synthetic */ DoubleFunction a(j$.util.function.g gVar) {
        if (gVar == null) {
            return null;
        }
        return gVar instanceof A ? ((A) gVar).a : new B(gVar);
    }

    @Override // java.util.function.DoubleFunction
    public /* synthetic */ Object apply(double d) {
        return this.a.apply(d);
    }
}
