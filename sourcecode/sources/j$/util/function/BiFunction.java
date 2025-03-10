package j$.util.function;

import java.util.Objects;

/* loaded from: classes2.dex */
public interface BiFunction<T, U, R> {

    /* renamed from: j$.util.function.BiFunction$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
        public static BiFunction $default$andThen(BiFunction biFunction, Function function) {
            Objects.requireNonNull(function);
            return new j$.util.concurrent.a(biFunction, function);
        }
    }

    <V> BiFunction<T, U, V> andThen(Function<? super R, ? extends V> function);

    R apply(T t, U u);
}
