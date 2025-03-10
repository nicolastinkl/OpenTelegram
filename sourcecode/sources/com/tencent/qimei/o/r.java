package com.tencent.qimei.o;

import android.os.SystemClock;
import com.tencent.qimei.o.m;
import com.tencent.qimei.sdk.Qimei;
import com.xinstall.model.XAppError;
import j$.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: QimeiQueryTask.java */
/* loaded from: classes.dex */
public final class r implements Runnable {
    public static final Map<String, r> a = new ConcurrentHashMap();
    public b d;
    public long f;
    public long g;
    public String i;
    public AtomicInteger b = new AtomicInteger();
    public long c = 0;
    public AtomicBoolean e = new AtomicBoolean(false);
    public boolean h = false;
    public a j = new a(3, new n(this));

    /* compiled from: QimeiQueryTask.java */
    static class a {
        public final int a;
        public final InterfaceC0023a b;
        public AtomicInteger c = new AtomicInteger();

        /* compiled from: QimeiQueryTask.java */
        /* renamed from: com.tencent.qimei.o.r$a$a, reason: collision with other inner class name */
        interface InterfaceC0023a {
        }

        public a(int i, InterfaceC0023a interfaceC0023a) {
            this.a = i;
            this.b = interfaceC0023a;
        }
    }

    /* compiled from: QimeiQueryTask.java */
    public interface b {
    }

    public r(String str) {
        this.i = "";
        this.i = str;
    }

