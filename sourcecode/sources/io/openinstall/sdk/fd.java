package io.openinstall.sdk;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes.dex */
class fd implements ThreadFactory {
    private final AtomicInteger a = new AtomicInteger(1);

    fd() {
    }

    @Override // java.util.concurrent.ThreadFactory
    public Thread newThread(Runnable runnable) {
        return new Thread(runnable, "pool-ot-" + this.a.getAndIncrement());
    }
}
