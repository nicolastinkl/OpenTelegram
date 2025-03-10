package com.shubao.xinstall.a.b;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public final class a {
    public Boolean a;
    public Boolean b;
    public Integer c;
    public Boolean d;
    public Long e;
    public String f;
    public ArrayList<com.shubao.xinstall.a.d.a> g = new ArrayList<>();
    private Boolean h;
    private Boolean i;

    public static a a(String str) {
        a aVar = new a();
        if (TextUtils.isEmpty(str)) {
            return aVar;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("wakeupStatsEnabled")) {
                aVar.h = Boolean.valueOf(jSONObject.optBoolean("wakeupStatsEnabled", false));
            }
            if (jSONObject.has("aliveStatsEnabled")) {
                aVar.a = Boolean.valueOf(jSONObject.optBoolean("aliveStatsEnabled", false));
            }
            if (jSONObject.has("registerStatsEnabled")) {
                aVar.b = Boolean.valueOf(jSONObject.optBoolean("registerStatsEnabled", false));
            }
            if (jSONObject.has("eventStatsEnabled")) {
                aVar.i = Boolean.valueOf(jSONObject.optBoolean("eventStatsEnabled", false));
            }
            if (jSONObject.has("isProhibit")) {
                aVar.d = Boolean.valueOf(jSONObject.optBoolean("isProhibit", false));
            }
            if (jSONObject.has("reportPeriod")) {
                aVar.e = Long.valueOf(jSONObject.optLong("reportPeriod"));
            }
            if (jSONObject.has("xinstallId")) {
                aVar.f = jSONObject.optString("xinstallId");
            }
            if (jSONObject.has("upErrorLogNum")) {
                aVar.c = Integer.valueOf(jSONObject.optInt("upErrorLogNum"));
            }
        } catch (JSONException unused) {
        }
        return aVar;
    }

    public static boolean a(Boolean bool) {
        return bool != null && bool.booleanValue();
    }

    public final Boolean a() {
        return Boolean.valueOf(a(this.h));
    }

    public final void a(a aVar, boolean z) {
        this.h = aVar.a();
        this.a = Boolean.valueOf(a(aVar.a));
        this.b = Boolean.valueOf(a(aVar.b));
        this.i = aVar.b();
        this.e = aVar.e;
        this.d = aVar.c();
        this.c = aVar.c;
        if (z) {
            this.f = aVar.f;
        }
    }

    public final Boolean b() {
        return Boolean.valueOf(a(this.i));
    }

    public final Boolean c() {
        return Boolean.valueOf(a(this.d));
    }

    public final void d() {
        Iterator<com.shubao.xinstall.a.d.a> it = this.g.iterator();
        while (it.hasNext()) {
            it.next().a(this);
        }
    }

    public final int hashCode() {
        Boolean bool = this.h;
        int hashCode = (bool != null ? bool.hashCode() : 0) * 31;
        Boolean bool2 = this.a;
        int hashCode2 = (hashCode + (bool2 != null ? bool2.hashCode() : 0)) * 31;
        Boolean bool3 = this.b;
        int hashCode3 = (hashCode2 + (bool3 != null ? bool3.hashCode() : 0)) * 31;
        Boolean bool4 = this.i;
        int hashCode4 = (hashCode3 + (bool4 != null ? bool4.hashCode() : 0)) * 31;
        Boolean bool5 = this.d;
        int hashCode5 = (hashCode4 + (bool5 != null ? bool5.hashCode() : 0)) * 31;
        Long l = this.e;
        int hashCode6 = (hashCode5 + (l != null ? l.hashCode() : 0)) * 31;
        Integer num = this.c;
        int hashCode7 = (hashCode6 + (num != null ? num.hashCode() : 0)) * 31;
        String str = this.f;
        return hashCode7 + (str != null ? str.hashCode() : 0);
    }

    public final String toString() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("wakeupStatsEnabled", this.h);
            jSONObject.put("aliveStatsEnabled", this.a);
            jSONObject.put("registerStatsEnabled", this.b);
            jSONObject.put("eventStatsEnabled", this.i);
            jSONObject.put("reportPeriod", this.e);
            jSONObject.put("isProhibit", this.d);
            jSONObject.put("xinstallId", this.f);
            jSONObject.put("upErrorLogNum", this.c);
        } catch (JSONException unused) {
        }
        return jSONObject.toString();
    }
}
