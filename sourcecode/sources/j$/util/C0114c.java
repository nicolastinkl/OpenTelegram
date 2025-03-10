package j$.util;

import j$.util.function.Function;
import java.io.Serializable;

/* renamed from: j$.util.c, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public final /* synthetic */ class C0114c implements java.util.Comparator, Serializable {
    public final /* synthetic */ int a = 1;
    public final /* synthetic */ java.util.Comparator b;
    public final /* synthetic */ Object c;

    public /* synthetic */ C0114c(java.util.Comparator comparator, Function function) {
        this.b = comparator;
        this.c = function;
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        switch (this.a) {
            case 0:
                java.util.Comparator comparator = this.b;
                java.util.Comparator comparator2 = (java.util.Comparator) this.c;
                int compare = comparator.compare(obj, obj2);
                return compare != 0 ? compare : comparator2.compare(obj, obj2);
            default:
                java.util.Comparator comparator3 = this.b;
                Function function = (Function) this.c;
                return comparator3.compare(function.apply(obj), function.apply(obj2));
        }
    }

    public /* synthetic */ C0114c(java.util.Comparator comparator, java.util.Comparator comparator2) {
        this.b = comparator;
        this.c = comparator2;
    }
}
