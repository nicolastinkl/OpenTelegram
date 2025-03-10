package io.openinstall.sdk;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
class dl implements Runnable {
    final /* synthetic */ dj a;

    dl(dj djVar) {
        this.a = djVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        dm dmVar;
        LinkedBlockingQueue linkedBlockingQueue;
        int i = 100;
        while (true) {
            try {
                linkedBlockingQueue = this.a.c;
                linkedBlockingQueue.poll(i, TimeUnit.MILLISECONDS);
            } catch (InterruptedException unused) {
            }
            dmVar = this.a.d;
            dmVar.a();
            i = Math.min(i * 10, 10000);
        }
    }
}
