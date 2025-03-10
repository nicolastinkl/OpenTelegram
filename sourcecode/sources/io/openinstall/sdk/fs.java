package io.openinstall.sdk;

import java.util.concurrent.LinkedBlockingQueue;

/* loaded from: classes.dex */
class fs implements Runnable {
    final /* synthetic */ LinkedBlockingQueue a;
    final /* synthetic */ fr b;

    fs(fr frVar, LinkedBlockingQueue linkedBlockingQueue) {
        this.b = frVar;
        this.a = linkedBlockingQueue;
    }

    @Override // java.lang.Runnable
    public void run() {
        dt h;
        dw a;
        dt h2;
        dt h3;
        LinkedBlockingQueue linkedBlockingQueue;
        ey eyVar;
        h = this.b.h();
        a = this.b.a(h.b(true));
        h2 = this.b.h();
        h2.a(false);
        h3 = this.b.h();
        if (h3.a() && a == null) {
            linkedBlockingQueue = this.a;
            eyVar = new ey("pbR", "jgkf", String.valueOf(false));
        } else if (a != null && a.c(2)) {
            this.a.offer(new ey("pbH", "pwcf", a.b()));
            return;
        } else if (a != null && a.c(1)) {
            this.a.offer(new ey("pbT", "aviw", a.a()));
            return;
        } else {
            linkedBlockingQueue = this.a;
            eyVar = new ey("pbT", "aviw", null);
        }
        linkedBlockingQueue.offer(eyVar);
    }
}
