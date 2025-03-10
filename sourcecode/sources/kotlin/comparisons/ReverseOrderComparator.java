package kotlin.comparisons;

import j$.util.AbstractC0112a;
import j$.util.Comparator;
import j$.util.function.Function;
import j$.util.function.ToDoubleFunction;
import j$.util.function.ToIntFunction;
import j$.util.function.ToLongFunction;
import java.util.Comparator;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Comparisons.kt */
/* loaded from: classes.dex */
final class ReverseOrderComparator implements Comparator<Comparable<? super Object>>, j$.util.Comparator {
    public static final ReverseOrderComparator INSTANCE = new ReverseOrderComparator();

    @Override // j$.util.Comparator
    public /* synthetic */ Comparator thenComparing(Function function) {
        Comparator w;
        w = AbstractC0112a.w(this, Comparator.CC.comparing(function));
        return w;
    }

    @Override // j$.util.Comparator
    public /* synthetic */ java.util.Comparator thenComparing(Function function, java.util.Comparator comparator) {
        return Comparator.CC.$default$thenComparing(this, function, comparator);
    }

    @Override // java.util.Comparator, j$.util.Comparator
    public /* synthetic */ java.util.Comparator thenComparing(java.util.Comparator comparator) {
        return Comparator.CC.$default$thenComparing(this, comparator);
    }

    @Override // j$.util.Comparator
    public /* synthetic */ java.util.Comparator thenComparingDouble(ToDoubleFunction toDoubleFunction) {
        return Comparator.CC.$default$thenComparingDouble(this, toDoubleFunction);
    }

    @Override // j$.util.Comparator
    public /* synthetic */ java.util.Comparator thenComparingInt(ToIntFunction toIntFunction) {
        java.util.Comparator w;
        w = AbstractC0112a.w(this, Comparator.CC.comparingInt(toIntFunction));
        return w;
    }

    @Override // j$.util.Comparator
    public /* synthetic */ java.util.Comparator thenComparingLong(ToLongFunction toLongFunction) {
        return Comparator.CC.$default$thenComparingLong(this, toLongFunction);
    }

    private ReverseOrderComparator() {
    }

    @Override // java.util.Comparator, j$.util.Comparator
    public int compare(Comparable<Object> a, Comparable<Object> b) {
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        return b.compareTo(a);
    }

    @Override // java.util.Comparator, j$.util.Comparator
    public final java.util.Comparator<Comparable<Object>> reversed() {
        return NaturalOrderComparator.INSTANCE;
    }
}
