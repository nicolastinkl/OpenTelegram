package j$.time.temporal;

import j$.time.Duration;

/* loaded from: classes2.dex */
public enum b implements v {
    NANOS("Nanos", Duration.b(1)),
    MICROS("Micros", Duration.b(1000)),
    MILLIS("Millis", Duration.b(1000000)),
    SECONDS("Seconds", Duration.ofSeconds(1)),
    MINUTES("Minutes", Duration.ofSeconds(60)),
    HOURS("Hours", Duration.ofSeconds(3600)),
    HALF_DAYS("HalfDays", Duration.ofSeconds(43200)),
    DAYS("Days", Duration.ofSeconds(86400)),
    WEEKS("Weeks", Duration.ofSeconds(604800)),
    MONTHS("Months", Duration.ofSeconds(2629746)),
    YEARS("Years", Duration.ofSeconds(31556952)),
    DECADES("Decades", Duration.ofSeconds(315569520)),
    CENTURIES("Centuries", Duration.ofSeconds(3155695200L)),
    MILLENNIA("Millennia", Duration.ofSeconds(31556952000L)),
    ERAS("Eras", Duration.ofSeconds(31556952000000000L)),
    FOREVER("Forever", Duration.c(Long.MAX_VALUE, 999999999));

    private final String a;

    b(String str, Duration duration) {
        this.a = str;
    }

    @Override // j$.time.temporal.v
    public boolean a() {
        return compareTo(DAYS) < 0;
    }

    @Override // j$.time.temporal.v
    public boolean b() {
        return compareTo(DAYS) >= 0 && this != FOREVER;
    }

    @Override // j$.time.temporal.v
    public long c(Temporal temporal, Temporal temporal2) {
        return temporal.i(temporal2, this);
    }

    @Override // j$.time.temporal.v
    public Temporal d(Temporal temporal, long j) {
        return temporal.f(j, this);
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.a;
    }
}
