package com.tencent.beacon.d;

import android.content.SharedPreferences;
import com.tencent.beacon.a.d.a;
import com.tencent.beacon.base.net.b.d;

/* compiled from: Heartbeat.java */
/* loaded from: classes.dex */
class b implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ com.tencent.beacon.a.d.a b;
    final /* synthetic */ c c;

    b(c cVar, String str, com.tencent.beacon.a.d.a aVar) {
        this.c = cVar;
        this.a = str;
        this.b = aVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        boolean c;
        boolean z;
        if (d.d()) {
            c = this.c.c();
            z = this.c.c;
            if (z && c) {
                com.tencent.beacon.base.util.c.a("[event] rqd_heartbeat A85=Y report success : " + this.a, new Object[0]);
                a.SharedPreferencesEditorC0016a edit = this.b.edit();
                if (com.tencent.beacon.base.util.b.a((SharedPreferences.Editor) edit)) {
                    edit.putString("active_user_date", this.a).commit();
                    edit.putString("HEART_DENGTA", this.a).commit();
                }
            }
        }
    }
}
