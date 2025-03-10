package j$.wrappers;

import j$.util.function.ToDoubleFunction;

/* loaded from: classes2.dex */
public final /* synthetic */ class y0 implements ToDoubleFunction {
    final /* synthetic */ java.util.function.ToDoubleFunction a;

    private /* synthetic */ y0(java.util.function.ToDoubleFunction toDoubleFunction) {
        this.a = toDoubleFunction;
    }

    public static /* synthetic */ ToDoubleFunction a(java.util.function.ToDoubleFunction toDoubleFunction) {
        if (toDoubleFunction == null) {
            return null;
        }
        return toDoubleFunction instanceof z0 ? ((z0) toDoubleFunction).a : new y0(toDoubleFunction);
    }

    @Override // j$.util.function.ToDoubleFunction
    public /* synthetic */ double applyAsDouble(Object obj) {
        return this.a.applyAsDouble(obj);
    }
}
