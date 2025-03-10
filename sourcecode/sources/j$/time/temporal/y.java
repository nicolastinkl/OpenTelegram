package j$.time.temporal;

import java.util.Objects;

/* loaded from: classes2.dex */
class y implements l {
    private static final x f = x.i(1, 7);
    private static final x g = x.k(0, 1, 4, 6);
    private static final x h = x.k(0, 1, 52, 54);
    private static final x i = x.j(1, 52, 53);
    private final String a;
    private final z b;
    private final v c;
    private final v d;
    private final x e;

    private y(String str, z zVar, v vVar, v vVar2, x xVar) {
        this.a = str;
        this.b = zVar;
        this.c = vVar;
        this.d = vVar2;
        this.e = xVar;
    }

    private int h(int i2, int i3) {
        return ((i3 - 1) + (i2 + 7)) / 7;
    }

    private int i(TemporalAccessor temporalAccessor) {
        return j$.lang.d.e(temporalAccessor.c(a.DAY_OF_WEEK) - this.b.d().k(), 7) + 1;
    }

    private int j(TemporalAccessor temporalAccessor) {
        int i2 = i(temporalAccessor);
        a aVar = a.DAY_OF_YEAR;
        int c = temporalAccessor.c(aVar);
        int r = r(c, i2);
        int h2 = h(r, c);
        if (h2 == 0) {
            Objects.requireNonNull((j$.time.chrono.h) j$.time.chrono.d.b(temporalAccessor));
            return j(j$.time.e.n(temporalAccessor).u(c, b.DAYS));
        }
        if (h2 <= 50) {
            return h2;
        }
        int h3 = h(r, this.b.e() + ((int) temporalAccessor.d(aVar).d()));
        return h2 >= h3 ? (h2 - h3) + 1 : h2;
    }

    static y k(z zVar) {
        return new y("DayOfWeek", zVar, b.DAYS, b.WEEKS, f);
    }

    static y l(z zVar) {
        return new y("WeekBasedYear", zVar, j.d, b.FOREVER, a.YEAR.c());
    }

    static y m(z zVar) {
        return new y("WeekOfMonth", zVar, b.WEEKS, b.MONTHS, g);
    }

    static y n(z zVar) {
        return new y("WeekOfWeekBasedYear", zVar, b.WEEKS, j.d, i);
    }

    static y o(z zVar) {
        return new y("WeekOfYear", zVar, b.WEEKS, b.YEARS, h);
    }

    private x p(TemporalAccessor temporalAccessor, l lVar) {
        int r = r(temporalAccessor.c(lVar), i(temporalAccessor));
        x d = temporalAccessor.d(lVar);
        return x.i(h(r, (int) d.e()), h(r, (int) d.d()));
    }

    private x q(TemporalAccessor temporalAccessor) {
        a aVar = a.DAY_OF_YEAR;
        if (!temporalAccessor.j(aVar)) {
            return h;
        }
        int i2 = i(temporalAccessor);
        int c = temporalAccessor.c(aVar);
        int r = r(c, i2);
        int h2 = h(r, c);
        if (h2 == 0) {
            return q(j$.time.e.n(temporalAccessor).u(c + 7, b.DAYS));
        }
        if (h2 < h(r, this.b.e() + ((int) temporalAccessor.d(aVar).d()))) {
            return x.i(1L, r1 - 1);
        }
        return q(j$.time.e.n(temporalAccessor).f((r0 - c) + 1 + 7, b.DAYS));
    }

    private int r(int i2, int i3) {
        int e = j$.lang.d.e(i2 - i3, 7);
        return e + 1 > this.b.e() ? 7 - e : -e;
    }

    @Override // j$.time.temporal.l
    public boolean a() {
        return false;
    }

    @Override // j$.time.temporal.l
    public boolean b() {
        return true;
    }

    @Override // j$.time.temporal.l
    public x c() {
        return this.e;
    }

