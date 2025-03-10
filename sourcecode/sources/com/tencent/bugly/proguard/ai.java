package com.tencent.bugly.proguard;

import android.content.Context;
import android.os.Process;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

/* compiled from: BUGLY */
/* loaded from: classes.dex */
public final class ai {
    private static ai b;
    public ah a;
    private final Context d;
    private long f;
    private long g;
    private Map<Integer, Long> e = new HashMap();
    private LinkedBlockingQueue<Runnable> h = new LinkedBlockingQueue<>();
    private LinkedBlockingQueue<Runnable> i = new LinkedBlockingQueue<>();
    private final Object j = new Object();
    private long k = 0;
    private int l = 0;
    private final w c = w.a();

    static /* synthetic */ int b(ai aiVar) {
        int i = aiVar.l - 1;
        aiVar.l = i;
        return i;
    }

    private ai(Context context) {
        this.d = context;
    }

    public static synchronized ai a(Context context) {
        ai aiVar;
        synchronized (ai.class) {
            if (b == null) {
                b = new ai(context);
            }
            aiVar = b;
        }
        return aiVar;
    }

    public static synchronized ai a() {
        ai aiVar;
        synchronized (ai.class) {
            aiVar = b;
        }
        return aiVar;
    }

    public final void a(int i, bq bqVar, String str, String str2, ah ahVar, long j, boolean z) {
        try {
        } catch (Throwable th) {
            th = th;
        }
        try {
            a(new aj(this.d, i, bqVar.g, ae.a((Object) bqVar), str, str2, ahVar, z), true, true, j);
        } catch (Throwable th2) {
            th = th2;
            if (al.a(th)) {
                return;
            }
            th.printStackTrace();
        }
    }

    private void a(int i, int i2, byte[] bArr, String str, String str2, ah ahVar, boolean z) {
        try {
        } catch (Throwable th) {
            th = th;
        }
        try {
            a(new aj(this.d, i, i2, bArr, str, str2, ahVar, 0, 0, false), z, false, 0L);
        } catch (Throwable th2) {
            th = th2;
            if (al.a(th)) {
                return;
            }
            th.printStackTrace();
        }
    }

    public final void a(int i, bq bqVar, String str, String str2, ah ahVar, boolean z) {
        a(i, bqVar.g, ae.a((Object) bqVar), str, str2, ahVar, z);
    }

    public final long a(boolean z) {
        long j;
        long b2 = ap.b();
        int i = z ? 5 : 3;
        List<y> a = this.c.a(i);
        if (a != null && a.size() > 0) {
            j = 0;
            try {
                y yVar = a.get(0);
                if (yVar.e >= b2) {
                    j = ap.d(yVar.g);
                    if (i == 3) {
                        this.f = j;
                    } else {
                        this.g = j;
                    }
                    a.remove(yVar);
                }
            } catch (Throwable th) {
                al.a(th);
            }
            if (a.size() > 0) {
                this.c.a(a);
            }
        } else {
            j = z ? this.g : this.f;
        }
        al.c("[UploadManager] Local network consume: %d KB", Long.valueOf(j / 1024));
        return j;
    }

    protected final synchronized void a(long j, boolean z) {
        int i = z ? 5 : 3;
        y yVar = new y();
        yVar.b = i;
        yVar.e = ap.b();
        yVar.c = "";
        yVar.d = "";
        yVar.g = ap.c(j);
        this.c.b(i);
        this.c.a(yVar);
        if (z) {
            this.g = j;
        } else {
            this.f = j;
        }
        al.c("[UploadManager] Network total consume: %d KB", Long.valueOf(j / 1024));
    }

    public final synchronized void a(int i, long j) {
        if (i < 0) {
            al.e("[UploadManager] Unknown uploading ID: %d", Integer.valueOf(i));
            return;
        }
        this.e.put(Integer.valueOf(i), Long.valueOf(j));
        y yVar = new y();
        yVar.b = i;
        yVar.e = j;
        yVar.c = "";
        yVar.d = "";
        yVar.g = new byte[0];
        this.c.b(i);
        this.c.a(yVar);
        al.c("[UploadManager] Uploading(ID:%d) time: %s", Integer.valueOf(i), ap.a(j));
    }

    public final synchronized long a(int i) {
        if (i >= 0) {
            Long l = this.e.get(Integer.valueOf(i));
            if (l != null) {
                return l.longValue();
            }
        } else {
            al.e("[UploadManager] Unknown upload ID: %d", Integer.valueOf(i));
        }
        return 0L;
    }

    public final boolean b(int i) {
        if (p.c) {
            al.c("Uploading frequency will not be checked if SDK is in debug mode.", new Object[0]);
            return true;
        }
        long currentTimeMillis = System.currentTimeMillis() - a(i);
        al.c("[UploadManager] Time interval is %d seconds since last uploading(ID: %d).", Long.valueOf(currentTimeMillis / 1000), Integer.valueOf(i));
        if (currentTimeMillis >= 30000) {
            return true;
        }
        al.a("[UploadManager] Data only be uploaded once in %d seconds.", 30L);
        return false;
    }

