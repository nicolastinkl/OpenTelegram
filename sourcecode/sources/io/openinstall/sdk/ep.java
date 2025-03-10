package io.openinstall.sdk;

import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class ep implements es {
    private int a;
    private String b;
    private String c;
    private String d;

    public static ep a(String str) throws JSONException {
        ep epVar = new ep();
        JSONObject jSONObject = new JSONObject(str);
        if (jSONObject.has("code") && !jSONObject.isNull("code")) {
            epVar.a(jSONObject.optInt("code"));
        }
        if (jSONObject.has("config") && !jSONObject.isNull("config")) {
            epVar.d(jSONObject.optString("config"));
        }
        if (jSONObject.has("body") && !jSONObject.isNull("body")) {
            epVar.c(jSONObject.optString("body"));
        }
        if (jSONObject.has("msg") && !jSONObject.isNull("msg")) {
            epVar.b(jSONObject.optString("msg"));
        }
        return epVar;
    }

    public int a() {
        return this.a;
    }

    public void a(int i) {
        this.a = i;
    }

    public String b() {
        return this.c;
    }

    public void b(String str) {
        this.c = str;
    }

    public String c() {
        return this.b;
    }

    public void c(String str) {
        this.b = str;
    }

    public String d() {
        return this.d;
    }

    public void d(String str) {
        this.d = str;
    }

    @Override // io.openinstall.sdk.es
    public boolean e() {
        return false;
    }

    @Override // io.openinstall.sdk.es
    public String f() {
        return this.c;
    }
}
