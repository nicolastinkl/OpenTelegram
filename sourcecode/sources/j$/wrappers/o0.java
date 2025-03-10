package j$.wrappers;

import java.util.function.ObjDoubleConsumer;

/* loaded from: classes2.dex */
public final /* synthetic */ class o0 implements j$.util.function.w {
    final /* synthetic */ ObjDoubleConsumer a;

    private /* synthetic */ o0(ObjDoubleConsumer objDoubleConsumer) {
        this.a = objDoubleConsumer;
    }

    public static /* synthetic */ j$.util.function.w a(ObjDoubleConsumer objDoubleConsumer) {
        if (objDoubleConsumer == null) {
            return null;
        }
        return objDoubleConsumer instanceof p0 ? ((p0) objDoubleConsumer).a : new o0(objDoubleConsumer);
    }

    @Override // j$.util.function.w
    public /* synthetic */ void accept(Object obj, double d) {
        this.a.accept(obj, d);
    }
}
