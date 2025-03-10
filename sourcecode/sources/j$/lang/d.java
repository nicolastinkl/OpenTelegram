package j$.lang;

import j$.time.temporal.TemporalAccessor;
import j$.time.temporal.l;
import j$.time.temporal.m;
import j$.time.temporal.n;
import j$.time.temporal.o;
import j$.time.temporal.t;
import j$.time.temporal.u;
import j$.time.temporal.w;
import j$.time.temporal.x;
import j$.util.function.BiConsumer;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentMap;

/* loaded from: classes2.dex */
public abstract /* synthetic */ class d {
    public static void a(ConcurrentMap concurrentMap, BiConsumer biConsumer) {
        Objects.requireNonNull(biConsumer);
        for (Map.Entry entry : concurrentMap.entrySet()) {
            try {
                biConsumer.accept(entry.getKey(), entry.getValue());
            } catch (IllegalStateException unused) {
            }
        }
    }

    public static int b(TemporalAccessor temporalAccessor, l lVar) {
        x d = temporalAccessor.d(lVar);
        if (!d.g()) {
            throw new w("Invalid field " + lVar + " for get() method, use getLong() instead");
        }
        long e = temporalAccessor.e(lVar);
        if (d.h(e)) {
            return (int) e;
        }
        throw new j$.time.b("Invalid value for " + lVar + " (valid values " + d + "): " + e);
    }

    public static Object c(TemporalAccessor temporalAccessor, u uVar) {
        int i = t.a;
        if (uVar == m.a || uVar == n.a || uVar == o.a) {
            return null;
        }
        return uVar.a(temporalAccessor);
    }

    public static x d(TemporalAccessor temporalAccessor, l lVar) {
        if (!(lVar instanceof j$.time.temporal.a)) {
            Objects.requireNonNull(lVar, "field");
            return lVar.g(temporalAccessor);
        }
        if (temporalAccessor.j(lVar)) {
            return lVar.c();
        }
        throw new w("Unsupported field: " + lVar);
    }

    public static /* synthetic */ int e(int i, int i2) {
        int i3 = i % i2;
        if (i3 == 0) {
            return 0;
        }
        return (((i ^ i2) >> 31) | 1) > 0 ? i3 : i3 + i2;
    }

    public static /* synthetic */ long f(long j, long j2) {
        long j3 = j + j2;
        if (((j2 ^ j) < 0) || ((j ^ j3) >= 0)) {
            return j3;
        }
        throw new ArithmeticException();
    }

    public static /* synthetic */ long g(long j, long j2) {
        long j3 = j % j2;
        if (j3 == 0) {
            return 0L;
        }
        return (((j ^ j2) >> 63) | 1) > 0 ? j3 : j3 + j2;
    }

    public static /* synthetic */ long h(long j, long j2) {
        long j3 = j / j2;
        return (j - (j2 * j3) != 0 && (((j ^ j2) >> 63) | 1) < 0) ? j3 - 1 : j3;
    }

    public static /* synthetic */ long i(long j, long j2) {
        int numberOfLeadingZeros = Long.numberOfLeadingZeros(~j2) + Long.numberOfLeadingZeros(j2) + Long.numberOfLeadingZeros(~j) + Long.numberOfLeadingZeros(j);
        if (numberOfLeadingZeros > 65) {
            return j * j2;
        }
        if (numberOfLeadingZeros >= 64) {
            if ((j >= 0) | (j2 != Long.MIN_VALUE)) {
                long j3 = j * j2;
                if (j == 0 || j3 / j == j2) {
                    return j3;
                }
            }
        }
        throw new ArithmeticException();
    }

    public static /* synthetic */ long j(long j, long j2) {
        long j3 = j - j2;
        if (((j2 ^ j) >= 0) || ((j ^ j3) >= 0)) {
            return j3;
        }
        throw new ArithmeticException();
    }
}
