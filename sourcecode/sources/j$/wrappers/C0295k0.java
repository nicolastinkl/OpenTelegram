package j$.wrappers;

import java.util.function.LongToIntFunction;

/* renamed from: j$.wrappers.k0, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public final /* synthetic */ class C0295k0 {
    final /* synthetic */ LongToIntFunction a;

    private /* synthetic */ C0295k0(LongToIntFunction longToIntFunction) {
        this.a = longToIntFunction;
    }

    public static /* synthetic */ C0295k0 b(LongToIntFunction longToIntFunction) {
        if (longToIntFunction == null) {
            return null;
        }
        return longToIntFunction instanceof AbstractC0297l0 ? ((AbstractC0297l0) longToIntFunction).a : new C0295k0(longToIntFunction);
    }

    public int a(long j) {
        return this.a.applyAsInt(j);
    }
}
