package com.tencent.beacon.a.b;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.SparseArray;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: BeaconAsyncTask.java */
/* loaded from: classes.dex */
public class i extends com.tencent.beacon.a.b.a {
    private static final int c;
    private static final int d;
    private static final AtomicInteger e;
    private final ScheduledExecutorService f;
    private final SparseArray<a> g;
    private final SparseArray<Handler> h;
    private final j i;
    private boolean j;

    /* compiled from: BeaconAsyncTask.java */
    private static final class a {
        private final Runnable a;
        private final long b;
        private final long c;
        private final TimeUnit d;
        private Future<?> e;

        a(Future<?> future, Runnable runnable, long j, long j2, TimeUnit timeUnit) {
            this.e = future;
            this.a = runnable;
            this.b = j;
            this.c = j2;
            this.d = timeUnit;
        }

        boolean a(boolean z) {
            return this.e.cancel(z);
        }

        boolean a() {
            return this.e.isCancelled();
        }
    }

    static {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        c = availableProcessors;
        d = Math.max(2, Math.min(availableProcessors - 1, 3));
        e = new AtomicInteger(0);
    }

    i() {
        this(null);
    }

    private boolean f() {
        if (!this.j) {
            return false;
        }
        com.tencent.beacon.base.util.c.b("[task] was closed , should all stopped!", new Object[0]);
        return true;
    }

    @Override // com.tencent.beacon.a.b.a
    public synchronized void a(Runnable runnable) {
        if (f()) {
            return;
        }
        this.f.execute(b(runnable));
    }

    @Override // com.tencent.beacon.a.b.a
    public synchronized void b(int i) {
        if (c()) {
            a aVar = this.g.get(i);
            if (aVar != null) {
                if (!aVar.a()) {
                } else {
                    aVar.e = this.f.scheduleAtFixedRate(aVar.a, aVar.b, aVar.c, aVar.d);
                }
            }
        }
    }

    @Override // com.tencent.beacon.a.b.a
    public synchronized void d() {
        com.tencent.beacon.base.util.c.a("[task] Resumed all schedule task", new Object[0]);
        if (f()) {
            return;
        }
        for (int i = 0; i < this.g.size(); i++) {
            b(this.g.keyAt(i));
        }
        com.tencent.beacon.base.util.c.a("[task] Resumed all schedule task", new Object[0]);
    }

    i(ScheduledExecutorService scheduledExecutorService) {
        this.j = false;
        j jVar = new j();
        this.i = jVar;
        this.f = scheduledExecutorService == null ? Executors.newScheduledThreadPool(d, jVar) : scheduledExecutorService;
        this.g = new SparseArray<>();
        this.h = new SparseArray<>();
    }

    @Override // com.tencent.beacon.a.b.a
    public synchronized void a(int i, long j, long j2, Runnable runnable) {
        if (f()) {
            return;
        }
        a aVar = this.g.get(i);
        if (aVar == null || aVar.a()) {
            Runnable b = b(runnable);
            if (j <= 0) {
                j = 0;
            }
            if (j2 < 100) {
                j2 = 100;
            }
            ScheduledExecutorService scheduledExecutorService = this.f;
            TimeUnit timeUnit = TimeUnit.MILLISECONDS;
            a aVar2 = new a(scheduledExecutorService.scheduleAtFixedRate(b, j, j2, timeUnit), b, j, j2, timeUnit);
            com.tencent.beacon.base.util.c.a("[task] add a new polling task! taskId: %d , periodTime: %d", Integer.valueOf(i), Long.valueOf(j2));
            this.g.put(i, aVar2);
        }
    }

    private Runnable b(Runnable runnable) {
        return new h(this, runnable);
    }

    @Override // com.tencent.beacon.a.b.a
    public void a(int i, boolean z) {
        a aVar = this.g.get(i);
        if (aVar == null || aVar.a()) {
            return;
        }
        com.tencent.beacon.base.util.c.a("[task] cancel a old pollingTaskWrapper!", new Object[0]);
        aVar.a(z);
    }

    @Override // com.tencent.beacon.a.b.a
    public synchronized void a(boolean z) {
        if (f()) {
            return;
        }
        for (int i = 0; i < this.g.size(); i++) {
            a(this.g.keyAt(i), z);
        }
        com.tencent.beacon.base.util.c.a("[task] All schedule tasks stop", new Object[0]);
    }

    @Override // com.tencent.beacon.a.b.a
    public synchronized void a(long j, Runnable runnable) {
        if (f()) {
            return;
        }
        Runnable b = b(runnable);
        if (j <= 0) {
            j = 0;
        }
        this.f.schedule(b, j, TimeUnit.MILLISECONDS);
    }

    @Override // com.tencent.beacon.a.b.a
    public synchronized Handler a(int i) {
        Handler handler;
        handler = this.h.get(i);
        if (handler == null) {
            HandlerThread handlerThread = new HandlerThread(this.i.a());
            handlerThread.start();
            handler = new Handler(handlerThread.getLooper());
        }
        this.h.put(i, handler);
        return handler;
    }
}
