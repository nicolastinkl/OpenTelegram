package com.tencent.qimei.g;

import android.net.ConnectivityManager;
import android.net.Network;

/* compiled from: NetworkStateReceiver.java */
/* loaded from: classes.dex */
public class a extends ConnectivityManager.NetworkCallback {
    public final /* synthetic */ b a;

    public a(b bVar) {
        this.a = bVar;
    }

    @Override // android.net.ConnectivityManager.NetworkCallback
    public void onAvailable(Network network) {
        boolean z;
        z = this.a.f;
        if (z) {
            return;
        }
        this.a.f = true;
        com.tencent.qimei.k.a.b("QM", "current network switched to the available state", new Object[0]);
        this.a.b();
    }

    @Override // android.net.ConnectivityManager.NetworkCallback
    public void onLost(Network network) {
        this.a.f = false;
        com.tencent.qimei.k.a.b("QM", "current network lost", new Object[0]);
        this.a.c();
    }
}
