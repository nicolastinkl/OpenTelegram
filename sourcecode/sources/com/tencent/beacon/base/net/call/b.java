package com.tencent.beacon.base.net.call;

import com.tencent.beacon.base.net.BResponse;
import com.tencent.beacon.base.net.NetException;

/* compiled from: HttpCall.java */
/* loaded from: classes.dex */
class b implements Callback<BResponse> {
    final /* synthetic */ Callback a;
    final /* synthetic */ c b;

    b(c cVar, Callback callback) {
        this.b = cVar;
        this.a = callback;
    }

    @Override // com.tencent.beacon.base.net.call.Callback
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onResponse(BResponse bResponse) throws NetException {
        Callback callback = this.a;
        if (callback != null) {
            callback.onResponse(bResponse);
        }
    }

    @Override // com.tencent.beacon.base.net.call.Callback
    public void onFailure(com.tencent.beacon.base.net.d dVar) {
        Callback callback = this.a;
        if (callback != null) {
            callback.onFailure(dVar);
        }
    }
}
