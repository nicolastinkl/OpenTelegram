package j$.time;

import j$.time.temporal.Temporal;
import j$.time.temporal.TemporalAccessor;
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
public final class e implements Temporal, j$.time.temporal.k, j$.time.chrono.b, Serializable {
    public static final e d = w(-999999999, 1, 1);
    public static final e e = w(999999999, 12, 31);
    private final int a;
    private final short b;
    private final short c;

    private e(int i, int i2, int i3) {
        this.a = i;
        this.b = (short) i2;
        this.c = (short) i3;
    }

    private static e D(int i, int i2, int i3) {
        int i4;
        if (i2 != 2) {
            if (i2 == 4 || i2 == 6 || i2 == 9 || i2 == 11) {
                i4 = 30;
            }
            return new e(i, i2, i3);
        }
        i4 = j$.time.chrono.h.a.a((long) i) ? 29 : 28;
        i3 = Math.min(i3, i4);
        return new e(i, i2, i3);
    }

    public static e n(TemporalAccessor temporalAccessor) {
        Objects.requireNonNull(temporalAccessor, "temporal");
        int i = t.a;
        e eVar = (e) temporalAccessor.g(r.a);
        if (eVar != null) {
            return eVar;
        }
        throw new b("Unable to obtain LocalDate from TemporalAccessor: " + temporalAccessor + " of type " + temporalAccessor.getClass().getName());
    }

    private int o(j$.time.temporal.l lVar) {
        switch (d.a[((j$.time.temporal.a) lVar).ordinal()]) {
            case 1:
                return this.c;
            case 2:
                return q();
            case 3:
                return ((this.c - 1) / 7) + 1;
            case 4:
                int i = this.a;
                return i >= 1 ? i : 1 - i;
            case 5:
                return p().k();
            case 6:
                return ((this.c - 1) % 7) + 1;
            case 7:
                return ((q() - 1) % 7) + 1;
            case 8:
                throw new w("Invalid field 'EpochDay' for get() method, use getLong() instead");
            case 9:
                return ((q() - 1) / 7) + 1;
            case 10:
                return this.b;
            case 11:
                throw new w("Invalid field 'ProlepticMonth' for get() method, use getLong() instead");
            case 12:
                return this.a;
            case 13:
                return this.a >= 1 ? 1 : 0;
            default:
                throw new w("Unsupported field: " + lVar);
        }
    }

    private long r() {
        return ((this.a * 12) + this.b) - 1;
    }

    private long v(e eVar) {
        return (((eVar.r() * 32) + eVar.c) - ((r() * 32) + this.c)) / 32;
    }

    public static e w(int i, int i2, int i3) {
        long j = i;
        j$.time.temporal.a.YEAR.i(j);
        j$.time.temporal.a.MONTH_OF_YEAR.i(i2);
        j$.time.temporal.a.DAY_OF_MONTH.i(i3);
        int i4 = 28;
        if (i3 > 28) {
            if (i2 != 2) {
                i4 = (i2 == 4 || i2 == 6 || i2 == 9 || i2 == 11) ? 30 : 31;
            } else if (j$.time.chrono.h.a.a(j)) {
                i4 = 29;
            }
            if (i3 > i4) {
                if (i3 == 29) {
                    throw new b("Invalid date 'February 29' as '" + i + "' is not a leap year");
                }
                StringBuilder a = a.a("Invalid date '");
                a.append(k.n(i2).name());
                a.append(" ");
                a.append(i3);
                a.append("'");
                throw new b(a.toString());
            }
        }
        return new e(i, i2, i3);
    }

