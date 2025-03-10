package io.openinstall.sdk;

/* loaded from: classes.dex */
class cp implements Runnable {
    final /* synthetic */ co a;

    cp(co coVar) {
        this.a = coVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.a.c();
    }
}
