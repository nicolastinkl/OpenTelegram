package com.tencent.beacon.event.immediate;

import com.tencent.beacon.a.b.g;
import com.tencent.beacon.base.net.RequestType;
import com.tencent.beacon.base.net.call.f;
import com.tencent.beacon.base.util.c;
import com.tencent.beacon.event.EventBean;
import com.tencent.beacon.event.d;
import com.tencent.beacon.pack.ResponsePackageV2;
import com.tencent.beacon.pack.a;
import java.util.Date;

/* loaded from: classes.dex */
public class BeaconImmediateReportCallback implements f<BeaconTransferResult> {
    private d a;
    private EventBean b;
    private String c;
    private long d = new Date().getTime();

    public BeaconImmediateReportCallback(d dVar, EventBean eventBean, String str) {
        this.a = dVar;
        this.b = eventBean;
        this.c = str;
    }

    private void a(com.tencent.beacon.base.net.d dVar) {
        c.a("[BeaconImmediateReportCallback]", dVar.toString(), new Object[0]);
        g.e().a(dVar.b, dVar.d, dVar.e);
        this.a.a(this.b, this.c);
    }

    public void onResponse(BeaconTransferResult beaconTransferResult) {
        if (beaconTransferResult == null) {
            a(new com.tencent.beacon.base.net.d(RequestType.LONG_CONNECTION.name(), "462", -1, "response fail! result is null"));
            return;
        }
        boolean z = beaconTransferResult.getCode() == 0 && beaconTransferResult.getBizCode() == 0;
        c.a("[BeaconImmediateReportCallback]", "result=%s, eventName=%s , logID=%s", beaconTransferResult.toString(), this.b.getEventCode(), this.c);
        if (!z) {
            a(new com.tencent.beacon.base.net.d(RequestType.LONG_CONNECTION.name(), "463", beaconTransferResult.getCode(), c.c("response fail! result = %s", beaconTransferResult.toString())));
            return;
        }
        byte[] bizBuffer = beaconTransferResult.getBizBuffer();
        ResponsePackageV2 responsePackageV2 = new ResponsePackageV2();
        try {
            responsePackageV2.readFrom(new a(bizBuffer));
            com.tencent.beacon.base.net.b.d.a(this.d, responsePackageV2.serverTime, responsePackageV2.srcGatewayIp);
        } catch (Throwable th) {
            c.a(th);
            a(new com.tencent.beacon.base.net.d(RequestType.LONG_CONNECTION.name(), "463", beaconTransferResult.getCode(), th.getMessage(), th));
        }
    }
}
