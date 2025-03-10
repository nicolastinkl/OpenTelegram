package j$.wrappers;

import java.util.function.IntBinaryOperator;

/* loaded from: classes2.dex */
public final /* synthetic */ class M implements IntBinaryOperator {
    final /* synthetic */ j$.util.function.k a;

    private /* synthetic */ M(j$.util.function.k kVar) {
        this.a = kVar;
    }

    public static /* synthetic */ IntBinaryOperator a(j$.util.function.k kVar) {
        if (kVar == null) {
            return null;
        }
        return kVar instanceof L ? ((L) kVar).a : new M(kVar);
    }

    @Override // java.util.function.IntBinaryOperator
    public /* synthetic */ int applyAsInt(int i, int i2) {
        return this.a.applyAsInt(i, i2);
    }
}
