package j$.wrappers;

import java.util.function.DoubleBinaryOperator;

/* renamed from: j$.wrappers.x, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public final /* synthetic */ class C0309x implements DoubleBinaryOperator {
    final /* synthetic */ j$.util.function.d a;

    private /* synthetic */ C0309x(j$.util.function.d dVar) {
        this.a = dVar;
    }

    public static /* synthetic */ DoubleBinaryOperator a(j$.util.function.d dVar) {
        if (dVar == null) {
            return null;
        }
        return dVar instanceof C0308w ? ((C0308w) dVar).a : new C0309x(dVar);
    }

    @Override // java.util.function.DoubleBinaryOperator
    public /* synthetic */ double applyAsDouble(double d, double d2) {
        return this.a.applyAsDouble(d, d2);
    }
}
