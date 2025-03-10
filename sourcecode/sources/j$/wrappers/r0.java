package j$.wrappers;

import java.util.function.ObjIntConsumer;

/* loaded from: classes2.dex */
public final /* synthetic */ class r0 implements ObjIntConsumer {
    final /* synthetic */ j$.util.function.x a;

    private /* synthetic */ r0(j$.util.function.x xVar) {
        this.a = xVar;
    }

    public static /* synthetic */ ObjIntConsumer a(j$.util.function.x xVar) {
        if (xVar == null) {
            return null;
        }
        return xVar instanceof q0 ? ((q0) xVar).a : new r0(xVar);
    }

    @Override // java.util.function.ObjIntConsumer
    public /* synthetic */ void accept(Object obj, int i) {
        this.a.accept(obj, i);
    }
}
