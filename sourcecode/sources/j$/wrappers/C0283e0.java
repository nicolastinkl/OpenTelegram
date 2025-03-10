package j$.wrappers;

import java.util.function.LongFunction;

/* renamed from: j$.wrappers.e0, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public final /* synthetic */ class C0283e0 implements j$.util.function.t {
    final /* synthetic */ LongFunction a;

    private /* synthetic */ C0283e0(LongFunction longFunction) {
        this.a = longFunction;
    }

    public static /* synthetic */ j$.util.function.t a(LongFunction longFunction) {
        if (longFunction == null) {
            return null;
        }
        return longFunction instanceof C0285f0 ? ((C0285f0) longFunction).a : new C0283e0(longFunction);
    }

    @Override // j$.util.function.t
    public /* synthetic */ Object apply(long j) {
        return this.a.apply(j);
    }
}
