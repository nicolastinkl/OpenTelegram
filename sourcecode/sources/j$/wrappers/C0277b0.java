package j$.wrappers;

import java.util.function.LongBinaryOperator;

/* renamed from: j$.wrappers.b0, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public final /* synthetic */ class C0277b0 implements LongBinaryOperator {
    final /* synthetic */ j$.util.function.q a;

    private /* synthetic */ C0277b0(j$.util.function.q qVar) {
        this.a = qVar;
    }

    public static /* synthetic */ LongBinaryOperator a(j$.util.function.q qVar) {
        if (qVar == null) {
            return null;
        }
        return qVar instanceof C0275a0 ? ((C0275a0) qVar).a : new C0277b0(qVar);
    }

    @Override // java.util.function.LongBinaryOperator
    public /* synthetic */ long applyAsLong(long j, long j2) {
        return this.a.applyAsLong(j, j2);
    }
}
