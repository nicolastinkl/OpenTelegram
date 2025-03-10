package io.openinstall.sdk;

import java.util.concurrent.LinkedBlockingQueue;

/* loaded from: classes.dex */
class fu implements Runnable {
    final /* synthetic */ LinkedBlockingQueue a;
    final /* synthetic */ ez b;

    fu(fr frVar, LinkedBlockingQueue linkedBlockingQueue, ez ezVar) {
        this.a = linkedBlockingQueue;
        this.b = ezVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.a.offer(this.b.a_());
    }
}
