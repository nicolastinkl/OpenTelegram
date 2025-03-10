package j$.time.zone;

import j$.lang.d;
import j$.time.Instant;
import j$.time.ZoneOffset;
import j$.time.e;
import j$.time.g;
import j$.util.AbstractC0112a;
import j$.util.concurrent.ConcurrentHashMap;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentMap;

/* loaded from: classes2.dex */
public final class c implements Serializable {
    private static final long[] h = new long[0];
    private static final b[] i = new b[0];
    private static final g[] j = new g[0];
    private final long[] a;
    private final ZoneOffset[] b;
    private final long[] c;
    private final g[] d;
    private final ZoneOffset[] e;
    private final b[] f;
    private final transient ConcurrentMap g = new ConcurrentHashMap();

    private c(ZoneOffset zoneOffset) {
        ZoneOffset[] zoneOffsetArr = {zoneOffset};
        this.b = zoneOffsetArr;
        long[] jArr = h;
        this.a = jArr;
        this.c = jArr;
        this.d = j;
        this.e = zoneOffsetArr;
        this.f = i;
    }

    private Object a(g gVar, a aVar) {
        g b = aVar.b();
        boolean h2 = aVar.h();
        boolean r = gVar.r(b);
        return h2 ? r ? aVar.f() : gVar.r(aVar.a()) ? aVar : aVar.e() : !r ? aVar.e() : gVar.r(aVar.a()) ? aVar.f() : aVar;
    }

    private a[] b(int i2) {
        Integer valueOf = Integer.valueOf(i2);
        a[] aVarArr = (a[]) this.g.get(valueOf);
        if (aVarArr != null) {
            return aVarArr;
        }
        b[] bVarArr = this.f;
        a[] aVarArr2 = new a[bVarArr.length];
        if (bVarArr.length > 0) {
            b bVar = bVarArr[0];
            throw null;
        }
        if (i2 < 2100) {
            this.g.putIfAbsent(valueOf, aVarArr2);
        }
        return aVarArr2;
    }

    private int c(long j2, ZoneOffset zoneOffset) {
        return e.x(d.h(j2 + zoneOffset.o(), 86400L)).s();
    }

    private Object e(g gVar) {
        int i2 = 0;
        if (this.c.length == 0) {
            return this.b[0];
        }
        if (this.f.length > 0) {
            if (gVar.q(this.d[r0.length - 1])) {
                a[] b = b(gVar.p());
                Object obj = null;
                int length = b.length;
                while (i2 < length) {
                    a aVar = b[i2];
                    Object a = a(gVar, aVar);
                    if ((a instanceof a) || a.equals(aVar.f())) {
                        return a;
                    }
                    i2++;
                    obj = a;
                }
                return obj;
            }
        }
        int binarySearch = Arrays.binarySearch(this.d, gVar);
        if (binarySearch == -1) {
            return this.e[0];
        }
        if (binarySearch < 0) {
            binarySearch = (-binarySearch) - 2;
        } else {
            Object[] objArr = this.d;
            if (binarySearch < objArr.length - 1) {
                int i3 = binarySearch + 1;
                if (objArr[binarySearch].equals(objArr[i3])) {
                    binarySearch = i3;
                }
            }
        }
        if ((binarySearch & 1) != 0) {
            return this.e[(binarySearch / 2) + 1];
        }
        g[] gVarArr = this.d;
        g gVar2 = gVarArr[binarySearch];
        g gVar3 = gVarArr[binarySearch + 1];
        ZoneOffset[] zoneOffsetArr = this.e;
        int i4 = binarySearch / 2;
        ZoneOffset zoneOffset = zoneOffsetArr[i4];
        ZoneOffset zoneOffset2 = zoneOffsetArr[i4 + 1];
        return zoneOffset2.o() > zoneOffset.o() ? new a(gVar2, zoneOffset, zoneOffset2) : new a(gVar3, zoneOffset, zoneOffset2);
    }

    public static c j(ZoneOffset zoneOffset) {
        return new c(zoneOffset);
    }

    public ZoneOffset d(Instant instant) {
        if (this.c.length == 0) {
            return this.b[0];
        }
        long epochSecond = instant.getEpochSecond();
        if (this.f.length > 0) {
            if (epochSecond > this.c[r8.length - 1]) {
                a[] b = b(c(epochSecond, this.e[r8.length - 1]));
                a aVar = null;
                for (int i2 = 0; i2 < b.length; i2++) {
                    aVar = b[i2];
                    if (epochSecond < aVar.i()) {
                        return aVar.f();
                    }
                }
                return aVar.e();
            }
        }
        int binarySearch = Arrays.binarySearch(this.c, epochSecond);
        if (binarySearch < 0) {
            binarySearch = (-binarySearch) - 2;
        }
        return this.e[binarySearch + 1];
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof c)) {
            return false;
        }
        c cVar = (c) obj;
        Objects.requireNonNull(cVar);
        return AbstractC0112a.u(null, null) && Arrays.equals(this.a, cVar.a) && Arrays.equals(this.b, cVar.b) && Arrays.equals(this.c, cVar.c) && Arrays.equals(this.e, cVar.e) && Arrays.equals(this.f, cVar.f);
    }

    public a f(g gVar) {
        Object e = e(gVar);
        if (e instanceof a) {
            return (a) e;
        }
        return null;
    }

    public List g(g gVar) {
        Object e = e(gVar);
        return e instanceof a ? ((a) e).g() : Collections.singletonList((ZoneOffset) e);
    }

    public boolean h(Instant instant) {
        ZoneOffset zoneOffset;
        if (this.c.length == 0) {
            zoneOffset = this.b[0];
        } else {
            int binarySearch = Arrays.binarySearch(this.a, instant.getEpochSecond());
            if (binarySearch < 0) {
                binarySearch = (-binarySearch) - 2;
            }
            zoneOffset = this.b[binarySearch + 1];
        }
        return !zoneOffset.equals(d(instant));
    }

    public int hashCode() {
        return ((((Arrays.hashCode(this.a) ^ 0) ^ Arrays.hashCode(this.b)) ^ Arrays.hashCode(this.c)) ^ Arrays.hashCode(this.e)) ^ Arrays.hashCode(this.f);
    }

    public boolean i() {
        return this.c.length == 0;
    }

    public String toString() {
        StringBuilder a = j$.time.a.a("ZoneRules[currentStandardOffset=");
        a.append(this.b[r1.length - 1]);
        a.append("]");
        return a.toString();
    }
}
