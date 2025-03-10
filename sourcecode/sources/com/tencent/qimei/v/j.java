package com.tencent.qimei.v;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: StrategyQueryTask.java */
/* loaded from: classes.dex */
public class j implements Runnable {
    public final String a;
    public final f c;
    public volatile AtomicBoolean b = new AtomicBoolean(false);
    public k d = null;

    public j(f fVar, String str) {
        this.c = fVar;
        this.a = str;
    }

    public final void b() {
        com.tencent.qimei.b.a.a().a(TimeUnit.MILLISECONDS.convert(1L, TimeUnit.DAYS), this);
        k kVar = this.d;
        if (kVar != null) {
            ((com.tencent.qimei.o.d) kVar).b();
        }
        this.b.set(false);
    }

    public final void c() {
    }

    @Override // java.lang.Runnable
    public void run() {
        String str;
        String str2 = "";
        this.b.set(true);
        String a = com.tencent.qimei.j.a.a();
        String D = d.a(this.a).D();
        if (D.isEmpty()) {
            str = com.tencent.qimei.e.a.a() + "/config";
        } else {
            str = D + "/config";
        }
        String str3 = this.a;
        com.tencent.qimei.u.d b = com.tencent.qimei.u.d.b();
        com.tencent.qimei.c.c j = com.tencent.qimei.c.c.j();
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(com.tencent.qimei.w.c.KEY_CIPHER_KEY.k, com.tencent.qimei.a.a.c(a, com.tencent.qimei.e.a.b()));
            String str4 = com.tencent.qimei.w.c.KEY_PLATFORM_ID.k;
            j.r();
            jSONObject.put(str4, 1);
            jSONObject.put(com.tencent.qimei.w.c.KEY_OS_VERSION.k, j.p());
            jSONObject.put(com.tencent.qimei.w.c.KEY_APP_VERSION.k, com.tencent.qimei.c.a.a());
            jSONObject.put(com.tencent.qimei.w.c.KEY_SDK_VERSION.k, b.getSdkVersion());
            jSONObject.put(com.tencent.qimei.w.c.KEY_AUDIT_VERSION.k, b.a() == null ? "" : b.a().O());
            jSONObject.put(com.tencent.qimei.w.c.KEY_APP_KEY.k, str3);
            jSONObject.put(com.tencent.qimei.w.c.KEY_CONFIG_VERSION.k, d.a(str3).r());
            jSONObject.put(com.tencent.qimei.w.c.KEY_PACKAGE_NAME.k, com.tencent.qimei.c.a.d());
            jSONObject.toString();
            str2 = jSONObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        com.tencent.qimei.a.a.a(str, str2, new i(this, a));
    }

    public void a() {
        try {
            String c = com.tencent.qimei.i.f.a(this.a).c("s_d");
            if (!c.equals("")) {
                try {
                    c = com.tencent.qimei.a.a.a(c, com.tencent.qimei.a.a.a("s_d"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (c != null) {
                a(c, false);
            }
        } catch (Exception e2) {
            com.tencent.qimei.k.a.a(e2);
        } finally {
            c();
        }
    }

    public final void a(String str, String str2) {
        String str3;
        try {
            com.tencent.qimei.f.b bVar = com.tencent.qimei.f.b.KEY_CODE;
            bVar.f = str;
            String a = bVar.a(this.a);
            if (!a.equals("0")) {
                if (a.equals("304")) {
                    com.tencent.qimei.i.f.a(this.a).a("s_s_t", System.currentTimeMillis());
                }
                b();
                return;
            }
            com.tencent.qimei.f.b bVar2 = com.tencent.qimei.f.b.KEY_DATA;
            bVar2.f = str;
            try {
                str3 = com.tencent.qimei.a.a.a(bVar2.a(this.a), str2);
            } catch (Exception e) {
                e.printStackTrace();
                str3 = "";
            }
            a(str3, true);
            this.c.a(true);
            b();
        } catch (Throwable th) {
            com.tencent.qimei.k.a.a(th);
        }
    }

    public void a(String str, boolean z) {
        try {
            g.a.put(this.a, str);
            String str2 = this.a;
            d.a(this.a, new h(str2, d.a(str2)));
            if (z) {
                com.tencent.qimei.i.f a = com.tencent.qimei.i.f.a(this.a);
                try {
                    str = com.tencent.qimei.a.a.b(str, com.tencent.qimei.a.a.a("s_d"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                a.a("s_d", str);
                com.tencent.qimei.i.f.a(this.a).a("s_s_t", System.currentTimeMillis());
            }
        } catch (Throwable th) {
            com.tencent.qimei.k.a.a(th);
        }
    }
}
