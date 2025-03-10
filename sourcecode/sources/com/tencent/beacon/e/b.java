package com.tencent.beacon.e;

import com.tencent.bugly.BuglyStrategy;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/* compiled from: DefaultStrategy.java */
/* loaded from: classes.dex */
public class b implements com.tencent.beacon.a.a.d {
    private static volatile b a;
    protected Boolean f;
    protected c h;
    protected int b = 48;
    protected int c = 2000;
    protected int d = 48;
    protected int e = 5000;
    protected boolean g = true;
    protected boolean i = true;
    protected boolean j = true;
    protected Set<String> k = null;
    protected Map<String, Float> l = null;
    protected boolean m = false;
    protected boolean n = false;
    protected boolean o = false;
    protected float p = 1.0f;

    /* renamed from: q, reason: collision with root package name */
    protected boolean f3q = false;
    protected boolean r = false;
    protected boolean s = false;
    protected int t = 1;
    protected long u = 6400;
    protected int v = 20;
    protected int w = 300;
    protected boolean x = true;
    protected boolean y = true;
    protected boolean z = true;
    protected boolean A = true;
    protected boolean B = true;
    protected int C = 10000;
    protected boolean D = true;
    protected boolean E = true;
    protected boolean F = false;
    protected int G = 1;

    protected b() {
        com.tencent.beacon.a.a.b.a().a(8, this);
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

    private void b(Map<String, String> map) {
        HashMap hashMap = new HashMap();
        hashMap.put("d_m", new HashMap(map));
        com.tencent.beacon.a.a.b.a().b(new com.tencent.beacon.a.a.c(2, hashMap));
    }

    private void o() {
        c cVar = this.h;
        if (cVar != null) {
            cVar.c();
        }
    }

    public synchronized int c() {
        return this.d;
    }

    public synchronized int d() {
        return this.b;
    }

    public synchronized int e() {
        return this.G;
    }

    public boolean f() {
        return this.y;
    }

    public boolean g() {
        return this.x;
    }

    public boolean h() {
        Boolean bool = this.f;
        return bool == null ? this.g : bool.booleanValue();
    }

    public synchronized boolean i() {
        return this.n;
    }

    public boolean j() {
        return this.i;
    }

    public boolean k() {
        return this.B;
    }

    public boolean l() {
        return this.A;
    }

    public boolean m() {
        return this.z;
    }

    public synchronized boolean n() {
        return this.F;
    }

    public synchronized void b(Set<String> set) {
        if (this.l == null) {
            this.l = new HashMap();
        }
        if (set == null) {
            return;
        }
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            String[] split = it.next().split(",");
            if (split.length == 3) {
                try {
                    this.l.put(split[0].toLowerCase(), Float.valueOf(Float.valueOf(split[1]).floatValue() / Float.valueOf(split[2]).floatValue()));
                } catch (Exception e) {
                    com.tencent.beacon.base.util.c.a(e);
                }
            }
        }
    }

    @Override // com.tencent.beacon.a.a.d
    public void a(com.tencent.beacon.a.a.c cVar) {
        if (cVar.a != 8) {
            return;
        }
        this.y = cVar.b.containsKey("u_c_a_e") ? ((Boolean) cVar.b.get("u_c_a_e")).booleanValue() : this.y;
        this.x = cVar.b.containsKey("u_c_b_e") ? ((Boolean) cVar.b.get("u_c_b_e")).booleanValue() : this.x;
        this.C = cVar.b.containsKey("u_c_d_s") ? ((Integer) cVar.b.get("u_c_d_s")).intValue() : this.C;
        this.i = cVar.b.containsKey("u_c_p_s") ? ((Boolean) cVar.b.get("u_c_p_s")).booleanValue() : this.i;
        this.z = cVar.b.containsKey("u_s_o_h") ? ((Boolean) cVar.b.get("u_s_o_h")).booleanValue() : this.z;
    }

    public synchronized boolean b(String str) {
        Map<String, Float> map = this.l;
        if (map != null && map.get(str.toLowerCase()) != null) {
            return new Random().nextInt(1000) + 1 <= ((int) (this.l.get(str.toLowerCase()).floatValue() * 1000.0f));
        }
        return true;
    }

    public void b(boolean z) {
        this.B = z;
    }

