package j$.time;

import j$.time.temporal.Temporal;
import j$.time.temporal.q;
import j$.time.temporal.r;
import j$.time.temporal.s;
import j$.time.temporal.t;
import j$.time.temporal.u;
import j$.time.temporal.v;
import j$.time.temporal.w;
import j$.time.temporal.x;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class p implements Temporal, j$.time.chrono.f, Serializable {
    private final g a;
    private final ZoneOffset b;
    private final ZoneId c;

    private p(g gVar, ZoneOffset zoneOffset, ZoneId zoneId) {
        this.a = gVar;
        this.b = zoneOffset;
        this.c = zoneId;
    }

    private static p h(long j, int i, ZoneId zoneId) {
        ZoneOffset d = zoneId.m().d(Instant.p(j, i));
        return new p(g.t(j, i, d), d, zoneId);
    }

    public static p n(Instant instant, ZoneId zoneId) {
        Objects.requireNonNull(instant, "instant");
        Objects.requireNonNull(zoneId, "zone");
        return h(instant.getEpochSecond(), instant.n(), zoneId);
    }

    public static p o(g gVar, ZoneId zoneId, ZoneOffset zoneOffset) {
        Objects.requireNonNull(gVar, "localDateTime");
        Objects.requireNonNull(zoneId, "zone");
        if (zoneId instanceof ZoneOffset) {
            return new p(gVar, (ZoneOffset) zoneId, zoneId);
        }
        j$.time.zone.c m = zoneId.m();
        List g = m.g(gVar);
        if (g.size() == 1) {
            zoneOffset = (ZoneOffset) g.get(0);
        } else if (g.size() == 0) {
            j$.time.zone.a f = m.f(gVar);
            gVar = gVar.x(f.c().getSeconds());
            zoneOffset = f.e();
        } else if (zoneOffset == null || !g.contains(zoneOffset)) {
            zoneOffset = (ZoneOffset) g.get(0);
            Objects.requireNonNull(zoneOffset, "offset");
        }
        return new p(gVar, zoneOffset, zoneId);
    }

    private p p(g gVar) {
        return o(gVar, this.c, this.b);
    }

    private p q(ZoneOffset zoneOffset) {
        return (zoneOffset.equals(this.b) || !this.c.m().g(this.a).contains(zoneOffset)) ? this : new p(this.a, zoneOffset, this.c);
    }

    @Override // j$.time.temporal.Temporal
    public Temporal a(j$.time.temporal.k kVar) {
        return o(g.s((e) kVar, this.a.C()), this.c, this.b);
    }

    @Override // j$.time.temporal.Temporal
    public Temporal b(j$.time.temporal.l lVar, long j) {
        if (!(lVar instanceof j$.time.temporal.a)) {
            return (p) lVar.f(this, j);
        }
        j$.time.temporal.a aVar = (j$.time.temporal.a) lVar;
        int i = o.a[aVar.ordinal()];
        return i != 1 ? i != 2 ? p(this.a.b(lVar, j)) : q(ZoneOffset.p(aVar.h(j))) : h(j, this.a.n(), this.c);
    }

    @Override // j$.time.temporal.TemporalAccessor
    public int c(j$.time.temporal.l lVar) {
        if (!(lVar instanceof j$.time.temporal.a)) {
            return j$.time.chrono.d.a(this, lVar);
        }
        int i = o.a[((j$.time.temporal.a) lVar).ordinal()];
        if (i != 1) {
            return i != 2 ? this.a.c(lVar) : this.b.o();
        }
        throw new w("Invalid field 'InstantSeconds' for get() method, use getLong() instead");
    }

    @Override // java.lang.Comparable
    public int compareTo(Object obj) {
        p pVar = (p) ((j$.time.chrono.f) obj);
        int compare = Long.compare(r(), pVar.r());
        if (compare != 0) {
            return compare;
        }
        int o = v().o() - pVar.v().o();
        if (o != 0) {
            return o;
        }
        int compareTo = ((g) u()).compareTo(pVar.u());
        if (compareTo != 0) {
            return compareTo;
        }
        int compareTo2 = m().l().compareTo(pVar.m().l());
        if (compareTo2 != 0) {
            return compareTo2;
        }
        k();
        j$.time.chrono.h hVar = j$.time.chrono.h.a;
        pVar.k();
        return 0;
    }

    @Override // j$.time.temporal.TemporalAccessor
    public x d(j$.time.temporal.l lVar) {
        return lVar instanceof j$.time.temporal.a ? (lVar == j$.time.temporal.a.INSTANT_SECONDS || lVar == j$.time.temporal.a.OFFSET_SECONDS) ? lVar.c() : this.a.d(lVar) : lVar.g(this);
    }

    @Override // j$.time.temporal.TemporalAccessor
    public long e(j$.time.temporal.l lVar) {
        if (!(lVar instanceof j$.time.temporal.a)) {
            return lVar.d(this);
        }
        int i = o.a[((j$.time.temporal.a) lVar).ordinal()];
        return i != 1 ? i != 2 ? this.a.e(lVar) : this.b.o() : r();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof p)) {
            return false;
        }
        p pVar = (p) obj;
        return this.a.equals(pVar.a) && this.b.equals(pVar.b) && this.c.equals(pVar.c);
    }

    @Override // j$.time.temporal.Temporal
    public Temporal f(long j, v vVar) {
        if (!(vVar instanceof j$.time.temporal.b)) {
            return (p) vVar.d(this, j);
        }
        if (vVar.b()) {
            return p(this.a.f(j, vVar));
        }
        g f = this.a.f(j, vVar);
        ZoneOffset zoneOffset = this.b;
        ZoneId zoneId = this.c;
        Objects.requireNonNull(f, "localDateTime");
        Objects.requireNonNull(zoneOffset, "offset");
        Objects.requireNonNull(zoneId, "zone");
        return zoneId.m().g(f).contains(zoneOffset) ? new p(f, zoneOffset, zoneId) : h(f.z(zoneOffset), f.n(), zoneId);
    }

    @Override // j$.time.temporal.TemporalAccessor
    public Object g(u uVar) {
        int i = t.a;
        if (uVar == r.a) {
            return this.a.A();
        }
        if (uVar == q.a || uVar == j$.time.temporal.m.a) {
            return this.c;
        }
        if (uVar == j$.time.temporal.p.a) {
            return this.b;
        }
        if (uVar == s.a) {
            return v();
        }
        if (uVar != j$.time.temporal.n.a) {
            return uVar == j$.time.temporal.o.a ? j$.time.temporal.b.NANOS : uVar.a(this);
        }
        k();
        return j$.time.chrono.h.a;
    }

    public int hashCode() {
        return (this.a.hashCode() ^ this.b.hashCode()) ^ Integer.rotateLeft(this.c.hashCode(), 3);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v19, types: [j$.time.p] */
    /* JADX WARN: Type inference failed for: r5v22 */
    /* JADX WARN: Type inference failed for: r5v23 */
    /* JADX WARN: Type inference failed for: r5v6 */
    @Override // j$.time.temporal.Temporal
    public long i(Temporal temporal, v vVar) {
        if (temporal instanceof p) {
            temporal = (p) temporal;
        } else {
            try {
                ZoneId k = ZoneId.k(temporal);
                j$.time.temporal.a aVar = j$.time.temporal.a.INSTANT_SECONDS;
                temporal = temporal.j(aVar) ? h(temporal.e(aVar), temporal.c(j$.time.temporal.a.NANO_OF_SECOND), k) : o(g.s(e.n(temporal), i.m(temporal)), k, null);
            } catch (b e) {
                throw new b("Unable to obtain ZonedDateTime from TemporalAccessor: " + temporal + " of type " + temporal.getClass().getName(), e);
            }
        }
        if (!(vVar instanceof j$.time.temporal.b)) {
            return vVar.c(this, temporal);
        }
        ZoneId zoneId = this.c;
        Objects.requireNonNull(temporal);
        Objects.requireNonNull(zoneId, "zone");
        boolean equals = temporal.c.equals(zoneId);
        p pVar = temporal;
        if (!equals) {
            pVar = h(temporal.a.z(temporal.b), temporal.a.n(), zoneId);
        }
        return vVar.b() ? this.a.i(pVar.a, vVar) : m.k(this.a, this.b).i(m.k(pVar.a, pVar.b), vVar);
    }

    @Override // j$.time.temporal.TemporalAccessor
    public boolean j(j$.time.temporal.l lVar) {
        return (lVar instanceof j$.time.temporal.a) || (lVar != null && lVar.e(this));
    }

    public j$.time.chrono.g k() {
        Objects.requireNonNull((e) s());
        return j$.time.chrono.h.a;
    }

    public ZoneOffset l() {
        return this.b;
    }

    public ZoneId m() {
        return this.c;
    }

    public long r() {
        return ((((e) s()).E() * 86400) + v().w()) - l().o();
    }

    public j$.time.chrono.b s() {
        return this.a.A();
    }

    public g t() {
        return this.a;
    }

    public String toString() {
        String str = this.a.toString() + this.b.toString();
        if (this.b == this.c) {
            return str;
        }
        return str + '[' + this.c.toString() + ']';
    }

    public j$.time.chrono.c u() {
        return this.a;
    }

    public i v() {
        return this.a.C();
    }
}
