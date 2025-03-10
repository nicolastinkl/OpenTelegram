package j$.wrappers;

import java.util.function.Predicate;

/* loaded from: classes2.dex */
public final /* synthetic */ class v0 implements Predicate {
    final /* synthetic */ j$.util.function.Predicate a;

    private /* synthetic */ v0(j$.util.function.Predicate predicate) {
        this.a = predicate;
    }

    public static /* synthetic */ Predicate a(j$.util.function.Predicate predicate) {
        if (predicate == null) {
            return null;
        }
        return predicate instanceof u0 ? ((u0) predicate).a : new v0(predicate);
    }

    @Override // java.util.function.Predicate
    public /* synthetic */ Predicate and(Predicate predicate) {
        return a(this.a.and(u0.a(predicate)));
    }

    @Override // java.util.function.Predicate
    public /* synthetic */ Predicate negate() {
        return a(this.a.negate());
    }

    @Override // java.util.function.Predicate
    public /* synthetic */ Predicate or(Predicate predicate) {
        return a(this.a.or(u0.a(predicate)));
    }

    @Override // java.util.function.Predicate
    public /* synthetic */ boolean test(Object obj) {
        return this.a.test(obj);
    }
}
