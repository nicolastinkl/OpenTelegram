package j$.time.format;

import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

/* loaded from: classes2.dex */
final class r implements h {
    private char a;
    private int b;

    r(char c, int i) {
        this.a = c;
        this.b = i;
    }

    @Override // j$.time.format.h
    public boolean a(v vVar, StringBuilder sb) {
        j$.time.temporal.l h;
        l lVar;
        Locale c = vVar.c();
        j$.time.temporal.v vVar2 = j$.time.temporal.z.h;
        Objects.requireNonNull(c, "locale");
        j$.time.temporal.z f = j$.time.temporal.z.f(j$.time.c.SUNDAY.m(r0.getFirstDayOfWeek() - 1), Calendar.getInstance(new Locale(c.getLanguage(), c.getCountry())).getMinimalDaysInFirstWeek());
        char c2 = this.a;
        if (c2 == 'W') {
            h = f.h();
        } else {
            if (c2 == 'Y') {
                j$.time.temporal.l g = f.g();
                int i = this.b;
                if (i == 2) {
                    lVar = new o(g, 2, 2, 0, o.i, 0, null);
                } else {
                    lVar = new l(g, i, 19, i < 4 ? A.NORMAL : A.EXCEEDS_PAD, -1);
                }
                return lVar.a(vVar, sb);
            }
            if (c2 == 'c' || c2 == 'e') {
                h = f.c();
            } else {
                if (c2 != 'w') {
                    throw new IllegalStateException("unreachable");
                }
                h = f.i();
            }
        }
        lVar = new l(h, this.b == 2 ? 2 : 1, 2, A.NOT_NEGATIVE);
        return lVar.a(vVar, sb);
    }

    public String toString() {
        String str;
        String str2;
        StringBuilder sb = new StringBuilder(30);
        sb.append("Localized(");
        char c = this.a;
        if (c == 'Y') {
            int i = this.b;
            if (i == 1) {
                str2 = "WeekBasedYear";
            } else if (i == 2) {
                str2 = "ReducedValue(WeekBasedYear,2,2,2000-01-01)";
            } else {
                sb.append("WeekBasedYear,");
                sb.append(this.b);
                sb.append(",");
                sb.append(19);
                sb.append(",");
                sb.append(this.b < 4 ? A.NORMAL : A.EXCEEDS_PAD);
            }
            sb.append(str2);
        } else {
            if (c == 'W') {
                str = "WeekOfMonth";
            } else if (c == 'c' || c == 'e') {
                str = "DayOfWeek";
            } else {
                if (c == 'w') {
                    str = "WeekOfWeekBasedYear";
                }
                sb.append(",");
                sb.append(this.b);
            }
            sb.append(str);
            sb.append(",");
            sb.append(this.b);
        }
        sb.append(")");
        return sb.toString();
    }
}