    public static e x(long j) {
        long j2;
        long j3 = (j + 719528) - 60;
        if (j3 < 0) {
            long j4 = ((j3 + 1) / 146097) - 1;
            j2 = j4 * 400;
            j3 += (-j4) * 146097;
        } else {
            j2 = 0;
        }
        long j5 = ((j3 * 400) + 591) / 146097;
        long j6 = j3 - ((j5 / 400) + (((j5 / 4) + (j5 * 365)) - (j5 / 100)));
        if (j6 < 0) {
            j5--;
            j6 = j3 - ((j5 / 400) + (((j5 / 4) + (365 * j5)) - (j5 / 100)));
        }
        int i = (int) j6;
        int i2 = ((i * 5) + 2) / 153;
        return new e(j$.time.temporal.a.YEAR.h(j5 + j2 + (i2 / 10)), ((i2 + 2) % 12) + 1, (i - (((i2 * 306) + 5) / 10)) + 1);
    }

    public e A(long j) {
        if (j == 0) {
            return this;
        }
        long j2 = (this.a * 12) + (this.b - 1) + j;
        return D(j$.time.temporal.a.YEAR.h(j$.lang.d.h(j2, 12L)), ((int) j$.lang.d.g(j2, 12L)) + 1, this.c);
    }

    public e B(long j) {
        return z(j$.lang.d.i(j, 7L));
    }

    public e C(long j) {
        return j == 0 ? this : D(j$.time.temporal.a.YEAR.h(this.a + j), this.b, this.c);
    }

    public long E() {
        long j;
        long j2 = this.a;
        long j3 = this.b;
        long j4 = (365 * j2) + 0;
        if (j2 >= 0) {
            j = ((j2 + 399) / 400) + (((3 + j2) / 4) - ((99 + j2) / 100)) + j4;
        } else {
            j = j4 - ((j2 / (-400)) + ((j2 / (-4)) - (j2 / (-100))));
        }
        long j5 = (((367 * j3) - 362) / 12) + j + (this.c - 1);
        if (j3 > 2) {
            j5--;
            if (!t()) {
                j5--;
            }
        }
        return j5 - 719528;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // j$.time.temporal.Temporal
    /* renamed from: F, reason: merged with bridge method [inline-methods] */
    public e b(j$.time.temporal.l lVar, long j) {
        j$.time.temporal.a aVar;
        long k;
        j$.time.temporal.a aVar2;
        if (!(lVar instanceof j$.time.temporal.a)) {
            return (e) lVar.f(this, j);
        }
        j$.time.temporal.a aVar3 = (j$.time.temporal.a) lVar;
        aVar3.i(j);
        switch (d.a[aVar3.ordinal()]) {
            case 1:
                int i = (int) j;
                if (this.c != i) {
                    return w(this.a, this.b, i);
                }
                return this;
            case 2:
                return G((int) j);
            case 3:
                aVar = j$.time.temporal.a.ALIGNED_WEEK_OF_MONTH;
                return B(j - e(aVar));
            case 4:
                if (this.a < 1) {
                    j = 1 - j;
                }
                return H((int) j);
            case 5:
                k = p().k();
                return z(j - k);
            case 6:
                aVar2 = j$.time.temporal.a.ALIGNED_DAY_OF_WEEK_IN_MONTH;
                k = e(aVar2);
                return z(j - k);
            case 7:
                aVar2 = j$.time.temporal.a.ALIGNED_DAY_OF_WEEK_IN_YEAR;
                k = e(aVar2);
                return z(j - k);
            case 8:
                return x(j);
            case 9:
                aVar = j$.time.temporal.a.ALIGNED_WEEK_OF_YEAR;
                return B(j - e(aVar));
            case 10:
                int i2 = (int) j;
                if (this.b != i2) {
                    j$.time.temporal.a.MONTH_OF_YEAR.i(i2);
                    return D(this.a, i2, this.c);
                }
                return this;
            case 11:
                return A(j - r());
            case 12:
                return H((int) j);
            case 13:
                return e(j$.time.temporal.a.ERA) == j ? this : H(1 - this.a);
            default:
                throw new w("Unsupported field: " + lVar);
        }
    }

    public e G(int i) {
        if (q() == i) {
            return this;
        }
        int i2 = this.a;
        long j = i2;
        j$.time.temporal.a.YEAR.i(j);
        j$.time.temporal.a.DAY_OF_YEAR.i(i);
        boolean a = j$.time.chrono.h.a.a(j);
        if (i == 366 && !a) {
            throw new b("Invalid date 'DayOfYear 366' as '" + i2 + "' is not a leap year");
        }
        k n = k.n(((i - 1) / 31) + 1);
        if (i > (n.m(a) + n.k(a)) - 1) {
            n = n.o(1L);
        }
        return new e(i2, n.l(), (i - n.k(a)) + 1);
    }

    public e H(int i) {
        if (this.a == i) {
            return this;
        }
        j$.time.temporal.a.YEAR.i(i);
        return D(i, this.b, this.c);
    }

    @Override // j$.time.temporal.Temporal
    public Temporal a(j$.time.temporal.k kVar) {
        return (e) kVar;
    }

    @Override // j$.time.temporal.TemporalAccessor
    public int c(j$.time.temporal.l lVar) {
        return lVar instanceof j$.time.temporal.a ? o(lVar) : j$.lang.d.b(this, lVar);
    }

    @Override // j$.time.temporal.TemporalAccessor
    public x d(j$.time.temporal.l lVar) {
        int i;
        if (!(lVar instanceof j$.time.temporal.a)) {
            return lVar.g(this);
        }
        j$.time.temporal.a aVar = (j$.time.temporal.a) lVar;
        if (!aVar.b()) {
            throw new w("Unsupported field: " + lVar);
        }
        int i2 = d.a[aVar.ordinal()];
        if (i2 == 1) {
            short s = this.b;
            i = s != 2 ? (s == 4 || s == 6 || s == 9 || s == 11) ? 30 : 31 : t() ? 29 : 28;
        } else {
            if (i2 != 2) {
                if (i2 == 3) {
                    return x.i(1L, (k.n(this.b) != k.FEBRUARY || t()) ? 5L : 4L);
                }
                if (i2 != 4) {
                    return lVar.c();
                }
                return x.i(1L, this.a <= 0 ? 1000000000L : 999999999L);
            }
            i = t() ? 366 : 365;
        }
        return x.i(1L, i);
    }

    @Override // j$.time.temporal.TemporalAccessor
    public long e(j$.time.temporal.l lVar) {
        return lVar instanceof j$.time.temporal.a ? lVar == j$.time.temporal.a.EPOCH_DAY ? E() : lVar == j$.time.temporal.a.PROLEPTIC_MONTH ? r() : o(lVar) : lVar.d(this);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof e) && l((e) obj) == 0;
    }

