package j$.time;

import j$.time.temporal.TemporalAccessor;
import j$.time.temporal.q;
import j$.time.temporal.t;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;

/* loaded from: classes2.dex */
public abstract class ZoneId implements Serializable {
    static {
        HashMap hashMap = new HashMap(64);
        hashMap.put("ACT", "Australia/Darwin");
        hashMap.put("AET", "Australia/Sydney");
        hashMap.put("AGT", "America/Argentina/Buenos_Aires");
        hashMap.put("ART", "Africa/Cairo");
        hashMap.put("AST", "America/Anchorage");
        hashMap.put("BET", "America/Sao_Paulo");
        hashMap.put("BST", "Asia/Dhaka");
        hashMap.put("CAT", "Africa/Harare");
        hashMap.put("CNT", "America/St_Johns");
        hashMap.put("CST", "America/Chicago");
        hashMap.put("CTT", "Asia/Shanghai");
        hashMap.put("EAT", "Africa/Addis_Ababa");
        hashMap.put("ECT", "Europe/Paris");
        hashMap.put("IET", "America/Indiana/Indianapolis");
        hashMap.put("IST", "Asia/Kolkata");
        hashMap.put("JST", "Asia/Tokyo");
        hashMap.put("MIT", "Pacific/Apia");
        hashMap.put("NET", "Asia/Yerevan");
        hashMap.put("NST", "Pacific/Auckland");
        hashMap.put("PLT", "Asia/Karachi");
        hashMap.put("PNT", "America/Phoenix");
        hashMap.put("PRT", "America/Puerto_Rico");
        hashMap.put("PST", "America/Los_Angeles");
        hashMap.put("SST", "Pacific/Guadalcanal");
        hashMap.put("VST", "Asia/Ho_Chi_Minh");
        hashMap.put("EST", "-05:00");
        hashMap.put("MST", "-07:00");
        hashMap.put("HST", "-10:00");
        Collections.unmodifiableMap(hashMap);
    }

    ZoneId() {
        if (getClass() != ZoneOffset.class && getClass() != n.class) {
            throw new AssertionError("Invalid subclass");
        }
    }

    public static ZoneId k(TemporalAccessor temporalAccessor) {
        int i = t.a;
        ZoneId zoneId = (ZoneId) temporalAccessor.g(q.a);
        if (zoneId != null) {
            return zoneId;
        }
        throw new b("Unable to obtain ZoneId from TemporalAccessor: " + temporalAccessor + " of type " + temporalAccessor.getClass().getName());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ZoneId) {
            return l().equals(((ZoneId) obj).l());
        }
        return false;
    }

    public int hashCode() {
        return l().hashCode();
    }

    public abstract String l();

    public abstract j$.time.zone.c m();

    public String toString() {
        return l();
    }
}
