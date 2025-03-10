package j$.util;

import j$.util.stream.AbstractC0238t1;
import j$.util.stream.Stream;

/* loaded from: classes2.dex */
public class DesugarArrays {
    public static r a(Object[] objArr, int i, int i2) {
        return H.m(objArr, i, i2, 1040);
    }

    public static <T> Stream<T> stream(T[] tArr) {
        return AbstractC0238t1.y(H.m(tArr, 0, tArr.length, 1040), false);
    }
}
