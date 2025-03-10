package j$.wrappers;

import java.util.function.IntConsumer;

/* loaded from: classes2.dex */
public final /* synthetic */ class N implements j$.util.function.m {
    final /* synthetic */ IntConsumer a;

    private /* synthetic */ N(IntConsumer intConsumer) {
        this.a = intConsumer;
    }

    public static /* synthetic */ j$.util.function.m b(IntConsumer intConsumer) {
        if (intConsumer == null) {
            return null;
        }
        return intConsumer instanceof O ? ((O) intConsumer).a : new N(intConsumer);
    }

    @Override // j$.util.function.m
    public /* synthetic */ void accept(int i) {
        this.a.accept(i);
    }

    @Override // j$.util.function.m
    public /* synthetic */ j$.util.function.m l(j$.util.function.m mVar) {
        return b(this.a.andThen(O.a(mVar)));
    }
}
