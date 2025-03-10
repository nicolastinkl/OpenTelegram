package com.tencent.qmsp.sdk.c;

import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes.dex */
public class o {
    private AtomicInteger a = new AtomicInteger(0);

    public void c() {
        int i;
        if (this.a.compareAndSet(0, 1) || this.a.compareAndSet(1, 1)) {
            return;
        }
        do {
            i = this.a.get();
        } while (!this.a.compareAndSet(i, i | 1));
        synchronized (this.a) {
            try {
                this.a.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
