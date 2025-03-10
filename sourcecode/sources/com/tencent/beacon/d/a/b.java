package com.tencent.beacon.d.a;

import android.app.Activity;

/* compiled from: LifecycleCallbacks.java */
/* loaded from: classes.dex */
class b implements Runnable {
    final /* synthetic */ Activity a;
    final /* synthetic */ c b;

    b(c cVar, Activity activity) {
        this.b = cVar;
        this.a = activity;
    }

    @Override // java.lang.Runnable
    public void run() {
        new com.tencent.beacon.d.c(this.a.getApplicationContext()).a();
    }
}
