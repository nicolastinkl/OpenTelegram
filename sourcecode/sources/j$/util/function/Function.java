package j$.util.function;

import j$.util.function.Function;
import java.util.Objects;

/* loaded from: classes2.dex */
public interface Function<T, R> {

    /* renamed from: j$.util.function.Function$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
        public static Function $default$andThen(Function function, Function function2) {
            Objects.requireNonNull(function2);
            return new i(function, function2, 0);
        }

        public static Function $default$compose(Function function, Function function2) {
            Objects.requireNonNull(function2);
            return new i(function, function2, 1);
        }

        public static <T> Function<T, T> identity() {
            return new Function() { // from class: j$.util.function.j
                @Override // j$.util.function.Function
                public /* synthetic */ Function andThen(Function function) {
                    return Function.CC.$default$andThen(this, function);
                }

                @Override // j$.util.function.Function
                public final Object apply(Object obj) {
                    return obj;
                }

                @Override // j$.util.function.Function
                public /* synthetic */ Function compose(Function function) {
                    return Function.CC.$default$compose(this, function);
                }
            };
        }
    }

    <V> Function<T, V> andThen(Function<? super R, ? extends V> function);

    R apply(T t);

    <V> Function<V, R> compose(Function<? super V, ? extends T> function);
}
