package j$.wrappers;

import java.util.function.ObjDoubleConsumer;

/* loaded from: classes2.dex */
public final /* synthetic */ class p0 implements ObjDoubleConsumer {
    final /* synthetic */ j$.util.function.w a;

    private /* synthetic */ p0(j$.util.function.w wVar) {
        this.a = wVar;
    }

    public static /* synthetic */ ObjDoubleConsumer a(j$.util.function.w wVar) {
        if (wVar == null) {
            return null;
        }
        return wVar instanceof o0 ? ((o0) wVar).a : new p0(wVar);
    }

    @Override // java.util.function.ObjDoubleConsumer
    public /* synthetic */ void accept(Object obj, double d) {
        this.a.accept(obj, d);
    }
}
