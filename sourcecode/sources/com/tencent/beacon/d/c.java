package com.tencent.beacon.d;

import android.content.Context;
import android.content.SharedPreferences;
import com.tencent.beacon.a.c.e;
import com.tencent.beacon.a.c.f;
import com.tencent.beacon.a.d.a;
import com.tencent.beacon.module.ModuleName;
import com.tencent.beacon.module.StatModule;
import java.util.HashMap;
import java.util.Map;

/* compiled from: Heartbeat.java */
/* loaded from: classes.dex */
public class c {
    protected final Context a;
    private final boolean b;
    private boolean c = false;

    public c(Context context) {
        this.a = context;
        this.b = com.tencent.beacon.a.c.b.f(context);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean c() {
        return ((StatModule) com.tencent.beacon.a.c.c.d().a(ModuleName.STAT)).c(b());
    }

    private Map<String, String> b() {
        this.c = com.tencent.beacon.a.c.b.d;
        HashMap hashMap = new HashMap(8);
        e l = e.l();
        f e = f.e();
        hashMap.put("A19", l.q());
        hashMap.put("A66", com.tencent.beacon.a.c.b.f(this.a) ? "F" : "B");
        hashMap.put("A68", "" + com.tencent.beacon.a.c.b.b(this.a));
        hashMap.put("A85", this.c ? "Y" : "N");
        hashMap.put("A20", e.j());
        hashMap.put("A69", e.k());
        return hashMap;
    }

    public void a(com.tencent.beacon.e.b bVar) {
        String d = com.tencent.beacon.base.util.b.d();
        com.tencent.beacon.a.d.a a = com.tencent.beacon.a.d.a.a();
        String string = a.getString("HEART_DENGTA", "");
        String string2 = a.getString("active_user_date", "");
        if (d.equals(string) || string2.equals(d)) {
            com.tencent.beacon.base.util.c.e("[event] heartbeat had upload!", new Object[0]);
            return;
        }
        if (bVar.a("rqd_heartbeat")) {
            com.tencent.beacon.base.util.c.e("[event] rqd_heartbeat not allowed in strategy!", new Object[0]);
        } else if (bVar.b("rqd_heartbeat")) {
            com.tencent.beacon.a.b.a.a().a(new b(this, d, a));
        } else {
            com.tencent.beacon.base.util.c.e("[event] rqd_heartbeat is sampled by svr rate!", new Object[0]);
        }
    }

    public void a() {
        com.tencent.beacon.a.d.a a = com.tencent.beacon.a.d.a.a();
        if (!com.tencent.beacon.base.util.b.d().equals(a.getString("active_user_date", ""))) {
            com.tencent.beacon.base.util.c.a("[event] recover a heart beat for active user.", new Object[0]);
            if (c()) {
                com.tencent.beacon.base.util.c.a("[event] rqd_heartbeat A85=Y report success", new Object[0]);
                a.SharedPreferencesEditorC0016a edit = a.edit();
                if (com.tencent.beacon.base.util.b.a((SharedPreferences.Editor) edit)) {
                    edit.putString("active_user_date", com.tencent.beacon.base.util.b.d()).apply();
                    return;
                }
                return;
            }
            return;
        }
        com.tencent.beacon.base.util.c.e("[event] active user event had upload.", new Object[0]);
    }
}
