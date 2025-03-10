package j$.wrappers;

import java.util.function.LongUnaryOperator;

/* renamed from: j$.wrappers.m0, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public final /* synthetic */ class C0299m0 implements j$.util.function.v {
    final /* synthetic */ LongUnaryOperator a;

    private /* synthetic */ C0299m0(LongUnaryOperator longUnaryOperator) {
        this.a = longUnaryOperator;
    }

    public static /* synthetic */ j$.util.function.v c(LongUnaryOperator longUnaryOperator) {
        if (longUnaryOperator == null) {
            return null;
        }
        return longUnaryOperator instanceof n0 ? ((n0) longUnaryOperator).a : new C0299m0(longUnaryOperator);
    }

    @Override // j$.util.function.v
    public /* synthetic */ j$.util.function.v a(j$.util.function.v vVar) {
        return c(this.a.andThen(n0.a(vVar)));
    }

    @Override // j$.util.function.v
    public /* synthetic */ long applyAsLong(long j) {
        return this.a.applyAsLong(j);
    }

    @Override // j$.util.function.v
    public /* synthetic */ j$.util.function.v b(j$.util.function.v vVar) {
        return c(this.a.compose(n0.a(vVar)));
    }
}