    private void b() {
        ak a = ak.a();
        LinkedBlockingQueue<Runnable> linkedBlockingQueue = new LinkedBlockingQueue<>();
        final LinkedBlockingQueue linkedBlockingQueue2 = new LinkedBlockingQueue();
        synchronized (this.j) {
            al.c("[UploadManager] Try to poll all upload task need and put them into temp queue (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
            int size = this.h.size();
            final int size2 = this.i.size();
            if (size == 0 && size2 == 0) {
                al.c("[UploadManager] There is no upload task in queue.", new Object[0]);
                return;
            }
            if (a == null || !a.c()) {
                size2 = 0;
            }
            a(this.h, linkedBlockingQueue, size);
            a(this.i, linkedBlockingQueue2, size2);
            a(size, linkedBlockingQueue);
            if (size2 > 0) {
                al.c("[UploadManager] Execute upload tasks of queue which has %d tasks (pid=%d | tid=%d)", Integer.valueOf(size2), Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
            }
            ak a2 = ak.a();
            if (a2 != null) {
                a2.a(new Runnable(this) { // from class: com.tencent.bugly.proguard.ai.2
                    @Override // java.lang.Runnable
                    public final void run() {
                        Runnable runnable;
                        for (int i = 0; i < size2 && (runnable = (Runnable) linkedBlockingQueue2.poll()) != null; i++) {
                            runnable.run();
                        }
                    }
                });
            }
        }
    }

    private static void a(LinkedBlockingQueue<Runnable> linkedBlockingQueue, LinkedBlockingQueue<Runnable> linkedBlockingQueue2, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            Runnable peek = linkedBlockingQueue.peek();
            if (peek == null) {
                return;
            }
            try {
                linkedBlockingQueue2.put(peek);
                linkedBlockingQueue.poll();
            } catch (Throwable th) {
                al.e("[UploadManager] Failed to add upload task to temp urgent queue: %s", th.getMessage());
            }
        }
    }

    private void a(int i, LinkedBlockingQueue<Runnable> linkedBlockingQueue) {
        ak a = ak.a();
        if (i > 0) {
            al.c("[UploadManager] Execute urgent upload tasks of queue which has %d tasks (pid=%d | tid=%d)", Integer.valueOf(i), Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
        }
        for (int i2 = 0; i2 < i; i2++) {
            final Runnable poll = linkedBlockingQueue.poll();
            if (poll == null) {
                return;
            }
            synchronized (this.j) {
                if (this.l >= 2 && a != null) {
                    a.a(poll);
                } else {
                    al.a("[UploadManager] Create and start a new thread to execute a upload task: %s", "BUGLY_ASYNC_UPLOAD");
                    if (ap.a(new Runnable() { // from class: com.tencent.bugly.proguard.ai.1
                        @Override // java.lang.Runnable
                        public final void run() {
                            poll.run();
                            synchronized (ai.this.j) {
                                ai.b(ai.this);
                            }
                        }
                    }, "BUGLY_ASYNC_UPLOAD") != null) {
                        synchronized (this.j) {
                            this.l++;
                        }
                    } else {
                        al.d("[UploadManager] Failed to start a thread to execute asynchronous upload task,will try again next time.", new Object[0]);
                        a(poll, true);
                    }
                }
            }
        }
    }

    private boolean a(Runnable runnable, boolean z) {
        if (runnable == null) {
            al.a("[UploadManager] Upload task should not be null", new Object[0]);
            return false;
        }
        try {
            al.c("[UploadManager] Add upload task to queue (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
            synchronized (this.j) {
                if (z) {
                    this.h.put(runnable);
                } else {
                    this.i.put(runnable);
                }
            }
            return true;
        } catch (Throwable th) {
            al.e("[UploadManager] Failed to add upload task to queue: %s", th.getMessage());
            return false;
        }
    }

    private void a(Runnable runnable, long j) {
        if (runnable == null) {
            al.d("[UploadManager] Upload task should not be null", new Object[0]);
            return;
        }
        al.c("[UploadManager] Execute synchronized upload task (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
        Thread a = ap.a(runnable, "BUGLY_SYNC_UPLOAD");
        if (a == null) {
            al.e("[UploadManager] Failed to start a thread to execute synchronized upload task, add it to queue.", new Object[0]);
            a(runnable, true);
            return;
        }
        try {
            a.join(j);
        } catch (Throwable th) {
            al.e("[UploadManager] Failed to join upload synchronized task with message: %s. Add it to queue.", th.getMessage());
            a(runnable, true);
            b();
        }
    }

    private void a(Runnable runnable, boolean z, boolean z2, long j) {
        al.c("[UploadManager] Add upload task (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
        if (z2) {
            a(runnable, j);
        } else {
            a(runnable, z);
            b();
        }
    }
}
