package j$.wrappers;

import java.util.function.ToDoubleFunction;

/* loaded from: classes2.dex */
public final /* synthetic */ class z0 implements ToDoubleFunction {
    final /* synthetic */ j$.util.function.ToDoubleFunction a;

    private /* synthetic */ z0(j$.util.function.ToDoubleFunction toDoubleFunction) {
        this.a = toDoubleFunction;
    }

    public static /* synthetic */ ToDoubleFunction a(j$.util.function.ToDoubleFunction toDoubleFunction) {
        if (toDoubleFunction == null) {
            return null;
        }
        return toDoubleFunction instanceof y0 ? ((y0) toDoubleFunction).a : new z0(toDoubleFunction);
    }

    @Override // java.util.function.ToDoubleFunction
    public /* synthetic */ double applyAsDouble(Object obj) {
        return this.a.applyAsDouble(obj);
    }
}
