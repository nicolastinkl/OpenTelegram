package io.openinstall.sdk;

import android.os.IBinder;
import java.util.concurrent.LinkedBlockingQueue;

/* loaded from: classes.dex */
class z implements Runnable {
    final /* synthetic */ IBinder a;
    final /* synthetic */ y b;

    z(y yVar, IBinder iBinder) {
        this.b = yVar;
        this.a = iBinder;
    }

    @Override // java.lang.Runnable
    public void run() {
        LinkedBlockingQueue linkedBlockingQueue;
        try {
            linkedBlockingQueue = this.b.c;
            linkedBlockingQueue.put(this.a);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
