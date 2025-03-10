package j$.time.temporal;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'DAY_OF_QUARTER' uses external variables
	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:451)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByRegister(EnumVisitor.java:395)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:324)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:262)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:151)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* loaded from: classes2.dex */
abstract class h implements l {
    public static final h DAY_OF_QUARTER;
    public static final h QUARTER_OF_YEAR;
    public static final h WEEK_BASED_YEAR;
    public static final h WEEK_OF_WEEK_BASED_YEAR;
    private static final int[] a;
    private static final /* synthetic */ h[] b;

    static {
        final String str = "DAY_OF_QUARTER";
        final int i = 0;
        h hVar = new h(str, i) { // from class: j$.time.temporal.d
            @Override // j$.time.temporal.l
            public x c() {
                return x.j(1L, 90L, 92L);
            }

            @Override // j$.time.temporal.l
            public long d(TemporalAccessor temporalAccessor) {
                int[] iArr;
                if (!e(temporalAccessor)) {
                    throw new w("Unsupported field: DayOfQuarter");
                }
                int c = temporalAccessor.c(a.DAY_OF_YEAR);
                int c2 = temporalAccessor.c(a.MONTH_OF_YEAR);
                long e = temporalAccessor.e(a.YEAR);
                iArr = h.a;
                return c - iArr[((c2 - 1) / 3) + (j$.time.chrono.h.a.a(e) ? 4 : 0)];
            }

            @Override // j$.time.temporal.l
            public boolean e(TemporalAccessor temporalAccessor) {
                if (temporalAccessor.j(a.DAY_OF_YEAR) && temporalAccessor.j(a.MONTH_OF_YEAR) && temporalAccessor.j(a.YEAR)) {
                    if (((j$.time.chrono.a) j$.time.chrono.d.b(temporalAccessor)).equals(j$.time.chrono.h.a)) {
                        return true;
                    }
                }
                return false;
            }

            @Override // j$.time.temporal.l
            public Temporal f(Temporal temporal, long j) {
                long d = d(temporal);
                c().b(j, this);
                a aVar = a.DAY_OF_YEAR;
                return temporal.b(aVar, (j - d) + temporal.e(aVar));
            }

            @Override // j$.time.temporal.h, j$.time.temporal.l
            public x g(TemporalAccessor temporalAccessor) {
                if (!e(temporalAccessor)) {
                    throw new w("Unsupported field: DayOfQuarter");
                }
                long e = temporalAccessor.e(h.QUARTER_OF_YEAR);
                if (e == 1) {
                    return j$.time.chrono.h.a.a(temporalAccessor.e(a.YEAR)) ? x.i(1L, 91L) : x.i(1L, 90L);
                }
                return e == 2 ? x.i(1L, 91L) : (e == 3 || e == 4) ? x.i(1L, 92L) : c();
            }

            @Override // java.lang.Enum
            public String toString() {
                return "DayOfQuarter";
            }
        };
        DAY_OF_QUARTER = hVar;
        final String str2 = "QUARTER_OF_YEAR";
        final int i2 = 1;
        h hVar2 = new h(str2, i2) { // from class: j$.time.temporal.e
            @Override // j$.time.temporal.l
            public x c() {
                return x.i(1L, 4L);
            }

            @Override // j$.time.temporal.l
            public long d(TemporalAccessor temporalAccessor) {
                if (e(temporalAccessor)) {
                    return (temporalAccessor.e(a.MONTH_OF_YEAR) + 2) / 3;
                }
                throw new w("Unsupported field: QuarterOfYear");
            }

            @Override // j$.time.temporal.l
            public boolean e(TemporalAccessor temporalAccessor) {
                if (temporalAccessor.j(a.MONTH_OF_YEAR)) {
                    if (((j$.time.chrono.a) j$.time.chrono.d.b(temporalAccessor)).equals(j$.time.chrono.h.a)) {
                        return true;
                    }
                }
                return false;
            }

            @Override // j$.time.temporal.l
            public Temporal f(Temporal temporal, long j) {
                long d = d(temporal);
                c().b(j, this);
                a aVar = a.MONTH_OF_YEAR;
                return temporal.b(aVar, ((j - d) * 3) + temporal.e(aVar));
            }

            @Override // java.lang.Enum
            public String toString() {
                return "QuarterOfYear";
            }
        };
        QUARTER_OF_YEAR = hVar2;
        final String str3 = "WEEK_OF_WEEK_BASED_YEAR";
        final int i3 = 2;
        h hVar3 = new h(str3, i3) { // from class: j$.time.temporal.f
            @Override // j$.time.temporal.l
            public x c() {
                return x.j(1L, 52L, 53L);
            }

            @Override // j$.time.temporal.l
            public long d(TemporalAccessor temporalAccessor) {
                if (e(temporalAccessor)) {
                    return h.j(j$.time.e.n(temporalAccessor));
                }
                throw new w("Unsupported field: WeekOfWeekBasedYear");
            }

            @Override // j$.time.temporal.l
            public boolean e(TemporalAccessor temporalAccessor) {
                if (temporalAccessor.j(a.EPOCH_DAY)) {
                    if (((j$.time.chrono.a) j$.time.chrono.d.b(temporalAccessor)).equals(j$.time.chrono.h.a)) {
                        return true;
                    }
                }
                return false;
            }

            @Override // j$.time.temporal.l
            public Temporal f(Temporal temporal, long j) {
                c().b(j, this);
                return temporal.f(j$.lang.d.j(j, d(temporal)), b.WEEKS);
            }

            @Override // j$.time.temporal.h, j$.time.temporal.l
            public x g(TemporalAccessor temporalAccessor) {
                if (e(temporalAccessor)) {
                    return h.i(j$.time.e.n(temporalAccessor));
                }
                throw new w("Unsupported field: WeekOfWeekBasedYear");
            }

            @Override // java.lang.Enum
            public String toString() {
                return "WeekOfWeekBasedYear";
            }
        };
        WEEK_OF_WEEK_BASED_YEAR = hVar3;
        final String str4 = "WEEK_BASED_YEAR";
        final int i4 = 3;
        h hVar4 = new h(str4, i4) { // from class: j$.time.temporal.g
            @Override // j$.time.temporal.l
            public x c() {
                return a.YEAR.c();
            }

            @Override // j$.time.temporal.l
            public long d(TemporalAccessor temporalAccessor) {
                int m;
                if (!e(temporalAccessor)) {
                    throw new w("Unsupported field: WeekBasedYear");
                }
                m = h.m(j$.time.e.n(temporalAccessor));
                return m;
            }

            @Override // j$.time.temporal.l
            public boolean e(TemporalAccessor temporalAccessor) {
                if (temporalAccessor.j(a.EPOCH_DAY)) {
                    if (((j$.time.chrono.a) j$.time.chrono.d.b(temporalAccessor)).equals(j$.time.chrono.h.a)) {
                        return true;
                    }
                }
                return false;
            }

            @Override // j$.time.temporal.l
            public Temporal f(Temporal temporal, long j) {
                int n;
                if (!e(temporal)) {
                    throw new w("Unsupported field: WeekBasedYear");
                }
                int a2 = c().a(j, h.WEEK_BASED_YEAR);
                j$.time.e n2 = j$.time.e.n(temporal);
                int c = n2.c(a.DAY_OF_WEEK);
                int j2 = h.j(n2);
                if (j2 == 53) {
                    n = h.n(a2);
                    if (n == 52) {
                        j2 = 52;
                    }
                }
                return temporal.a(j$.time.e.w(a2, 1, 4).z(((j2 - 1) * 7) + (c - r6.c(r0))));
            }

            @Override // java.lang.Enum
            public String toString() {
                return "WeekBasedYear";
            }
        };
        WEEK_BASED_YEAR = hVar4;
        b = new h[]{hVar, hVar2, hVar3, hVar4};
        a = new int[]{0, 90, 181, 273, 0, 91, 182, 274};
    }

