package com.tencent.qimei.o;

import com.tencent.qimei.o.m;
import com.tencent.qimei.sdk.Qimei;
import j$.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: TokenHolder.java */
/* loaded from: classes.dex */
public class w {
    public static final Map<String, w> a = new ConcurrentHashMap();
    public final String b;
    public final Object c = new Object();

    public w(String str) {
        this.b = str;
    }

    public final String b() {
        String c;
        synchronized (this.c) {
            c = com.tencent.qimei.i.f.a(this.b).c("tn");
        }
        return c;
    }

    public final void c(String str) {
        com.tencent.qimei.b.a.a().a(new v(this, str));
    }

    public static synchronized w a(String str) {
        w wVar;
        synchronized (w.class) {
            Map<String, w> map = a;
            wVar = map.get(str);
            if (wVar == null) {
                wVar = new w(str);
                map.put(str, wVar);
            }
        }
        return wVar;
    }

    public final void b(String str) {
        if (com.tencent.qimei.c.a.i()) {
            synchronized (this.c) {
                com.tencent.qimei.i.f.a(this.b).a("tn", str);
                com.tencent.qimei.i.f.a(this.b).a("t_s_t", System.currentTimeMillis());
            }
        }
    }

    public final String a() {
        Qimei i = com.tencent.qimei.a.a.i(this.b);
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(m.a.KEY_PARAMS_APP_KEY.W, this.b);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String a2 = m.a.a(com.tencent.qimei.j.a.a(), this.b, i, jSONObject.toString());
        com.tencent.qimei.b.a.a().a(new v(this, a2));
        return a2;
    }
}
