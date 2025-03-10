package j$.time.format;

import j$.time.Instant;
import j$.time.ZoneId;
import j$.time.ZoneOffset;
import j$.time.temporal.TemporalAccessor;
import j$.util.concurrent.ConcurrentHashMap;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TimeZone;

/* loaded from: classes2.dex */
final class t extends s {
    private static final Map d = new ConcurrentHashMap();
    private final B c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    t(B b, Set set) {
        super(j$.time.temporal.q.a, "ZoneText(" + b + ")");
        int i = j$.time.temporal.t.a;
        new HashMap();
        new HashMap();
        Objects.requireNonNull(b, "textStyle");
        this.c = b;
    }

    @Override // j$.time.format.s, j$.time.format.h
    public boolean a(v vVar, StringBuilder sb) {
        String[] strArr;
        int i = j$.time.temporal.t.a;
        ZoneId zoneId = (ZoneId) vVar.f(j$.time.temporal.m.a);
        if (zoneId == null) {
            return false;
        }
        String l = zoneId.l();
        if (!(zoneId instanceof ZoneOffset)) {
            TemporalAccessor d2 = vVar.d();
            char c = d2.j(j$.time.temporal.a.INSTANT_SECONDS) ? zoneId.m().h(Instant.m(d2)) ? (char) 1 : (char) 0 : (char) 2;
            Locale c2 = vVar.c();
            String str = null;
            Map map = null;
            if (this.c != B.NARROW) {
                Map map2 = d;
                SoftReference softReference = (SoftReference) map2.get(l);
                if (softReference == null || (map = (Map) softReference.get()) == null || (strArr = (String[]) map.get(c2)) == null) {
                    TimeZone timeZone = TimeZone.getTimeZone(l);
                    String[] strArr2 = {l, timeZone.getDisplayName(false, 1, c2), timeZone.getDisplayName(false, 0, c2), timeZone.getDisplayName(true, 1, c2), timeZone.getDisplayName(true, 0, c2), l, l};
                    if (map == null) {
                        map = new ConcurrentHashMap();
                    }
                    map.put(c2, strArr2);
                    map2.put(l, new SoftReference(map));
                    strArr = strArr2;
                }
                int a = this.c.a();
                str = c != 0 ? c != 1 ? strArr[a + 5] : strArr[a + 3] : strArr[a + 1];
            }
            if (str != null) {
                l = str;
            }
        }
        sb.append(l);
        return true;
    }
}
