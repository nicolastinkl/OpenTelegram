package com.shubao.xinstall.a.b;

import android.text.TextUtils;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public final class e {
    public String a;
    public String b;
    private int c;

    public static e a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        e eVar = new e();
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("xkText")) {
                eVar.a = jSONObject.optString("xkText");
            }
            if (jSONObject.has("xkHtml")) {
                eVar.b = jSONObject.optString("xkHtml");
            }
            if (jSONObject.has("xkType")) {
                eVar.c = jSONObject.optInt("xkType");
            }
            return eVar;
        } catch (JSONException unused) {
            return null;
        }
    }

    public static String a(e eVar) {
        int i;
        JSONObject jSONObject = new JSONObject();
        if (eVar == null) {
            i = 0;
        } else {
            try {
                jSONObject.put("xkText", eVar.a);
                jSONObject.put("xkHtml", eVar.b);
                i = eVar.c;
            } catch (JSONException unused) {
            }
        }
        jSONObject.put("xkType", i);
        return jSONObject.toString();
    }

    public final void a(int i) {
        this.c = i | this.c;
    }

    public final boolean b(int i) {
        return (i & this.c) != 0;
    }
}
