package com.tencent.beacon.a.b;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: BeaconThreadFactory.java */
/* loaded from: classes.dex */
public final class j implements ThreadFactory {
    private final AtomicInteger a = new AtomicInteger(1);

    public String a() {
        return "beacon-thread-" + this.a.getAndIncrement();
    }

    @Override // java.util.concurrent.ThreadFactory
    public Thread newThread(Runnable runnable) {
        try {
            return new Thread(runnable, a());
        } catch (Exception e) {
            com.tencent.beacon.base.util.c.a(e);
            return null;
        } catch (OutOfMemoryError unused) {
            com.tencent.beacon.base.util.c.b("[task] memory not enough, create thread failed.", new Object[0]);
            return null;
        }
    }
}
