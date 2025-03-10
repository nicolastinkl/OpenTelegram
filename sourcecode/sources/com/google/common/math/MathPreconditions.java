package com.google.common.math;

/* loaded from: classes.dex */
final class MathPreconditions {
    static void checkRoundingUnnecessary(boolean z) {
        if (!z) {
            throw new ArithmeticException("mode was UNNECESSARY, but rounding was necessary");
        }
    }
}
