package j$.util;

import j$.util.function.Consumer;

/* loaded from: classes2.dex */
public interface p extends n {
    void d(j$.util.function.s sVar);

    void forEachRemaining(Consumer consumer);

    @Override // java.util.Iterator
    Long next();

    long nextLong();
}
