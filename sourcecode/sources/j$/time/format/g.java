package j$.time.format;

import j$.time.ZoneId;
import j$.time.ZoneOffset;
import j$.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class g {
    private static final Map h;
    public static final /* synthetic */ int i = 0;
    private g a;
    private final g b;
    private final List c;
    private final boolean d;
    private int e;
    private char f;
    private int g;

    static final class a implements h {
        private final h[] a;
        private final boolean b;

        a(List list, boolean z) {
            this.a = (h[]) list.toArray(new h[list.size()]);
            this.b = z;
        }

        a(h[] hVarArr, boolean z) {
            this.a = hVarArr;
            this.b = z;
        }

        @Override // j$.time.format.h
        public boolean a(v vVar, StringBuilder sb) {
            int length = sb.length();
            if (this.b) {
                vVar.g();
            }
            try {
                for (h hVar : this.a) {
                    if (!hVar.a(vVar, sb)) {
                        sb.setLength(length);
                        return true;
                    }
                }
                if (this.b) {
                    vVar.a();
                }
                return true;
            } finally {
                if (this.b) {
                    vVar.a();
                }
            }
        }

        public a b(boolean z) {
            return z == this.b ? this : new a(this.a, z);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (this.a != null) {
                sb.append(this.b ? "[" : "(");
                for (h hVar : this.a) {
                    sb.append(hVar);
                }
                sb.append(this.b ? "]" : ")");
            }
            return sb.toString();
        }
    }

    static {
        HashMap hashMap = new HashMap();
        h = hashMap;
        hashMap.put('G', j$.time.temporal.a.ERA);
        hashMap.put('y', j$.time.temporal.a.YEAR_OF_ERA);
        hashMap.put('u', j$.time.temporal.a.YEAR);
        j$.time.temporal.l lVar = j$.time.temporal.j.a;
        hashMap.put('Q', lVar);
        hashMap.put('q', lVar);
        j$.time.temporal.a aVar = j$.time.temporal.a.MONTH_OF_YEAR;
        hashMap.put('M', aVar);
        hashMap.put('L', aVar);
        hashMap.put('D', j$.time.temporal.a.DAY_OF_YEAR);
        hashMap.put('d', j$.time.temporal.a.DAY_OF_MONTH);
        hashMap.put('F', j$.time.temporal.a.ALIGNED_DAY_OF_WEEK_IN_MONTH);
        j$.time.temporal.a aVar2 = j$.time.temporal.a.DAY_OF_WEEK;
        hashMap.put('E', aVar2);
        hashMap.put('c', aVar2);
        hashMap.put('e', aVar2);
        hashMap.put('a', j$.time.temporal.a.AMPM_OF_DAY);
        hashMap.put('H', j$.time.temporal.a.HOUR_OF_DAY);
        hashMap.put('k', j$.time.temporal.a.CLOCK_HOUR_OF_DAY);
        hashMap.put('K', j$.time.temporal.a.HOUR_OF_AMPM);
        hashMap.put('h', j$.time.temporal.a.CLOCK_HOUR_OF_AMPM);
        hashMap.put('m', j$.time.temporal.a.MINUTE_OF_HOUR);
        hashMap.put('s', j$.time.temporal.a.SECOND_OF_MINUTE);
        j$.time.temporal.a aVar3 = j$.time.temporal.a.NANO_OF_SECOND;
        hashMap.put('S', aVar3);
        hashMap.put('A', j$.time.temporal.a.MILLI_OF_DAY);
        hashMap.put('n', aVar3);
        hashMap.put('N', j$.time.temporal.a.NANO_OF_DAY);
    }

    public g() {
        this.a = this;
        this.c = new ArrayList();
        this.g = -1;
        this.b = null;
        this.d = false;
    }

    private g(g gVar, boolean z) {
        this.a = this;
        this.c = new ArrayList();
        this.g = -1;
        this.b = gVar;
        this.d = z;
    }

    private int d(h hVar) {
        Objects.requireNonNull(hVar, "pp");
        g gVar = this.a;
        int i2 = gVar.e;
        if (i2 > 0) {
            n nVar = new n(hVar, i2, gVar.f);
            gVar.e = 0;
            gVar.f = (char) 0;
            hVar = nVar;
        }
        gVar.c.add(hVar);
        this.a.g = -1;
        return r5.c.size() - 1;
    }

    private g m(l lVar) {
        l d;
        A a2;
        g gVar = this.a;
        int i2 = gVar.g;
        if (i2 >= 0) {
            l lVar2 = (l) gVar.c.get(i2);
            if (lVar.b == lVar.c) {
                a2 = lVar.d;
                if (a2 == A.NOT_NEGATIVE) {
                    d = lVar2.e(lVar.c);
                    d(lVar.d());
                    this.a.g = i2;
                    this.a.c.set(i2, d);
                }
            }
            d = lVar2.d();
            this.a.g = d(lVar);
            this.a.c.set(i2, d);
        } else {
            gVar.g = d(lVar);
        }
        return this;
    }

    private DateTimeFormatter x(Locale locale, int i2, j$.time.chrono.g gVar) {
        Objects.requireNonNull(locale, "locale");
        while (this.a.b != null) {
            r();
        }
        return new DateTimeFormatter(new a(this.c, false), locale, y.a, i2, null, gVar, null);
    }

    public g a(DateTimeFormatter dateTimeFormatter) {
        d(dateTimeFormatter.e(false));
        return this;
    }

    public g b(j$.time.temporal.l lVar, int i2, int i3, boolean z) {
        d(new i(lVar, i2, i3, z));
        return this;
    }

    public g c() {
        d(new j(-2));
        return this;
    }

    public g e(char c) {
        d(new f(c));
        return this;
    }

    public g f(String str) {
        Objects.requireNonNull(str, "literal");
        if (str.length() > 0) {
            d(str.length() == 1 ? new f(str.charAt(0)) : new k(str));
        }
        return this;
    }

    public g g(B b) {
        Objects.requireNonNull(b, "style");
        if (b != B.FULL && b != B.SHORT) {
            throw new IllegalArgumentException("Style must be either full or short");
        }
        d(new k(b));
        return this;
    }

    public g h(String str, String str2) {
        d(new m(str, str2));
        return this;
    }

    public g i() {
        d(m.d);
        return this;
    }

    /* JADX WARN: Code restructure failed: missing block: B:67:0x00ed, code lost:
    
        if (r3 == 1) goto L133;
     */
    /* JADX WARN: Removed duplicated region for block: B:247:0x031a  */
    /* JADX WARN: Removed duplicated region for block: B:253:0x0333 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public j$.time.format.g j(java.lang.String r18) {
        /*
            Method dump skipped, instructions count: 952
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.time.format.g.j(java.lang.String):j$.time.format.g");
    }

    public g k(j$.time.temporal.l lVar, B b) {
        Objects.requireNonNull(lVar, "field");
        Objects.requireNonNull(b, "textStyle");
        d(new q(lVar, b, new x()));
        return this;
    }

    public g l(j$.time.temporal.l lVar, Map map) {
        Objects.requireNonNull(lVar, "field");
        LinkedHashMap linkedHashMap = new LinkedHashMap(map);
        B b = B.FULL;
        d(new q(lVar, b, new c(this, new w(Collections.singletonMap(b, linkedHashMap)))));
        return this;
    }

    public g n(j$.time.temporal.l lVar) {
        Objects.requireNonNull(lVar, "field");
        m(new l(lVar, 1, 19, A.NORMAL));
        return this;
    }

    public g o(j$.time.temporal.l lVar, int i2) {
        Objects.requireNonNull(lVar, "field");
        if (i2 >= 1 && i2 <= 19) {
            m(new l(lVar, i2, i2, A.NOT_NEGATIVE));
            return this;
        }
        throw new IllegalArgumentException("The width must be from 1 to 19 inclusive but was " + i2);
    }

    public g p(j$.time.temporal.l lVar, int i2, int i3, A a2) {
        if (i2 == i3 && a2 == A.NOT_NEGATIVE) {
            o(lVar, i3);
            return this;
        }
        Objects.requireNonNull(lVar, "field");
        Objects.requireNonNull(a2, "signStyle");
        if (i2 < 1 || i2 > 19) {
            throw new IllegalArgumentException("The minimum width must be from 1 to 19 inclusive but was " + i2);
        }
        if (i3 < 1 || i3 > 19) {
            throw new IllegalArgumentException("The maximum width must be from 1 to 19 inclusive but was " + i3);
        }
        if (i3 >= i2) {
            m(new l(lVar, i2, i3, a2));
            return this;
        }
        throw new IllegalArgumentException("The maximum width must exceed or equal the minimum width but " + i3 + " < " + i2);
    }

    public g q() {
        d(new s(new j$.time.temporal.u() { // from class: j$.time.format.a
            @Override // j$.time.temporal.u
            public final Object a(TemporalAccessor temporalAccessor) {
                int i2 = g.i;
                int i3 = j$.time.temporal.t.a;
                ZoneId zoneId = (ZoneId) temporalAccessor.g(j$.time.temporal.m.a);
                if (zoneId == null || (zoneId instanceof ZoneOffset)) {
                    return null;
                }
                return zoneId;
            }
        }, "ZoneRegionId()"));
        return this;
    }

    public g r() {
        g gVar = this.a;
        if (gVar.b == null) {
            throw new IllegalStateException("Cannot call optionalEnd() as there was no previous call to optionalStart()");
        }
        if (gVar.c.size() > 0) {
            g gVar2 = this.a;
            a aVar = new a(gVar2.c, gVar2.d);
            this.a = this.a.b;
            d(aVar);
        } else {
            this.a = this.a.b;
        }
        return this;
    }

    public g s() {
        g gVar = this.a;
        gVar.g = -1;
        this.a = new g(gVar, true);
        return this;
    }

    public g t() {
        d(p.INSENSITIVE);
        return this;
    }

    public g u() {
        d(p.SENSITIVE);
        return this;
    }

    public g v() {
        d(p.LENIENT);
        return this;
    }

    public DateTimeFormatter w() {
        return x(Locale.getDefault(), 2, null);
    }

    DateTimeFormatter y(int i2, j$.time.chrono.g gVar) {
        return x(Locale.getDefault(), i2, gVar);
    }
}
