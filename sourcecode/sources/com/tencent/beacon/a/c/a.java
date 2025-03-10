package com.tencent.beacon.a.c;

import android.content.SharedPreferences;
import com.tencent.beacon.a.d.a;

/* compiled from: AppInfo.java */
/* loaded from: classes.dex */
class a implements Runnable {
    final /* synthetic */ com.tencent.beacon.a.d.a a;
    final /* synthetic */ String b;
    final /* synthetic */ long c;

    a(com.tencent.beacon.a.d.a aVar, String str, long j) {
        this.a = aVar;
        this.b = str;
        this.c = j;
    }

    @Override // java.lang.Runnable
    public void run() {
        a.SharedPreferencesEditorC0016a edit = this.a.edit();
        if (com.tencent.beacon.base.util.b.a((SharedPreferences.Editor) edit)) {
            edit.putLong(this.b, this.c);
        }
    }
}
