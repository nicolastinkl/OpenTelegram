package com.tencent.beacon.a.b;

import com.tencent.beacon.base.net.HttpMethod;
import com.tencent.beacon.base.net.call.Callback;
import java.util.LinkedHashMap;
import java.util.Map;

/* compiled from: AbstractAttaReport.java */
/* loaded from: classes.dex */
class c implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ String b;
    final /* synthetic */ Throwable c;
    final /* synthetic */ boolean d;
    final /* synthetic */ Callback e;
    final /* synthetic */ e f;

    c(e eVar, String str, String str2, Throwable th, boolean z, Callback callback) {
        this.f = eVar;
        this.a = str;
        this.b = str2;
        this.c = th;
        this.d = z;
        this.e = callback;
    }

    @Override // java.lang.Runnable
    public void run() {
        Map map;
        synchronized (this.f) {
            map = e.a;
            LinkedHashMap linkedHashMap = new LinkedHashMap(map);
            linkedHashMap.put("error_code", this.a);
            linkedHashMap.put("error_msg", this.b);
            linkedHashMap.put("error_stack_full", com.tencent.beacon.base.util.b.a(this.c));
            linkedHashMap.put("_dc", String.valueOf(Math.random()));
            com.tencent.beacon.base.net.c.c().a(com.tencent.beacon.base.net.call.e.b().b(this.d ? "https://htrace.wetvinfo.com/kv" : "https://h.trace.qq.com/kv").a("atta").a(linkedHashMap).a(HttpMethod.POST).a()).a(this.e);
            com.tencent.beacon.base.util.c.d("[atta] upload a new error, errorCode: %s, message: %s, stack: %s", this.a, this.b, com.tencent.beacon.base.util.b.a(this.c));
        }
    }
}
