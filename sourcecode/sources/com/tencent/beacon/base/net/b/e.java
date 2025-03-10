package com.tencent.beacon.base.net.b;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: NetworkStateReceiver.java */
/* loaded from: classes.dex */
public final class e extends BroadcastReceiver implements Runnable {
    private static final List<a> a = new ArrayList();
    private static volatile boolean b = false;
    private boolean c = true;
    private volatile boolean d = false;

    /* compiled from: NetworkStateReceiver.java */
    public interface a {
        void a();

        void b();
    }

    public static void a(Context context, a aVar) {
        if (context == null) {
            com.tencent.beacon.base.util.c.b("[net] context == null!", new Object[0]);
            return;
        }
        List<a> list = a;
        synchronized (list) {
            if (!list.contains(aVar)) {
                list.add(aVar);
            }
        }
        if (b) {
            return;
        }
        context.registerReceiver(new e(), new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        b = true;
    }

    private void b() {
        List<a> list = a;
        synchronized (list) {
            Iterator<a> it = list.iterator();
            while (it.hasNext()) {
                it.next().b();
            }
        }
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (this.c) {
            this.c = false;
        } else {
            if (this.d) {
                return;
            }
            com.tencent.beacon.a.b.a.a().a(this);
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        this.d = true;
        if (d.d()) {
            com.tencent.beacon.base.util.c.d("[net] current network available!", new Object[0]);
            a();
        } else {
            com.tencent.beacon.base.util.c.d("[net] current network unavailable!", new Object[0]);
            b();
        }
        this.d = false;
    }

    private void a() {
        List<a> list = a;
        synchronized (list) {
            Iterator<a> it = list.iterator();
            while (it.hasNext()) {
                it.next().a();
            }
        }
    }
}
