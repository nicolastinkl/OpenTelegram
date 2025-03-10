package j$.wrappers;

import java.util.function.LongConsumer;

/* renamed from: j$.wrappers.d0, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public final /* synthetic */ class C0281d0 implements LongConsumer {
    final /* synthetic */ j$.util.function.s a;

    private /* synthetic */ C0281d0(j$.util.function.s sVar) {
        this.a = sVar;
    }

    public static /* synthetic */ LongConsumer a(j$.util.function.s sVar) {
        if (sVar == null) {
            return null;
        }
        return sVar instanceof C0279c0 ? ((C0279c0) sVar).a : new C0281d0(sVar);
    }

    @Override // java.util.function.LongConsumer
    public /* synthetic */ void accept(long j) {
        this.a.accept(j);
    }

    @Override // java.util.function.LongConsumer
    public /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
        return a(this.a.f(C0279c0.b(longConsumer)));
    }
}
