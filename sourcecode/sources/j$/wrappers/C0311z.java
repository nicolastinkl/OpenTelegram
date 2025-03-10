package j$.wrappers;

import java.util.function.DoubleConsumer;

/* renamed from: j$.wrappers.z, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public final /* synthetic */ class C0311z implements DoubleConsumer {
    final /* synthetic */ j$.util.function.f a;

    private /* synthetic */ C0311z(j$.util.function.f fVar) {
        this.a = fVar;
    }

    public static /* synthetic */ DoubleConsumer a(j$.util.function.f fVar) {
        if (fVar == null) {
            return null;
        }
        return fVar instanceof C0310y ? ((C0310y) fVar).a : new C0311z(fVar);
    }

    @Override // java.util.function.DoubleConsumer
    public /* synthetic */ void accept(double d) {
        this.a.accept(d);
    }

    @Override // java.util.function.DoubleConsumer
    public /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
        return a(this.a.j(C0310y.b(doubleConsumer)));
    }
}
