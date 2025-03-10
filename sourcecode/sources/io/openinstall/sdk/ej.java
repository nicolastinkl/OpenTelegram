package io.openinstall.sdk;

import android.text.TextUtils;
import com.youth.banner.config.BannerConfig;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class ej {
    private static volatile ej a;
    private final eh b;
    private final az c;
    private eg d;
    private ef e;
    private CountDownLatch f = null;
    private String g;
    private String h;
    private Map<String, String> i;

    private ej(az azVar, eh ehVar) {
        this.c = azVar;
        this.b = ehVar;
    }

    public static ej a(az azVar) {
        if (a == null) {
            synchronized (ej.class) {
                if (a == null) {
                    a = new ej(azVar, new eh());
                }
            }
        }
        return a;
    }

    private void a(es esVar) {
        if (esVar instanceof ep) {
            String d = ((ep) esVar).d();
            if (TextUtils.isEmpty(d)) {
                return;
            }
            ba b = ba.b(d);
            ba f = this.c.f();
            if (!f.equals(b)) {
                f.a(b);
                this.c.d().a(f);
            }
            if (TextUtils.isEmpty(f.h())) {
                return;
            }
            this.c.i().b(aw.a().d(), f.h());
        }
    }

    private synchronized Map<String, String> b() {
        if (this.i == null) {
            this.i = this.d.a_();
        }
        return this.i;
    }

    private void b(eo eoVar) {
        if (this.f != null) {
            System.currentTimeMillis();
            try {
                this.f.await(3L, TimeUnit.SECONDS);
            } catch (InterruptedException unused) {
            }
        }
        String str = eoVar.f() ? this.g : this.h;
        if (str == null || str.length() == 0) {
            str = eoVar.f() ? this.e.b() : this.e.c();
        }
        eoVar.b(str);
    }

    private void b(es esVar) {
        if (esVar instanceof ep) {
            int a2 = ((ep) esVar).a();
            if (a2 == 1 || a2 == 15) {
                this.c.d().b(true);
            }
        }
    }

    private Map<String, Object> c() {
        HashMap hashMap = new HashMap(b());
        ba f = this.c.f();
        hashMap.put("f3ef", TextUtils.isEmpty(f.h()) ? this.c.i().a(aw.a().d()) : f.h());
        hashMap.put("qmvzs", String.valueOf(System.currentTimeMillis()));
        return hashMap;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x004e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public io.openinstall.sdk.es a(io.openinstall.sdk.eo r3) {
        /*
            r2 = this;
            io.openinstall.sdk.az r0 = r2.c
            io.openinstall.sdk.ax r0 = r0.d()
            boolean r0 = r0.j()
            if (r0 == 0) goto L19
            io.openinstall.sdk.eq r3 = new io.openinstall.sdk.eq
            java.lang.Exception r0 = new java.lang.Exception
            java.lang.String r1 = "request forbidden"
            r0.<init>(r1)
            r3.<init>(r0)
            return r3
        L19:
            java.util.Map r0 = r2.c()
            r3.a(r0)
            r2.b(r3)
            io.openinstall.sdk.eh r0 = r2.b     // Catch: java.lang.Exception -> L30 io.openinstall.sdk.ei -> L37
            r1 = 5000(0x1388, float:7.006E-42)
            java.lang.String r3 = r0.a(r3, r1)     // Catch: java.lang.Exception -> L30 io.openinstall.sdk.ei -> L37
            io.openinstall.sdk.ep r3 = io.openinstall.sdk.ep.a(r3)     // Catch: java.lang.Exception -> L30 io.openinstall.sdk.ei -> L37
            goto L42
        L30:
            r3 = move-exception
            io.openinstall.sdk.eq r0 = new io.openinstall.sdk.eq
            r0.<init>(r3)
            goto L41
        L37:
            r3 = move-exception
            io.openinstall.sdk.er r0 = new io.openinstall.sdk.er
            int r1 = r3.a
            java.lang.String r3 = r3.b
            r0.<init>(r1, r3)
        L41:
            r3 = r0
        L42:
            r2.a(r3)
            r2.b(r3)
            boolean r0 = r3.e()
            if (r0 == 0) goto L53
            io.openinstall.sdk.ef r0 = r2.e
            r0.a()
        L53:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: io.openinstall.sdk.ej.a(io.openinstall.sdk.eo):io.openinstall.sdk.es");
    }

    public es a(Map<String, ?> map) {
        eo eoVar = new eo(true, "/init");
        eoVar.b(map);
        return a(eoVar);
    }

    public String a(String str) {
        try {
            return this.b.a(new em(str), BannerConfig.LOOP_TIME);
        } catch (Exception unused) {
            return null;
        }
    }

    public void a() {
        this.f = new CountDownLatch(1);
    }

    public void a(ef efVar) {
        this.e = efVar;
    }

    public void a(eg egVar) {
        this.d = egVar;
    }

    public void a(String str, String str2) {
        this.g = str;
        this.h = str2;
        CountDownLatch countDownLatch = this.f;
        if (countDownLatch != null) {
            countDownLatch.countDown();
        }
    }

    public es b(String str) {
        en enVar = new en(false, "/stats/events");
        enVar.a(str);
        return a(enVar);
    }

    public es b(Map<String, ?> map) {
        eo eoVar = new eo(true, "/decode-wakeup-url");
        eoVar.b(map);
        return a(eoVar);
    }

    public es c(Map<String, ?> map) {
        eo eoVar = new eo(false, "/stats/wakeup");
        eoVar.b(map);
        return a(eoVar);
    }

    public es d(Map<String, ?> map) {
        eo eoVar = new eo(false, "/share/report");
        eoVar.b(map);
        return a(eoVar);
    }
}
