package com.shubao.xinstall.a.a;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import com.shubao.xinstall.a.f.k;
import com.shubao.xinstall.a.f.o;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public final class f extends com.shubao.xinstall.a.a.a {
    final LinkedBlockingQueue<Object> j;
    volatile boolean k;
    int l;
    public File m;
    Long n;
    public long o;
    List<com.shubao.xinstall.a.b.c> p;

    /* renamed from: q, reason: collision with root package name */
    public volatile int f1q;
    volatile int r;
    private final Thread s;
    private final Application t;
    private Application.ActivityLifecycleCallbacks u;
    private List<com.shubao.xinstall.a.b.c> v;
    private com.shubao.xinstall.a.d.c w;

    class a implements Runnable {
        a() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            if (f.this.b.a(10L)) {
                f fVar = f.this;
                if (!fVar.i.a && fVar.c.c().booleanValue()) {
                    if (Calendar.getInstance().getTime().getTime() > f.this.e.g()) {
                        f.this.e.a(0L);
                        if (f.this.m.exists()) {
                            f.this.m.delete();
                        }
                        com.shubao.xinstall.a.b.c cVar = new com.shubao.xinstall.a.b.c(2, "checkPermission", 0, "");
                        cVar.a = true;
                        f.this.a(cVar);
                    }
                }
            }
        }
    }

    class b implements com.shubao.xinstall.a.d.a {
        b() {
        }

        @Override // com.shubao.xinstall.a.d.a
        public final void a(com.shubao.xinstall.a.b.a aVar) {
            f.this.n = aVar.e;
        }
    }

    class c implements Runnable {
        c() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            while (f.this.k) {
                f fVar = f.this;
                if (fVar.i.a) {
                    return;
                }
                try {
                    LinkedBlockingQueue<Object> linkedBlockingQueue = fVar.j;
                    Long l = fVar.n;
                    linkedBlockingQueue.poll(l != null ? l.longValue() : 10L, TimeUnit.SECONDS);
                    f.this.a((com.shubao.xinstall.a.b.c) null);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class d extends com.shubao.xinstall.a.c {
        long a = 0;

        d() {
        }

        @Override // com.shubao.xinstall.a.c, android.app.Application.ActivityLifecycleCallbacks
        public final void onActivityStarted(Activity activity) {
            if (f.this.l == 0) {
                this.a = System.currentTimeMillis();
                k.a().execute(f.this.new a());
            }
            f.this.l++;
        }

        @Override // com.shubao.xinstall.a.c, android.app.Application.ActivityLifecycleCallbacks
        public final void onActivityStopped(Activity activity) {
            f fVar = f.this;
            int i = fVar.l - 1;
            fVar.l = i;
            if (i == 0) {
                long currentTimeMillis = System.currentTimeMillis();
                long j = this.a;
                f.a(f.this, j / 1000, (currentTimeMillis - j) / 1000);
            }
        }
    }

    class e implements Runnable {
        e() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            if (f.this.b.a(10L)) {
                f.a(f.this);
            }
        }
    }

    public f(Context context, h hVar, com.shubao.xinstall.a.b.a aVar, g gVar) {
        super(context, hVar, aVar, gVar);
        this.j = new LinkedBlockingQueue<>(1);
        this.k = false;
        this.v = new ArrayList();
        this.p = new ArrayList();
        this.f1q = 0;
        this.r = 0;
        this.w = new com.shubao.xinstall.a.d.c() { // from class: com.shubao.xinstall.a.a.f.1
            @Override // com.shubao.xinstall.a.d.c
            public final void a(int i, boolean z) {
                if (i == 0) {
                    f.this.p.clear();
                    f.a(f.this, z);
                }
                f.this.r = i;
            }
        };
        Application application = (Application) context.getApplicationContext();
        this.t = application;
        Thread thread = new Thread(new c());
        this.s = thread;
        this.m = new File(context.getFilesDir(), "reportlog");
        this.o = System.currentTimeMillis();
        aVar.g.add(new b());
        this.l = 0;
        this.k = true;
        thread.start();
        d dVar = new d();
        this.u = dVar;
        application.registerActivityLifecycleCallbacks(dVar);
        this.h.execute(new e());
    }

    static /* synthetic */ void a(f fVar) {
        com.shubao.xinstall.a.b.c cVar = new com.shubao.xinstall.a.b.c(4, "alive", 1, "");
        cVar.a = true;
        fVar.b(cVar);
    }

    static /* synthetic */ void a(f fVar, long j, long j2) {
        if (!com.shubao.xinstall.a.b.a.a(fVar.c.a)) {
            o.a("没有在线时长统计上报能力");
            return;
        }
        if (j2 > 1) {
            com.shubao.xinstall.a.b.c cVar = new com.shubao.xinstall.a.b.c(j);
            cVar.c = "aliveDuration";
            cVar.b = 5;
            cVar.g = j2;
            fVar.b(cVar);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x0028, code lost:
    
        if ((r7.n.longValue() * 1000) < (java.lang.System.currentTimeMillis() - r7.o)) goto L13;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static /* synthetic */ void a(com.shubao.xinstall.a.a.f r7, boolean r8) {
        /*
            com.shubao.xinstall.a.b.a r0 = r7.c
            java.lang.String r0 = r0.f
            boolean r0 = com.shubao.xinstall.a.f.s.a(r0)
            if (r0 != 0) goto L38
            if (r8 != 0) goto L2e
            java.lang.Long r8 = r7.n
            r0 = 1
            if (r8 == 0) goto L2b
            int r8 = r7.f1q
            if (r8 == r0) goto L2b
            long r1 = java.lang.System.currentTimeMillis()
            java.lang.Long r8 = r7.n
            long r3 = r8.longValue()
            r5 = 1000(0x3e8, double:4.94E-321)
            long r3 = r3 * r5
            long r5 = r7.o
            long r1 = r1 - r5
            int r8 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r8 >= 0) goto L2b
            goto L2c
        L2b:
            r0 = 0
        L2c:
            if (r0 == 0) goto L38
        L2e:
            com.shubao.xinstall.a.a.a.g r8 = new com.shubao.xinstall.a.a.a.g
            r8.<init>(r7)
            java.util.concurrent.ThreadPoolExecutor r7 = r7.h
            r7.execute(r8)
        L38:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.shubao.xinstall.a.a.f.a(com.shubao.xinstall.a.a.f, boolean):void");
    }

    private void b(com.shubao.xinstall.a.b.c cVar) {
        if (!this.i.a && !this.c.c().booleanValue()) {
            a(cVar);
        } else if (o.a) {
            o.c("您的账号已被封禁");
        }
    }

    final synchronized void a(com.shubao.xinstall.a.b.c cVar) {
        if (cVar != null) {
            this.v.add(cVar);
        }
        if (this.f1q != 1 && this.r != 1) {
            if (!this.v.isEmpty()) {
                this.p.addAll(this.v);
                this.v.clear();
            }
            this.r = 1;
            k.a().execute(new com.shubao.xinstall.a.a.a.k(this.m, this.p, this.w));
        }
    }

    public final void a(String str, Object obj) {
        if (!this.c.b().booleanValue()) {
            o.a("没有事件上报能力");
        } else {
            if (TextUtils.isEmpty(str)) {
                return;
            }
            com.shubao.xinstall.a.b.c cVar = new com.shubao.xinstall.a.b.c(6, str, obj, "");
            cVar.g = 0L;
            b(cVar);
        }
    }

    public final void a(String str, Object obj, Object obj2) {
        if (!this.c.b().booleanValue()) {
            o.a("没有事件上报能力");
        } else {
            if (TextUtils.isEmpty(str)) {
                return;
            }
            b(new com.shubao.xinstall.a.b.c(2, str, obj, obj2));
        }
    }

    public final void k() {
        if (!com.shubao.xinstall.a.b.a.a(this.c.b)) {
            o.a("没有注册量统计能力");
            return;
        }
        com.shubao.xinstall.a.b.c cVar = new com.shubao.xinstall.a.b.c(1, "register", 1, "");
        cVar.a = true;
        b(cVar);
    }
}
