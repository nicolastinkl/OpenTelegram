package j$.time.format;

import j$.time.Instant;
import j$.time.ZoneId;
import j$.time.ZoneOffset;
import j$.time.temporal.TemporalAccessor;
import j$.util.AbstractC0112a;
import java.util.Locale;
import java.util.Objects;

/* loaded from: classes2.dex */
final class v {
    private TemporalAccessor a;
    private DateTimeFormatter b;
    private int c;

    v(TemporalAccessor temporalAccessor, DateTimeFormatter dateTimeFormatter) {
        j$.time.chrono.g a = dateTimeFormatter.a();
        ZoneId d = dateTimeFormatter.d();
        if (a != null || d != null) {
            int i = j$.time.temporal.t.a;
            j$.time.chrono.g gVar = (j$.time.chrono.g) temporalAccessor.g(j$.time.temporal.n.a);
            ZoneId zoneId = (ZoneId) temporalAccessor.g(j$.time.temporal.m.a);
            j$.time.e eVar = null;
            a = AbstractC0112a.u(a, gVar) ? null : a;
            d = AbstractC0112a.u(d, zoneId) ? null : d;
            if (a != null || d != null) {
                j$.time.chrono.g gVar2 = a != null ? a : gVar;
                if (d != null) {
                    if (temporalAccessor.j(j$.time.temporal.a.INSTANT_SECONDS)) {
                        if (gVar2 == null) {
                            j$.time.chrono.h hVar = j$.time.chrono.h.a;
                        }
                        temporalAccessor = j$.time.p.n(Instant.m(temporalAccessor), d);
                    } else {
                        j$.time.zone.c m = d.m();
                        if ((m.i() ? m.d(Instant.c) : d) instanceof ZoneOffset) {
                            j$.time.temporal.a aVar = j$.time.temporal.a.OFFSET_SECONDS;
                            if (temporalAccessor.j(aVar) && temporalAccessor.c(aVar) != d.m().d(Instant.c).o()) {
                                throw new j$.time.b("Unable to apply override zone '" + d + "' because the temporal object being formatted has a different offset but does not represent an instant: " + temporalAccessor);
                            }
                        }
                    }
                }
                zoneId = d != null ? d : zoneId;
                if (a != null) {
                    if (temporalAccessor.j(j$.time.temporal.a.EPOCH_DAY)) {
                        Objects.requireNonNull((j$.time.chrono.h) gVar2);
                        eVar = j$.time.e.n(temporalAccessor);
                    } else if (a != j$.time.chrono.h.a || gVar != null) {
                        for (j$.time.temporal.a aVar2 : j$.time.temporal.a.values()) {
                            if (aVar2.b() && temporalAccessor.j(aVar2)) {
                                throw new j$.time.b("Unable to apply override chronology '" + a + "' because the temporal object being formatted contains date fields but does not represent a whole date: " + temporalAccessor);
                            }
                        }
                    }
                }
                temporalAccessor = new u(eVar, temporalAccessor, gVar2, zoneId);
            }
        }
        this.a = temporalAccessor;
        this.b = dateTimeFormatter;
    }

    void a() {
        this.c--;
    }

    y b() {
        return this.b.b();
    }

    Locale c() {
        return this.b.c();
    }

    TemporalAccessor d() {
        return this.a;
    }

    Long e(j$.time.temporal.l lVar) {
        try {
            return Long.valueOf(this.a.e(lVar));
        } catch (j$.time.b e) {
            if (this.c > 0) {
                return null;
            }
            throw e;
        }
    }

    Object f(j$.time.temporal.u uVar) {
        Object g = this.a.g(uVar);
        if (g != null || this.c != 0) {
            return g;
        }
        StringBuilder a = j$.time.a.a("Unable to extract value: ");
        a.append(this.a.getClass());
        throw new j$.time.b(a.toString());
    }

    void g() {
        this.c++;
    }

    public String toString() {
        return this.a.toString();
    }
}
