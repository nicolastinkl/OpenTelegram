package j$.wrappers;

import j$.util.function.BiFunction;
import j$.util.function.Function;
import java.util.function.BinaryOperator;

/* renamed from: j$.wrappers.t, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public final /* synthetic */ class C0305t implements j$.util.function.b {
    final /* synthetic */ BinaryOperator a;

    private /* synthetic */ C0305t(BinaryOperator binaryOperator) {
        this.a = binaryOperator;
    }

    public static /* synthetic */ j$.util.function.b a(BinaryOperator binaryOperator) {
        if (binaryOperator == null) {
            return null;
        }
        return binaryOperator instanceof C0306u ? ((C0306u) binaryOperator).a : new C0305t(binaryOperator);
    }

    @Override // j$.util.function.BiFunction
    public /* synthetic */ BiFunction andThen(Function function) {
        return C0304s.a(this.a.andThen(C$r8$wrapper$java$util$function$Function$WRP.convert(function)));
    }

    @Override // j$.util.function.BiFunction
    public /* synthetic */ Object apply(Object obj, Object obj2) {
        return this.a.apply(obj, obj2);
    }
}
