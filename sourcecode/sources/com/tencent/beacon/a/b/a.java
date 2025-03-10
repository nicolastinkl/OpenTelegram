package com.tencent.beacon.a.b;

import android.os.Handler;
import java.util.concurrent.ScheduledExecutorService;

/* compiled from: AbsAsyncTask.java */
/* loaded from: classes.dex */
public abstract class a {
    protected static volatile a a;
    private boolean b = true;

    /* compiled from: AbsAsyncTask.java */
    /* renamed from: com.tencent.beacon.a.b.a$a, reason: collision with other inner class name */
    private static class C0015a {
        static final a a = new i();
    }

    public static synchronized a a() {
        a aVar;
        synchronized (a.class) {
            if (a == null) {
                a = new i();
            }
            aVar = a;
        }
        return aVar;
    }

    public static a b() {
        return C0015a.a;
    }

    public abstract Handler a(int i);

    public abstract void a(int i, long j, long j2, Runnable runnable);

    public abstract void a(int i, boolean z);

    public abstract void a(long j, Runnable runnable);

    public abstract void a(Runnable runnable);

    public abstract void a(boolean z);

    public abstract void b(int i);

    public boolean c() {
        return this.b;
    }

    public abstract void d();

    public static synchronized void a(ScheduledExecutorService scheduledExecutorService) {
        synchronized (a.class) {
            if (a == null) {
                a = new i(scheduledExecutorService);
            }
        }
    }
}
