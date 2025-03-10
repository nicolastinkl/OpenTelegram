package j$.wrappers;

import java.util.function.LongToDoubleFunction;

/* renamed from: j$.wrappers.i0, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public final /* synthetic */ class C0291i0 {
    final /* synthetic */ LongToDoubleFunction a;

    private /* synthetic */ C0291i0(LongToDoubleFunction longToDoubleFunction) {
        this.a = longToDoubleFunction;
    }

    public static /* synthetic */ C0291i0 b(LongToDoubleFunction longToDoubleFunction) {
        if (longToDoubleFunction == null) {
            return null;
        }
        return longToDoubleFunction instanceof AbstractC0293j0 ? ((AbstractC0293j0) longToDoubleFunction).a : new C0291i0(longToDoubleFunction);
    }

    public double a(long j) {
        return this.a.applyAsDouble(j);
    }
}
