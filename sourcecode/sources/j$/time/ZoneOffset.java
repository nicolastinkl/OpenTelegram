package j$.time;

import j$.time.temporal.Temporal;
import j$.time.temporal.TemporalAccessor;
import j$.time.temporal.q;
import j$.time.temporal.t;
import j$.time.temporal.u;
import j$.time.temporal.w;
import j$.time.temporal.x;
import j$.util.concurrent.ConcurrentHashMap;
import java.util.Objects;
import java.util.concurrent.ConcurrentMap;

/* JADX WARN: Unexpected interfaces in signature: [java.io.Serializable] */
/* loaded from: classes2.dex */
public final class ZoneOffset extends ZoneId implements TemporalAccessor, j$.time.temporal.k, Comparable<ZoneOffset> {
    private final int a;
    private final transient String b;
    private static final ConcurrentMap c = new ConcurrentHashMap(16, 0.75f, 4);
    private static final ConcurrentMap d = new ConcurrentHashMap(16, 0.75f, 4);
    public static final ZoneOffset UTC = p(0);
    public static final ZoneOffset e = p(-64800);
    public static final ZoneOffset f = p(64800);

    private ZoneOffset(int i) {
        String sb;
        this.a = i;
        if (i == 0) {
            sb = "Z";
        } else {
            int abs = Math.abs(i);
            StringBuilder sb2 = new StringBuilder();
            int i2 = abs / 3600;
            int i3 = (abs / 60) % 60;
            sb2.append(i < 0 ? "-" : "+");
            sb2.append(i2 < 10 ? "0" : "");
            sb2.append(i2);
            sb2.append(i3 < 10 ? ":0" : ":");
            sb2.append(i3);
            int i4 = abs % 60;
            if (i4 != 0) {
                sb2.append(i4 >= 10 ? ":" : ":0");
                sb2.append(i4);
            }
            sb = sb2.toString();
        }
        this.b = sb;
    }

    public static ZoneOffset n(TemporalAccessor temporalAccessor) {
        Objects.requireNonNull(temporalAccessor, "temporal");
        int i = t.a;
        ZoneOffset zoneOffset = (ZoneOffset) temporalAccessor.g(j$.time.temporal.p.a);
        if (zoneOffset != null) {
            return zoneOffset;
        }
        throw new b("Unable to obtain ZoneOffset from TemporalAccessor: " + temporalAccessor + " of type " + temporalAccessor.getClass().getName());
    }

    public static ZoneOffset p(int i) {
        if (i < -64800 || i > 64800) {
            throw new b("Zone offset not in valid range: -18:00 to +18:00");
        }
        if (i % 900 != 0) {
            return new ZoneOffset(i);
        }
        Integer valueOf = Integer.valueOf(i);
        ConcurrentMap concurrentMap = c;
        ZoneOffset zoneOffset = (ZoneOffset) concurrentMap.get(valueOf);
        if (zoneOffset != null) {
            return zoneOffset;
        }
        concurrentMap.putIfAbsent(valueOf, new ZoneOffset(i));
        ZoneOffset zoneOffset2 = (ZoneOffset) concurrentMap.get(valueOf);
        d.putIfAbsent(zoneOffset2.b, zoneOffset2);
        return zoneOffset2;
    }

    @Override // j$.time.temporal.TemporalAccessor
    public int c(j$.time.temporal.l lVar) {
        if (lVar == j$.time.temporal.a.OFFSET_SECONDS) {
            return this.a;
        }
        if (!(lVar instanceof j$.time.temporal.a)) {
            return j$.lang.d.d(this, lVar).a(e(lVar), lVar);
        }
        throw new w("Unsupported field: " + lVar);
    }

    @Override // java.lang.Comparable
    public int compareTo(ZoneOffset zoneOffset) {
        return zoneOffset.a - this.a;
    }

    @Override // j$.time.temporal.TemporalAccessor
    public x d(j$.time.temporal.l lVar) {
        return j$.lang.d.d(this, lVar);
    }

    @Override // j$.time.temporal.TemporalAccessor
    public long e(j$.time.temporal.l lVar) {
        if (lVar == j$.time.temporal.a.OFFSET_SECONDS) {
            return this.a;
        }
        if (!(lVar instanceof j$.time.temporal.a)) {
            return lVar.d(this);
        }
        throw new w("Unsupported field: " + lVar);
    }

    @Override // j$.time.ZoneId
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof ZoneOffset) && this.a == ((ZoneOffset) obj).a;
    }

    @Override // j$.time.temporal.TemporalAccessor
    public Object g(u uVar) {
        int i = t.a;
        return (uVar == j$.time.temporal.p.a || uVar == q.a) ? this : j$.lang.d.c(this, uVar);
    }

    @Override // j$.time.temporal.k
    public Temporal h(Temporal temporal) {
        return temporal.b(j$.time.temporal.a.OFFSET_SECONDS, this.a);
    }

    @Override // j$.time.ZoneId
    public int hashCode() {
        return this.a;
    }

    @Override // j$.time.temporal.TemporalAccessor
    public boolean j(j$.time.temporal.l lVar) {
        return lVar instanceof j$.time.temporal.a ? lVar == j$.time.temporal.a.OFFSET_SECONDS : lVar != null && lVar.e(this);
    }

    @Override // j$.time.ZoneId
    public String l() {
        return this.b;
    }

    @Override // j$.time.ZoneId
    public j$.time.zone.c m() {
        return j$.time.zone.c.j(this);
    }

    public int o() {
        return this.a;
    }

    @Override // j$.time.ZoneId
    public String toString() {
        return this.b;
    }
}
