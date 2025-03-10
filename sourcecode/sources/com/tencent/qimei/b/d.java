package com.tencent.qimei.b;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: BeaconThreadFactory.java */
/* loaded from: classes.dex */
public final class d implements ThreadFactory {
    public final AtomicInteger a = new AtomicInteger(1);

    @Override // java.util.concurrent.ThreadFactory
    public Thread newThread(Runnable runnable) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("qm-thread-");
            sb.append(this.a.getAndIncrement());
            return new Thread(runnable, sb.toString());
        } catch (Exception e) {
            com.tencent.qimei.k.a.a(e);
            return null;
        } catch (OutOfMemoryError unused) {
            return null;
        }
    }
}
