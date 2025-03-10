package com.tencent.beacon.base.net;

import com.tencent.beacon.base.net.call.Callback;
import com.tencent.beacon.base.net.call.JceRequestEntity;
import com.tencent.cos.xml.CosXmlServiceConfig;

/* compiled from: BeaconNet.java */
/* loaded from: classes.dex */
class a implements Callback<byte[]> {
    final /* synthetic */ JceRequestEntity a;
    final /* synthetic */ boolean b;
    final /* synthetic */ Callback c;
    final /* synthetic */ c d;

    a(c cVar, JceRequestEntity jceRequestEntity, boolean z, Callback callback) {
        this.d = cVar;
        this.a = jceRequestEntity;
        this.b = z;
        this.c = callback;
    }

    @Override // com.tencent.beacon.base.net.call.Callback
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onResponse(byte[] bArr) {
        if (bArr == null || bArr.length <= 0) {
            onFailure(new d(this.a.getType().name(), this.b ? "402" : "452", 200, "raw response == null", null));
            return;
        }
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("jceRequest: ");
            sb.append(this.a.getType());
            sb.append(" request success!");
            com.tencent.beacon.base.util.c.a("[BeaconNet]", sb.toString(), new Object[0]);
            StringBuilder sb2 = new StringBuilder();
            sb2.append("mode: ");
            sb2.append(this.b ? "socket" : CosXmlServiceConfig.HTTP_PROTOCOL);
            com.tencent.beacon.base.util.c.a("[BeaconNet]", sb2.toString(), new Object[0]);
            this.c.onResponse(bArr);
            this.d.f();
        } catch (Exception e) {
            onFailure(new d(this.a.getType().name(), this.b ? "403" : "453", 200, e.getMessage(), e));
        }
    }

    @Override // com.tencent.beacon.base.net.call.Callback
    public void onFailure(d dVar) {
        com.tencent.beacon.base.util.c.a("[BeaconNet]", "jceRequest: " + dVar.toString(), new Object[0]);
        this.d.a(dVar);
        this.c.onFailure(dVar);
        this.d.f();
    }
}
