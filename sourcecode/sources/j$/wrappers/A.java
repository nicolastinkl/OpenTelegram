package j$.wrappers;

import java.util.function.DoubleFunction;

/* loaded from: classes2.dex */
public final /* synthetic */ class A implements j$.util.function.g {
    final /* synthetic */ DoubleFunction a;

    private /* synthetic */ A(DoubleFunction doubleFunction) {
        this.a = doubleFunction;
    }

    public static /* synthetic */ j$.util.function.g a(DoubleFunction doubleFunction) {
        if (doubleFunction == null) {
            return null;
        }
        return doubleFunction instanceof B ? ((B) doubleFunction).a : new A(doubleFunction);
    }

    @Override // j$.util.function.g
    public /* synthetic */ Object apply(double d) {
        return this.a.apply(d);
    }
}
