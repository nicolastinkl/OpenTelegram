package j$.wrappers;

import java.util.function.ToLongFunction;

/* loaded from: classes2.dex */
public final /* synthetic */ class D0 implements ToLongFunction {
    final /* synthetic */ j$.util.function.ToLongFunction a;

    private /* synthetic */ D0(j$.util.function.ToLongFunction toLongFunction) {
        this.a = toLongFunction;
    }

    public static /* synthetic */ ToLongFunction a(j$.util.function.ToLongFunction toLongFunction) {
        if (toLongFunction == null) {
            return null;
        }
        return toLongFunction instanceof C0 ? ((C0) toLongFunction).a : new D0(toLongFunction);
    }

    @Override // java.util.function.ToLongFunction
    public /* synthetic */ long applyAsLong(Object obj) {
        return this.a.applyAsLong(obj);
    }
}
