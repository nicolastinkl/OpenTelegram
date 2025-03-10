package com.tencent.beacon.a.b;

import com.tencent.beacon.base.net.BResponse;
import com.tencent.beacon.base.net.call.Callback;

/* compiled from: AbstractAttaReport.java */
/* loaded from: classes.dex */
class d implements Callback<BResponse> {
    final /* synthetic */ e a;

    d(e eVar) {
        this.a = eVar;
    }

    @Override // com.tencent.beacon.base.net.call.Callback
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onResponse(BResponse bResponse) {
        if (bResponse != null) {
            com.tencent.beacon.base.util.c.a("AttaReport", "oversea net ret: " + bResponse.toString(), new Object[0]);
        }
    }

    @Override // com.tencent.beacon.base.net.call.Callback
    public void onFailure(com.tencent.beacon.base.net.d dVar) {
    }
}
