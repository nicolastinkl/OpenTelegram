package com.tencent.qimei.b;

import android.util.SparseArray;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: BeaconAsyncTask.java */
/* loaded from: classes.dex */
public class c extends a {
    public static final int b;
    public static final int c;
    public final ScheduledExecutorService d;
    public boolean e = false;
    public final d f;

    static {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        b = availableProcessors;
        c = Math.max(2, Math.min(availableProcessors - 1, 3));
        new AtomicInteger(0);
    }

    public c() {
        d dVar = new d();
        this.f = dVar;
        this.d = Executors.newScheduledThreadPool(c, dVar);
        new SparseArray();
        new SparseArray();
    }

    @Override // com.tencent.qimei.b.a
    public synchronized void a(long j, Runnable runnable) {
        if (this.e) {
            return;
        }
        b bVar = new b(this, runnable);
        if (j <= 0) {
            j = 0;
        }
        this.d.schedule(bVar, j, TimeUnit.MILLISECONDS);
    }

    @Override // com.tencent.qimei.b.a
    public synchronized void a(Runnable runnable) {
        if (this.e) {
            return;
        }
        try {
            this.d.execute(new b(this, runnable));
        } catch (Exception unused) {
        }
    }
}
