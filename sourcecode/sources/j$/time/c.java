package j$.time;

import j$.time.temporal.Temporal;
import j$.time.temporal.TemporalAccessor;
import j$.time.temporal.t;
import j$.time.temporal.u;
import j$.time.temporal.w;
import j$.time.temporal.x;

/* loaded from: classes2.dex */
public enum c implements TemporalAccessor, j$.time.temporal.k {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY;

    private static final c[] a = values();

    public static c l(int i) {
        if (i >= 1 && i <= 7) {
            return a[i - 1];
        }
        throw new b("Invalid value for DayOfWeek: " + i);
    }

    @Override // j$.time.temporal.TemporalAccessor
    public int c(j$.time.temporal.l lVar) {
        return lVar == j$.time.temporal.a.DAY_OF_WEEK ? k() : j$.lang.d.b(this, lVar);
    }

    @Override // j$.time.temporal.TemporalAccessor
    public x d(j$.time.temporal.l lVar) {
        return lVar == j$.time.temporal.a.DAY_OF_WEEK ? lVar.c() : j$.lang.d.d(this, lVar);
    }

    @Override // j$.time.temporal.TemporalAccessor
    public long e(j$.time.temporal.l lVar) {
        if (lVar == j$.time.temporal.a.DAY_OF_WEEK) {
            return k();
        }
        if (!(lVar instanceof j$.time.temporal.a)) {
            return lVar.d(this);
        }
        throw new w("Unsupported field: " + lVar);
    }

    @Override // j$.time.temporal.TemporalAccessor
    public Object g(u uVar) {
        int i = t.a;
        return uVar == j$.time.temporal.o.a ? j$.time.temporal.b.DAYS : j$.lang.d.c(this, uVar);
    }

    @Override // j$.time.temporal.k
    public Temporal h(Temporal temporal) {
        return temporal.b(j$.time.temporal.a.DAY_OF_WEEK, k());
    }

    @Override // j$.time.temporal.TemporalAccessor
    public boolean j(j$.time.temporal.l lVar) {
        return lVar instanceof j$.time.temporal.a ? lVar == j$.time.temporal.a.DAY_OF_WEEK : lVar != null && lVar.e(this);
    }

    public int k() {
        return ordinal() + 1;
    }

    public c m(long j) {
        return a[((((int) (j % 7)) + 7) + ordinal()) % 7];
    }
}
