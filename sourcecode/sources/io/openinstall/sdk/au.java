package io.openinstall.sdk;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class au {
    private volatile av a = null;
    private final CountDownLatch b = new CountDownLatch(1);
    private final LinkedBlockingQueue<Object> c = new LinkedBlockingQueue<>(1);
    private final Object d = new Object();

    public Object a(long j) throws InterruptedException {
        return this.c.poll(j, TimeUnit.SECONDS);
    }

    public void a() {
        if (this.a == null || this.a == av.a || this.a == av.b) {
            this.c.offer(this.d);
        }
    }

    public synchronized void a(av avVar) {
        this.a = avVar;
    }

    public void a(String str, long j) {
        if (this.a == null || this.a == av.a || this.a == av.b) {
            this.c.offer(this.d);
            try {
                this.b.await(j, TimeUnit.SECONDS);
            } catch (InterruptedException unused) {
                if (ga.a) {
                    ga.b("%s awaitInit interrupted", str);
                }
            }
        }
    }

    public boolean b() {
        return this.a == av.d;
    }

    public boolean c() {
        return this.a == av.e || this.a == av.d || this.a == av.f;
    }

    public synchronized av d() {
        return this.a;
    }

    public void e() {
        this.b.countDown();
    }
}
