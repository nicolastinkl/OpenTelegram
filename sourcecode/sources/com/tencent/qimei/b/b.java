package com.tencent.qimei.b;

import com.tencent.qimei.j.e;

/* compiled from: BeaconAsyncTask.java */
/* loaded from: classes.dex */
public class b implements Runnable {
    public final /* synthetic */ Runnable a;

    public b(c cVar, Runnable runnable) {
        this.a = runnable;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            this.a.run();
        } catch (Throwable th) {
            e.a(th.getMessage());
            com.tencent.qimei.k.a.a(th);
        }
    }
}
