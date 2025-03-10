package io.openinstall.sdk;

/* loaded from: classes.dex */
class dv implements Runnable {
    final /* synthetic */ dt a;

    dv(dt dtVar) {
        this.a = dtVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.a.b("lifecycle");
    }
}
