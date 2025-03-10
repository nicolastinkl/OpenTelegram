package j$.util;

import j$.util.function.Function;
import j$.util.function.ToDoubleFunction;
import j$.util.function.ToIntFunction;
import j$.util.function.ToLongFunction;
import java.io.Serializable;

/* renamed from: j$.util.d, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public final /* synthetic */ class C0115d implements java.util.Comparator, Serializable {
    public final /* synthetic */ int a = 0;
    public final /* synthetic */ Object b;

    public /* synthetic */ C0115d(Function function) {
        this.b = function;
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        switch (this.a) {
            case 0:
                Function function = (Function) this.b;
                return ((Comparable) function.apply(obj)).compareTo(function.apply(obj2));
            case 1:
                ToDoubleFunction toDoubleFunction = (ToDoubleFunction) this.b;
                return Double.compare(toDoubleFunction.applyAsDouble(obj), toDoubleFunction.applyAsDouble(obj2));
            case 2:
                ToIntFunction toIntFunction = (ToIntFunction) this.b;
                return Integer.compare(toIntFunction.applyAsInt(obj), toIntFunction.applyAsInt(obj2));
            default:
                ToLongFunction toLongFunction = (ToLongFunction) this.b;
                return Long.compare(toLongFunction.applyAsLong(obj), toLongFunction.applyAsLong(obj2));
        }
    }

    public /* synthetic */ C0115d(ToDoubleFunction toDoubleFunction) {
        this.b = toDoubleFunction;
    }

    public /* synthetic */ C0115d(ToIntFunction toIntFunction) {
        this.b = toIntFunction;
    }

    public /* synthetic */ C0115d(ToLongFunction toLongFunction) {
        this.b = toLongFunction;
    }
}