    public static /* synthetic */ void b(r rVar) {
        String str = rVar.i;
        int nextInt = com.tencent.qimei.s.e.a.nextInt(101);
        com.tencent.qimei.k.a.b("CollectRate", "抽样结果为：sample = %d", Integer.valueOf(nextInt));
        if (!(nextInt <= com.tencent.qimei.v.d.a(str).C())) {
            com.tencent.qimei.k.a.b("上报", "Qm性能上报被抽样拦截～", new Object[0]);
            return;
        }
        long uptimeMillis = SystemClock.uptimeMillis();
        long j = uptimeMillis - rVar.g;
        long j2 = uptimeMillis - rVar.f;
        long j3 = com.tencent.qimei.c.c.j().d;
        b bVar = rVar.d;
        long j4 = bVar != null ? ((u) bVar).l : 0L;
        String str2 = rVar.i;
        com.tencent.qimei.n.c a2 = com.tencent.qimei.n.i.a().a(com.tencent.qimei.n.e.REPORT_QM_USED_TIME.K, Long.valueOf(j)).a(com.tencent.qimei.n.e.REPORT_QM_LOCAL_USED_TIME.K, Long.valueOf(j2)).a(com.tencent.qimei.n.e.REPORT_OD_USED_TIME.K, Long.valueOf(j3)).a(com.tencent.qimei.n.e.REPORT_QM_INIT_TIME.K, Long.valueOf(j4));
        a2.a = str2;
        a2.c = "/report";
        a2.a("v1");
        com.tencent.qimei.k.a.b("QM", "Qm性能上报(appKey: %s), %d %d %d %d", rVar.i, Long.valueOf(j), Long.valueOf(j2), Long.valueOf(j3), Long.valueOf(j4));
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x00b6  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00c6  */
    @Override // java.lang.Runnable
    @android.annotation.SuppressLint({"MissingPermission"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void run() {
        /*
            r13 = this;
            java.util.concurrent.atomic.AtomicBoolean r0 = r13.e
            boolean r0 = r0.get()
            java.lang.String r1 = "QM"
            r2 = 0
            r3 = 1
            if (r0 == 0) goto L18
            java.lang.Object[] r0 = new java.lang.Object[r3]
            java.lang.String r3 = r13.i
            r0[r2] = r3
            java.lang.String r2 = "QM正在请求中，取消该次请求(appKey: %s)"
            com.tencent.qimei.k.a.b(r1, r2, r0)
            return
        L18:
            java.util.concurrent.atomic.AtomicBoolean r0 = r13.e
            r0.set(r3)
            java.lang.Object[] r0 = new java.lang.Object[r3]
            java.lang.String r4 = r13.i
            r0[r2] = r4
            java.lang.String r4 = "开始执行QM请求任务(appKey: %s)"
            com.tencent.qimei.k.a.b(r1, r4, r0)
            boolean r0 = com.tencent.qimei.a.a.a()
            if (r0 != 0) goto L3f
            java.lang.Object[] r0 = new java.lang.Object[r3]
            java.lang.String r3 = r13.i
            r0[r2] = r3
            java.lang.String r2 = "没有网络，取消QM请求(appKey: %s)"
            com.tencent.qimei.k.a.b(r1, r2, r0)
            r13.a()
            return
        L3f:
            boolean r0 = com.tencent.qimei.c.a.i()
            if (r0 != 0) goto L4c
            r13.d()
            r13.a()
            return
        L4c:
            long r4 = android.os.SystemClock.uptimeMillis()
            r13.f = r4
            com.tencent.qimei.o.m r6 = com.tencent.qimei.o.m.a
            java.lang.String r0 = com.tencent.qimei.j.a.a()
            java.lang.String r4 = r13.i
            com.tencent.qimei.v.b r4 = com.tencent.qimei.v.d.a(r4)
            java.lang.String r4 = r4.D()
            boolean r5 = r13.h
            java.lang.String r7 = ""
            if (r5 == 0) goto L73
            java.lang.String r5 = r13.i
            com.tencent.qimei.v.b r5 = com.tencent.qimei.v.d.a(r5)
            java.lang.String r5 = r5.u()
            goto L74
        L73:
            r5 = r7
        L74:
            java.lang.String r4 = r6.a(r4, r5)
            java.lang.String r5 = r13.i
            com.tencent.qimei.o.l r5 = com.tencent.qimei.o.l.a(r5)
            com.tencent.qimei.sdk.Qimei r9 = r5.c
            long r10 = java.lang.System.currentTimeMillis()
            r13.c = r10
            java.lang.String r5 = r13.i
            int r5 = com.tencent.qimei.a.a.c(r5)
            if (r5 == 0) goto La6
            org.json.JSONObject r8 = new org.json.JSONObject     // Catch: org.json.JSONException -> La2
            r8.<init>()     // Catch: org.json.JSONException -> La2
            java.lang.String r10 = "delay"
            java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch: org.json.JSONException -> La2
            r8.put(r10, r5)     // Catch: org.json.JSONException -> La2
            java.lang.String r5 = r8.toString()     // Catch: org.json.JSONException -> La2
            r12 = r5
            goto La7
        La2:
            r5 = move-exception
            r5.printStackTrace()
        La6:
            r12 = r7
        La7:
            java.lang.String r8 = r13.i
            long r10 = r13.c
            r7 = r0
            java.lang.String r5 = r6.a(r7, r8, r9, r10, r12)
            boolean r6 = android.text.TextUtils.isEmpty(r5)
            if (r6 == 0) goto Lc6
            java.lang.Object[] r0 = new java.lang.Object[r3]
            java.lang.String r3 = r13.i
            r0[r2] = r3
            java.lang.String r2 = "获取请求参数错误，取消QM请求(appKey: %s)"
            com.tencent.qimei.k.a.b(r1, r2, r0)
            r13.a()
            return
        Lc6:
            long r6 = android.os.SystemClock.uptimeMillis()
            r13.g = r6
            com.tencent.qimei.o.o r6 = new com.tencent.qimei.o.o
            r6.<init>(r13, r0)
            com.tencent.qimei.a.a.a(r4, r5, r6)
            r0 = 2
            java.lang.Object[] r0 = new java.lang.Object[r0]
            java.lang.String r5 = r13.i
            r0[r2] = r5
            r0[r3] = r4
            java.lang.String r2 = "开始请求Qm(appKey: %s), url: %s"
            com.tencent.qimei.k.a.b(r1, r2, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.qimei.o.r.run():void");
    }

    public final void c() {
        com.tencent.qimei.k.a.b("QM", "Qm请求失败(appKey: %s)", this.i);
        a();
        if (this.h) {
            this.h = false;
            return;
        }
        a aVar = this.j;
        aVar.c.getAndIncrement();
        boolean z = aVar.c.get() >= aVar.a;
        if (z) {
            aVar.c.set(0);
            a.InterfaceC0023a interfaceC0023a = aVar.b;
            if (interfaceC0023a != null) {
                n nVar = (n) interfaceC0023a;
                if (!com.tencent.qimei.v.d.a(nVar.a.i).u().isEmpty()) {
                    nVar.a.h = true;
                    com.tencent.qimei.b.a.a().a(10000L, nVar.a);
                }
            }
        }
        if (z) {
            return;
        }
        com.tencent.qimei.b.a.a().a(10000L, this);
    }

    public final void d() {
        com.tencent.qimei.b.a.a().a(300L, new p(this));
    }

    public static synchronized r a(String str, b bVar) {
        r rVar;
        synchronized (r.class) {
            Map<String, r> map = a;
            rVar = map.get(str);
            if (rVar == null) {
                rVar = new r(str);
                rVar.d = bVar;
                map.put(str, rVar);
            }
        }
        return rVar;
    }

    public final void a(String str, String str2) {
        com.tencent.qimei.f.b bVar = com.tencent.qimei.f.b.KEY_CODE;
        bVar.f = str;
        String a2 = bVar.a(this.i);
        if (!a2.equals("0")) {
            c();
            return;
        }
        String d = com.tencent.qimei.a.a.d(m.b.KEY_DATA.a(str, new m.b[0]), str2);
        com.tencent.qimei.k.a.b("QM", "(appKey: %s)Qm响应 data解密: %s", this.i, d);
        if (d != null && !d.isEmpty()) {
            l a3 = l.a(this.i);
            Qimei qimei = a3.c;
            a3.b(d);
            Qimei qimei2 = a3.c;
            boolean z = a3.g;
            if (qimei != null && !qimei.isEmpty()) {
                String a4 = qimei.a();
                String b2 = qimei.b();
                if (a4 != null && b2 != null && (a4.isEmpty() || !b2.isEmpty())) {
                    String a5 = qimei2.a();
                    String b3 = qimei2.b();
                    if ((a5.isEmpty() || !b3.isEmpty()) && (!a4.equals(a5) || !b2.equals(b3))) {
                        String str3 = this.i;
                        com.tencent.qimei.n.c a6 = com.tencent.qimei.n.i.a().a(com.tencent.qimei.n.e.REPORT_QM_CHANGE_OLD_Q16.K, a4).a(com.tencent.qimei.n.e.REPORT_QM_CHANGE_OLD_Q36.K, b2).a(com.tencent.qimei.n.e.REPORT_QM_CHANGE_NEW_Q16.K, a5).a(com.tencent.qimei.n.e.REPORT_QM_CHANGE_NEW_Q36.K, b3).a(com.tencent.qimei.n.e.REPORT_QM_FROM_BEACON.K, z ? "1" : "0");
                        a6.a = str3;
                        a6.c = "/report";
                        a6.a("v3");
                    }
                }
            }
            Qimei qimei3 = l.a(this.i).c;
            if (qimei3 == null || qimei3.isEmpty()) {
                String str4 = this.i;
                com.tencent.qimei.n.c a7 = com.tencent.qimei.n.i.a().a(com.tencent.qimei.n.e.REPORT_QM_ERROR_CODE.K, XAppError.NO_PERMISSION).a(com.tencent.qimei.n.e.REPORT_QM_ERROR_DESC.K, a2);
                a7.a = str4;
                a7.c = "/report";
                a7.a("v2");
            }
            Qimei qimei4 = a3.c;
            if (qimei4 != null && !qimei4.isEmpty()) {
                b();
                com.tencent.qimei.a.a.e(this.i, d);
                a3.e = this.c;
                a3.d = a3.b();
                com.tencent.qimei.i.f.a(a3.b).a("tt", a3.d);
            }
            a();
            return;
        }
        String str5 = this.i;
        com.tencent.qimei.n.c a8 = com.tencent.qimei.n.i.a().a(com.tencent.qimei.n.e.REPORT_QM_ERROR_CODE.K, "1002").a(com.tencent.qimei.n.e.REPORT_QM_ERROR_DESC.K, "decrypt error, aes key: " + str2 + ", content:" + str);
        a8.a = str5;
        a8.c = "/report";
        a8.a("v2");
        c();
    }

    public final void b() {
        com.tencent.qimei.b.a.a().a(new q(this));
    }

    public final void a(String str, int i, String str2) {
        a aVar = this.j;
        if (aVar.c.get() >= aVar.a - 1) {
            String str3 = this.i;
            com.tencent.qimei.n.c a2 = com.tencent.qimei.n.i.a().a(com.tencent.qimei.n.e.REPORT_QM_ERROR_CODE.K, str.equals("451") ? XAppError.REQUEST_FAIL : XAppError.INIT_FAIL).a(com.tencent.qimei.n.e.REPORT_QM_ERROR_DESC.K, "error code: " + i + ", msg:" + str2);
            a2.a = str3;
            a2.c = "/report";
            a2.a("v2");
        }
        c();
    }

    public void a() {
        this.e.set(false);
    }
}
