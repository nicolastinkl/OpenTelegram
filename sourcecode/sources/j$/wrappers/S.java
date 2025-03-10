package j$.wrappers;

import java.util.function.IntPredicate;

/* loaded from: classes2.dex */
public final /* synthetic */ class S {
    final /* synthetic */ IntPredicate a;

    private /* synthetic */ S(IntPredicate intPredicate) {
        this.a = intPredicate;
    }

    public static /* synthetic */ S a(IntPredicate intPredicate) {
        if (intPredicate == null) {
            return null;
        }
        return intPredicate instanceof T ? ((T) intPredicate).a : new S(intPredicate);
    }

    public boolean b(int i) {
        return this.a.test(i);
    }
}
