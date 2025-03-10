package com.tencent.beacon.base.net.call;

/* compiled from: JceCall.java */
/* loaded from: classes.dex */
class g implements Runnable {
    final /* synthetic */ Callback a;
    final /* synthetic */ j b;

    g(j jVar, Callback callback) {
        this.b = jVar;
        this.a = callback;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.b.b(this.a);
    }
}
