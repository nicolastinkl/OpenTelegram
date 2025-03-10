package j$.wrappers;

import java.util.function.LongBinaryOperator;

/* renamed from: j$.wrappers.a0, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public final /* synthetic */ class C0275a0 implements j$.util.function.q {
    final /* synthetic */ LongBinaryOperator a;

    private /* synthetic */ C0275a0(LongBinaryOperator longBinaryOperator) {
        this.a = longBinaryOperator;
    }

    public static /* synthetic */ j$.util.function.q a(LongBinaryOperator longBinaryOperator) {
        if (longBinaryOperator == null) {
            return null;
        }
        return longBinaryOperator instanceof C0277b0 ? ((C0277b0) longBinaryOperator).a : new C0275a0(longBinaryOperator);
    }

    @Override // j$.util.function.q
    public /* synthetic */ long applyAsLong(long j, long j2) {
        return this.a.applyAsLong(j, j2);
    }
}
