package j$.time;

import com.tencent.beacon.pack.AbstractJceStruct;
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
public final class i implements Temporal, j$.time.temporal.k, Comparable, Serializable {
    public static final i e;
    public static final i f;
    private static final i[] g = new i[24];
    private final byte a;
    private final byte b;
    private final byte c;
    private final int d;

    static {
        int i = 0;
        while (true) {
            i[] iVarArr = g;
            if (i >= iVarArr.length) {
                i iVar = iVarArr[0];
                i iVar2 = iVarArr[12];
                e = iVarArr[0];
                f = new i(23, 59, 59, 999999999);
                return;
            }
            iVarArr[i] = new i(i, 0, 0, 0);
            i++;
        }
    }

    private i(int i, int i2, int i3, int i4) {
        this.a = (byte) i;
        this.b = (byte) i2;
        this.c = (byte) i3;
        this.d = i4;
    }

    private static i l(int i, int i2, int i3, int i4) {
        return ((i2 | i3) | i4) == 0 ? g[i] : new i(i, i2, i3, i4);
    }

    public static i m(TemporalAccessor temporalAccessor) {
        Objects.requireNonNull(temporalAccessor, "temporal");
        int i = t.a;
        i iVar = (i) temporalAccessor.g(s.a);
        if (iVar != null) {
            return iVar;
        }
        throw new b("Unable to obtain LocalTime from TemporalAccessor: " + temporalAccessor + " of type " + temporalAccessor.getClass().getName());
    }

    private int n(j$.time.temporal.l lVar) {
        switch (h.a[((j$.time.temporal.a) lVar).ordinal()]) {
            case 1:
                return this.d;
            case 2:
                throw new w("Invalid field 'NanoOfDay' for get() method, use getLong() instead");
            case 3:
                return this.d / 1000;
            case 4:
                throw new w("Invalid field 'MicroOfDay' for get() method, use getLong() instead");
            case 5:
                return this.d / MediaController.VIDEO_BITRATE_480;
            case 6:
                return (int) (v() / 1000000);
            case 7:
                return this.c;
            case 8:
                return w();
            case 9:
                return this.b;
            case 10:
                return (this.a * 60) + this.b;
            case 11:
                return this.a % AbstractJceStruct.ZERO_TAG;
            case 12:
                int i = this.a % AbstractJceStruct.ZERO_TAG;
                if (i % 12 == 0) {
                    return 12;
                }
                return i;
            case 13:
                return this.a;
            case 14:
                byte b = this.a;
                if (b == 0) {
                    return 24;
                }
                return b;
            case 15:
                return this.a / AbstractJceStruct.ZERO_TAG;
            default:
                throw new w("Unsupported field: " + lVar);
        }
    }

    public static i q(long j) {
        j$.time.temporal.a.NANO_OF_DAY.i(j);
        int i = (int) (j / 3600000000000L);
        long j2 = j - (i * 3600000000000L);
        int i2 = (int) (j2 / 60000000000L);
        long j3 = j2 - (i2 * 60000000000L);
        int i3 = (int) (j3 / 1000000000);
        return l(i, i2, i3, (int) (j3 - (i3 * 1000000000)));
    }

    @Override // j$.time.temporal.Temporal
    public Temporal a(j$.time.temporal.k kVar) {
        boolean z = kVar instanceof i;
        Object obj = kVar;
        if (!z) {
            obj = ((e) kVar).h(this);
        }
        return (i) obj;
    }

    @Override // j$.time.temporal.TemporalAccessor
    public int c(j$.time.temporal.l lVar) {
        return lVar instanceof j$.time.temporal.a ? n(lVar) : j$.lang.d.b(this, lVar);
    }

    @Override // j$.time.temporal.TemporalAccessor
    public x d(j$.time.temporal.l lVar) {
        return j$.lang.d.d(this, lVar);
    }

