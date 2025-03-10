package j$.wrappers;

import java.util.function.DoubleUnaryOperator;

/* loaded from: classes2.dex */
public final /* synthetic */ class I {
    final /* synthetic */ DoubleUnaryOperator a;

    private /* synthetic */ I(DoubleUnaryOperator doubleUnaryOperator) {
        this.a = doubleUnaryOperator;
    }

    public static /* synthetic */ I b(DoubleUnaryOperator doubleUnaryOperator) {
        if (doubleUnaryOperator == null) {
            return null;
        }
        return doubleUnaryOperator instanceof J ? ((J) doubleUnaryOperator).a : new I(doubleUnaryOperator);
    }

    public double a(double d) {
        return this.a.applyAsDouble(d);
    }
}
