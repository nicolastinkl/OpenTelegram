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
import java.util.Objects;

/* loaded from: classes2.dex */
public final class m implements Temporal, j$.time.temporal.k, Comparable, Serializable {
    private final g a;
    private final ZoneOffset b;

    static {
        new m(g.c, ZoneOffset.f);
        new m(g.d, ZoneOffset.e);
    }

    private m(g gVar, ZoneOffset zoneOffset) {
        Objects.requireNonNull(gVar, "dateTime");
        this.a = gVar;
        Objects.requireNonNull(zoneOffset, "offset");
        this.b = zoneOffset;
    }

    public static m k(g gVar, ZoneOffset zoneOffset) {
        return new m(gVar, zoneOffset);
    }

    public static m l(Instant instant, ZoneId zoneId) {
        Objects.requireNonNull(instant, "instant");
        Objects.requireNonNull(zoneId, "zone");
        ZoneOffset d = j$.time.zone.c.j((ZoneOffset) zoneId).d(instant);
        return new m(g.t(instant.getEpochSecond(), instant.n(), d), d);
    }

    private m p(g gVar, ZoneOffset zoneOffset) {
        return (this.a == gVar && this.b.equals(zoneOffset)) ? this : new m(gVar, zoneOffset);
    }

    @Override // j$.time.temporal.Temporal
    public Temporal a(j$.time.temporal.k kVar) {
        return p(this.a.a(kVar), this.b);
    }

    @Override // j$.time.temporal.Temporal
    public Temporal b(j$.time.temporal.l lVar, long j) {
        g gVar;
        ZoneOffset p;
        if (!(lVar instanceof j$.time.temporal.a)) {
            return (m) lVar.f(this, j);
        }
        j$.time.temporal.a aVar = (j$.time.temporal.a) lVar;
        int i = l.a[aVar.ordinal()];
        if (i == 1) {
            return l(Instant.p(j, this.a.n()), this.b);
        }
        if (i != 2) {
            gVar = this.a.b(lVar, j);
            p = this.b;
        } else {
            gVar = this.a;
            p = ZoneOffset.p(aVar.h(j));
        }
        return p(gVar, p);
    }

    @Override // j$.time.temporal.TemporalAccessor
    public int c(j$.time.temporal.l lVar) {
        if (!(lVar instanceof j$.time.temporal.a)) {
            return j$.lang.d.b(this, lVar);
        }
        int i = l.a[((j$.time.temporal.a) lVar).ordinal()];
        if (i != 1) {
            return i != 2 ? this.a.c(lVar) : this.b.o();
        }
        throw new w("Invalid field 'InstantSeconds' for get() method, use getLong() instead");
    }

    @Override // java.lang.Comparable
    public int compareTo(Object obj) {
        int compare;
        m mVar = (m) obj;
        if (this.b.equals(mVar.b)) {
            compare = this.a.compareTo(mVar.a);
        } else {
            compare = Long.compare(m(), mVar.m());
            if (compare == 0) {
                compare = o().o() - mVar.o().o();
            }
        }
        return compare == 0 ? this.a.compareTo(mVar.a) : compare;
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
        int i = l.a[((j$.time.temporal.a) lVar).ordinal()];
        return i != 1 ? i != 2 ? this.a.e(lVar) : this.b.o() : m();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof m)) {
            return false;
        }
        m mVar = (m) obj;
        return this.a.equals(mVar.a) && this.b.equals(mVar.b);
    }

    @Override // j$.time.temporal.Temporal
    public Temporal f(long j, v vVar) {
        return vVar instanceof j$.time.temporal.b ? p(this.a.f(j, vVar), this.b) : (m) vVar.d(this, j);
    }

    @Override // j$.time.temporal.TemporalAccessor
    public Object g(u uVar) {
        int i = t.a;
        if (uVar == j$.time.temporal.p.a || uVar == q.a) {
            return this.b;
        }
        if (uVar == j$.time.temporal.m.a) {
            return null;
        }
        return uVar == r.a ? this.a.A() : uVar == s.a ? o() : uVar == j$.time.temporal.n.a ? j$.time.chrono.h.a : uVar == j$.time.temporal.o.a ? j$.time.temporal.b.NANOS : uVar.a(this);
    }

    @Override // j$.time.temporal.k
    public Temporal h(Temporal temporal) {
        return temporal.b(j$.time.temporal.a.EPOCH_DAY, this.a.A().E()).b(j$.time.temporal.a.NANO_OF_DAY, o().v()).b(j$.time.temporal.a.OFFSET_SECONDS, this.b.o());
    }

    public int hashCode() {
        return this.a.hashCode() ^ this.b.hashCode();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v16, types: [j$.time.m] */
    /* JADX WARN: Type inference failed for: r4v18 */
    /* JADX WARN: Type inference failed for: r4v6 */
    /* JADX WARN: Type inference failed for: r4v7 */
    @Override // j$.time.temporal.Temporal
    public long i(Temporal temporal, v vVar) {
        if (temporal instanceof m) {
            temporal = (m) temporal;
        } else {
            try {
                ZoneOffset n = ZoneOffset.n(temporal);
                int i = t.a;
                e eVar = (e) temporal.g(r.a);
                i iVar = (i) temporal.g(s.a);
                temporal = (eVar == null || iVar == null) ? l(Instant.m(temporal), n) : new m(g.s(eVar, iVar), n);
            } catch (b e) {
                throw new b("Unable to obtain OffsetDateTime from TemporalAccessor: " + temporal + " of type " + temporal.getClass().getName(), e);
            }
        }
        if (!(vVar instanceof j$.time.temporal.b)) {
            return vVar.c(this, temporal);
        }
        ZoneOffset zoneOffset = this.b;
        boolean equals = zoneOffset.equals(temporal.b);
        m mVar = temporal;
        if (!equals) {
            mVar = new m(temporal.a.x(zoneOffset.o() - temporal.b.o()), zoneOffset);
        }
        return this.a.i(mVar.a, vVar);
    }

    @Override // j$.time.temporal.TemporalAccessor
    public boolean j(j$.time.temporal.l lVar) {
        return (lVar instanceof j$.time.temporal.a) || (lVar != null && lVar.e(this));
    }

    public long m() {
        return this.a.z(this.b);
    }

    public g n() {
        return this.a;
    }

    public i o() {
        return this.a.C();
    }

    public String toString() {
        return this.a.toString() + this.b.toString();
    }
}
