package j$.wrappers;

import java.util.function.LongPredicate;

/* renamed from: j$.wrappers.g0, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public final /* synthetic */ class C0287g0 {
    final /* synthetic */ LongPredicate a;

    private /* synthetic */ C0287g0(LongPredicate longPredicate) {
        this.a = longPredicate;
    }

    public static /* synthetic */ C0287g0 a(LongPredicate longPredicate) {
        if (longPredicate == null) {
            return null;
        }
        return longPredicate instanceof AbstractC0289h0 ? ((AbstractC0289h0) longPredicate).a : new C0287g0(longPredicate);
    }

    public boolean b(long j) {
        return this.a.test(j);
    }
}
