package j$.wrappers;

import java.util.function.IntUnaryOperator;

/* loaded from: classes2.dex */
public final /* synthetic */ class Z implements IntUnaryOperator {
    final /* synthetic */ j$.util.function.IntUnaryOperator a;

    private /* synthetic */ Z(j$.util.function.IntUnaryOperator intUnaryOperator) {
        this.a = intUnaryOperator;
    }

    public static /* synthetic */ IntUnaryOperator a(j$.util.function.IntUnaryOperator intUnaryOperator) {
        if (intUnaryOperator == null) {
            return null;
        }
        return intUnaryOperator instanceof Y ? ((Y) intUnaryOperator).a : new Z(intUnaryOperator);
    }

    @Override // java.util.function.IntUnaryOperator
    public /* synthetic */ IntUnaryOperator andThen(IntUnaryOperator intUnaryOperator) {
        return a(this.a.andThen(Y.a(intUnaryOperator)));
    }

    @Override // java.util.function.IntUnaryOperator
    public /* synthetic */ int applyAsInt(int i) {
        return this.a.applyAsInt(i);
    }

    @Override // java.util.function.IntUnaryOperator
    public /* synthetic */ IntUnaryOperator compose(IntUnaryOperator intUnaryOperator) {
        return a(this.a.compose(Y.a(intUnaryOperator)));
    }
}
