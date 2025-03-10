package com.tencent.beacon.a.b;

import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: BeaconAsyncTask.java */
/* loaded from: classes.dex */
class h implements Runnable {
    final /* synthetic */ Runnable a;
    final /* synthetic */ i b;

    h(i iVar, Runnable runnable) {
        this.b = iVar;
        this.a = runnable;
    }

    @Override // java.lang.Runnable
    public void run() {
        AtomicInteger atomicInteger;
        try {
            this.a.run();
        } catch (Throwable th) {
            atomicInteger = i.e;
            if (atomicInteger.addAndGet(1) < 100) {
                g.e().a("599", "[task] run occur error!", th);
            }
            com.tencent.beacon.base.util.e.a(th.getMessage());
            com.tencent.beacon.base.util.c.a(th);
        }
    }
}
