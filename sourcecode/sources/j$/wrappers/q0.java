package j$.wrappers;

import java.util.function.ObjIntConsumer;

/* loaded from: classes2.dex */
public final /* synthetic */ class q0 implements j$.util.function.x {
    final /* synthetic */ ObjIntConsumer a;

    private /* synthetic */ q0(ObjIntConsumer objIntConsumer) {
        this.a = objIntConsumer;
    }

    public static /* synthetic */ j$.util.function.x a(ObjIntConsumer objIntConsumer) {
        if (objIntConsumer == null) {
            return null;
        }
        return objIntConsumer instanceof r0 ? ((r0) objIntConsumer).a : new q0(objIntConsumer);
    }

    @Override // j$.util.function.x
    public /* synthetic */ void accept(Object obj, int i) {
        this.a.accept(obj, i);
    }
}
