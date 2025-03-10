package io.openinstall.sdk;

/* loaded from: classes.dex */
class dg implements Runnable {
    final /* synthetic */ df a;

    dg(df dfVar) {
        this.a = dfVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        boolean z;
        boolean z2;
        z = this.a.c;
        if (z) {
            z2 = this.a.d;
            if (z2) {
                this.a.c = false;
                this.a.b();
            }
        }
    }
}
