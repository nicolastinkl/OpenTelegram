package j$.wrappers;

import j$.util.function.ToIntFunction;

/* loaded from: classes2.dex */
public final /* synthetic */ class A0 implements ToIntFunction {
    final /* synthetic */ java.util.function.ToIntFunction a;

    private /* synthetic */ A0(java.util.function.ToIntFunction toIntFunction) {
        this.a = toIntFunction;
    }

    public static /* synthetic */ ToIntFunction a(java.util.function.ToIntFunction toIntFunction) {
        if (toIntFunction == null) {
            return null;
        }
        return toIntFunction instanceof B0 ? ((B0) toIntFunction).a : new A0(toIntFunction);
    }

    @Override // j$.util.function.ToIntFunction
    public /* synthetic */ int applyAsInt(Object obj) {
        return this.a.applyAsInt(obj);
    }
}
