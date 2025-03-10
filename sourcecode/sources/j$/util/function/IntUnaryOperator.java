package j$.util.function;

import java.util.Objects;

/* loaded from: classes2.dex */
public interface IntUnaryOperator {

    /* renamed from: j$.util.function.IntUnaryOperator$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
        public static IntUnaryOperator $default$andThen(IntUnaryOperator intUnaryOperator, IntUnaryOperator intUnaryOperator2) {
            Objects.requireNonNull(intUnaryOperator2);
            return new p(intUnaryOperator, intUnaryOperator2, 0);
        }

        public static IntUnaryOperator $default$compose(IntUnaryOperator intUnaryOperator, IntUnaryOperator intUnaryOperator2) {
            Objects.requireNonNull(intUnaryOperator2);
            return new p(intUnaryOperator, intUnaryOperator2, 1);
        }
    }

    IntUnaryOperator andThen(IntUnaryOperator intUnaryOperator);

    int applyAsInt(int i);

    IntUnaryOperator compose(IntUnaryOperator intUnaryOperator);
}
