package j$.wrappers;

import java.util.function.DoubleConsumer;

/* renamed from: j$.wrappers.y, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public final /* synthetic */ class C0310y implements j$.util.function.f {
    final /* synthetic */ DoubleConsumer a;

    private /* synthetic */ C0310y(DoubleConsumer doubleConsumer) {
        this.a = doubleConsumer;
    }

    public static /* synthetic */ j$.util.function.f b(DoubleConsumer doubleConsumer) {
        if (doubleConsumer == null) {
            return null;
        }
        return doubleConsumer instanceof C0311z ? ((C0311z) doubleConsumer).a : new C0310y(doubleConsumer);
    }

    @Override // j$.util.function.f
    public /* synthetic */ void accept(double d) {
        this.a.accept(d);
    }

    @Override // j$.util.function.f
    public /* synthetic */ j$.util.function.f j(j$.util.function.f fVar) {
        return b(this.a.andThen(C0311z.a(fVar)));
    }
}
