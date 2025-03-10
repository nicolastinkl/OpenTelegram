package com.tencent.beacon.base.net.call;

import java.util.Date;

/* compiled from: JceCall.java */
/* loaded from: classes.dex */
public class j implements a<byte[]> {
    private final JceRequestEntity a;
    private long b;

    public j(JceRequestEntity jceRequestEntity) {
        this.a = jceRequestEntity;
    }

    public void a(Callback<byte[]> callback) {
        com.tencent.beacon.a.b.a.a().a(new g(this, callback));
    }

    public void b(Callback<byte[]> callback) {
        this.b = new Date().getTime();
        com.tencent.beacon.base.net.c.c().a(this.a, new i(this, callback));
    }

    public void a(Callback<byte[]> callback, com.tencent.beacon.a.b.a aVar) {
        aVar.a(new h(this, callback));
    }
}
