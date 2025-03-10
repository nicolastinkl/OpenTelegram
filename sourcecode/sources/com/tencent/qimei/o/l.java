package com.tencent.qimei.o;

import android.content.SharedPreferences;
import android.text.TextUtils;
import com.tencent.qimei.o.m;
import com.tencent.qimei.sdk.Qimei;
import j$.util.concurrent.ConcurrentHashMap;
import java.util.Map;

/* compiled from: QimeiHolder.java */
/* loaded from: classes.dex */
public class l {
    public static final Map<String, l> a = new ConcurrentHashMap();
    public final String b;
    public Qimei c;
    public boolean h = false;
    public String d = "";
    public long e = 0;
    public int f = 0;
    public boolean g = false;

    public l(String str) {
        this.b = str;
        Qimei qimei = new Qimei();
        this.c = qimei;
        qimei.setAppKey(str);
    }

    public static synchronized l a(String str) {
        l lVar;
        synchronized (l.class) {
            Map<String, l> map = a;
            lVar = map.get(str);
            if (lVar == null) {
                lVar = new l(str);
                if (!lVar.h) {
                    lVar.a();
                    lVar.h = true;
                }
                map.put(str, lVar);
            }
        }
        return lVar;
    }

    public final String b() {
        String d = com.tencent.qimei.l.d.a(this.b).d();
        return d == null ? "" : com.tencent.qimei.j.a.b(d);
    }

    public void b(String str) {
        if (str == null || str.isEmpty()) {
            return;
        }
        Qimei a2 = m.b.a(str);
        this.c = a2;
        a2.setAppKey(this.b);
    }

    public final synchronized void a() {
        b(com.tencent.qimei.a.a.e(this.b));
        com.tencent.qimei.q.a a2 = com.tencent.qimei.q.a.a(this.b);
        if (a2.a()) {
            a2.f = this.c;
            Qimei qimei = new Qimei();
            this.c = qimei;
            qimei.setAppKey(this.b);
            com.tencent.qimei.a.a.b(this.b);
            SharedPreferences sharedPreferences = com.tencent.qimei.i.f.a(this.b).b;
            if (sharedPreferences != null) {
                sharedPreferences.edit().remove("is_first").apply();
            }
            return;
        }
        String a3 = this.c.a();
        String b = this.c.b();
        if (TextUtils.isEmpty(a3) && TextUtils.isEmpty(b)) {
            com.tencent.qimei.k.a.b("QM", "Local qm cache not found, try load from old version cache(appKey: %s)", this.b);
            Qimei a4 = k.a();
            if (a4 == null) {
                com.tencent.qimei.k.a.b("QM", "Local qm cache failed(appKey: %s)", this.b);
                return;
            } else {
                this.c = a4;
                this.g = true;
            }
        }
        com.tencent.qimei.k.a.b("QM", "(appKey: %s) Qm load successfully from cache, detail: %s", this.b, this.c.toString());
    }
}
