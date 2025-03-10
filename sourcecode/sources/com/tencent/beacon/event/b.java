package com.tencent.beacon.event;

import com.tencent.beacon.event.immediate.BeaconImmediateReportCallback;
import com.tencent.beacon.event.immediate.BeaconTransferArgs;
import com.tencent.beacon.event.immediate.IBeaconImmediateReport;
import com.tencent.beacon.event.open.BeaconReport;
import com.tencent.beacon.pack.RequestPackageV2;

/* compiled from: EventManager.java */
/* loaded from: classes.dex */
class b implements Runnable {
    final /* synthetic */ EventBean a;
    final /* synthetic */ String b;
    final /* synthetic */ d c;

    b(d dVar, EventBean eventBean, String str) {
        this.c = dVar;
        this.a = eventBean;
        this.b = str;
    }

    @Override // java.lang.Runnable
    public void run() {
        BeaconTransferArgs a;
        try {
            RequestPackageV2 a2 = com.tencent.beacon.event.c.d.a(this.a);
            IBeaconImmediateReport immediateReport = BeaconReport.getInstance().getImmediateReport();
            a = this.c.a(a2.toByteArray(), this.a);
            immediateReport.reportImmediate(a, new BeaconImmediateReportCallback(this.c, this.a, this.b));
        } catch (Throwable th) {
            com.tencent.beacon.base.util.c.b("[immediate] report error!", new Object[0]);
            com.tencent.beacon.base.util.c.a(th);
            this.c.a(this.a, this.b);
            com.tencent.beacon.a.b.g.e().a("515", "immediate report error!", th);
        }
    }
}
