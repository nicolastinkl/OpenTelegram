package com.tencent.beacon.a.c;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.beacon.module.BeaconModule;
import com.tencent.beacon.module.ModuleName;

/* compiled from: BeaconInfo.java */
/* loaded from: classes.dex */
public class c {
    private static volatile c a;
    private Context b;
    private byte c;
    private String d;
    private String f;
    private long g;
    private String e = "";
    private String h = "";
    private String i = "";
    private String j = "";
    private String k = "";
    private String l = "";
    private boolean m = true;

    public c() {
        this.c = (byte) -1;
        this.d = "";
        this.f = "";
        this.c = (byte) 1;
        this.d = "beacon";
        this.f = "unknown";
    }

    public static c d() {
        if (a == null) {
            synchronized (c.class) {
                if (a == null) {
                    a = new c();
                }
            }
        }
        return a;
    }

    public synchronized void a(long j) {
        this.g = j;
    }

    public synchronized String b() {
        return this.f;
    }

    public synchronized Context c() {
        return this.b;
    }

    public String e() {
        return this.l;
    }

    public String f() {
        return this.h;
    }

    public String g() {
        return this.k;
    }

    public synchronized byte h() {
        return this.c;
    }

    public synchronized String i() {
        return this.d;
    }

    public String j() {
        return "4.2.80.6";
    }

    public synchronized long k() {
        return this.g;
    }

    public String l() {
        return this.j;
    }

    public boolean m() {
        return this.m;
    }

    public synchronized void a(Context context) {
        if (this.b == null) {
            Context applicationContext = context.getApplicationContext();
            this.b = applicationContext;
            if (applicationContext == null) {
                this.b = context;
            }
        }
    }

    public void b(String str) {
        this.f = str;
    }

    public void c(String str) {
        this.l = str;
    }

    public void e(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.k = str;
    }

    public String a() {
        return this.i;
    }

    public void a(String str) {
        this.i = str;
    }

    public void d(String str) {
        this.h = str;
    }

    public BeaconModule a(ModuleName moduleName) {
        return BeaconModule.a.get(moduleName);
    }

    public void a(boolean z) {
        this.m = z;
    }
}
