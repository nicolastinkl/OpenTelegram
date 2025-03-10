package j$.time.format;

import j$.time.ZoneId;
import j$.time.format.g;
import j$.time.temporal.TemporalAccessor;
import j$.util.AbstractC0112a;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes2.dex */
public final class DateTimeFormatter {
    public static final DateTimeFormatter h;
    private final g.a a;
    private final Locale b;
    private final y c;
    private final int d;
    private final Set e;
    private final j$.time.chrono.g f;
    private final ZoneId g;

    static {
        g gVar = new g();
        j$.time.temporal.a aVar = j$.time.temporal.a.YEAR;
        A a = A.EXCEEDS_PAD;
        g p = gVar.p(aVar, 4, 10, a);
        p.e('-');
        j$.time.temporal.a aVar2 = j$.time.temporal.a.MONTH_OF_YEAR;
        p.o(aVar2, 2);
        p.e('-');
        j$.time.temporal.a aVar3 = j$.time.temporal.a.DAY_OF_MONTH;
        p.o(aVar3, 2);
        j$.time.chrono.h hVar = j$.time.chrono.h.a;
        DateTimeFormatter y = p.y(1, hVar);
        g gVar2 = new g();
        gVar2.t();
        gVar2.a(y);
        gVar2.i();
        gVar2.y(1, hVar);
        g gVar3 = new g();
        gVar3.t();
        gVar3.a(y);
        gVar3.s();
        gVar3.i();
        gVar3.y(1, hVar);
        g gVar4 = new g();
        j$.time.temporal.a aVar4 = j$.time.temporal.a.HOUR_OF_DAY;
        gVar4.o(aVar4, 2);
        gVar4.e(':');
        j$.time.temporal.a aVar5 = j$.time.temporal.a.MINUTE_OF_HOUR;
        gVar4.o(aVar5, 2);
        gVar4.s();
        gVar4.e(':');
        j$.time.temporal.a aVar6 = j$.time.temporal.a.SECOND_OF_MINUTE;
        gVar4.o(aVar6, 2);
        gVar4.s();
        gVar4.b(j$.time.temporal.a.NANO_OF_SECOND, 0, 9, true);
        DateTimeFormatter y2 = gVar4.y(1, null);
        g gVar5 = new g();
        gVar5.t();
        gVar5.a(y2);
        gVar5.i();
        gVar5.y(1, null);
        g gVar6 = new g();
        gVar6.t();
        gVar6.a(y2);
        gVar6.s();
        gVar6.i();
        gVar6.y(1, null);
        g gVar7 = new g();
        gVar7.t();
        gVar7.a(y);
        gVar7.e('T');
        gVar7.a(y2);
        DateTimeFormatter y3 = gVar7.y(1, hVar);
        g gVar8 = new g();
        gVar8.t();
        gVar8.a(y3);
        gVar8.i();
        DateTimeFormatter y4 = gVar8.y(1, hVar);
        g gVar9 = new g();
        gVar9.a(y4);
        gVar9.s();
        gVar9.e('[');
        gVar9.u();
        gVar9.q();
        gVar9.e(']');
        gVar9.y(1, hVar);
        g gVar10 = new g();
        gVar10.a(y3);
        gVar10.s();
        gVar10.i();
        gVar10.s();
        gVar10.e('[');
        gVar10.u();
        gVar10.q();
        gVar10.e(']');
        gVar10.y(1, hVar);
        g gVar11 = new g();
        gVar11.t();
        g p2 = gVar11.p(aVar, 4, 10, a);
        p2.e('-');
        p2.o(j$.time.temporal.a.DAY_OF_YEAR, 3);
        p2.s();
        p2.i();
        p2.y(1, hVar);
        g gVar12 = new g();
        gVar12.t();
        g p3 = gVar12.p(j$.time.temporal.j.c, 4, 10, a);
        p3.f("-W");
        p3.o(j$.time.temporal.j.b, 2);
        p3.e('-');
        j$.time.temporal.a aVar7 = j$.time.temporal.a.DAY_OF_WEEK;
        p3.o(aVar7, 1);
        p3.s();
        p3.i();
        p3.y(1, hVar);
        g gVar13 = new g();
        gVar13.t();
        gVar13.c();
        h = gVar13.y(1, null);
        g gVar14 = new g();
        gVar14.t();
        gVar14.o(aVar, 4);
        gVar14.o(aVar2, 2);
        gVar14.o(aVar3, 2);
        gVar14.s();
        gVar14.h("+HHMMss", "Z");
        gVar14.y(1, hVar);
        HashMap hashMap = new HashMap();
        hashMap.put(1L, "Mon");
        hashMap.put(2L, "Tue");
        hashMap.put(3L, "Wed");
        hashMap.put(4L, "Thu");
        hashMap.put(5L, "Fri");
        hashMap.put(6L, "Sat");
        hashMap.put(7L, "Sun");
        HashMap hashMap2 = new HashMap();
        hashMap2.put(1L, "Jan");
        hashMap2.put(2L, "Feb");
        hashMap2.put(3L, "Mar");
        hashMap2.put(4L, "Apr");
        hashMap2.put(5L, "May");
        hashMap2.put(6L, "Jun");
        hashMap2.put(7L, "Jul");
        hashMap2.put(8L, "Aug");
        hashMap2.put(9L, "Sep");
        hashMap2.put(10L, "Oct");
        hashMap2.put(11L, "Nov");
        hashMap2.put(12L, "Dec");
        g gVar15 = new g();
        gVar15.t();
        gVar15.v();
        gVar15.s();
        gVar15.l(aVar7, hashMap);
        gVar15.f(", ");
        gVar15.r();
        g p4 = gVar15.p(aVar3, 1, 2, A.NOT_NEGATIVE);
        p4.e(' ');
        p4.l(aVar2, hashMap2);
        p4.e(' ');
        p4.o(aVar, 4);
        p4.e(' ');
        p4.o(aVar4, 2);
        p4.e(':');
        p4.o(aVar5, 2);
        p4.s();
        p4.e(':');
        p4.o(aVar6, 2);
        p4.r();
        p4.e(' ');
        p4.h("+HHMM", "GMT");
        p4.y(2, hVar);
    }

