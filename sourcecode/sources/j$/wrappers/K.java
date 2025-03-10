package j$.wrappers;

import j$.util.function.Function;

/* loaded from: classes2.dex */
public final /* synthetic */ class K implements Function {
    final /* synthetic */ java.util.function.Function a;

    private /* synthetic */ K(java.util.function.Function function) {
        this.a = function;
    }

    public static /* synthetic */ Function a(java.util.function.Function function) {
        if (function == null) {
            return null;
        }
        return function instanceof C$r8$wrapper$java$util$function$Function$WRP ? ((C$r8$wrapper$java$util$function$Function$WRP) function).a : new K(function);
    }

    @Override // j$.util.function.Function
    public /* synthetic */ Function andThen(Function function) {
        return a(this.a.andThen(C$r8$wrapper$java$util$function$Function$WRP.convert(function)));
    }

    @Override // j$.util.function.Function
    public /* synthetic */ Object apply(Object obj) {
        return this.a.apply(obj);
    }

    @Override // j$.util.function.Function
    public /* synthetic */ Function compose(Function function) {
        return a(this.a.compose(C$r8$wrapper$java$util$function$Function$WRP.convert(function)));
    }
}
