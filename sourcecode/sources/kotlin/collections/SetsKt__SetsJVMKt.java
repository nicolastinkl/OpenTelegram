package kotlin.collections;

import java.util.Collections;
import java.util.Set;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SetsJVM.kt */
/* loaded from: classes.dex */
class SetsKt__SetsJVMKt {
    public static final <T> Set<T> setOf(T t) {
        Set<T> singleton = Collections.singleton(t);
        Intrinsics.checkNotNullExpressionValue(singleton, "singleton(element)");
        return singleton;
    }
}
