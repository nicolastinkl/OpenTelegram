package com.tencent.beacon.e;

import android.content.Context;

/* compiled from: StrategyHolder.java */
/* loaded from: classes.dex */
class f implements Runnable {
    final /* synthetic */ h a;

    f(h hVar) {
        this.a = hVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        Context context;
        Context context2;
        context = this.a.c;
        if (context != null) {
            h hVar = this.a;
            context2 = hVar.c;
            hVar.a(context2);
        }
    }
}
