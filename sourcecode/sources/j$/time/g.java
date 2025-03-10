package j$.time;

import j$.time.temporal.Temporal;
import j$.time.temporal.q;
import j$.time.temporal.r;
import j$.time.temporal.s;
import j$.time.temporal.t;
import j$.time.temporal.u;
import j$.time.temporal.v;
import j$.time.temporal.x;
import java.io.Serializable;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class g implements Temporal, j$.time.temporal.k, j$.time.chrono.c, Serializable {
    public static final g c = s(e.d, i.e);
    public static final g d = s(e.e, i.f);
    private final e a;
    private final i b;

    private g(e eVar, i iVar) {
        this.a = eVar;
        this.b = iVar;
    }

    private g D(e eVar, i iVar) {
        return (this.a == eVar && this.b == iVar) ? this : new g(eVar, iVar);
    }

    private int l(g gVar) {
        int l = this.a.l(gVar.a);
        return l == 0 ? this.b.compareTo(gVar.b) : l;
    }

    public static g s(e eVar, i iVar) {
        Objects.requireNonNull(eVar, "date");
        Objects.requireNonNull(iVar, "time");
        return new g(eVar, iVar);
    }

    public static g t(long j, int i, ZoneOffset zoneOffset) {
        Objects.requireNonNull(zoneOffset, "offset");
        long j2 = i;
        j$.time.temporal.a.NANO_OF_SECOND.i(j2);
        return new g(e.x(j$.lang.d.h(j + zoneOffset.o(), 86400L)), i.q((((int) j$.lang.d.g(r5, 86400L)) * 1000000000) + j2));
    }

    private g y(e eVar, long j, long j2, long j3, long j4, int i) {
        i q2;
        e eVar2 = eVar;
        if ((j | j2 | j3 | j4) == 0) {
            q2 = this.b;
        } else {
            long j5 = i;
            long v = this.b.v();
            long j6 = ((((j % 24) * 3600000000000L) + ((j2 % 1440) * 60000000000L) + ((j3 % 86400) * 1000000000) + (j4 % 86400000000000L)) * j5) + v;
            long h = j$.lang.d.h(j6, 86400000000000L) + (((j / 24) + (j2 / 1440) + (j3 / 86400) + (j4 / 86400000000000L)) * j5);
            long g = j$.lang.d.g(j6, 86400000000000L);
            q2 = g == v ? this.b : i.q(g);
            eVar2 = eVar2.z(h);
        }
        return D(eVar2, q2);
    }

    public e A() {
        return this.a;
    }

    public j$.time.chrono.b B() {
        return this.a;
    }

    public i C() {
        return this.b;
    }

    @Override // j$.time.temporal.Temporal
    /* renamed from: E, reason: merged with bridge method [inline-methods] */
    public g a(j$.time.temporal.k kVar) {
        return kVar instanceof e ? D((e) kVar, this.b) : kVar instanceof i ? D(this.a, (i) kVar) : kVar instanceof g ? (g) kVar : (g) kVar.h(this);
    }

    @Override // j$.time.temporal.Temporal
    /* renamed from: F, reason: merged with bridge method [inline-methods] */
    public g b(j$.time.temporal.l lVar, long j) {
        return lVar instanceof j$.time.temporal.a ? ((j$.time.temporal.a) lVar).a() ? D(this.a, this.b.b(lVar, j)) : D(this.a.b(lVar, j), this.b) : (g) lVar.f(this, j);
    }

    @Override // j$.time.temporal.TemporalAccessor
    public int c(j$.time.temporal.l lVar) {
        return lVar instanceof j$.time.temporal.a ? ((j$.time.temporal.a) lVar).a() ? this.b.c(lVar) : this.a.c(lVar) : j$.lang.d.b(this, lVar);
    }

    @Override // j$.time.temporal.TemporalAccessor
    public x d(j$.time.temporal.l lVar) {
        if (!(lVar instanceof j$.time.temporal.a)) {
            return lVar.g(this);
        }
        if (!((j$.time.temporal.a) lVar).a()) {
            return this.a.d(lVar);
        }
        i iVar = this.b;
        Objects.requireNonNull(iVar);
        return j$.lang.d.d(iVar, lVar);
    }

    @Override // j$.time.temporal.TemporalAccessor
    public long e(j$.time.temporal.l lVar) {
        return lVar instanceof j$.time.temporal.a ? ((j$.time.temporal.a) lVar).a() ? this.b.e(lVar) : this.a.e(lVar) : lVar.d(this);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof g)) {
            return false;
        }
        g gVar = (g) obj;
        return this.a.equals(gVar.a) && this.b.equals(gVar.b);
    }

    @Override // j$.time.temporal.TemporalAccessor
    public Object g(u uVar) {
        int i = t.a;
        if (uVar == r.a) {
            return this.a;
        }
        if (uVar == j$.time.temporal.m.a || uVar == q.a || uVar == j$.time.temporal.p.a) {
            return null;
        }
        if (uVar == s.a) {
            return C();
        }
        if (uVar != j$.time.temporal.n.a) {
            return uVar == j$.time.temporal.o.a ? j$.time.temporal.b.NANOS : uVar.a(this);
        }
        m();
        return j$.time.chrono.h.a;
    }

    @Override // j$.time.temporal.k
    public Temporal h(Temporal temporal) {
        return temporal.b(j$.time.temporal.a.EPOCH_DAY, this.a.E()).b(j$.time.temporal.a.NANO_OF_DAY, this.b.v());
    }

    public int hashCode() {
        return this.a.hashCode() ^ this.b.hashCode();
    }

    @Override // j$.time.temporal.Temporal
    public long i(Temporal temporal, v vVar) {
        g gVar;
        long j;
        long j2;
        long j3;
        if (temporal instanceof g) {
            gVar = (g) temporal;
        } else if (temporal instanceof p) {
            gVar = ((p) temporal).t();
        } else if (temporal instanceof m) {
            gVar = ((m) temporal).n();
        } else {
            try {
                gVar = new g(e.n(temporal), i.m(temporal));
            } catch (b e) {
                throw new b("Unable to obtain LocalDateTime from TemporalAccessor: " + temporal + " of type " + temporal.getClass().getName(), e);
            }
        }
        if (!(vVar instanceof j$.time.temporal.b)) {
            return vVar.c(this, gVar);
        }
        if (!vVar.a()) {
            e eVar = gVar.a;
            e eVar2 = this.a;
            Objects.requireNonNull(eVar);
            if (!(eVar2 instanceof e) ? eVar.E() <= eVar2.E() : eVar.l(eVar2) <= 0) {
                if (gVar.b.compareTo(this.b) < 0) {
                    eVar = eVar.z(-1L);
                    return this.a.i(eVar, vVar);
                }
            }
            e eVar3 = this.a;
            if (!(eVar3 instanceof e) ? eVar.E() >= eVar3.E() : eVar.l(eVar3) >= 0) {
                if (gVar.b.compareTo(this.b) > 0) {
                    eVar = eVar.z(1L);
                }
            }
            return this.a.i(eVar, vVar);
        }
        long m = this.a.m(gVar.a);
        if (m == 0) {
            return this.b.i(gVar.b, vVar);
        }
        long v = gVar.b.v() - this.b.v();
        if (m > 0) {
            j = m - 1;
            j2 = v + 86400000000000L;
        } else {
            j = m + 1;
            j2 = v - 86400000000000L;
        }
        switch (f.a[((j$.time.temporal.b) vVar).ordinal()]) {
            case 1:
                j = j$.lang.d.i(j, 86400000000000L);
                break;
            case 2:
                j = j$.lang.d.i(j, 86400000000L);
                j3 = 1000;
                j2 /= j3;
                break;
            case 3:
                j = j$.lang.d.i(j, 86400000L);
                j3 = 1000000;
                j2 /= j3;
                break;
            case 4:
                j = j$.lang.d.i(j, 86400L);
                j3 = 1000000000;
                j2 /= j3;
                break;
            case 5:
                j = j$.lang.d.i(j, 1440L);
                j3 = 60000000000L;
                j2 /= j3;
                break;
            case 6:
                j = j$.lang.d.i(j, 24L);
                j3 = 3600000000000L;
                j2 /= j3;
                break;
            case 7:
                j = j$.lang.d.i(j, 2L);
                j3 = 43200000000000L;
                j2 /= j3;
                break;
        }
        return j$.lang.d.f(j, j2);
    }

    @Override // j$.time.temporal.TemporalAccessor
    public boolean j(j$.time.temporal.l lVar) {
        if (!(lVar instanceof j$.time.temporal.a)) {
            return lVar != null && lVar.e(this);
        }
        j$.time.temporal.a aVar = (j$.time.temporal.a) lVar;
        return aVar.b() || aVar.a();
    }

    @Override // java.lang.Comparable
    /* renamed from: k, reason: merged with bridge method [inline-methods] */
    public int compareTo(j$.time.chrono.c cVar) {
        if (cVar instanceof g) {
            return l((g) cVar);
        }
        g gVar = (g) cVar;
        int compareTo = ((e) B()).compareTo(gVar.B());
        if (compareTo != 0) {
            return compareTo;
        }
        int compareTo2 = C().compareTo(gVar.C());
        if (compareTo2 != 0) {
            return compareTo2;
        }
        m();
        j$.time.chrono.h hVar = j$.time.chrono.h.a;
        gVar.m();
        return 0;
    }

    public j$.time.chrono.g m() {
        Objects.requireNonNull((e) B());
        return j$.time.chrono.h.a;
    }

    public int n() {
        return this.b.o();
    }

    public int o() {
        return this.b.p();
    }

    public int p() {
        return this.a.s();
    }

    public boolean q(j$.time.chrono.c cVar) {
        if (cVar instanceof g) {
            return l((g) cVar) > 0;
        }
        long E = ((e) B()).E();
        g gVar = (g) cVar;
        long E2 = ((e) gVar.B()).E();
        return E > E2 || (E == E2 && C().v() > gVar.C().v());
    }

    public boolean r(j$.time.chrono.c cVar) {
        if (cVar instanceof g) {
            return l((g) cVar) < 0;
        }
        long E = ((e) B()).E();
        g gVar = (g) cVar;
        long E2 = ((e) gVar.B()).E();
        return E < E2 || (E == E2 && C().v() < gVar.C().v());
    }

    public String toString() {
        return this.a.toString() + 'T' + this.b.toString();
    }

    @Override // j$.time.temporal.Temporal
    /* renamed from: u, reason: merged with bridge method [inline-methods] */
    public g f(long j, v vVar) {
        if (!(vVar instanceof j$.time.temporal.b)) {
            return (g) vVar.d(this, j);
        }
        switch (f.a[((j$.time.temporal.b) vVar).ordinal()]) {
            case 1:
                return w(j);
            case 2:
                return v(j / 86400000000L).w((j % 86400000000L) * 1000);
            case 3:
                return v(j / 86400000).w((j % 86400000) * 1000000);
            case 4:
                return x(j);
            case 5:
                return y(this.a, 0L, j, 0L, 0L, 1);
            case 6:
                return y(this.a, j, 0L, 0L, 0L, 1);
            case 7:
                g v = v(j / 256);
                return v.y(v.a, (j % 256) * 12, 0L, 0L, 0L, 1);
            default:
                return D(this.a.f(j, vVar), this.b);
        }
    }

    public g v(long j) {
        return D(this.a.z(j), this.b);
    }

    public g w(long j) {
        return y(this.a, 0L, 0L, 0L, j, 1);
    }

    public g x(long j) {
        return y(this.a, 0L, 0L, j, 0L, 1);
    }

    public long z(ZoneOffset zoneOffset) {
        Objects.requireNonNull(zoneOffset, "offset");
        return ((((e) B()).E() * 86400) + C().w()) - zoneOffset.o();
    }
}
