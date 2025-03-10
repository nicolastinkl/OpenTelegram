package com.tencent.beacon.event;

/* compiled from: EventManager.java */
/* loaded from: classes.dex */
class a implements Runnable {
    final /* synthetic */ EventBean a;
    final /* synthetic */ d b;

    a(d dVar, EventBean eventBean) {
        this.b = dVar;
        this.a = eventBean;
    }

    @Override // java.lang.Runnable
    public void run() {
        com.tencent.beacon.event.a.a aVar;
        com.tencent.beacon.event.a.a aVar2;
        aVar = this.b.b;
        if (!aVar.a(this.a.getEventType())) {
            aVar2 = this.b.b;
            boolean a = aVar2.a(this.a);
            com.tencent.beacon.base.util.c.a("[EventModule]", 2, "event: %s. insert to DB %s", this.a.getEventCode(), Boolean.valueOf(a));
            if (a) {
                this.b.b();
                return;
            }
            return;
        }
        com.tencent.beacon.a.b.g.e().a("602", "type: " + com.tencent.beacon.event.c.d.a(this.a.getEventType()) + " max db count!");
        com.tencent.beacon.base.util.c.a("[EventModule]", 2, "event: %s. insert to DB false. reason: DB count max!", this.a.getEventCode());
    }
}
