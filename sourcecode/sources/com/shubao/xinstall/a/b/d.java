package com.shubao.xinstall.a.b;

import android.util.Log;
import com.shubao.xinstall.a.f.s;
import com.xinstall.model.XAppError;
import org.json.JSONObject;

/* loaded from: classes.dex */
public final class d {
    public com.shubao.xinstall.a.e.b a;
    public String b;
    public String c;
    public String d;
    public String e;
    private String f;

    public d() {
    }

    public d(com.shubao.xinstall.a.e.b bVar, String str) {
        this.a = bVar;
        this.b = str;
    }

    public static d a(String str) {
        d dVar = new d();
        try {
            JSONObject jSONObject = new JSONObject(str);
            String optString = jSONObject.optString("retCode", XAppError.REQUEST_FAIL);
            if (optString.equals("0000")) {
                dVar.a = com.shubao.xinstall.a.e.b.SUCCESS;
                dVar.b = "0000";
                if (jSONObject.has("retMsg") && !jSONObject.isNull("retMsg")) {
                    dVar.d = jSONObject.optString("retMsg");
                }
                if (jSONObject.has("map") && !jSONObject.isNull("map")) {
                    JSONObject optJSONObject = jSONObject.optJSONObject("map");
                    dVar.b(optJSONObject.optString("sdkLog"));
                    if (optJSONObject.has("config") && !optJSONObject.isNull("config")) {
                        dVar.e = optJSONObject.optString("config");
                        optJSONObject.remove("config");
                    }
                    dVar.c = optJSONObject.toString();
                }
            } else if (!jSONObject.has("map") || jSONObject.isNull("map")) {
                dVar.a = com.shubao.xinstall.a.e.b.RESP200;
                dVar.b = "-1";
                dVar.d = optString + " : " + jSONObject.optString("retMsg");
            } else {
                JSONObject optJSONObject2 = jSONObject.optJSONObject("map");
                dVar.b(optJSONObject2.optString("sdkLog"));
                if (optJSONObject2.has("config") && !optJSONObject2.isNull("config")) {
                    dVar.a = com.shubao.xinstall.a.e.b.SUCCESS;
                    dVar.b = "-1";
                    dVar.e = optJSONObject2.optString("config");
                }
            }
            return dVar;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void b(String str) {
        this.f = str;
        if (s.a(str)) {
            return;
        }
        Log.i("Xinstall sdkLog", str);
    }
}