    @Override // j$.time.temporal.TemporalAccessor
    public Object g(u uVar) {
        int i = t.a;
        if (uVar == r.a) {
            return this;
        }
        if (uVar == j$.time.temporal.m.a || uVar == q.a || uVar == j$.time.temporal.p.a || uVar == s.a) {
            return null;
        }
        return uVar == j$.time.temporal.n.a ? j$.time.chrono.h.a : uVar == j$.time.temporal.o.a ? j$.time.temporal.b.DAYS : uVar.a(this);
    }

    @Override // j$.time.temporal.k
    public Temporal h(Temporal temporal) {
        return temporal.b(j$.time.temporal.a.EPOCH_DAY, E());
    }

    public int hashCode() {
        int i = this.a;
        return (((i << 11) + (this.b << 6)) + this.c) ^ (i & (-2048));
    }

    @Override // j$.time.temporal.Temporal
    public long i(Temporal temporal, v vVar) {
        long m;
        long j;
        e n = n(temporal);
        if (!(vVar instanceof j$.time.temporal.b)) {
            return vVar.c(this, n);
        }
        switch (d.b[((j$.time.temporal.b) vVar).ordinal()]) {
            case 1:
                return m(n);
            case 2:
                m = m(n);
                j = 7;
                break;
            case 3:
                return v(n);
            case 4:
                m = v(n);
                j = 12;
                break;
            case 5:
                m = v(n);
                j = 120;
                break;
            case 6:
                m = v(n);
                j = 1200;
                break;
            case 7:
                m = v(n);
                j = 12000;
                break;
            case 8:
                j$.time.temporal.a aVar = j$.time.temporal.a.ERA;
                return n.e(aVar) - e(aVar);
            default:
                throw new w("Unsupported unit: " + vVar);
        }
        return m / j;
    }

