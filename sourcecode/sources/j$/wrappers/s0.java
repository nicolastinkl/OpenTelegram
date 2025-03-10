package j$.wrappers;

import java.util.function.ObjLongConsumer;

/* loaded from: classes2.dex */
public final /* synthetic */ class s0 implements j$.util.function.y {
    final /* synthetic */ ObjLongConsumer a;

    private /* synthetic */ s0(ObjLongConsumer objLongConsumer) {
        this.a = objLongConsumer;
    }

    public static /* synthetic */ j$.util.function.y a(ObjLongConsumer objLongConsumer) {
        if (objLongConsumer == null) {
            return null;
        }
        return objLongConsumer instanceof t0 ? ((t0) objLongConsumer).a : new s0(objLongConsumer);
    }

    @Override // j$.util.function.y
    public /* synthetic */ void accept(Object obj, long j) {
        this.a.accept(obj, j);
    }
}
