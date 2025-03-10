package com.tencent.qimei.g;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkRequest;
import android.os.Build;
import j$.util.concurrent.ConcurrentHashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: NetworkStateReceiver.java */
/* loaded from: classes.dex */
public final class b extends BroadcastReceiver implements Runnable {
    public static b a;
    public ConnectivityManager.NetworkCallback c;
    public ConnectivityManager g;
    public final Map<c, String> b = new ConcurrentHashMap();
    public boolean d = true;
    public volatile boolean e = false;
    public boolean f = false;

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (this.d) {
            this.d = false;
        } else {
            if (this.e) {
                return;
            }
            com.tencent.qimei.b.a.a().a(this);
        }
    }

    @Override // java.lang.Runnable
    @SuppressLint({"MissingPermission"})
    public void run() {
        this.e = true;
        if (com.tencent.qimei.a.a.a()) {
            b();
        } else {
            c();
        }
        this.e = false;
    }

    public synchronized void b(Context context) {
        if (this.b.isEmpty()) {
            if (Build.VERSION.SDK_INT >= 21) {
                ConnectivityManager connectivityManager = this.g;
                if (connectivityManager != null) {
                    connectivityManager.unregisterNetworkCallback(this.c);
                }
            } else if (context != null) {
                try {
                    context.unregisterReceiver(this);
                } catch (Exception e) {
                    com.tencent.qimei.k.a.a(e);
                }
            }
        }
    }

    public final void c() {
        Iterator<c> it = this.b.keySet().iterator();
        while (it.hasNext()) {
            it.next().b();
        }
    }

    public static b a() {
        if (a == null) {
            synchronized (b.class) {
                if (a == null) {
                    a = new b();
                }
            }
        }
        return a;
    }

    @SuppressLint({"MissingPermission"})
    public synchronized void a(Context context) {
        if (this.b.isEmpty()) {
            this.f = com.tencent.qimei.a.a.a();
            if (Build.VERSION.SDK_INT >= 21) {
                this.c = new a(this);
                this.g = (ConnectivityManager) context.getSystemService("connectivity");
                NetworkRequest.Builder builder = new NetworkRequest.Builder();
                builder.addTransportType(1);
                builder.addTransportType(0);
                builder.addTransportType(3);
                ConnectivityManager connectivityManager = this.g;
                if (connectivityManager != null) {
                    try {
                        connectivityManager.registerNetworkCallback(builder.build(), this.c);
                    } catch (SecurityException e) {
                        com.tencent.qimei.k.a.a(e);
                    }
                }
            } else if (context != null) {
                try {
                    context.registerReceiver(this, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
                } catch (Exception e2) {
                    com.tencent.qimei.k.a.a(e2);
                }
            }
        }
    }

    public final void b() {
        Iterator<c> it = this.b.keySet().iterator();
        while (it.hasNext()) {
            it.next().a();
        }
    }

    public synchronized void a(String str, c cVar) {
        this.b.put(cVar, str);
    }

    public synchronized void a(String str) {
        for (Map.Entry<c, String> entry : this.b.entrySet()) {
            if (str.equals(entry.getValue())) {
                this.b.remove(entry.getKey());
            }
        }
    }
}
