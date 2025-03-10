package com.tencent.qimei.l;

import android.annotation.SuppressLint;
import android.os.SystemClock;
import android.text.TextUtils;
import com.tencent.qmsp.oaid2.VendorManager;
import j$.util.concurrent.ConcurrentHashMap;
import java.util.Map;

/* compiled from: MultiAppKeyDeviceInfo.java */
@SuppressLint({"MissingPermission"})
/* loaded from: classes.dex */
public class d implements com.tencent.qimei.g.c {
    public static final Map<String, d> a = new ConcurrentHashMap();
    public final String b;
    public final Object c = new Object();
    public boolean d = false;

    public d(String str) {
        this.b = str;
    }

    public String d() {
        return !com.tencent.qimei.v.d.a(this.b).t() ? "" : com.tencent.qimei.c.c.j().c();
    }

    public String e() {
        String m = com.tencent.qimei.v.d.a(this.b).m();
        return !TextUtils.isEmpty(m) ? m : !com.tencent.qimei.v.d.a(this.b).j() ? "" : com.tencent.qimei.c.c.j().d();
    }

    public String f() {
        String e = com.tencent.qimei.v.d.a(this.b).e();
        return !TextUtils.isEmpty(e) ? e : !com.tencent.qimei.v.d.a(this.b).E() ? "" : com.tencent.qimei.c.c.j().h();
    }

    public String g() {
        String a2 = com.tencent.qimei.v.d.a(this.b).a();
        return !TextUtils.isEmpty(a2) ? a2 : !com.tencent.qimei.v.d.a(this.b).F() ? "" : com.tencent.qimei.c.c.j().i();
    }

    public String h() {
        String o = com.tencent.qimei.v.d.a(this.b).o();
        return !TextUtils.isEmpty(o) ? o : !com.tencent.qimei.v.d.a(this.b).q() ? "" : com.tencent.qimei.c.c.j().l();
    }

    public String i() {
        String p = com.tencent.qimei.v.d.a(this.b).p();
        return !TextUtils.isEmpty(p) ? p : !com.tencent.qimei.v.d.a(this.b).n() ? "" : com.tencent.qimei.c.c.j().e();
    }

    public String j() {
        String str;
        String A = com.tencent.qimei.v.d.a(this.b).A();
        return !TextUtils.isEmpty(A) ? A : (com.tencent.qimei.v.d.a(this.b).h() && (str = com.tencent.qimei.c.c.j().h) != null) ? str : "";
    }

    @Override // com.tencent.qimei.g.c
    public void b() {
        com.tencent.qimei.c.c.j().v();
    }

    public String c() {
        String d = com.tencent.qimei.v.d.a(this.b).d();
        return !TextUtils.isEmpty(d) ? d : !com.tencent.qimei.v.d.a(this.b).y() ? "" : com.tencent.qimei.c.c.j().b();
    }

    public static synchronized d a(String str) {
        d dVar;
        synchronized (d.class) {
            Map<String, d> map = a;
            dVar = map.get(str);
            if (dVar == null) {
                dVar = new d(str);
                map.put(str, dVar);
            }
        }
        return dVar;
    }

    public static /* synthetic */ void a(d dVar, com.tencent.qimei.c.d dVar2) {
        dVar.d = false;
        com.tencent.qimei.b.a.a().a(10000L, new b(dVar, dVar2));
        com.tencent.qimei.c.c j = com.tencent.qimei.c.c.j();
        c cVar = new c(dVar, dVar2);
        if (j.b != null) {
            if (j.h == null) {
                j.c = SystemClock.elapsedRealtime();
                new VendorManager().getVendorInfo(j.b, new com.tencent.qimei.c.b(j, cVar));
            }
        } else {
            cVar.a(3);
        }
        com.tencent.qimei.k.a.b("SDK_INIT ｜ DeviceInfo", " 初始化完成 ", new Object[0]);
    }

    @Override // com.tencent.qimei.g.c
    public void a() {
        com.tencent.qimei.c.c.j().v();
    }
}
