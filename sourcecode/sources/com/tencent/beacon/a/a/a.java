package com.tencent.beacon.a.a;

/* compiled from: BeaconBus.java */
/* loaded from: classes.dex */
class a implements Runnable {
    final /* synthetic */ c a;
    final /* synthetic */ b b;

    a(b bVar, c cVar) {
        this.b = bVar;
        this.a = cVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.b.b(this.a);
    }
}
