package j$.util.concurrent.atomic;

import j$.util.function.IntUnaryOperator;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes2.dex */
public class DesugarAtomicInteger {
    public static int updateAndGet(AtomicInteger atomicInteger, IntUnaryOperator intUnaryOperator) {
        int i;
        int applyAsInt;
        do {
            i = atomicInteger.get();
            applyAsInt = intUnaryOperator.applyAsInt(i);
        } while (!atomicInteger.compareAndSet(i, applyAsInt));
        return applyAsInt;
    }
}
