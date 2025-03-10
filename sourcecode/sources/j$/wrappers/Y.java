package j$.wrappers;

import j$.util.function.IntUnaryOperator;

/* loaded from: classes2.dex */
public final /* synthetic */ class Y implements IntUnaryOperator {
    final /* synthetic */ java.util.function.IntUnaryOperator a;

    private /* synthetic */ Y(java.util.function.IntUnaryOperator intUnaryOperator) {
        this.a = intUnaryOperator;
    }

    public static /* synthetic */ IntUnaryOperator a(java.util.function.IntUnaryOperator intUnaryOperator) {
        if (intUnaryOperator == null) {
            return null;
        }
        return intUnaryOperator instanceof Z ? ((Z) intUnaryOperator).a : new Y(intUnaryOperator);
    }

    @Override // j$.util.function.IntUnaryOperator
    public /* synthetic */ IntUnaryOperator andThen(IntUnaryOperator intUnaryOperator) {
        return a(this.a.andThen(Z.a(intUnaryOperator)));
    }

    @Override // j$.util.function.IntUnaryOperator
    public /* synthetic */ int applyAsInt(int i) {
        return this.a.applyAsInt(i);
    }

    @Override // j$.util.function.IntUnaryOperator
    public /* synthetic */ IntUnaryOperator compose(IntUnaryOperator intUnaryOperator) {
        return a(this.a.compose(Z.a(intUnaryOperator)));
    }
}
