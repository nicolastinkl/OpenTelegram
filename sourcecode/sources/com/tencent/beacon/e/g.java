package com.tencent.beacon.e;

import android.content.SharedPreferences;
import com.tencent.beacon.a.d.a;
import java.util.Date;

/* compiled from: StrategyHolder.java */
/* loaded from: classes.dex */
class g implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ String b;
    final /* synthetic */ h c;

    g(h hVar, String str, String str2) {
        this.c = hVar;
        this.a = str;
        this.b = str2;
    }

    @Override // java.lang.Runnable
    public void run() {
        Date d = com.tencent.beacon.base.util.b.d(this.a);
        long time = d != null ? d.getTime() / 1000 : 0L;
        if (time == 0) {
            time = (new Date().getTime() / 1000) + 86400;
        }
        a.SharedPreferencesEditorC0016a edit = com.tencent.beacon.a.d.a.a().edit();
        if (com.tencent.beacon.base.util.b.a((SharedPreferences.Editor) edit)) {
            edit.putString("sid_value", this.b).putLong("sid_mt", time);
        }
    }
}
