package com.tencent.beacon.event;

import com.tencent.beacon.base.net.call.Callback;

/* compiled from: EventManager.java */
/* loaded from: classes.dex */
class c implements Callback<byte[]> {
    final /* synthetic */ EventBean a;
    final /* synthetic */ String b;
    final /* synthetic */ d c;

    c(d dVar, EventBean eventBean, String str) {
        this.c = dVar;
        this.a = eventBean;
        this.b = str;
    }

    @Override // com.tencent.beacon.base.net.call.Callback
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onResponse(byte[] bArr) {
        com.tencent.beacon.base.util.c.a("[EventManager]", "convert to report by beacon socket success, eventCode = %s, logId = %s", this.a.getEventCode(), this.b);
    }

    @Override // com.tencent.beacon.base.net.call.Callback
    public void onFailure(com.tencent.beacon.base.net.d dVar) {
        com.tencent.beacon.base.util.c.e("convert to report by beacon socket also fail, failure = %s", dVar.toString());
        com.tencent.beacon.a.b.g.e().a("464", dVar.toString());
        this.c.b(this.b, this.a);
    }
}
