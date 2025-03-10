package j$.wrappers;

import java.util.function.IntBinaryOperator;

/* loaded from: classes2.dex */
public final /* synthetic */ class L implements j$.util.function.k {
    final /* synthetic */ IntBinaryOperator a;

    private /* synthetic */ L(IntBinaryOperator intBinaryOperator) {
        this.a = intBinaryOperator;
    }

    public static /* synthetic */ j$.util.function.k a(IntBinaryOperator intBinaryOperator) {
        if (intBinaryOperator == null) {
            return null;
        }
        return intBinaryOperator instanceof M ? ((M) intBinaryOperator).a : new L(intBinaryOperator);
    }

    @Override // j$.util.function.k
    public /* synthetic */ int applyAsInt(int i, int i2) {
        return this.a.applyAsInt(i, i2);
    }
}
