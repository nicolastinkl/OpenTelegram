package j$.wrappers;

import j$.util.function.Supplier;

/* loaded from: classes2.dex */
public final /* synthetic */ class w0 implements Supplier {
    final /* synthetic */ java.util.function.Supplier a;

    private /* synthetic */ w0(java.util.function.Supplier supplier) {
        this.a = supplier;
    }

    public static /* synthetic */ Supplier a(java.util.function.Supplier supplier) {
        if (supplier == null) {
            return null;
        }
        return supplier instanceof x0 ? ((x0) supplier).a : new w0(supplier);
    }

    @Override // j$.util.function.Supplier
    public /* synthetic */ Object get() {
        return this.a.get();
    }
}
