package com.tencent.qimei.n;

import android.content.Context;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: Reporter.java */
/* loaded from: classes.dex */
public class i implements a {
    public static volatile i a;
    public f b;

    public void a(Context context) {
        this.b = new f();
    }

    public void b(String str, Map<String, Object> map, String str2, String str3) {
        boolean contains;
        if (com.tencent.qimei.v.d.a(str3).B() && com.tencent.qimei.c.a.i()) {
            f fVar = this.b;
            if (fVar.a(str2)) {
                contains = false;
            } else {
                contains = fVar.a.contains(str3 + str2);
            }
            if (contains) {
                return;
            }
            com.tencent.qimei.b.a.a().a(new g(this, str, map, str2, str3));
        }
    }

    public static synchronized i a() {
        i iVar;
        synchronized (i.class) {
            if (a == null) {
                synchronized (i.class) {
                    if (a == null) {
                        a = new i();
                    }
                }
            }
            iVar = a;
        }
        return iVar;
    }

    public final void a(String str, Map<String, Object> map, String str2, String str3) {
        String str4;
        String str5 = "";
        d dVar = d.a;
        String a2 = dVar.a(com.tencent.qimei.v.d.a(str3).D(), str);
        String c = com.tencent.qimei.l.d.a(str3).c();
        try {
            JSONObject jSONObject = new JSONObject();
            dVar.a(jSONObject, str3);
            com.tencent.qimei.c.c j = com.tencent.qimei.c.c.j();
            jSONObject.put(e.REPORT_DATA_IP.K, j.k());
            jSONObject.put(e.REPORT_DATA_NET_TYPE.K, j.m());
            jSONObject.put(e.REPORT_AD.K, c);
            if (map != null) {
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    jSONObject.put(entry.getKey(), entry.getValue());
                }
            }
            try {
                str4 = com.tencent.qimei.a.a.b(jSONObject.toString(), "dZdcQik9lkNsvFYx");
            } catch (Exception e) {
                e.printStackTrace();
                str4 = "";
            }
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put(e.REPORT_TYPE.K, str2);
            jSONObject2.put(e.REPORT_DATA.K, str4);
            str5 = jSONObject2.toString();
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        com.tencent.qimei.a.a.a(a2, str5, new h(this));
        f fVar = this.b;
        if (fVar.a(str2)) {
            return;
        }
        fVar.a.add(str3 + str2);
    }

    public c a(String str, Object obj) {
        c cVar = new c();
        cVar.b.put(str, obj);
        return cVar;
    }
}