    h(String str, int i, c cVar) {
    }

    static x i(j$.time.e eVar) {
        return x.i(1L, n(m(eVar)));
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0056, code lost:
    
        if ((r0 == -3 || (r0 == -2 && r5.t())) == false) goto L20;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static int j(j$.time.e r5) {
        /*
            j$.time.c r0 = r5.p()
            int r0 = r0.ordinal()
            int r1 = r5.q()
            r2 = 1
            int r1 = r1 - r2
            int r0 = 3 - r0
            int r0 = r0 + r1
            int r3 = r0 / 7
            int r3 = r3 * 7
            int r0 = r0 - r3
            r3 = -3
            int r0 = r0 + r3
            if (r0 >= r3) goto L1c
            int r0 = r0 + 7
        L1c:
            if (r1 >= r0) goto L3f
            r0 = 180(0xb4, float:2.52E-43)
            j$.time.e r5 = r5.G(r0)
            r0 = -1
            j$.time.e r5 = r5.C(r0)
            int r5 = m(r5)
            int r5 = n(r5)
            long r0 = (long) r5
            r2 = 1
            j$.time.temporal.x r5 = j$.time.temporal.x.i(r2, r0)
            long r0 = r5.d()
            int r5 = (int) r0
            goto L5b
        L3f:
            int r1 = r1 - r0
            int r1 = r1 / 7
            int r1 = r1 + r2
            r4 = 53
            if (r1 != r4) goto L59
            if (r0 == r3) goto L55
            r3 = -2
            if (r0 != r3) goto L53
            boolean r5 = r5.t()
            if (r5 == 0) goto L53
            goto L55
        L53:
            r5 = 0
            goto L56
        L55:
            r5 = 1
        L56:
            if (r5 != 0) goto L59
            goto L5a
        L59:
            r2 = r1
        L5a:
            r5 = r2
        L5b:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.time.temporal.h.j(j$.time.e):int");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int m(j$.time.e eVar) {
        int s = eVar.s();
        int q2 = eVar.q();
        if (q2 <= 3) {
            return q2 - eVar.p().ordinal() < -2 ? s - 1 : s;
        }
        if (q2 >= 363) {
            return ((q2 - 363) - (eVar.t() ? 1 : 0)) - eVar.p().ordinal() >= 0 ? s + 1 : s;
        }
        return s;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int n(int i) {
        j$.time.e w = j$.time.e.w(i, 1, 1);
        if (w.p() != j$.time.c.THURSDAY) {
            return (w.p() == j$.time.c.WEDNESDAY && w.t()) ? 53 : 52;
        }
        return 53;
    }

    public static h valueOf(String str) {
        return (h) Enum.valueOf(h.class, str);
    }

    public static h[] values() {
        return (h[]) b.clone();
    }

    @Override // j$.time.temporal.l
    public boolean a() {
        return false;
    }

    @Override // j$.time.temporal.l
    public boolean b() {
        return true;
    }

    public x g(TemporalAccessor temporalAccessor) {
        return c();
    }
}
