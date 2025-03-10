package j$.wrappers;

import java.util.function.ObjLongConsumer;

/* loaded from: classes2.dex */
public final /* synthetic */ class t0 implements ObjLongConsumer {
    final /* synthetic */ j$.util.function.y a;

    private /* synthetic */ t0(j$.util.function.y yVar) {
        this.a = yVar;
    }

    public static /* synthetic */ ObjLongConsumer a(j$.util.function.y yVar) {
        if (yVar == null) {
            return null;
        }
        return yVar instanceof s0 ? ((s0) yVar).a : new t0(yVar);
    }

    @Override // java.util.function.ObjLongConsumer
    public /* synthetic */ void accept(Object obj, long j) {
        this.a.accept(obj, j);
    }
}
