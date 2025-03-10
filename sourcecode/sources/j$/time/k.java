package j$.time;

import j$.time.temporal.Temporal;
import j$.time.temporal.TemporalAccessor;
import j$.time.temporal.t;
import j$.time.temporal.u;
import j$.time.temporal.w;
import j$.time.temporal.x;

/* loaded from: classes2.dex */
public enum k implements TemporalAccessor, j$.time.temporal.k {
    JANUARY,
    FEBRUARY,
    MARCH,
    APRIL,
    MAY,
    JUNE,
    JULY,
    AUGUST,
    SEPTEMBER,
    OCTOBER,
    NOVEMBER,
    DECEMBER;

    private static final k[] a = values();

    public static k n(int i) {
        if (i >= 1 && i <= 12) {
            return a[i - 1];
        }
        throw new b("Invalid value for MonthOfYear: " + i);
    }

    @Override // j$.time.temporal.TemporalAccessor
    public int c(j$.time.temporal.l lVar) {
        return lVar == j$.time.temporal.a.MONTH_OF_YEAR ? l() : j$.lang.d.b(this, lVar);
    }

    @Override // j$.time.temporal.TemporalAccessor
    public x d(j$.time.temporal.l lVar) {
        return lVar == j$.time.temporal.a.MONTH_OF_YEAR ? lVar.c() : j$.lang.d.d(this, lVar);
    }

    @Override // j$.time.temporal.TemporalAccessor
    public long e(j$.time.temporal.l lVar) {
        if (lVar == j$.time.temporal.a.MONTH_OF_YEAR) {
            return l();
        }
        if (!(lVar instanceof j$.time.temporal.a)) {
            return lVar.d(this);
        }
        throw new w("Unsupported field: " + lVar);
    }

    @Override // j$.time.temporal.TemporalAccessor
    public Object g(u uVar) {
        int i = t.a;
        return uVar == j$.time.temporal.n.a ? j$.time.chrono.h.a : uVar == j$.time.temporal.o.a ? j$.time.temporal.b.MONTHS : j$.lang.d.c(this, uVar);
    }

    @Override // j$.time.temporal.k
    public Temporal h(Temporal temporal) {
        if (((j$.time.chrono.a) j$.time.chrono.d.b(temporal)).equals(j$.time.chrono.h.a)) {
            return temporal.b(j$.time.temporal.a.MONTH_OF_YEAR, l());
        }
        throw new b("Adjustment only supported on ISO date-time");
    }

    @Override // j$.time.temporal.TemporalAccessor
    public boolean j(j$.time.temporal.l lVar) {
        return lVar instanceof j$.time.temporal.a ? lVar == j$.time.temporal.a.MONTH_OF_YEAR : lVar != null && lVar.e(this);
    }

    public int k(boolean z) {
        int i;
        switch (j.a[ordinal()]) {
            case 1:
                return 32;
            case 2:
                i = 91;
                break;
            case 3:
                i = 152;
                break;
            case 4:
                i = 244;
                break;
            case 5:
                i = 305;
                break;
            case 6:
                return 1;
            case 7:
                i = 60;
                break;
            case 8:
                i = 121;
                break;
            case 9:
                i = 182;
                break;
            case 10:
                i = 213;
                break;
            case 11:
                i = 274;
                break;
            default:
                i = 335;
                break;
        }
        return (z ? 1 : 0) + i;
    }

    public int l() {
        return ordinal() + 1;
    }

    public int m(boolean z) {
        int i = j.a[ordinal()];
        return i != 1 ? (i == 2 || i == 3 || i == 4 || i == 5) ? 30 : 31 : z ? 29 : 28;
    }

    public k o(long j) {
        return a[((((int) (j % 12)) + 12) + ordinal()) % 12];
    }
}
