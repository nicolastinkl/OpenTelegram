package com.tencent.bugly.proguard;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/* compiled from: BUGLY */
/* loaded from: classes.dex */
public final class aq extends BroadcastReceiver {
    private static aq d;
    private Context b;
    private String c;
    private boolean e = true;
    private IntentFilter a = new IntentFilter();

    public static synchronized aq a() {
        aq aqVar;
        synchronized (aq.class) {
            if (d == null) {
                d = new aq();
            }
            aqVar = d;
        }
        return aqVar;
    }

    public final synchronized void a(String str) {
        if (!this.a.hasAction(str)) {
            this.a.addAction(str);
        }
        al.c("add action %s", str);
    }

    public final synchronized void a(Context context) {
        this.b = context;
        ap.a(new Runnable() { // from class: com.tencent.bugly.proguard.aq.1
            @Override // java.lang.Runnable
            public final void run() {
                try {
                    al.a(aq.d.getClass(), "Register broadcast receiver of Bugly.", new Object[0]);
                    synchronized (this) {
                        aq.this.b.registerReceiver(aq.d, aq.this.a, "com.tencent.bugly.BuglyBroadcastReceiver.permission", null);
                    }
                } catch (Throwable th) {
                    th.printStackTrace();
                }
            }
        });
    }

    public final synchronized void b(Context context) {
        try {
            al.a(aq.class, "Unregister broadcast receiver of Bugly.", new Object[0]);
            context.unregisterReceiver(this);
            this.b = context;
        } catch (Throwable th) {
            if (al.a(th)) {
                return;
            }
            th.printStackTrace();
        }
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        try {
            a(context, intent);
        } catch (Throwable th) {
            if (al.a(th)) {
                return;
            }
            th.printStackTrace();
        }
    }

    private synchronized boolean a(Context context, Intent intent) {
        if (context != null && intent != null) {
            if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                if (this.e) {
                    this.e = false;
                    return true;
                }
                String c = ab.c(this.b);
                al.c("is Connect BC ".concat(String.valueOf(c)), new Object[0]);
                al.a("network %s changed to %s", this.c, String.valueOf(c));
                if (c == null) {
                    this.c = null;
                    return true;
                }
                String str = this.c;
                this.c = c;
                long currentTimeMillis = System.currentTimeMillis();
                ac a = ac.a();
                ai a2 = ai.a();
                aa a3 = aa.a(context);
                if (a != null && a2 != null && a3 != null) {
                    if (!c.equals(str) && currentTimeMillis - a2.a(at.a) > 30000) {
                        al.a("try to upload crash on network changed.", new Object[0]);
                        at a4 = at.a();
                        if (a4 != null) {
                            a4.a(0L);
                        }
                        al.a("try to upload userinfo on network changed.", new Object[0]);
                        s.b.b();
                    }
                    return true;
                }
                al.d("not inited BC not work", new Object[0]);
                return true;
            }
        }
        return false;
    }
}