    @Override // j$.time.temporal.l
    public long d(TemporalAccessor temporalAccessor) {
        int j;
        int h2;
        v vVar = this.d;
        if (vVar != b.WEEKS) {
            if (vVar == b.MONTHS) {
                int i2 = i(temporalAccessor);
                int c = temporalAccessor.c(a.DAY_OF_MONTH);
                h2 = h(r(c, i2), c);
            } else if (vVar == b.YEARS) {
                int i3 = i(temporalAccessor);
                int c2 = temporalAccessor.c(a.DAY_OF_YEAR);
                h2 = h(r(c2, i3), c2);
            } else {
                if (vVar != z.h) {
                    if (vVar != b.FOREVER) {
                        StringBuilder a = j$.time.a.a("unreachable, rangeUnit: ");
                        a.append(this.d);
                        a.append(", this: ");
                        a.append(this);
                        throw new IllegalStateException(a.toString());
                    }
                    int i4 = i(temporalAccessor);
                    int c3 = temporalAccessor.c(a.YEAR);
                    a aVar = a.DAY_OF_YEAR;
                    int c4 = temporalAccessor.c(aVar);
                    int r = r(c4, i4);
                    int h3 = h(r, c4);
                    if (h3 == 0) {
                        c3--;
                    } else {
                        if (h3 >= h(r, this.b.e() + ((int) temporalAccessor.d(aVar).d()))) {
                            c3++;
                        }
                    }
                    return c3;
                }
                j = j(temporalAccessor);
            }
            return h2;
        }
        j = i(temporalAccessor);
        return j;
    }

    @Override // j$.time.temporal.l
    public boolean e(TemporalAccessor temporalAccessor) {
        a aVar;
        if (!temporalAccessor.j(a.DAY_OF_WEEK)) {
            return false;
        }
        v vVar = this.d;
        if (vVar == b.WEEKS) {
            return true;
        }
        if (vVar == b.MONTHS) {
            aVar = a.DAY_OF_MONTH;
        } else if (vVar == b.YEARS || vVar == z.h) {
            aVar = a.DAY_OF_YEAR;
        } else {
            if (vVar != b.FOREVER) {
                return false;
            }
            aVar = a.YEAR;
        }
        return temporalAccessor.j(aVar);
    }

    @Override // j$.time.temporal.l
    public Temporal f(Temporal temporal, long j) {
        l lVar;
        l lVar2;
        if (this.e.a(j, this) == temporal.c(this)) {
            return temporal;
        }
        if (this.d != b.FOREVER) {
            return temporal.f(r0 - r1, this.c);
        }
        lVar = this.b.c;
        int c = temporal.c(lVar);
        lVar2 = this.b.e;
        int c2 = temporal.c(lVar2);
        j$.time.e w = j$.time.e.w((int) j, 1, 1);
        int r = r(1, i(w));
        return w.f(((Math.min(c2, h(r, this.b.e() + (w.t() ? 366 : 365)) - 1) - 1) * 7) + (c - 1) + (-r), b.DAYS);
    }

    @Override // j$.time.temporal.l
    public x g(TemporalAccessor temporalAccessor) {
        v vVar = this.d;
        if (vVar == b.WEEKS) {
            return this.e;
        }
        if (vVar == b.MONTHS) {
            return p(temporalAccessor, a.DAY_OF_MONTH);
        }
        if (vVar == b.YEARS) {
            return p(temporalAccessor, a.DAY_OF_YEAR);
        }
        if (vVar == z.h) {
            return q(temporalAccessor);
        }
        if (vVar == b.FOREVER) {
            return a.YEAR.c();
        }
        StringBuilder a = j$.time.a.a("unreachable, rangeUnit: ");
        a.append(this.d);
        a.append(", this: ");
        a.append(this);
        throw new IllegalStateException(a.toString());
    }

    public String toString() {
        return this.a + "[" + this.b.toString() + "]";
    }
}