    @Override // j$.time.temporal.TemporalAccessor
    public boolean j(j$.time.temporal.l lVar) {
        return lVar instanceof j$.time.temporal.a ? lVar.b() : lVar != null && lVar.e(this);
    }

    @Override // java.lang.Comparable
    /* renamed from: k, reason: merged with bridge method [inline-methods] */
    public int compareTo(j$.time.chrono.b bVar) {
        if (bVar instanceof e) {
            return l((e) bVar);
        }
        int compare = Long.compare(E(), ((e) bVar).E());
        if (compare != 0) {
            return compare;
        }
        j$.time.chrono.h hVar = j$.time.chrono.h.a;
        return 0;
    }

    int l(e eVar) {
        int i = this.a - eVar.a;
        if (i != 0) {
            return i;
        }
        int i2 = this.b - eVar.b;
        return i2 == 0 ? this.c - eVar.c : i2;
    }

    long m(e eVar) {
        return eVar.E() - E();
    }

    public c p() {
        return c.l(((int) j$.lang.d.g(E() + 3, 7L)) + 1);
    }

    public int q() {
        return (k.n(this.b).k(t()) + this.c) - 1;
    }

    public int s() {
        return this.a;
    }

    public boolean t() {
        return j$.time.chrono.h.a.a(this.a);
    }

    public String toString() {
        int i;
        int i2 = this.a;
        short s = this.b;
        short s2 = this.c;
        int abs = Math.abs(i2);
        StringBuilder sb = new StringBuilder(10);
        if (abs < 1000) {
            if (i2 < 0) {
                sb.append(i2 - 10000);
                i = 1;
            } else {
                sb.append(i2 + 10000);
                i = 0;
            }
            sb.deleteCharAt(i);
        } else {
            if (i2 > 9999) {
                sb.append('+');
            }
            sb.append(i2);
        }
        sb.append(s < 10 ? "-0" : "-");
        sb.append((int) s);
        sb.append(s2 >= 10 ? "-" : "-0");
        sb.append((int) s2);
        return sb.toString();
    }

    public j$.time.chrono.b u(long j, v vVar) {
        return j == Long.MIN_VALUE ? f(Long.MAX_VALUE, vVar).f(1L, vVar) : f(-j, vVar);
    }

    @Override // j$.time.temporal.Temporal
    /* renamed from: y, reason: merged with bridge method [inline-methods] */
    public e f(long j, v vVar) {
        if (!(vVar instanceof j$.time.temporal.b)) {
            return (e) vVar.d(this, j);
        }
        switch (d.b[((j$.time.temporal.b) vVar).ordinal()]) {
            case 1:
                return z(j);
            case 2:
                return B(j);
            case 3:
                return A(j);
            case 4:
                return C(j);
            case 5:
                return C(j$.lang.d.i(j, 10L));
            case 6:
                return C(j$.lang.d.i(j, 100L));
            case 7:
                return C(j$.lang.d.i(j, 1000L));
            case 8:
                j$.time.temporal.a aVar = j$.time.temporal.a.ERA;
                return b(aVar, j$.lang.d.f(e(aVar), j));
            default:
                throw new w("Unsupported unit: " + vVar);
        }
    }

    public e z(long j) {
        return j == 0 ? this : x(j$.lang.d.f(E(), j));
    }
}
