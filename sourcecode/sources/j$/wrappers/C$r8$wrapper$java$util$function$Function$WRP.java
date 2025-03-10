package j$.wrappers;

import java.util.function.Function;

/* renamed from: j$.wrappers.$r8$wrapper$java$util$function$Function$-WRP, reason: invalid class name */
/* loaded from: classes2.dex */
public final /* synthetic */ class C$r8$wrapper$java$util$function$Function$WRP implements Function {
    final /* synthetic */ j$.util.function.Function a;

    private /* synthetic */ C$r8$wrapper$java$util$function$Function$WRP(j$.util.function.Function function) {
        this.a = function;
    }

    public static /* synthetic */ Function convert(j$.util.function.Function function) {
        if (function == null) {
            return null;
        }
        return function instanceof K ? ((K) function).a : new C$r8$wrapper$java$util$function$Function$WRP(function);
    }

    @Override // java.util.function.Function
    public /* synthetic */ Function andThen(Function function) {
        return convert(this.a.andThen(K.a(function)));
    }

    @Override // java.util.function.Function
    public /* synthetic */ Object apply(Object obj) {
        return this.a.apply(obj);
    }

    @Override // java.util.function.Function
    public /* synthetic */ Function compose(Function function) {
        return convert(this.a.compose(K.a(function)));
    }
}
