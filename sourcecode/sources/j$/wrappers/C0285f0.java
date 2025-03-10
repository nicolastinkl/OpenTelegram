package j$.wrappers;

import java.util.function.LongFunction;

/* renamed from: j$.wrappers.f0, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public final /* synthetic */ class C0285f0 implements LongFunction {
    final /* synthetic */ j$.util.function.t a;

    private /* synthetic */ C0285f0(j$.util.function.t tVar) {
        this.a = tVar;
    }

    public static /* synthetic */ LongFunction a(j$.util.function.t tVar) {
        if (tVar == null) {
            return null;
        }
        return tVar instanceof C0283e0 ? ((C0283e0) tVar).a : new C0285f0(tVar);
    }

    @Override // java.util.function.LongFunction
    public /* synthetic */ Object apply(long j) {
        return this.a.apply(j);
    }
}
