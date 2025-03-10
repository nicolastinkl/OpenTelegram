package j$.util.function;

import j$.util.function.IntUnaryOperator;

/* loaded from: classes2.dex */
public final /* synthetic */ class p implements IntUnaryOperator {
    public final /* synthetic */ int a;
    public final /* synthetic */ IntUnaryOperator b;
    public final /* synthetic */ IntUnaryOperator c;

    public /* synthetic */ p(IntUnaryOperator intUnaryOperator, IntUnaryOperator intUnaryOperator2, int i) {
        this.a = i;
        if (i != 1) {
            this.b = intUnaryOperator;
            this.c = intUnaryOperator2;
        } else {
            this.b = intUnaryOperator;
            this.c = intUnaryOperator2;
        }
    }

    @Override // j$.util.function.IntUnaryOperator
    public /* synthetic */ IntUnaryOperator andThen(IntUnaryOperator intUnaryOperator) {
        switch (this.a) {
        }
        return IntUnaryOperator.CC.$default$andThen(this, intUnaryOperator);
    }

    @Override // j$.util.function.IntUnaryOperator
    public final int applyAsInt(int i) {
        switch (this.a) {
            case 0:
                return this.c.applyAsInt(this.b.applyAsInt(i));
            default:
                return this.b.applyAsInt(this.c.applyAsInt(i));
        }
    }

    @Override // j$.util.function.IntUnaryOperator
    public /* synthetic */ IntUnaryOperator compose(IntUnaryOperator intUnaryOperator) {
        switch (this.a) {
        }
        return IntUnaryOperator.CC.$default$compose(this, intUnaryOperator);
    }
}