    DateTimeFormatter(g.a aVar, Locale locale, y yVar, int i, Set set, j$.time.chrono.g gVar, ZoneId zoneId) {
        Objects.requireNonNull(aVar, "printerParser");
        this.a = aVar;
        this.e = set;
        Objects.requireNonNull(locale, "locale");
        this.b = locale;
        Objects.requireNonNull(yVar, "decimalStyle");
        this.c = yVar;
        z.a(i, "resolverStyle");
        this.d = i;
        this.f = gVar;
        this.g = zoneId;
    }

    public static DateTimeFormatter ofPattern(String str) {
        g gVar = new g();
        gVar.j(str);
        return gVar.w();
    }

    public j$.time.chrono.g a() {
        return this.f;
    }

    public y b() {
        return this.c;
    }

    public Locale c() {
        return this.b;
    }

    public ZoneId d() {
        return this.g;
    }

    g.a e(boolean z) {
        return this.a.b(z);
    }

    public String format(TemporalAccessor temporalAccessor) {
        StringBuilder sb = new StringBuilder(32);
        Objects.requireNonNull(temporalAccessor, "temporal");
        try {
            this.a.a(new v(temporalAccessor, this), sb);
            return sb.toString();
        } catch (IOException e) {
            throw new j$.time.b(e.getMessage(), e);
        }
    }

    public String toString() {
        String aVar = this.a.toString();
        return aVar.startsWith("[") ? aVar : aVar.substring(1, aVar.length() - 1);
    }

    public DateTimeFormatter withZone(ZoneId zoneId) {
        return AbstractC0112a.u(this.g, zoneId) ? this : new DateTimeFormatter(this.a, this.b, this.c, this.d, this.e, this.f, zoneId);
    }
}
