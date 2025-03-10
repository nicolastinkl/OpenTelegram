package j$.time.temporal;

import j$.time.Duration;

/* loaded from: classes2.dex */
enum i implements v {
    WEEK_BASED_YEARS("WeekBasedYears", Duration.ofSeconds(31556952)),
    QUARTER_YEARS("QuarterYears", Duration.ofSeconds(7889238));

    private final String a;

    i(String str, Duration duration) {
        this.a = str;
    }

    @Override // j$.time.temporal.v
    public boolean a() {
        return false;
    }

    @Override // j$.time.temporal.v
    public boolean b() {
        return true;
    }

    @Override // j$.time.temporal.v
    public long c(Temporal temporal, Temporal temporal2) {
        if (temporal.getClass() != temporal2.getClass()) {
            return temporal.i(temporal2, this);
        }
        int i = c.a[ordinal()];
        if (i == 1) {
            l lVar = j.c;
            return j$.lang.d.j(temporal2.e(lVar), temporal.e(lVar));
        }
        if (i == 2) {
            return temporal.i(temporal2, b.MONTHS) / 3;
        }
        throw new IllegalStateException("Unreachable");
    }

    @Override // j$.time.temporal.v
    public Temporal d(Temporal temporal, long j) {
        int i = c.a[ordinal()];
        if (i == 1) {
            return temporal.b(j.c, j$.lang.d.f(temporal.c(r0), j));
        }
        if (i == 2) {
            return temporal.f(j / 256, b.YEARS).f((j % 256) * 3, b.MONTHS);
        }
        throw new IllegalStateException("Unreachable");
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.a;
    }
}
