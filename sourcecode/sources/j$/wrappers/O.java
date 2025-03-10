package j$.wrappers;

import java.util.function.IntConsumer;

/* loaded from: classes2.dex */
public final /* synthetic */ class O implements IntConsumer {
    final /* synthetic */ j$.util.function.m a;

    private /* synthetic */ O(j$.util.function.m mVar) {
        this.a = mVar;
    }

    public static /* synthetic */ IntConsumer a(j$.util.function.m mVar) {
        if (mVar == null) {
            return null;
        }
        return mVar instanceof N ? ((N) mVar).a : new O(mVar);
    }

    @Override // java.util.function.IntConsumer
    public /* synthetic */ void accept(int i) {
        this.a.accept(i);
    }

    @Override // java.util.function.IntConsumer
    public /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
        return a(this.a.l(N.b(intConsumer)));
    }
}
