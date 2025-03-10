package com.tencent.qmsp.sdk.b;

import android.text.TextUtils;
import com.tencent.qmsp.sdk.b.a;
import com.tencent.qmsp.sdk.f.h;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class g {
    private static g c;
    private String a = "Qp.netImp";
    private f b = null;

    class a implements f {
        a() {
        }

        @Override // com.tencent.qmsp.sdk.b.f
        public void a(int i, String str, int i2, JSONObject jSONObject, e eVar) {
            try {
                JSONObject b = g.this.b(8, jSONObject);
                if (b == null) {
                    eVar.a(163, null);
                    return;
                }
                a.d a = com.tencent.qmsp.sdk.b.a.a(i, str, i2, b);
                if (a.b != 0) {
                    eVar.a(162, a.a);
                } else {
                    JSONObject jSONObject2 = a.a;
                    eVar.a(161, (jSONObject2 == null || !(jSONObject2 instanceof JSONObject)) ? null : g.this.b(9, jSONObject2));
                }
            } catch (Exception e) {
                com.tencent.qmsp.sdk.f.g.b(g.this.a, 0, "send fail！");
                eVar.a(162, null);
                e.printStackTrace();
            }
        }
    }

    private g() {
    }

    private JSONObject a(int i, JSONObject jSONObject) {
        String a2;
        if ((jSONObject instanceof JSONObject) && jSONObject != null) {
            try {
                if (i != 8) {
                    if (i == 9 && (a2 = com.tencent.qmsp.sdk.c.f.a(i, 0, 0, 0, a(jSONObject), "")) != null && !TextUtils.isEmpty(a2)) {
                        return new JSONObject(a2);
                    }
                    return null;
                }
                String a3 = com.tencent.qmsp.sdk.c.f.a(i, 0, 0, 0, jSONObject.toString(), "");
                if (a3 != null && !TextUtils.isEmpty(a3)) {
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put(com.tencent.qmsp.sdk.a.e.a(17), a3);
                    return jSONObject2;
                }
                return null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static g b() {
        if (c == null) {
            synchronized (g.class) {
                if (c == null) {
                    c = new g();
                }
            }
        }
        return c;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public JSONObject b(int i, JSONObject jSONObject) {
        if (!(jSONObject instanceof JSONObject) || jSONObject == null) {
            return null;
        }
        return a(i, jSONObject);
    }

    public String a(JSONObject jSONObject) {
        try {
            return jSONObject.optString(com.tencent.qmsp.sdk.a.e.a(17));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void a() {
        this.b = new a();
    }

    public void a(int i, String str, int i2, JSONObject jSONObject, e eVar) {
        f fVar;
        if (!(jSONObject instanceof JSONObject) || jSONObject == null || eVar == null || (fVar = this.b) == null) {
            com.tencent.qmsp.sdk.f.g.d(this.a, 0, h.a(h.a));
        } else {
            fVar.a(i, str, i2, jSONObject, eVar);
        }
    }
}
