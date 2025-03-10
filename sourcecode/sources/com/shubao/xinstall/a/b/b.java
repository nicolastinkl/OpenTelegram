package com.shubao.xinstall.a.b;

import java.util.IdentityHashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public final class b {
    public String a;
    public String b;
    public String c;
    public String d;
    public Boolean e;
    private IdentityHashMap<String, String> f = com.shubao.xinstall.a.b.a();

    public final JSONObject a() {
        JSONObject jSONObject = new JSONObject();
        try {
            Object obj = this.a;
            if (obj != null) {
                jSONObject.put("occurTime", obj);
            }
            Object obj2 = this.b;
            if (obj2 != null) {
                jSONObject.put("apiUrl", obj2);
            }
            Object obj3 = this.c;
            if (obj3 != null) {
                jSONObject.put("statusCode", obj3);
            }
            Object obj4 = this.d;
            if (obj4 != null) {
                jSONObject.put("errResponse", obj4);
            }
            Object obj5 = this.e;
            if (obj5 != null) {
                jSONObject.put("isDNSFailed", obj5);
            }
            IdentityHashMap<String, String> identityHashMap = this.f;
            if (identityHashMap != null && identityHashMap.size() > 0) {
                JSONObject jSONObject2 = new JSONObject();
                for (Map.Entry<String, String> entry : this.f.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    jSONObject2.put(key, value);
                    System.out.println("key=" + key + "value=" + value);
                }
                jSONObject.put("baseInfo", jSONObject2);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }
}
