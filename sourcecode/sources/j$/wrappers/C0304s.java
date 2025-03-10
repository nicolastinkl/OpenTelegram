package j$.wrappers;

import j$.util.function.BiFunction;
import j$.util.function.Function;

/* renamed from: j$.wrappers.s, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public final /* synthetic */ class C0304s implements BiFunction {
    final /* synthetic */ java.util.function.BiFunction a;

    private /* synthetic */ C0304s(java.util.function.BiFunction biFunction) {
        this.a = biFunction;
    }

    public static /* synthetic */ BiFunction a(java.util.function.BiFunction biFunction) {
        if (biFunction == null) {
            return null;
        }
        return biFunction instanceof C$r8$wrapper$java$util$function$BiFunction$WRP ? ((C$r8$wrapper$java$util$function$BiFunction$WRP) biFunction).a : new C0304s(biFunction);
    }

    @Override // j$.util.function.BiFunction
    public /* synthetic */ BiFunction andThen(Function function) {
        return a(this.a.andThen(C$r8$wrapper$java$util$function$Function$WRP.convert(function)));
    }

    @Override // j$.util.function.BiFunction
    public /* synthetic */ Object apply(Object obj, Object obj2) {
        return this.a.apply(obj, obj2);
    }
}
