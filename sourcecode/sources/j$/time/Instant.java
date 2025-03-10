package j$.time;

import j$.time.format.DateTimeFormatter;
import j$.time.temporal.Temporal;
import j$.time.temporal.TemporalAccessor;
import j$.time.temporal.q;
import j$.time.temporal.r;
import j$.time.temporal.s;
import j$.time.temporal.t;
import j$.time.temporal.u;
import j$.time.temporal.v;
import j$.time.temporal.w;
import j$.time.temporal.x;
import java.io.Serializable;
import java.util.Objects;
import org.telegram.messenger.MediaController;

/* loaded from: classes2.dex */
public final class Instant implements Temporal, j$.time.temporal.k, Comparable<Instant>, Serializable {
    private final long a;
    private final int b;
    public static final Instant c = new Instant(0, 0);
    public static final Instant MIN = p(-31557014167219200L, 0);
    public static final Instant MAX = p(31556889864403199L, 999999999);

    static /* synthetic */ class a {
        static final /* synthetic */ int[] a;
        static final /* synthetic */ int[] b;

        static {
            int[] iArr = new int[j$.time.temporal.b.values().length];
            b = iArr;
            try {
                iArr[j$.time.temporal.b.NANOS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                b[j$.time.temporal.b.MICROS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                b[j$.time.temporal.b.MILLIS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                b[j$.time.temporal.b.SECONDS.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                b[j$.time.temporal.b.MINUTES.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                b[j$.time.temporal.b.HOURS.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                b[j$.time.temporal.b.HALF_DAYS.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                b[j$.time.temporal.b.DAYS.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            int[] iArr2 = new int[j$.time.temporal.a.values().length];
            a = iArr2;
            try {
                iArr2[j$.time.temporal.a.NANO_OF_SECOND.ordinal()] = 1;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                a[j$.time.temporal.a.MICRO_OF_SECOND.ordinal()] = 2;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                a[j$.time.temporal.a.MILLI_OF_SECOND.ordinal()] = 3;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                a[j$.time.temporal.a.INSTANT_SECONDS.ordinal()] = 4;
            } catch (NoSuchFieldError unused12) {
            }
        }
    }

    private Instant(long j, int i) {
        this.a = j;
        this.b = i;
    }

    private static Instant l(long j, int i) {
        if ((i | j) == 0) {
            return c;
        }
        if (j < -31557014167219200L || j > 31556889864403199L) {
            throw new b("Instant exceeds minimum or maximum instant");
        }
        return new Instant(j, i);
    }

    public static Instant m(TemporalAccessor temporalAccessor) {
        if (temporalAccessor instanceof Instant) {
            return (Instant) temporalAccessor;
        }
        Objects.requireNonNull(temporalAccessor, "temporal");
        try {
            return p(temporalAccessor.e(j$.time.temporal.a.INSTANT_SECONDS), temporalAccessor.c(j$.time.temporal.a.NANO_OF_SECOND));
        } catch (b e) {
            throw new b("Unable to obtain Instant from TemporalAccessor: " + temporalAccessor + " of type " + temporalAccessor.getClass().getName(), e);
        }
    }

    private long o(Instant instant) {
        return j$.lang.d.f(j$.lang.d.i(j$.lang.d.j(instant.a, this.a), 1000000000L), instant.b - this.b);
    }

    public static Instant ofEpochSecond(long j) {
        return l(j, 0);
    }

    public static Instant p(long j, long j2) {
        return l(j$.lang.d.f(j, j$.lang.d.h(j2, 1000000000L)), (int) j$.lang.d.g(j2, 1000000000L));
    }

    private Instant q(long j, long j2) {
        if ((j | j2) == 0) {
            return this;
        }
        return p(j$.lang.d.f(j$.lang.d.f(this.a, j), j2 / 1000000000), this.b + (j2 % 1000000000));
    }

    private long s(Instant instant) {
        long j = j$.lang.d.j(instant.a, this.a);
        long j2 = instant.b - this.b;
        return (j <= 0 || j2 >= 0) ? (j >= 0 || j2 <= 0) ? j : j + 1 : j - 1;
    }

    @Override // j$.time.temporal.Temporal
    public Temporal a(j$.time.temporal.k kVar) {
        return (Instant) ((e) kVar).h(this);
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0046, code lost:
    
        if (r3 != r2.b) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0050, code lost:
    
        r4 = r2.a;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x004e, code lost:
    
        if (r3 != r2.b) goto L22;
     */
    @Override // j$.time.temporal.Temporal
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public j$.time.temporal.Temporal b(j$.time.temporal.l r3, long r4) {
        /*
            r2 = this;
            boolean r0 = r3 instanceof j$.time.temporal.a
            if (r0 == 0) goto L68
            r0 = r3
            j$.time.temporal.a r0 = (j$.time.temporal.a) r0
            r0.i(r4)
            int[] r1 = j$.time.Instant.a.a
            int r0 = r0.ordinal()
            r0 = r1[r0]
            r1 = 1
            if (r0 == r1) goto L57
            r1 = 2
            if (r0 == r1) goto L49
            r1 = 3
            if (r0 == r1) goto L3e
            r1 = 4
            if (r0 != r1) goto L27
            long r0 = r2.a
            int r3 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1))
            if (r3 == 0) goto L66
            int r3 = r2.b
            goto L52
        L27:
            j$.time.temporal.w r4 = new j$.time.temporal.w
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r0 = "Unsupported field: "
            r5.append(r0)
            r5.append(r3)
            java.lang.String r3 = r5.toString()
            r4.<init>(r3)
            throw r4
        L3e:
            int r3 = (int) r4
            r4 = 1000000(0xf4240, float:1.401298E-39)
            int r3 = r3 * r4
            int r4 = r2.b
            if (r3 == r4) goto L66
            goto L50
        L49:
            int r3 = (int) r4
            int r3 = r3 * 1000
            int r4 = r2.b
            if (r3 == r4) goto L66
        L50:
            long r4 = r2.a
        L52:
            j$.time.Instant r3 = l(r4, r3)
            goto L6e
        L57:
            int r3 = r2.b
            long r0 = (long) r3
            int r3 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1))
            if (r3 == 0) goto L66
            long r0 = r2.a
            int r3 = (int) r4
            j$.time.Instant r3 = l(r0, r3)
            goto L6e
        L66:
            r3 = r2
            goto L6e
        L68:
            j$.time.temporal.Temporal r3 = r3.f(r2, r4)
            j$.time.Instant r3 = (j$.time.Instant) r3
        L6e:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.time.Instant.b(j$.time.temporal.l, long):j$.time.temporal.Temporal");
    }

    @Override // j$.time.temporal.TemporalAccessor
    public int c(j$.time.temporal.l lVar) {
        if (!(lVar instanceof j$.time.temporal.a)) {
            return j$.lang.d.d(this, lVar).a(lVar.d(this), lVar);
        }
        int i = a.a[((j$.time.temporal.a) lVar).ordinal()];
        if (i == 1) {
            return this.b;
        }
        if (i == 2) {
            return this.b / 1000;
        }
        if (i == 3) {
            return this.b / MediaController.VIDEO_BITRATE_480;
        }
        if (i == 4) {
            j$.time.temporal.a.INSTANT_SECONDS.h(this.a);
        }
        throw new w("Unsupported field: " + lVar);
    }

    @Override // j$.time.temporal.TemporalAccessor
    public x d(j$.time.temporal.l lVar) {
        return j$.lang.d.d(this, lVar);
    }

    @Override // j$.time.temporal.TemporalAccessor
    public long e(j$.time.temporal.l lVar) {
        int i;
        if (!(lVar instanceof j$.time.temporal.a)) {
            return lVar.d(this);
        }
        int i2 = a.a[((j$.time.temporal.a) lVar).ordinal()];
        if (i2 == 1) {
            i = this.b;
        } else if (i2 == 2) {
            i = this.b / 1000;
        } else {
            if (i2 != 3) {
                if (i2 == 4) {
                    return this.a;
                }
                throw new w("Unsupported field: " + lVar);
            }
            i = this.b / MediaController.VIDEO_BITRATE_480;
        }
        return i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Instant)) {
            return false;
        }
        Instant instant = (Instant) obj;
        return this.a == instant.a && this.b == instant.b;
    }

    @Override // j$.time.temporal.Temporal
    public Temporal f(long j, v vVar) {
        long j2;
        if (!(vVar instanceof j$.time.temporal.b)) {
            return (Instant) vVar.d(this, j);
        }
        switch (a.b[((j$.time.temporal.b) vVar).ordinal()]) {
            case 1:
                return q(0L, j);
            case 2:
                return q(j / 1000000, (j % 1000000) * 1000);
            case 3:
                return q(j / 1000, (j % 1000) * 1000000);
            case 4:
                return q(j, 0L);
            case 5:
                j2 = 60;
                break;
            case 6:
                j2 = 3600;
                break;
            case 7:
                j2 = 43200;
                break;
            case 8:
                j2 = 86400;
                break;
            default:
                throw new w("Unsupported unit: " + vVar);
        }
        return r(j$.lang.d.i(j, j2));
    }

    @Override // j$.time.temporal.TemporalAccessor
    public Object g(u uVar) {
        int i = t.a;
        if (uVar == j$.time.temporal.o.a) {
            return j$.time.temporal.b.NANOS;
        }
        if (uVar == j$.time.temporal.n.a || uVar == j$.time.temporal.m.a || uVar == q.a || uVar == j$.time.temporal.p.a || uVar == r.a || uVar == s.a) {
            return null;
        }
        return uVar.a(this);
    }

    public long getEpochSecond() {
        return this.a;
    }

    @Override // j$.time.temporal.k
    public Temporal h(Temporal temporal) {
        return temporal.b(j$.time.temporal.a.INSTANT_SECONDS, this.a).b(j$.time.temporal.a.NANO_OF_SECOND, this.b);
    }

    public int hashCode() {
        long j = this.a;
        return (this.b * 51) + ((int) (j ^ (j >>> 32)));
    }

    @Override // j$.time.temporal.Temporal
    public long i(Temporal temporal, v vVar) {
        Instant m = m(temporal);
        if (!(vVar instanceof j$.time.temporal.b)) {
            return vVar.c(this, m);
        }
        switch (a.b[((j$.time.temporal.b) vVar).ordinal()]) {
            case 1:
                return o(m);
            case 2:
                return o(m) / 1000;
            case 3:
                return j$.lang.d.j(m.t(), t());
            case 4:
                return s(m);
            case 5:
                return s(m) / 60;
            case 6:
                return s(m) / 3600;
            case 7:
                return s(m) / 43200;
            case 8:
                return s(m) / 86400;
            default:
                throw new w("Unsupported unit: " + vVar);
        }
    }

    public boolean isAfter(Instant instant) {
        return compareTo(instant) > 0;
    }

    @Override // j$.time.temporal.TemporalAccessor
    public boolean j(j$.time.temporal.l lVar) {
        return lVar instanceof j$.time.temporal.a ? lVar == j$.time.temporal.a.INSTANT_SECONDS || lVar == j$.time.temporal.a.NANO_OF_SECOND || lVar == j$.time.temporal.a.MICRO_OF_SECOND || lVar == j$.time.temporal.a.MILLI_OF_SECOND : lVar != null && lVar.e(this);
    }

    @Override // java.lang.Comparable
    /* renamed from: k, reason: merged with bridge method [inline-methods] */
    public int compareTo(Instant instant) {
        int compare = Long.compare(this.a, instant.a);
        return compare != 0 ? compare : this.b - instant.b;
    }

    public int n() {
        return this.b;
    }

    public Instant r(long j) {
        return q(j, 0L);
    }

    public long t() {
        long i;
        int i2;
        long j = this.a;
        if (j >= 0 || this.b <= 0) {
            i = j$.lang.d.i(j, 1000L);
            i2 = this.b / MediaController.VIDEO_BITRATE_480;
        } else {
            i = j$.lang.d.i(j + 1, 1000L);
            i2 = (this.b / MediaController.VIDEO_BITRATE_480) - 1000;
        }
        return j$.lang.d.f(i, i2);
    }

    public String toString() {
        return DateTimeFormatter.h.format(this);
    }
}