    public int b() {
        return this.C;
    }

    public void b(int i) {
        if (i < 24 || i > 100) {
            return;
        }
        this.b = i;
    }

    public synchronized void a(Map<String, String> map) {
        if (map != null) {
            b(map);
            try {
                if (this.b == 48) {
                    this.b = com.tencent.beacon.base.util.b.a(map.get("realtimeUploadNum"), this.b, 24, 100);
                }
                if (this.d == 48) {
                    this.d = com.tencent.beacon.base.util.b.a(map.get("normalUploadNum"), this.d, 24, 100);
                }
                if (this.e == 5000) {
                    this.e = com.tencent.beacon.base.util.b.a(map.get("normalPollingTime"), this.e, 2000, 3600000);
                }
                if (this.c == 2000) {
                    this.c = com.tencent.beacon.base.util.b.a(map.get("realtimePollingTime"), this.c, 1000, 10000);
                }
                this.j = com.tencent.beacon.base.util.b.a(map.get("heartOnOff"), this.j);
                this.m = com.tencent.beacon.base.util.b.a(map.get("tidyEF"), this.m);
                this.n = com.tencent.beacon.base.util.b.a(map.get("lauEveSim"), this.n);
                this.o = com.tencent.beacon.base.util.b.a(map.get("zeroPeakOnOff"), this.o);
                String str = map.get("zeroPeakRate");
                if (str != null) {
                    String trim = str.trim();
                    if (trim.length() > 0) {
                        String[] split = trim.split(",");
                        if (split.length == 2) {
                            try {
                                this.p = Float.valueOf(split[0]).floatValue() / Float.valueOf(split[1]).floatValue();
                            } catch (Exception e) {
                                com.tencent.beacon.base.util.c.a(e);
                            }
                        }
                    }
                }
                this.F = com.tencent.beacon.base.util.b.a(map.get("straOnOff"), this.F);
                this.G = com.tencent.beacon.base.util.b.a(map.get("straDayMaxCount"), this.G, 1, Integer.MAX_VALUE);
                this.f3q = com.tencent.beacon.base.util.b.a(map.get("acceleEnable"), this.f3q);
                this.r = com.tencent.beacon.base.util.b.a(map.get("gyroEnable"), this.r);
                this.s = com.tencent.beacon.base.util.b.a(map.get("magneticEnable"), this.s);
                this.t = com.tencent.beacon.base.util.b.a(map.get("gatherCount"), this.t, 1, 50);
                this.u = com.tencent.beacon.base.util.b.a(map.get("gatherDur"), this.u, 1000L, 20000L);
                this.v = com.tencent.beacon.base.util.b.a(map.get("hertzCount"), this.v, 20, 100);
                this.w = com.tencent.beacon.base.util.b.a(map.get("consuming"), this.w, 60, 86400);
                this.x = com.tencent.beacon.base.util.b.a(map.get("bidEnable"), this.x);
                this.y = com.tencent.beacon.base.util.b.a(map.get("auditEnable"), this.y);
                this.C = com.tencent.beacon.base.util.b.a(map.get("maxDBCount"), this.C, 10000, BuglyStrategy.a.MAX_USERDATA_VALUE_LENGTH);
                com.tencent.beacon.base.net.b.b.c(map.get("eventUrl"));
                com.tencent.beacon.base.net.b.b.e(map.get("strategyUrl"));
            } catch (Exception e2) {
                com.tencent.beacon.base.util.c.a(e2);
            }
        }
    }

    public synchronized void a(Set<String> set) {
        this.k = set;
    }

    public synchronized boolean a(String str) {
        boolean z;
        z = false;
        Set<String> set = this.k;
        if (set != null && set.size() > 0) {
            z = this.k.contains(str);
        }
        return z;
    }

    public void a(boolean z) {
        this.A = z;
    }

    public void a(int i) {
        if (i < 24 || i > 100) {
            return;
        }
        this.d = i;
    }

    public void a(boolean z, boolean z2) {
        com.tencent.beacon.base.util.c.a("event report state changed: " + z, new Object[0]);
        boolean h = h();
        if (z2) {
            this.f = Boolean.valueOf(z);
        } else {
            this.g = z;
        }
        if (h != h()) {
            o();
        }
    }

    public void a(c cVar) {
        this.h = cVar;
    }
}