    @Override // j$.time.temporal.TemporalAccessor
    public long e(j$.time.temporal.l lVar) {
        return lVar instanceof j$.time.temporal.a ? lVar == j$.time.temporal.a.NANO_OF_DAY ? v() : lVar == j$.time.temporal.a.MICRO_OF_DAY ? v() / 1000 : n(lVar) : lVar.d(this);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof i)) {
            return false;
        }
        i iVar = (i) obj;
        return this.a == iVar.a && this.b == iVar.b && this.c == iVar.c && this.d == iVar.d;
    }

    @Override // j$.time.temporal.Temporal
    public Temporal f(long j, v vVar) {
        long j2;
        long j3;
        if (!(vVar instanceof j$.time.temporal.b)) {
            return (i) vVar.d(this, j);
        }
        switch (h.b[((j$.time.temporal.b) vVar).ordinal()]) {
            case 1:
                return t(j);
            case 2:
                j2 = j % 86400000000L;
                j3 = 1000;
                j = j2 * j3;
                return t(j);
            case 3:
                j2 = j % 86400000;
                j3 = 1000000;
                j = j2 * j3;
                return t(j);
            case 4:
                return u(j);
            case 5:
                return s(j);
            case 7:
                j = (j % 2) * 12;
            case 6:
                return r(j);
            default:
                throw new w("Unsupported unit: " + vVar);
        }
    }

    @Override // j$.time.temporal.TemporalAccessor
    public Object g(u uVar) {
        int i = t.a;
        if (uVar == j$.time.temporal.n.a || uVar == j$.time.temporal.m.a || uVar == q.a || uVar == j$.time.temporal.p.a) {
            return null;
        }
        if (uVar == s.a) {
            return this;
        }
        if (uVar == r.a) {
            return null;
        }
        return uVar == j$.time.temporal.o.a ? j$.time.temporal.b.NANOS : uVar.a(this);
    }

    @Override // j$.time.temporal.k
    public Temporal h(Temporal temporal) {
        return temporal.b(j$.time.temporal.a.NANO_OF_DAY, v());
    }

    public int hashCode() {
        long v = v();
        return (int) (v ^ (v >>> 32));
    }

    @Override // j$.time.temporal.Temporal
    public long i(Temporal temporal, v vVar) {
        long j;
        i m = m(temporal);
        if (!(vVar instanceof j$.time.temporal.b)) {
            return vVar.c(this, m);
        }
        long v = m.v() - v();
        switch (h.b[((j$.time.temporal.b) vVar).ordinal()]) {
            case 1:
                return v;
            case 2:
                j = 1000;
                break;
            case 3:
                j = 1000000;
                break;
            case 4:
                j = 1000000000;
                break;
            case 5:
                j = 60000000000L;
                break;
            case 6:
                j = 3600000000000L;
                break;
            case 7:
                j = 43200000000000L;
                break;
            default:
                throw new w("Unsupported unit: " + vVar);
        }
        return v / j;
    }

    @Override // j$.time.temporal.TemporalAccessor
    public boolean j(j$.time.temporal.l lVar) {
        return lVar instanceof j$.time.temporal.a ? lVar.a() : lVar != null && lVar.e(this);
    }

    @Override // java.lang.Comparable
    /* renamed from: k, reason: merged with bridge method [inline-methods] */
    public int compareTo(i iVar) {
        int compare = Integer.compare(this.a, iVar.a);
        if (compare != 0) {
            return compare;
        }
        int compare2 = Integer.compare(this.b, iVar.b);
        if (compare2 != 0) {
            return compare2;
        }
        int compare3 = Integer.compare(this.c, iVar.c);
        return compare3 == 0 ? Integer.compare(this.d, iVar.d) : compare3;
    }

    public int o() {
        return this.d;
    }

    public int p() {
        return this.c;
    }

    public i r(long j) {
        return j == 0 ? this : l(((((int) (j % 24)) + this.a) + 24) % 24, this.b, this.c, this.d);
    }

    public i s(long j) {
        if (j == 0) {
            return this;
        }
        int i = (this.a * 60) + this.b;
        int i2 = ((((int) (j % 1440)) + i) + 1440) % 1440;
        return i == i2 ? this : l(i2 / 60, i2 % 60, this.c, this.d);
    }

    public i t(long j) {
        if (j == 0) {
            return this;
        }
        long v = v();
        long j2 = (((j % 86400000000000L) + v) + 86400000000000L) % 86400000000000L;
        return v == j2 ? this : l((int) (j2 / 3600000000000L), (int) ((j2 / 60000000000L) % 60), (int) ((j2 / 1000000000) % 60), (int) (j2 % 1000000000));
    }

    public String toString() {
        int i;
        StringBuilder sb = new StringBuilder(18);
        byte b = this.a;
        byte b2 = this.b;
        byte b3 = this.c;
        int i2 = this.d;
        sb.append(b < 10 ? "0" : "");
        sb.append((int) b);
        sb.append(b2 < 10 ? ":0" : ":");
        sb.append((int) b2);
        if (b3 > 0 || i2 > 0) {
            sb.append(b3 >= 10 ? ":" : ":0");
            sb.append((int) b3);
            if (i2 > 0) {
                sb.append('.');
                int i3 = MediaController.VIDEO_BITRATE_480;
                if (i2 % MediaController.VIDEO_BITRATE_480 == 0) {
                    i = (i2 / MediaController.VIDEO_BITRATE_480) + 1000;
                } else {
                    if (i2 % 1000 == 0) {
                        i2 /= 1000;
                    } else {
                        i3 = 1000000000;
                    }
                    i = i2 + i3;
                }
                sb.append(Integer.toString(i).substring(1));
            }
        }
        return sb.toString();
    }

    public i u(long j) {
        if (j == 0) {
            return this;
        }
        int i = (this.b * 60) + (this.a * 3600) + this.c;
        int i2 = ((((int) (j % 86400)) + i) + 86400) % 86400;
        return i == i2 ? this : l(i2 / 3600, (i2 / 60) % 60, i2 % 60, this.d);
    }

    public long v() {
        return (this.c * 1000000000) + (this.b * 60000000000L) + (this.a * 3600000000000L) + this.d;
    }

    public int w() {
        return (this.b * 60) + (this.a * 3600) + this.c;
    }

    @Override // j$.time.temporal.Temporal
    /* renamed from: x, reason: merged with bridge method [inline-methods] */
    public i b(j$.time.temporal.l lVar, long j) {
        int i;
        long j2;
        long j3;
        if (!(lVar instanceof j$.time.temporal.a)) {
            return (i) lVar.f(this, j);
        }
        j$.time.temporal.a aVar = (j$.time.temporal.a) lVar;
        aVar.i(j);
        switch (h.a[aVar.ordinal()]) {
            case 1:
                i = (int) j;
                return z(i);
            case 2:
                return q(j);
            case 3:
                i = ((int) j) * 1000;
                return z(i);
            case 4:
                j2 = 1000;
                j *= j2;
                return q(j);
            case 5:
                i = ((int) j) * MediaController.VIDEO_BITRATE_480;
                return z(i);
            case 6:
                j2 = 1000000;
                j *= j2;
                return q(j);
            case 7:
                int i2 = (int) j;
                if (this.c != i2) {
                    j$.time.temporal.a.SECOND_OF_MINUTE.i(i2);
                    return l(this.a, this.b, i2, this.d);
                }
                return this;
            case 8:
                return u(j - w());
            case 9:
                int i3 = (int) j;
                if (this.b != i3) {
                    j$.time.temporal.a.MINUTE_OF_HOUR.i(i3);
                    return l(this.a, i3, this.c, this.d);
                }
                return this;
            case 10:
                return s(j - ((this.a * 60) + this.b));
            case 12:
                if (j == 12) {
                    j = 0;
                }
            case 11:
                j3 = j - (this.a % AbstractJceStruct.ZERO_TAG);
                return r(j3);
            case 14:
                if (j == 24) {
                    j = 0;
                }
            case 13:
                return y((int) j);
            case 15:
                j3 = (j - (this.a / AbstractJceStruct.ZERO_TAG)) * 12;
                return r(j3);
            default:
                throw new w("Unsupported field: " + lVar);
        }
    }

    public i y(int i) {
        if (this.a == i) {
            return this;
        }
        j$.time.temporal.a.HOUR_OF_DAY.i(i);
        return l(i, this.b, this.c, this.d);
    }

    public i z(int i) {
        if (this.d == i) {
            return this;
        }
        j$.time.temporal.a.NANO_OF_SECOND.i(i);
        return l(this.a, this.b, this.c, i);
    }
}
