package com.tencent.qimei.o;

import com.tencent.qimei.o.d;
import com.tencent.qimei.o.m;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: AsyInitTask.java */
/* loaded from: classes.dex */
public class c implements com.tencent.qimei.c.d {
    public final /* synthetic */ d a;

    public c(d dVar) {
        this.a = dVar;
    }

    @Override // com.tencent.qimei.c.d
    public void a(int i) {
        String str;
        d.a aVar;
        String str2;
        str = this.a.c;
        boolean z = false;
        com.tencent.qimei.k.a.b("SDK_INIT", "OD 初始化完成(appKey: %s)，结果:%s", str, Integer.valueOf(i));
        aVar = this.a.j;
        u uVar = (u) aVar;
        if (uVar.d()) {
            w a = w.a(uVar.g);
            String b = a.b();
            if (b.isEmpty()) {
                a.a();
            } else {
                long b2 = com.tencent.qimei.i.f.a(a.b).b("t_s_t");
                if (0 != b2 && com.tencent.qimei.c.a.c() > b2) {
                    z = true;
                }
                if (z) {
                    try {
                        JSONObject jSONObject = new JSONObject(b);
                        m.a aVar2 = m.a.KEY_ENCRYPT_KEY;
                        String optString = jSONObject.optString(aVar2.W);
                        m.a aVar3 = m.a.KEY_PARAMS;
                        String optString2 = jSONObject.optString(aVar3.W);
                        JSONObject jSONObject2 = new JSONObject();
                        jSONObject2.put(m.a.KEY_PARAMS_APP_KEY.W, a.b);
                        jSONObject2.put(aVar2.W, optString);
                        jSONObject2.put(aVar3.W, optString2);
                        str2 = jSONObject2.toString();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        str2 = "";
                    }
                    a.c(m.a.a(com.tencent.qimei.j.a.a(), a.b, com.tencent.qimei.a.a.i(a.b), str2));
                } else if (com.tencent.qimei.a.a.a(com.tencent.qimei.i.f.a(a.b).b("t_s_t"))) {
                    a.a();
                }
            }
        }
        uVar.e();
        uVar.getQimei(new t(uVar));
    }
}
