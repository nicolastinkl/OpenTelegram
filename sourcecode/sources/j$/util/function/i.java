package j$.util.function;

import j$.util.function.Function;

/* loaded from: classes2.dex */
public final /* synthetic */ class i implements Function {
    public final /* synthetic */ int a;
    public final /* synthetic */ Function b;
    public final /* synthetic */ Function c;

    public /* synthetic */ i(Function function, Function function2, int i) {
        this.a = i;
        if (i != 1) {
            this.b = function;
            this.c = function2;
        } else {
            this.b = function;
            this.c = function2;
        }
    }

    @Override // j$.util.function.Function
    public /* synthetic */ Function andThen(Function function) {
        switch (this.a) {
        }
        return Function.CC.$default$andThen(this, function);
    }

    @Override // j$.util.function.Function
    public final Object apply(Object obj) {
        switch (this.a) {
            case 0:
                return this.c.apply(this.b.apply(obj));
            default:
                return this.b.apply(this.c.apply(obj));
        }
    }

    @Override // j$.util.function.Function
    public /* synthetic */ Function compose(Function function) {
        switch (this.a) {
        }
        return Function.CC.$default$compose(this, function);
    }
}
