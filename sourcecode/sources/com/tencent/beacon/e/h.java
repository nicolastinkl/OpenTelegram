package com.tencent.beacon.e;

import android.content.Context;
import android.util.Base64;
import java.util.Date;

/* compiled from: StrategyHolder.java */
/* loaded from: classes.dex */
public class h {
    private static volatile h a;
    private d d;
    private final String b = "sid";
    private String e = "";
    private boolean f = true;
    private int g = 8081;
    private String h = "";
    private String i = "";
    private final Context c = com.tencent.beacon.a.c.c.d().c();

    private h() {
        com.tencent.beacon.a.b.a.a().a(new f(this));
    }

    public static h b() {
        if (a == null) {
            synchronized (h.class) {
                if (a == null) {
                    a = new h();
                }
            }
        }
        return a;
    }

    public synchronized String c() {
        return this.e;
    }

    public synchronized String d() {
        return this.h;
    }

    public synchronized void a(String str, String str2) {
        com.tencent.beacon.base.util.c.a("[net] -> update local sid|time[%s|%s].", str, str2);
        this.e = str;
        com.tencent.beacon.a.b.a.a().a(new g(this, str2, str));
    }

    public synchronized void a(String str) {
        this.e = str;
    }

    public void a(d dVar) {
        this.d = dVar;
    }

    public synchronized void a(Context context) {
        com.tencent.beacon.a.d.a a2 = com.tencent.beacon.a.d.a.a();
        String string = a2.getString("sid_value", "");
        if (a2.getLong("sid_mt", 0L) > new Date().getTime() / 1000) {
            a(string);
        }
        a(context, com.tencent.beacon.base.util.b.b());
    }

    synchronized void a(Context context, String str) {
        this.i = str;
        byte[] a2 = com.tencent.beacon.base.net.b.c.a(context, str);
        if (a2 != null) {
            this.h = Base64.encodeToString(a2, 2);
        }
    }

    public synchronized String a() {
        return this.i;
    }
}
