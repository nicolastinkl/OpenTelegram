package j$.time.format;

import j$.time.ZoneOffset;
import j$.time.temporal.TemporalAccessor;

/* loaded from: classes2.dex */
final class j implements h {
    j(int i) {
    }

    @Override // j$.time.format.h
    public boolean a(v vVar, StringBuilder sb) {
        Long e = vVar.e(j$.time.temporal.a.INSTANT_SECONDS);
        TemporalAccessor d = vVar.d();
        j$.time.temporal.a aVar = j$.time.temporal.a.NANO_OF_SECOND;
        Long valueOf = d.j(aVar) ? Long.valueOf(vVar.d().e(aVar)) : null;
        int i = 0;
        if (e == null) {
            return false;
        }
        long longValue = e.longValue();
        int h = aVar.h(valueOf != null ? valueOf.longValue() : 0L);
        if (longValue >= -62167219200L) {
            long j = (longValue - 315569520000L) + 62167219200L;
            long h2 = j$.lang.d.h(j, 315569520000L) + 1;
            j$.time.g t = j$.time.g.t(j$.lang.d.g(j, 315569520000L) - 62167219200L, 0, ZoneOffset.UTC);
            if (h2 > 0) {
                sb.append('+');
                sb.append(h2);
            }
            sb.append(t);
            if (t.o() == 0) {
                sb.append(":00");
            }
        } else {
            long j2 = longValue + 62167219200L;
            long j3 = j2 / 315569520000L;
            long j4 = j2 % 315569520000L;
            j$.time.g t2 = j$.time.g.t(j4 - 62167219200L, 0, ZoneOffset.UTC);
            int length = sb.length();
            sb.append(t2);
            if (t2.o() == 0) {
                sb.append(":00");
            }
            if (j3 < 0) {
                if (t2.p() == -10000) {
                    sb.replace(length, length + 2, Long.toString(j3 - 1));
                } else if (j4 == 0) {
                    sb.insert(length, j3);
                } else {
                    sb.insert(length + 1, Math.abs(j3));
                }
            }
        }
        if (h > 0) {
            sb.append('.');
            int i2 = 100000000;
            while (true) {
                if (h <= 0 && i % 3 == 0 && i >= -2) {
                    break;
                }
                int i3 = h / i2;
                sb.append((char) (i3 + 48));
                h -= i3 * i2;
                i2 /= 10;
                i++;
            }
        }
        sb.append('Z');
        return true;
    }

    public String toString() {
        return "Instant()";
    }
}
