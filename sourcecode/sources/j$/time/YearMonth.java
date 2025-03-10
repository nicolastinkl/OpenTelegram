package j$.time;

import j$.time.format.A;
import j$.time.temporal.Temporal;
import j$.time.temporal.t;
import j$.time.temporal.u;
import j$.time.temporal.v;
import j$.time.temporal.w;
import j$.time.temporal.x;
import java.io.Serializable;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class YearMonth implements Temporal, j$.time.temporal.k, Comparable<YearMonth>, Serializable {
    private final int a;
    private final int b;

    static /* synthetic */ class a {
        static final /* synthetic */ int[] a;
        static final /* synthetic */ int[] b;

        static {
            int[] iArr = new int[j$.time.temporal.b.values().length];
            b = iArr;
            try {
                iArr[j$.time.temporal.b.MONTHS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                b[j$.time.temporal.b.YEARS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                b[j$.time.temporal.b.DECADES.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                b[j$.time.temporal.b.CENTURIES.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                b[j$.time.temporal.b.MILLENNIA.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                b[j$.time.temporal.b.ERAS.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            int[] iArr2 = new int[j$.time.temporal.a.values().length];
            a = iArr2;
            try {
                iArr2[j$.time.temporal.a.MONTH_OF_YEAR.ordinal()] = 1;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                a[j$.time.temporal.a.PROLEPTIC_MONTH.ordinal()] = 2;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                a[j$.time.temporal.a.YEAR_OF_ERA.ordinal()] = 3;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                a[j$.time.temporal.a.YEAR.ordinal()] = 4;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                a[j$.time.temporal.a.ERA.ordinal()] = 5;
            } catch (NoSuchFieldError unused11) {
            }
        }
    }

    static {
        j$.time.format.g p = new j$.time.format.g().p(j$.time.temporal.a.YEAR, 4, 10, A.EXCEEDS_PAD);
        p.e('-');
        p.o(j$.time.temporal.a.MONTH_OF_YEAR, 2);
        p.w();
    }

    private YearMonth(int i, int i2) {
        this.a = i;
        this.b = i2;
    }

    private long k() {
        return ((this.a * 12) + this.b) - 1;
    }

    private YearMonth n(int i, int i2) {
        return (this.a == i && this.b == i2) ? this : new YearMonth(i, i2);
    }

    public static YearMonth of(int i, int i2) {
        j$.time.temporal.a.YEAR.i(i);
        j$.time.temporal.a.MONTH_OF_YEAR.i(i2);
        return new YearMonth(i, i2);
    }

    @Override // j$.time.temporal.Temporal
    public Temporal a(j$.time.temporal.k kVar) {
        return (YearMonth) ((e) kVar).h(this);
    }

    @Override // j$.time.temporal.TemporalAccessor
    public int c(j$.time.temporal.l lVar) {
        return d(lVar).a(e(lVar), lVar);
    }

    @Override // java.lang.Comparable
    public int compareTo(YearMonth yearMonth) {
        YearMonth yearMonth2 = yearMonth;
        int i = this.a - yearMonth2.a;
        return i == 0 ? this.b - yearMonth2.b : i;
    }

    @Override // j$.time.temporal.TemporalAccessor
    public x d(j$.time.temporal.l lVar) {
        if (lVar == j$.time.temporal.a.YEAR_OF_ERA) {
            return x.i(1L, this.a <= 0 ? 1000000000L : 999999999L);
        }
        return j$.lang.d.d(this, lVar);
    }

    @Override // j$.time.temporal.TemporalAccessor
    public long e(j$.time.temporal.l lVar) {
        int i;
        if (!(lVar instanceof j$.time.temporal.a)) {
            return lVar.d(this);
        }
        int i2 = a.a[((j$.time.temporal.a) lVar).ordinal()];
        if (i2 == 1) {
            i = this.b;
        } else {
            if (i2 == 2) {
                return k();
            }
            if (i2 == 3) {
                int i3 = this.a;
                if (i3 < 1) {
                    i3 = 1 - i3;
                }
                return i3;
            }
            if (i2 != 4) {
                if (i2 == 5) {
                    return this.a < 1 ? 0 : 1;
                }
                throw new w("Unsupported field: " + lVar);
            }
            i = this.a;
        }
        return i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof YearMonth)) {
            return false;
        }
        YearMonth yearMonth = (YearMonth) obj;
        return this.a == yearMonth.a && this.b == yearMonth.b;
    }

    @Override // j$.time.temporal.Temporal
    public Temporal f(long j, v vVar) {
        long j2;
        if (!(vVar instanceof j$.time.temporal.b)) {
            return (YearMonth) vVar.d(this, j);
        }
        switch (a.b[((j$.time.temporal.b) vVar).ordinal()]) {
            case 1:
                return l(j);
            case 2:
                return m(j);
            case 3:
                j2 = 10;
                break;
            case 4:
                j2 = 100;
                break;
            case 5:
                j2 = 1000;
                break;
            case 6:
                j$.time.temporal.a aVar = j$.time.temporal.a.ERA;
                return b(aVar, j$.lang.d.f(e(aVar), j));
            default:
                throw new w("Unsupported unit: " + vVar);
        }
        j = j$.lang.d.i(j, j2);
        return m(j);
    }

    @Override // j$.time.temporal.TemporalAccessor
    public Object g(u uVar) {
        int i = t.a;
        return uVar == j$.time.temporal.n.a ? j$.time.chrono.h.a : uVar == j$.time.temporal.o.a ? j$.time.temporal.b.MONTHS : j$.lang.d.c(this, uVar);
    }

    @Override // j$.time.temporal.k
    public Temporal h(Temporal temporal) {
        if (((j$.time.chrono.a) j$.time.chrono.d.b(temporal)).equals(j$.time.chrono.h.a)) {
            return temporal.b(j$.time.temporal.a.PROLEPTIC_MONTH, k());
        }
        throw new b("Adjustment only supported on ISO date-time");
    }

    public int hashCode() {
        return this.a ^ (this.b << 27);
    }

    @Override // j$.time.temporal.Temporal
    public long i(Temporal temporal, v vVar) {
        YearMonth of;
        if (temporal instanceof YearMonth) {
            of = (YearMonth) temporal;
        } else {
            Objects.requireNonNull(temporal, "temporal");
            try {
                if (!j$.time.chrono.h.a.equals(j$.time.chrono.d.b(temporal))) {
                    temporal = e.n(temporal);
                }
                of = of(temporal.c(j$.time.temporal.a.YEAR), temporal.c(j$.time.temporal.a.MONTH_OF_YEAR));
            } catch (b e) {
                throw new b("Unable to obtain YearMonth from TemporalAccessor: " + temporal + " of type " + temporal.getClass().getName(), e);
            }
        }
        if (!(vVar instanceof j$.time.temporal.b)) {
            return vVar.c(this, of);
        }
        long k = of.k() - k();
        switch (a.b[((j$.time.temporal.b) vVar).ordinal()]) {
            case 1:
                return k;
            case 2:
                return k / 12;
            case 3:
                return k / 120;
            case 4:
                return k / 1200;
            case 5:
                return k / 12000;
            case 6:
                j$.time.temporal.a aVar = j$.time.temporal.a.ERA;
                return of.e(aVar) - e(aVar);
            default:
                throw new w("Unsupported unit: " + vVar);
        }
    }

    @Override // j$.time.temporal.TemporalAccessor
    public boolean j(j$.time.temporal.l lVar) {
        return lVar instanceof j$.time.temporal.a ? lVar == j$.time.temporal.a.YEAR || lVar == j$.time.temporal.a.MONTH_OF_YEAR || lVar == j$.time.temporal.a.PROLEPTIC_MONTH || lVar == j$.time.temporal.a.YEAR_OF_ERA || lVar == j$.time.temporal.a.ERA : lVar != null && lVar.e(this);
    }

    public YearMonth l(long j) {
        if (j == 0) {
            return this;
        }
        long j2 = (this.a * 12) + (this.b - 1) + j;
        return n(j$.time.temporal.a.YEAR.h(j$.lang.d.h(j2, 12L)), ((int) j$.lang.d.g(j2, 12L)) + 1);
    }

    public int lengthOfMonth() {
        return k.n(this.b).m(j$.time.chrono.h.a.a(this.a));
    }

    public YearMonth m(long j) {
        return j == 0 ? this : n(j$.time.temporal.a.YEAR.h(this.a + j), this.b);
    }

    @Override // j$.time.temporal.Temporal
    /* renamed from: o, reason: merged with bridge method [inline-methods] */
    public YearMonth b(j$.time.temporal.l lVar, long j) {
        if (!(lVar instanceof j$.time.temporal.a)) {
            return (YearMonth) lVar.f(this, j);
        }
        j$.time.temporal.a aVar = (j$.time.temporal.a) lVar;
        aVar.i(j);
        int i = a.a[aVar.ordinal()];
        if (i == 1) {
            int i2 = (int) j;
            j$.time.temporal.a.MONTH_OF_YEAR.i(i2);
            return n(this.a, i2);
        }
        if (i == 2) {
            return l(j - k());
        }
        if (i == 3) {
            if (this.a < 1) {
                j = 1 - j;
            }
            return p((int) j);
        }
        if (i == 4) {
            return p((int) j);
        }
        if (i == 5) {
            return e(j$.time.temporal.a.ERA) == j ? this : p(1 - this.a);
        }
        throw new w("Unsupported field: " + lVar);
    }

    public YearMonth p(int i) {
        j$.time.temporal.a.YEAR.i(i);
        return n(i, this.b);
    }

    public String toString() {
        int i;
        int abs = Math.abs(this.a);
        StringBuilder sb = new StringBuilder(9);
        if (abs < 1000) {
            int i2 = this.a;
            if (i2 < 0) {
                sb.append(i2 - 10000);
                i = 1;
            } else {
                sb.append(i2 + 10000);
                i = 0;
            }
            sb.deleteCharAt(i);
        } else {
            sb.append(this.a);
        }
        sb.append(this.b < 10 ? "-0" : "-");
        sb.append(this.b);
        return sb.toString();
    }
}
