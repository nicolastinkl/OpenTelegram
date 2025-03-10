package io.openinstall.sdk;

/* loaded from: classes.dex */
class eu implements Runnable {
    final /* synthetic */ et a;

    eu(et etVar) {
        this.a = etVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        System.currentTimeMillis();
        ew n = this.a.n();
        et etVar = this.a;
        if (etVar.b != null) {
            etVar.a.b().post(new ev(this, n));
        }
        System.currentTimeMillis();
    }
}
