package com.tencent.beacon.event.b;

import com.tencent.beacon.event.EventBean;
import com.tencent.beacon.event.open.BeaconEvent;

/* compiled from: EventHandler.java */
/* loaded from: classes.dex */
public abstract class c {
    private c a;

    abstract BeaconEvent a(BeaconEvent beaconEvent);

    public void a(c cVar) {
        this.a = cVar;
    }

    public final EventBean b(BeaconEvent beaconEvent) {
        BeaconEvent a = a(beaconEvent);
        c cVar = this.a;
        return cVar != null ? cVar.b(a) : com.tencent.beacon.event.c.a.a(a);
    }
}
