package j$.wrappers;

import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;

/* renamed from: j$.wrappers.u, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public final /* synthetic */ class C0306u implements BinaryOperator {
    final /* synthetic */ j$.util.function.b a;

    private /* synthetic */ C0306u(j$.util.function.b bVar) {
        this.a = bVar;
    }

    public static /* synthetic */ BinaryOperator a(j$.util.function.b bVar) {
        if (bVar == null) {
            return null;
        }
        return bVar instanceof C0305t ? ((C0305t) bVar).a : new C0306u(bVar);
    }

    @Override // java.util.function.BiFunction
    public /* synthetic */ BiFunction andThen(Function function) {
        return C$r8$wrapper$java$util$function$BiFunction$WRP.convert(this.a.andThen(K.a(function)));
    }

    @Override // java.util.function.BiFunction
    public /* synthetic */ Object apply(Object obj, Object obj2) {
        return this.a.apply(obj, obj2);
    }
}
