package j$.util;

import j$.util.function.Consumer;

/* loaded from: classes2.dex */
public interface l extends n {
    void e(j$.util.function.f fVar);

    void forEachRemaining(Consumer consumer);

    @Override // java.util.Iterator
    Double next();

    double nextDouble();
}
