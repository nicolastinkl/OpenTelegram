package io.openinstall.sdk;

import android.text.TextUtils;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class ba {
    private Boolean a;
    private Boolean b;
    private Boolean c;
    private Boolean d;
    private long e = 3600;
    private String f;

    public static ba b(String str) {
        ba baVar = new ba();
        if (TextUtils.isEmpty(str)) {
            return baVar;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("wakeupStatsEnabled")) {
                baVar.a(Boolean.valueOf(jSONObject.optBoolean("wakeupStatsEnabled", true)));
            }
            if (jSONObject.has("aliveStatsEnabled")) {
                baVar.c(Boolean.valueOf(jSONObject.optBoolean("aliveStatsEnabled", true)));
            }
            if (jSONObject.has("registerStatsEnabled")) {
                baVar.b(Boolean.valueOf(jSONObject.optBoolean("registerStatsEnabled", true)));
            }
            if (jSONObject.has("eventStatsEnabled")) {
                baVar.c(Boolean.valueOf(jSONObject.optBoolean("eventStatsEnabled", true)));
            }
            if (jSONObject.has("reportPeriod")) {
                baVar.a(jSONObject.optLong("reportPeriod"));
            }
            if (jSONObject.has("installId")) {
                baVar.a(jSONObject.optString("installId"));
            }
        } catch (JSONException unused) {
        }
        return baVar;
    }

    private boolean d(Boolean bool) {
        if (bool == null) {
            return true;
        }
        return bool.booleanValue();
    }

    public Boolean a() {
        return this.a;
    }

    public void a(long j) {
        this.e = j;
    }

    public void a(ba baVar) {
        this.a = baVar.a();
        this.b = baVar.e();
        this.c = baVar.c();
        this.d = baVar.e();
        this.e = baVar.g();
        this.f = baVar.h();
    }

    public void a(Boolean bool) {
        this.a = bool;
    }

    public void a(String str) {
        this.f = str;
    }

    public void b(Boolean bool) {
        this.c = bool;
    }

    public boolean b() {
        return d(this.a);
    }

    public Boolean c() {
        return this.c;
    }

    public void c(Boolean bool) {
        this.d = bool;
    }

    public boolean d() {
        return d(this.c);
    }

    public Boolean e() {
        return this.d;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ba baVar = (ba) obj;
        if (this.e != baVar.e) {
            return false;
        }
        Boolean bool = this.a;
        if (bool == null ? baVar.a != null : !bool.equals(baVar.a)) {
            return false;
        }
        Boolean bool2 = this.b;
        if (bool2 == null ? baVar.b != null : !bool2.equals(baVar.b)) {
            return false;
        }
        Boolean bool3 = this.c;
        if (bool3 == null ? baVar.c != null : !bool3.equals(baVar.c)) {
            return false;
        }
        Boolean bool4 = this.d;
        if (bool4 == null ? baVar.d != null : !bool4.equals(baVar.d)) {
            return false;
        }
        String str = this.f;
        String str2 = baVar.f;
        return str != null ? str.equals(str2) : str2 == null;
    }

    public boolean f() {
        return d(this.d);
    }

    public long g() {
        return this.e;
    }

    public String h() {
        return this.f;
    }

    public int hashCode() {
        Boolean bool = this.a;
        int hashCode = (bool != null ? bool.hashCode() : 0) * 31;
        Boolean bool2 = this.b;
        int hashCode2 = (hashCode + (bool2 != null ? bool2.hashCode() : 0)) * 31;
        Boolean bool3 = this.c;
        int hashCode3 = (hashCode2 + (bool3 != null ? bool3.hashCode() : 0)) * 31;
        Boolean bool4 = this.d;
        int hashCode4 = (hashCode3 + (bool4 != null ? bool4.hashCode() : 0)) * 31;
        long j = this.e;
        int i = (hashCode4 + ((int) (j ^ (j >>> 32)))) * 31;
        String str = this.f;
        return i + (str != null ? str.hashCode() : 0);
    }

    public String i() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("wakeupStatsEnabled", this.a);
            jSONObject.put("registerStatsEnabled", this.c);
            jSONObject.put("eventStatsEnabled", this.d);
            jSONObject.put("reportPeriod", this.e);
            jSONObject.put("installId", this.f);
        } catch (JSONException unused) {
        }
        return jSONObject.toString();
    }
}
