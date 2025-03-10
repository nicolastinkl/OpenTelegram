package j$.wrappers;

import java.util.function.IntFunction;

/* loaded from: classes2.dex */
public final /* synthetic */ class P implements j$.util.function.n {
    final /* synthetic */ IntFunction a;

    private /* synthetic */ P(IntFunction intFunction) {
        this.a = intFunction;
    }

    public static /* synthetic */ j$.util.function.n a(IntFunction intFunction) {
        if (intFunction == null) {
            return null;
        }
        return intFunction instanceof Q ? ((Q) intFunction).a : new P(intFunction);
    }

    @Override // j$.util.function.n
    public /* synthetic */ Object apply(int i) {
        return this.a.apply(i);
    }
}
