package j$.wrappers;

import java.util.function.IntToDoubleFunction;

/* loaded from: classes2.dex */
public final /* synthetic */ class U {
    final /* synthetic */ IntToDoubleFunction a;

    private /* synthetic */ U(IntToDoubleFunction intToDoubleFunction) {
        this.a = intToDoubleFunction;
    }

    public static /* synthetic */ U b(IntToDoubleFunction intToDoubleFunction) {
        if (intToDoubleFunction == null) {
            return null;
        }
        return intToDoubleFunction instanceof V ? ((V) intToDoubleFunction).a : new U(intToDoubleFunction);
    }

    public double a(int i) {
        return this.a.applyAsDouble(i);
    }
}
