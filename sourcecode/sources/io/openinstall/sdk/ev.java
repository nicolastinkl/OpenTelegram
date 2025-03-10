package io.openinstall.sdk;

/* loaded from: classes.dex */
class ev implements Runnable {
    final /* synthetic */ ew a;
    final /* synthetic */ eu b;

    ev(eu euVar, ew ewVar) {
        this.b = euVar;
        this.a = ewVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.b.a.b.a(this.a);
    }
}
