package j$.wrappers;

import java.util.function.DoublePredicate;

/* loaded from: classes2.dex */
public final /* synthetic */ class C {
    final /* synthetic */ DoublePredicate a;

    private /* synthetic */ C(DoublePredicate doublePredicate) {
        this.a = doublePredicate;
    }

    public static /* synthetic */ C a(DoublePredicate doublePredicate) {
        if (doublePredicate == null) {
            return null;
        }
        return doublePredicate instanceof D ? ((D) doublePredicate).a : new C(doublePredicate);
    }

    public boolean b(double d) {
        return this.a.test(d);
    }
}
