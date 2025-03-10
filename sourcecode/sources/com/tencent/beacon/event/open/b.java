package com.tencent.beacon.event.open;

import com.tencent.beacon.a.b.g;
import com.tencent.beacon.a.c.j;
import com.tencent.beacon.base.util.c;

/* compiled from: BeaconReport.java */
/* loaded from: classes.dex */
class b implements Runnable {
    final /* synthetic */ BeaconConfig a;
    final /* synthetic */ BeaconReport b;

    b(BeaconReport beaconReport, BeaconConfig beaconConfig) {
        this.b = beaconReport;
        this.a = beaconConfig;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            j.a();
            j.h();
            this.b.a(this.a);
            this.b.a();
            c.a("BeaconReport", "App: %s start success!", com.tencent.beacon.a.c.c.d().f());
        } catch (Throwable th) {
            g.e().a("201", "sdk init error! package name: " + com.tencent.beacon.a.c.b.b() + " , msg:" + th.getMessage(), th);
            StringBuilder sb = new StringBuilder();
            sb.append("BeaconReport init error: ");
            sb.append(th.getMessage());
            c.b(sb.toString(), new Object[0]);
            c.a(th);
        }
    }
}
