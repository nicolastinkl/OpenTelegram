package j$.util;

import j$.util.function.Consumer;

/* loaded from: classes2.dex */
public interface n extends java.util.Iterator {

    public interface a extends n {
        void c(j$.util.function.m mVar);

        void forEachRemaining(Consumer consumer);

        @Override // java.util.Iterator
        Integer next();

        int nextInt();
    }

    void forEachRemaining(Object obj);
}
