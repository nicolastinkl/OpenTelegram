package com.tencent.beacon.a.b;

import com.tencent.beacon.base.net.BResponse;
import com.tencent.beacon.base.net.call.Callback;

/* compiled from: AbstractAttaReport.java */
/* loaded from: classes.dex */
class b implements Callback<BResponse> {
    final /* synthetic */ String a;
    final /* synthetic */ String b;
    final /* synthetic */ Throwable c;
    final /* synthetic */ e d;

    b(e eVar, String str, String str2, Throwable th) {
        this.d = eVar;
        this.a = str;
        this.b = str2;
        this.c = th;
    }

    @Override // com.tencent.beacon.base.net.call.Callback
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onResponse(BResponse bResponse) {
        com.tencent.beacon.base.util.c.a("AttaReport", "net ret: " + bResponse.toString(), new Object[0]);
    }

    @Override // com.tencent.beacon.base.net.call.Callback
    public void onFailure(com.tencent.beacon.base.net.d dVar) {
        this.d.b(this.a, this.b, this.c);
    }
}
