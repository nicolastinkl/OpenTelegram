package com.tencent.qimei.v;

import j$.util.concurrent.ConcurrentHashMap;
import java.util.Map;

/* compiled from: StrategyController.java */
/* loaded from: classes.dex */
public class f {
    public static final Map<String, f> a = new ConcurrentHashMap();
    public static final Object b = new Object();
    public final String c;
    public j d;
    public boolean e = false;

    public f(String str) {
        this.c = str;
        this.d = new j(this, str);
    }

    public static synchronized f a(String str) {
        f fVar;
        synchronized (f.class) {
            Map<String, f> map = a;
            fVar = map.get(str);
            if (fVar == null) {
                fVar = new f(str);
                map.put(str, fVar);
            }
        }
        return fVar;
    }

    public final synchronized void b() {
        if (com.tencent.qimei.c.a.i()) {
            if (com.tencent.qimei.a.a.a(com.tencent.qimei.i.f.a(this.c).b("s_s_t"))) {
                com.tencent.qimei.k.a.b("SDK_INIT ｜ 策略", "距离上次请求Strategy超过24小时", new Object[0]);
                if (!this.d.b.get()) {
                    com.tencent.qimei.b.a.a().a(this.d);
                }
            } else {
                k kVar = this.d.d;
                if (kVar != null) {
                    ((com.tencent.qimei.o.d) kVar).b();
                }
            }
        }
    }

    public boolean a() {
        boolean z;
        synchronized (b) {
            z = this.e;
        }
        return z;
    }

    public void a(boolean z) {
        synchronized (b) {
            this.e = z;
        }
    }
}
