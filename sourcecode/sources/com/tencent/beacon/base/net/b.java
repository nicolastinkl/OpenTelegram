package com.tencent.beacon.base.net;

import com.tencent.beacon.base.net.call.Callback;

/* compiled from: BeaconNet.java */
/* loaded from: classes.dex */
class b implements Callback<BResponse> {
    final /* synthetic */ com.tencent.beacon.base.net.call.e a;
    final /* synthetic */ Callback b;
    final /* synthetic */ c c;

    b(c cVar, com.tencent.beacon.base.net.call.e eVar, Callback callback) {
        this.c = cVar;
        this.a = eVar;
        this.b = callback;
    }

    @Override // com.tencent.beacon.base.net.call.Callback
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onResponse(BResponse bResponse) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("httpRequest: ");
            sb.append(this.a.h());
            sb.append(" request success!");
            com.tencent.beacon.base.util.c.a("[BeaconNet]", sb.toString(), new Object[0]);
            this.b.onResponse(bResponse);
            this.c.f();
        } catch (Exception e) {
            onFailure(new d(this.a.h(), "453", 200, e.getMessage(), e));
        }
    }

    @Override // com.tencent.beacon.base.net.call.Callback
    public void onFailure(d dVar) {
        com.tencent.beacon.base.util.c.a("[BeaconNet]", "httpRequest: " + dVar.toString(), new Object[0]);
        this.c.a(dVar);
        this.b.onFailure(dVar);
        this.c.f();
    }
}
