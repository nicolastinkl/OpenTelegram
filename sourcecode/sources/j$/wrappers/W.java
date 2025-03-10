package j$.wrappers;

import java.util.function.IntToLongFunction;

/* loaded from: classes2.dex */
public final /* synthetic */ class W implements j$.util.function.o {
    final /* synthetic */ IntToLongFunction a;

    private /* synthetic */ W(IntToLongFunction intToLongFunction) {
        this.a = intToLongFunction;
    }

    public static /* synthetic */ j$.util.function.o a(IntToLongFunction intToLongFunction) {
        if (intToLongFunction == null) {
            return null;
        }
        return intToLongFunction instanceof X ? ((X) intToLongFunction).a : new W(intToLongFunction);
    }

    @Override // j$.util.function.o
    public /* synthetic */ long applyAsLong(int i) {
        return this.a.applyAsLong(i);
    }
}
