package j$.time;

import j$.time.temporal.Temporal;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.regex.Pattern;
import org.telegram.messenger.MediaController;

/* loaded from: classes2.dex */
public final class Duration implements Comparable<Duration>, Serializable {
    public static final Duration c = new Duration(0, 0);
    private static final BigInteger d = BigInteger.valueOf(1000000000);
    private final long a;
    private final int b;

    static {
        Pattern.compile("([-+]?)P(?:([-+]?[0-9]+)D)?(T(?:([-+]?[0-9]+)H)?(?:([-+]?[0-9]+)M)?(?:([-+]?[0-9]+)(?:[.,]([0-9]{0,9}))?S)?)?", 2);
    }

    private Duration(long j, int i) {
        this.a = j;
        this.b = i;
    }

    private static Duration a(long j, int i) {
        return (((long) i) | j) == 0 ? c : new Duration(j, i);
    }

    public static Duration b(long j) {
        long j2 = j / 1000000000;
        int i = (int) (j % 1000000000);
        if (i < 0) {
            i = (int) (i + 1000000000);
            j2--;
        }
        return a(j2, i);
    }

    public static Duration between(Temporal temporal, Temporal temporal2) {
        try {
            return b(temporal.i(temporal2, j$.time.temporal.b.NANOS));
        } catch (b | ArithmeticException unused) {
            long i = temporal.i(temporal2, j$.time.temporal.b.SECONDS);
            long j = 0;
            try {
                j$.time.temporal.a aVar = j$.time.temporal.a.NANO_OF_SECOND;
                long e = temporal2.e(aVar) - temporal.e(aVar);
                if (i > 0 && e < 0) {
                    i++;
                } else if (i < 0 && e > 0) {
                    i--;
                }
                j = e;
            } catch (b unused2) {
            }
            return c(i, j);
        }
    }

    public static Duration c(long j, long j2) {
        return a(j$.lang.d.f(j, j$.lang.d.h(j2, 1000000000L)), (int) j$.lang.d.g(j2, 1000000000L));
    }

    public static Duration ofMillis(long j) {
        long j2 = j / 1000;
        int i = (int) (j % 1000);
        if (i < 0) {
            i += 1000;
            j2--;
        }
        return a(j2, i * MediaController.VIDEO_BITRATE_480);
    }

    public static Duration ofSeconds(long j) {
        return a(j, 0);
    }

    public Duration abs() {
        long j = this.a;
        if (!(j < 0)) {
            return this;
        }
        BigInteger bigIntegerExact = BigDecimal.valueOf(j).add(BigDecimal.valueOf(this.b, 9)).multiply(BigDecimal.valueOf(-1L)).movePointRight(9).toBigIntegerExact();
        BigInteger[] divideAndRemainder = bigIntegerExact.divideAndRemainder(d);
        if (divideAndRemainder[0].bitLength() <= 63) {
            return c(divideAndRemainder[0].longValue(), divideAndRemainder[1].intValue());
        }
        throw new ArithmeticException("Exceeds capacity of Duration: " + bigIntegerExact);
    }

    @Override // java.lang.Comparable
    public int compareTo(Duration duration) {
        int compare = Long.compare(this.a, duration.a);
        return compare != 0 ? compare : this.b - duration.b;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Duration)) {
            return false;
        }
        Duration duration = (Duration) obj;
        return this.a == duration.a && this.b == duration.b;
    }

    public long getSeconds() {
        return this.a;
    }

    public int hashCode() {
        long j = this.a;
        return (this.b * 51) + ((int) (j ^ (j >>> 32)));
    }

    public long toMillis() {
        return j$.lang.d.f(j$.lang.d.i(this.a, 1000L), this.b / MediaController.VIDEO_BITRATE_480);
    }

    public long toNanos() {
        return j$.lang.d.f(j$.lang.d.i(this.a, 1000000000L), this.b);
    }

    public String toString() {
        if (this == c) {
            return "PT0S";
        }
        long j = this.a;
        long j2 = j / 3600;
        int i = (int) ((j % 3600) / 60);
        int i2 = (int) (j % 60);
        StringBuilder sb = new StringBuilder(24);
        sb.append("PT");
        if (j2 != 0) {
            sb.append(j2);
            sb.append('H');
        }
        if (i != 0) {
            sb.append(i);
            sb.append('M');
        }
        if (i2 == 0 && this.b == 0 && sb.length() > 2) {
            return sb.toString();
        }
        if (i2 >= 0 || this.b <= 0) {
            sb.append(i2);
        } else if (i2 == -1) {
            sb.append("-0");
        } else {
            sb.append(i2 + 1);
        }
        if (this.b > 0) {
            int length = sb.length();
            if (i2 < 0) {
                sb.append(2000000000 - this.b);
            } else {
                sb.append(this.b + 1000000000);
            }
            while (sb.charAt(sb.length() - 1) == '0') {
                sb.setLength(sb.length() - 1);
            }
            sb.setCharAt(length, '.');
        }
        sb.append('S');
        return sb.toString();
    }
}
