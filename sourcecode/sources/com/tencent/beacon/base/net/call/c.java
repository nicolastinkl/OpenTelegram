package com.tencent.beacon.base.net.call;

import com.tencent.beacon.base.net.BResponse;

/* compiled from: HttpCall.java */
/* loaded from: classes.dex */
public class c implements a<BResponse> {
    private e a;

    public c(e eVar) {
        this.a = eVar;
    }

    public void a(Callback<BResponse> callback) {
        com.tencent.beacon.base.net.c.c().a(this.a, new b(this, callback));
    }
}
