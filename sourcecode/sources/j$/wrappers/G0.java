package j$.wrappers;

import j$.util.function.BiConsumer;
import j$.util.function.Function;
import j$.util.function.Supplier;
import j$.util.stream.Collector;
import java.util.Set;

/* loaded from: classes2.dex */
public final /* synthetic */ class G0 implements Collector {
    final /* synthetic */ java.util.stream.Collector a;

    private /* synthetic */ G0(java.util.stream.Collector collector) {
        this.a = collector;
    }

    public static /* synthetic */ Collector a(java.util.stream.Collector collector) {
        if (collector == null) {
            return null;
        }
        return collector instanceof H0 ? ((H0) collector).a : new G0(collector);
    }

    @Override // j$.util.stream.Collector
    public /* synthetic */ BiConsumer accumulator() {
        return C0303q.a(this.a.accumulator());
    }

    @Override // j$.util.stream.Collector
    public /* synthetic */ Set characteristics() {
        return this.a.characteristics();
    }

    @Override // j$.util.stream.Collector
    public /* synthetic */ j$.util.function.b combiner() {
        return C0305t.a(this.a.combiner());
    }

    @Override // j$.util.stream.Collector
    public /* synthetic */ Function finisher() {
        return K.a(this.a.finisher());
    }

    @Override // j$.util.stream.Collector
    public /* synthetic */ Supplier supplier() {
        return w0.a(this.a.supplier());
    }
}
