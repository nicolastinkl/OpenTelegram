package j$.wrappers;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/* loaded from: classes2.dex */
public final /* synthetic */ class H0 implements Collector {
    final /* synthetic */ j$.util.stream.Collector a;

    private /* synthetic */ H0(j$.util.stream.Collector collector) {
        this.a = collector;
    }

    public static /* synthetic */ Collector a(j$.util.stream.Collector collector) {
        if (collector == null) {
            return null;
        }
        return collector instanceof G0 ? ((G0) collector).a : new H0(collector);
    }

    @Override // java.util.stream.Collector
    public /* synthetic */ BiConsumer accumulator() {
        return r.a(this.a.accumulator());
    }

    @Override // java.util.stream.Collector
    public /* synthetic */ Set characteristics() {
        return this.a.characteristics();
    }

    @Override // java.util.stream.Collector
    public /* synthetic */ BinaryOperator combiner() {
        return C0306u.a(this.a.combiner());
    }

    @Override // java.util.stream.Collector
    public /* synthetic */ Function finisher() {
        return C$r8$wrapper$java$util$function$Function$WRP.convert(this.a.finisher());
    }

    @Override // java.util.stream.Collector
    public /* synthetic */ Supplier supplier() {
        return x0.a(this.a.supplier());
    }
}
