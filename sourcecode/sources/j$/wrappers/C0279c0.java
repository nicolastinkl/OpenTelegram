package j$.wrappers;

import java.util.function.LongConsumer;

/* renamed from: j$.wrappers.c0, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public final /* synthetic */ class C0279c0 implements j$.util.function.s {
    final /* synthetic */ LongConsumer a;

    private /* synthetic */ C0279c0(LongConsumer longConsumer) {
        this.a = longConsumer;
    }

    public static /* synthetic */ j$.util.function.s b(LongConsumer longConsumer) {
        if (longConsumer == null) {
            return null;
        }
        return longConsumer instanceof C0281d0 ? ((C0281d0) longConsumer).a : new C0279c0(longConsumer);
    }

    @Override // j$.util.function.s
    public /* synthetic */ void accept(long j) {
        this.a.accept(j);
    }

    @Override // j$.util.function.s
    public /* synthetic */ j$.util.function.s f(j$.util.function.s sVar) {
        return b(this.a.andThen(C0281d0.a(sVar)));
    }
}
