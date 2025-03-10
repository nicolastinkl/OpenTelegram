package io.openinstall.sdk;

import java.util.concurrent.LinkedBlockingQueue;

/* loaded from: classes.dex */
class ft implements Runnable {
    final /* synthetic */ LinkedBlockingQueue a;
    final /* synthetic */ fr b;

    ft(fr frVar, LinkedBlockingQueue linkedBlockingQueue) {
        this.b = frVar;
        this.a = linkedBlockingQueue;
    }

    @Override // java.lang.Runnable
    public void run() {
        bc f;
        f = this.b.f();
        this.a.offer(new ey("aI", "ihse", f.l()));
    }
}
