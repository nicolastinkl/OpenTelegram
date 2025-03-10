package j$.wrappers;

import java.util.function.DoubleToIntFunction;

/* loaded from: classes2.dex */
public final /* synthetic */ class E {
    final /* synthetic */ DoubleToIntFunction a;

    private /* synthetic */ E(DoubleToIntFunction doubleToIntFunction) {
        this.a = doubleToIntFunction;
    }

    public static /* synthetic */ E b(DoubleToIntFunction doubleToIntFunction) {
        if (doubleToIntFunction == null) {
            return null;
        }
        return doubleToIntFunction instanceof F ? ((F) doubleToIntFunction).a : new E(doubleToIntFunction);
    }

    public int a(double d) {
        return this.a.applyAsInt(d);
    }
}
