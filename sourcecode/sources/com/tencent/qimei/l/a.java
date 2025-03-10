package com.tencent.qimei.l;

import android.text.TextUtils;
import com.tencent.qimei.i.f;

/* compiled from: MultiAppKeyDeviceInfo.java */
/* loaded from: classes.dex */
public class a implements Runnable {
    public final /* synthetic */ com.tencent.qimei.c.d a;
    public final /* synthetic */ d b;

    public a(d dVar, com.tencent.qimei.c.d dVar2) {
        this.b = dVar;
        this.a = dVar2;
    }

    @Override // java.lang.Runnable
    public void run() {
        String str;
        String str2;
        str = this.b.b;
        String c = f.a(str).c("is_first");
        str2 = this.b.b;
        if (!com.tencent.qimei.v.d.a(str2).h() || TextUtils.isEmpty(c)) {
            this.a.a(2);
        } else {
            d.a(this.b, this.a);
        }
    }
}
