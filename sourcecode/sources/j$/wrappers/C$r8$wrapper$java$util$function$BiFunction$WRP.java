package j$.wrappers;

import java.util.function.BiFunction;
import java.util.function.Function;

/* renamed from: j$.wrappers.$r8$wrapper$java$util$function$BiFunction$-WRP, reason: invalid class name */
/* loaded from: classes2.dex */
public final /* synthetic */ class C$r8$wrapper$java$util$function$BiFunction$WRP implements BiFunction {
    final /* synthetic */ j$.util.function.BiFunction a;

    private /* synthetic */ C$r8$wrapper$java$util$function$BiFunction$WRP(j$.util.function.BiFunction biFunction) {
        this.a = biFunction;
    }

    public static /* synthetic */ BiFunction convert(j$.util.function.BiFunction biFunction) {
        if (biFunction == null) {
            return null;
        }
        return biFunction instanceof C0304s ? ((C0304s) biFunction).a : new C$r8$wrapper$java$util$function$BiFunction$WRP(biFunction);
    }

    @Override // java.util.function.BiFunction
    public /* synthetic */ BiFunction andThen(Function function) {
        return convert(this.a.andThen(K.a(function)));
    }

    @Override // java.util.function.BiFunction
    public /* synthetic */ Object apply(Object obj, Object obj2) {
        return this.a.apply(obj, obj2);
    }
}
