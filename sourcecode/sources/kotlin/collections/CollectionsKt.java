package kotlin.collections;

import java.util.List;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public final class CollectionsKt extends CollectionsKt___CollectionsKt {
    public static /* bridge */ /* synthetic */ <T> T first(List<? extends T> list) {
        return (T) CollectionsKt___CollectionsKt.first((List) list);
    }

    public static /* bridge */ /* synthetic */ Appendable joinTo$default(Iterable iterable, Appendable appendable, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, int i, CharSequence charSequence4, Function1 function1, int i2, Object obj) {
        Appendable joinTo;
        joinTo = CollectionsKt___CollectionsKt.joinTo(iterable, appendable, (r14 & 2) != 0 ? ", " : charSequence, (r14 & 4) != 0 ? "" : charSequence2, (r14 & 8) == 0 ? charSequence3 : "", (r14 & 16) != 0 ? -1 : i, (r14 & 32) != 0 ? "..." : charSequence4, (r14 & 64) != 0 ? null : function1);
        return joinTo;
    }

    public static /* bridge */ /* synthetic */ <T> T last(List<? extends T> list) {
        return (T) CollectionsKt___CollectionsKt.last(list);
    }

    public static /* bridge */ /* synthetic */ <T> T single(Iterable<? extends T> iterable) {
        return (T) CollectionsKt___CollectionsKt.single(iterable);
    }
}
