package com.shubao.xinstall.a.b;

import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public final class c {
    public boolean a;
    public int b;
    public String c;
    public Object d;
    public Object e;
    public long f;
    public long g;

    public c() {
        this.a = false;
    }

    public c(int i, String str, Object obj, Object obj2) {
        this.a = false;
        this.b = i;
        this.c = str;
        this.d = obj;
        this.e = obj2;
        this.f = System.currentTimeMillis() / 1000;
    }

    public c(long j) {
        this.a = false;
        this.f = j;
    }

    public final JSONObject a() {
        JSONObject jSONObject = new JSONObject();
        try {
            try {
                jSONObject.put("type", this.b);
                jSONObject.put("name", this.c);
                jSONObject.put("value", this.d);
                jSONObject.put("value2", this.e);
                jSONObject.put("timeSeconds", this.f);
                jSONObject.put("duration", this.g);
                return jSONObject;
            } catch (JSONException e) {
                e.printStackTrace();
                return jSONObject;
            }
        } catch (Throwable unused) {
            return jSONObject;
        }
    }
}
