package com.tencent.qimei.o;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import com.tencent.qimei.s.c;
import com.tencent.qimei.sdk.IQimeiSDK;
import com.tencent.qimei.sdk.Qimei;
import com.tencent.qimei.sdk.QimeiSDK;
import com.youth.banner.config.BannerConfig;
import j$.util.concurrent.ConcurrentHashMap;
import java.util.Map;

/* compiled from: HidBuilder.java */
/* loaded from: classes.dex */
public class j implements Runnable, c.b {
    public static final String a = "j";
    public static final Map<String, j> b = new ConcurrentHashMap();
    public final String c;
    public int d;
    public int e;
    public int f;
    public boolean g;
    public boolean h;
    public Context i;
    public boolean j;

    public j(String str) {
        this.c = str;
    }

    public final void d() {
        if (Build.VERSION.SDK_INT >= 23) {
            Looper.getMainLooper().getQueue().addIdleHandler(new h(this));
        }
        com.tencent.qimei.b.a.a().a(30000L, new i(this));
    }

    @Override // java.lang.Runnable
    public void run() {
        IQimeiSDK qimeiSDK = QimeiSDK.getInstance(this.c);
        Qimei qimei = qimeiSDK.getQimei();
        if (qimei == null || qimei.isEmpty()) {
            qimeiSDK.getQimei(new g(this));
        } else {
            a();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0075  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0081  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void a(com.tencent.qimei.y.b r10) {
        /*
            r9 = this;
            if (r10 != 0) goto L3
            return
        L3:
            java.lang.String r0 = r10.a
            java.lang.String r1 = r10.b
            java.lang.String r2 = r10.c
            java.lang.String r10 = r10.d
            boolean r3 = android.text.TextUtils.isEmpty(r2)
            r4 = 0
            r5 = 1
            if (r3 != 0) goto L2c
            boolean r3 = android.text.TextUtils.isEmpty(r10)
            if (r3 != 0) goto L2c
            java.lang.String r3 = "null"
            boolean r3 = r3.equals(r2)
            if (r3 != 0) goto L2c
            int r3 = r2.length()
            r6 = 32
            if (r3 == r6) goto L2a
            goto L2c
        L2a:
            r3 = 0
            goto L2d
        L2c:
            r3 = 1
        L2d:
            if (r3 == 0) goto L46
            java.lang.String r10 = com.tencent.qimei.o.j.a
            r0 = 2
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r0[r4] = r1
            int r1 = r2.length()
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            r0[r5] = r1
            java.lang.String r1 = "%s hid is invail, len = %d"
            com.tencent.qimei.k.a.a(r10, r1, r0)
            return
        L46:
            java.lang.String r3 = "h_s_t"
            boolean r6 = r9.a(r3, r1)
            java.lang.String r7 = "hi"
            if (r6 == 0) goto L51
            goto L70
        L51:
            java.lang.String r6 = r9.c
            com.tencent.qimei.i.f r6 = com.tencent.qimei.i.f.a(r6)
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r8.append(r7)
            r8.append(r1)
            java.lang.String r8 = r8.toString()
            java.lang.String r6 = r6.c(r8)
            boolean r6 = r2.equals(r6)
            if (r6 != 0) goto L72
        L70:
            r6 = 1
            goto L73
        L72:
            r6 = 0
        L73:
            if (r6 != 0) goto L81
            java.lang.String r10 = com.tencent.qimei.o.j.a
            java.lang.Object[] r0 = new java.lang.Object[r5]
            r0[r4] = r1
            java.lang.String r1 = "%s hid same and in 24h"
            com.tencent.qimei.k.a.b(r10, r1, r0)
            return
        L81:
            java.lang.String r4 = r9.c
            com.tencent.qimei.i.f r4 = com.tencent.qimei.i.f.a(r4)
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r7)
            r5.append(r1)
            java.lang.String r5 = r5.toString()
            r4.a(r5, r2)
            java.lang.String r4 = r9.c
            com.tencent.qimei.i.f r4 = com.tencent.qimei.i.f.a(r4)
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r3)
            r5.append(r1)
            java.lang.String r3 = r5.toString()
            long r5 = java.lang.System.currentTimeMillis()
            r4.a(r3, r5)
            java.lang.String r3 = r9.c
            com.tencent.qimei.n.b.a(r3, r0, r1, r2, r10)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.qimei.o.j.a(com.tencent.qimei.y.b):void");
    }

    public final synchronized void b() {
        if (this.j) {
            return;
        }
        com.tencent.qimei.b.a.a().a(this);
        this.j = true;
    }

    public final void c() {
        boolean z;
        boolean z2 = false;
        try {
            Class.forName("com.tencent.smtt.sdk.WebView");
            z = true;
        } catch (ClassNotFoundException unused) {
            z = false;
        }
        if (z) {
            Object a2 = com.tencent.qimei.a.a.a("com.tencent.smtt.sdk.QbSdk", "isTbsCoreInited", new Class[0], new Object[0]);
            Object a3 = com.tencent.qimei.a.a.a("com.tencent.smtt.sdk.QbSdk", "getTbsVersion", new Class[]{Context.class}, new Object[]{this.i});
            if (a2 != null && a3 != null) {
                boolean booleanValue = ((Boolean) a2).booleanValue();
                int intValue = ((Integer) a3).intValue();
                if (!booleanValue || intValue == 0) {
                    com.tencent.qimei.k.a.a(a, "x5 not ready,isInited: %b x5Version: %d", Boolean.valueOf(booleanValue), a3);
                } else {
                    z2 = true;
                }
            }
        }
        if (z2) {
            if (this.d != 0) {
                this.g = true;
                com.tencent.qimei.y.k kVar = new com.tencent.qimei.y.k(this.i);
                new Handler(Looper.getMainLooper()).post(new com.tencent.qimei.y.i(kVar));
                kVar.c.b();
                new Handler(Looper.getMainLooper()).post(new com.tencent.qimei.y.j(kVar));
                a(kVar.c.a());
            }
        } else if (this.e != 0) {
            this.h = true;
            com.tencent.qimei.y.g gVar = new com.tencent.qimei.y.g(this.i);
            new Handler(Looper.getMainLooper()).post(new com.tencent.qimei.y.e(gVar));
            gVar.c.b();
            new Handler(Looper.getMainLooper()).post(new com.tencent.qimei.y.f(gVar));
            a(gVar.c.a());
        }
        com.tencent.qimei.b.a a4 = com.tencent.qimei.b.a.a();
        int i = BannerConfig.SCROLL_TIME;
        if (this.g) {
            i = this.d;
        } else if (this.h) {
            i = this.e;
        }
        a4.a(i * 1000, this);
    }

    public static synchronized j a(String str) {
        j jVar;
        synchronized (j.class) {
            Map<String, j> map = b;
            jVar = map.get(str);
            if (jVar == null) {
                jVar = new j(str);
                map.put(str, jVar);
            }
        }
        return jVar;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:13:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void a() {
        /*
            r7 = this;
            com.tencent.qimei.s.c r0 = new com.tencent.qimei.s.c
            android.content.Context r1 = r7.i
            java.lang.String r2 = r7.c
            r0.<init>(r1, r2, r7)
            java.lang.String r1 = "tun-cos-1258344701.html"
            boolean r2 = r0.a(r1)
            java.lang.String r3 = "tun-cos-1258344701.js"
            r4 = 0
            r5 = 1
            if (r2 == 0) goto L1d
            boolean r2 = r0.a(r3)
            if (r2 == 0) goto L1d
            r2 = 1
            goto L1e
        L1d:
            r2 = 0
        L1e:
            if (r2 != 0) goto L28
            r0.a(r5)
            r0.b(r5)
        L26:
            r4 = 1
            goto L55
        L28:
            java.lang.String r2 = "lc_fe_tm"
            java.lang.String r6 = ""
            boolean r2 = r7.a(r2, r6)
            if (r2 == 0) goto L55
            java.lang.String r2 = "lc_fe_st_hm"
            boolean r1 = r0.a(r1, r2)
            java.lang.String r2 = "lc_fe_st_js"
            boolean r2 = r0.a(r3, r2)
            if (r1 == 0) goto L44
            if (r2 == 0) goto L44
            r1 = 1
            goto L45
        L44:
            r1 = 0
        L45:
            if (r1 != 0) goto L4e
            r0.a(r5)
            r0.b(r5)
            goto L26
        L4e:
            r0.a(r4)
            r0.b(r4)
            goto L26
        L55:
            if (r4 != 0) goto L5a
            r7.c()
        L5a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.qimei.o.j.a():void");
    }

    public final boolean a(String str, String str2) {
        return com.tencent.qimei.a.a.a(com.tencent.qimei.i.f.a(this.c).b(str + str2));
    }
}
