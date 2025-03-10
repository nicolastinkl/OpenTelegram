package io.openinstall.sdk;

import android.util.Pair;
import java.util.concurrent.LinkedBlockingQueue;

/* loaded from: classes.dex */
class fi implements Runnable {
    final /* synthetic */ LinkedBlockingQueue a;
    final /* synthetic */ fh b;

    fi(fh fhVar, LinkedBlockingQueue linkedBlockingQueue) {
        this.b = fhVar;
        this.a = linkedBlockingQueue;
    }

    @Override // java.lang.Runnable
    public void run() {
        dt h;
        String str;
        LinkedBlockingQueue linkedBlockingQueue;
        String a;
        h = this.b.h();
        dw b = h.b(true);
        if (b == null || !b.c(2)) {
            str = "aviw";
            if (b == null || !b.c(1)) {
                this.a.offer(Pair.create("aviw", null));
                return;
            } else {
                linkedBlockingQueue = this.a;
                a = b.a();
            }
        } else {
            linkedBlockingQueue = this.a;
            a = b.b();
            str = "pwcf";
        }
        linkedBlockingQueue.offer(Pair.create(str, a));
    }
}
