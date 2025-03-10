package com.tencent.beacon.d.a;

import com.tencent.beacon.a.c.e;
import com.tencent.beacon.a.c.f;
import com.tencent.beacon.module.StatModule;
import java.util.Map;

/* compiled from: LifecycleCallbacks.java */
/* loaded from: classes.dex */
class a implements Runnable {
    final /* synthetic */ c a;

    a(c cVar) {
        this.a = cVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        Map map;
        Map map2;
        Map map3;
        Map map4;
        StatModule statModule;
        Map<String, String> map5;
        e l = e.l();
        f e = f.e();
        map = this.a.g;
        map.put("A19", l.q());
        map2 = this.a.g;
        map2.put("A85", com.tencent.beacon.a.c.b.d ? "Y" : "N");
        map3 = this.a.g;
        map3.put("A20", e.j());
        map4 = this.a.g;
        map4.put("A69", e.k());
        statModule = this.a.h;
        map5 = this.a.g;
        statModule.b(map5);
    }
}
