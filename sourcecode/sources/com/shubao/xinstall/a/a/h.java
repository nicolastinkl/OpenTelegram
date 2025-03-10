package com.shubao.xinstall.a.a;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public final class h {
    public c a;
    public CountDownLatch b = new CountDownLatch(1);
    public LinkedBlockingQueue<Object> c = new LinkedBlockingQueue<>(1);
    private Object d = new Object();

    public final boolean a(long j) {
        c cVar = this.a;
        if (cVar == null || cVar == c.b || cVar == c.c) {
            this.c.offer(this.d);
            try {
                this.b.await(j, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return this.a == c.a;
    }
}
