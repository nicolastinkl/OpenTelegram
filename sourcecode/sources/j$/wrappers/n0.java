package j$.wrappers;

import java.util.function.LongUnaryOperator;

/* loaded from: classes2.dex */
public final /* synthetic */ class n0 implements LongUnaryOperator {
    final /* synthetic */ j$.util.function.v a;

    private /* synthetic */ n0(j$.util.function.v vVar) {
        this.a = vVar;
    }

    public static /* synthetic */ LongUnaryOperator a(j$.util.function.v vVar) {
        if (vVar == null) {
            return null;
        }
        return vVar instanceof C0299m0 ? ((C0299m0) vVar).a : new n0(vVar);
    }

    @Override // java.util.function.LongUnaryOperator
    public /* synthetic */ LongUnaryOperator andThen(LongUnaryOperator longUnaryOperator) {
        return a(this.a.a(C0299m0.c(longUnaryOperator)));
    }

    @Override // java.util.function.LongUnaryOperator
    public /* synthetic */ long applyAsLong(long j) {
        return this.a.applyAsLong(j);
    }

    @Override // java.util.function.LongUnaryOperator
    public /* synthetic */ LongUnaryOperator compose(LongUnaryOperator longUnaryOperator) {
        return a(this.a.b(C0299m0.c(longUnaryOperator)));
    }
}
