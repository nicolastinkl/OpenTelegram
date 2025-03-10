package com.tencent.qimei.q;

import com.tencent.qimei.i.f;
import com.tencent.qimei.sdk.Qimei;
import j$.util.concurrent.ConcurrentHashMap;
import java.util.Map;

/* compiled from: CloneDetection.java */
/* loaded from: classes.dex */
public class a {
    public static final String a = "a";
    public static final Map<String, a> b = new ConcurrentHashMap();
    public final String c;
    public boolean d = false;
    public boolean e = false;
    public Qimei f;
    public boolean g;

    public a(String str) {
        this.c = str;
    }

    public static synchronized a a(String str) {
        a aVar;
        synchronized (a.class) {
            Map<String, a> map = b;
            aVar = map.get(str);
            if (aVar == null) {
                aVar = new a(str);
                map.put(str, aVar);
            }
        }
        return aVar;
    }

    public boolean a() {
        boolean z;
        if (this.g) {
            z = this.d;
        } else {
            long b2 = f.a(this.c).b("q_s_t");
            if (b2 != 0) {
                long c = com.tencent.qimei.c.a.c();
                this.g = true;
                if (c > b2) {
                    z = true;
                }
            }
            z = false;
        }
        this.d = z;
        return z || this.e;
    }
}
