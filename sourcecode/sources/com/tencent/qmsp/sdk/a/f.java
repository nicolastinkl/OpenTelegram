package com.tencent.qmsp.sdk.a;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class f {
    private static String a = "Qp.RPT";

    static class a implements Runnable {
        final /* synthetic */ int a;
        final /* synthetic */ JSONObject b;

        a(int i, JSONObject jSONObject) {
            this.a = i;
            this.b = jSONObject;
        }

        @Override // java.lang.Runnable
        public void run() {
            f.b(this.a, this.b);
        }
    }

    static class b implements com.tencent.qmsp.sdk.b.e {
        b() {
        }

        @Override // com.tencent.qmsp.sdk.b.e
        public void a(int i, JSONObject jSONObject) {
            if (i == 161) {
                com.tencent.qmsp.sdk.f.g.a(f.a, 1, String.format("ret: %d", 161));
            }
        }
    }

    public static void a(String str, int i) {
        if (str != null) {
            JSONObject jSONObject = new JSONObject();
            JSONArray jSONArray = new JSONArray();
            JSONObject jSONObject2 = new JSONObject();
            JSONObject a2 = d.a(3);
            if (a(a2)) {
                try {
                    jSONObject2.put(e.a(15), a2);
                    jSONObject2.put(e.a(16), new JSONObject().put("log", str));
                    jSONArray.put(jSONObject2);
                    jSONObject.put("arr", jSONArray);
                    com.tencent.qmsp.sdk.f.g.a(a, 1, jSONObject2.toString());
                    com.tencent.qmsp.sdk.app.b.e().a(new a(i, jSONObject));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected static boolean a(JSONObject jSONObject) {
        try {
            jSONObject.put(e.a(12), c.b());
            jSONObject.put(e.a(13), c.f());
            jSONObject.put(e.a(14), c.a());
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(int i, JSONObject jSONObject) {
        String appID;
        if (jSONObject == null) {
            return;
        }
        com.tencent.qmsp.sdk.f.g.a(a, 0, "Rpt: " + jSONObject);
        com.tencent.qmsp.sdk.b.g b2 = com.tencent.qmsp.sdk.b.g.b();
        appID = com.tencent.qmsp.sdk.app.a.getAppID();
        b2.a(3, appID, i, jSONObject, new b());
    }
}
