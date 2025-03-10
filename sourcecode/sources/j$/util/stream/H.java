package j$.util.stream;

import j$.util.function.BiConsumer;
import j$.util.function.BiFunction;
import j$.util.function.Function;

/* loaded from: classes2.dex */
public final /* synthetic */ class H implements j$.util.function.b {
    public final /* synthetic */ int a;
    public final /* synthetic */ BiConsumer b;

    public /* synthetic */ H(BiConsumer biConsumer, int i) {
        this.a = i;
        if (i == 1) {
            this.b = biConsumer;
        } else if (i != 2) {
            this.b = biConsumer;
        } else {
            this.b = biConsumer;
        }
    }

    @Override // j$.util.function.BiFunction
    public /* synthetic */ BiFunction andThen(Function function) {
        switch (this.a) {
        }
        return BiFunction.CC.$default$andThen(this, function);
    }

    @Override // j$.util.function.BiFunction
    public final Object apply(Object obj, Object obj2) {
        switch (this.a) {
            case 0:
                this.b.accept(obj, obj2);
                break;
            case 1:
                this.b.accept(obj, obj2);
                break;
            default:
                this.b.accept(obj, obj2);
                break;
        }
        return obj;
    }
}
