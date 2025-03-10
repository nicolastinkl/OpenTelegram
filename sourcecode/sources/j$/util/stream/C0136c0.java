package j$.util.stream;

import j$.util.OptionalInt;
import j$.util.function.Predicate;

/* renamed from: j$.util.stream.c0, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public final /* synthetic */ class C0136c0 implements Predicate {
    public static final /* synthetic */ C0136c0 a = new C0136c0();

    private /* synthetic */ C0136c0() {
    }

    @Override // j$.util.function.Predicate
    public /* synthetic */ Predicate and(Predicate predicate) {
        return Predicate.CC.$default$and(this, predicate);
    }

    @Override // j$.util.function.Predicate
    public /* synthetic */ Predicate negate() {
        return Predicate.CC.$default$negate(this);
    }

    @Override // j$.util.function.Predicate
    public /* synthetic */ Predicate or(Predicate predicate) {
        return Predicate.CC.$default$or(this, predicate);
    }

    @Override // j$.util.function.Predicate
    public final boolean test(Object obj) {
        return ((OptionalInt) obj).isPresent();
    }
}
