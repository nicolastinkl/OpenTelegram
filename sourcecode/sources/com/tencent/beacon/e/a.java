package com.tencent.beacon.e;

import java.util.Calendar;
import java.util.Map;

/* compiled from: BeaconStrategy.java */
/* loaded from: classes.dex */
public class a implements d {
    private static volatile a a;
    public boolean b = false;
    private String c = "oth.str.mdt.qq.com";
    private int d = 360;
    private int e = 100;
    private Map<String, String> f = null;
    private boolean g = false;
    private e h = new e(1);

    private a() {
    }

    public static a a() {
        if (a == null) {
            synchronized (a.class) {
                if (a == null) {
                    a = new a();
                }
            }
        }
        return a;
    }

    public int b() {
        return this.d;
    }

    public synchronized int c() {
        String str;
        Map<String, String> map = this.f;
        if (map == null || (str = map.get("maxStrategyQueryOneDay")) == null || str.trim().equals("")) {
            return this.e;
        }
        int i = this.e;
        try {
            i = Integer.parseInt(str);
        } catch (Exception e) {
            com.tencent.beacon.base.util.c.a(e);
        }
        return i;
    }

    public e d() {
        return this.h;
    }

    public synchronized boolean e() {
        Map<String, String> map = this.f;
        if (map == null || !"y".equalsIgnoreCase(map.get("zeroPeak"))) {
            return false;
        }
        return Calendar.getInstance().get(11) == 0;
    }

    public void a(int i) {
        this.d = i;
    }

    public synchronized void a(Map<String, String> map) {
        this.f = map;
    }

    public String a(String str) {
        Map<String, String> a2;
        e eVar = this.h;
        if (eVar == null || (a2 = eVar.a()) == null) {
            return null;
        }
        return a2.get(str);
    }
}
