package j$.time.temporal;

import j$.util.concurrent.ConcurrentHashMap;
import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.ConcurrentMap;

/* loaded from: classes2.dex */
public final class z implements Serializable {
    private static final ConcurrentMap g = new ConcurrentHashMap(4, 0.75f, 2);
    public static final v h;
    private final j$.time.c a;
    private final int b;
    private final transient l c = y.k(this);
    private final transient l d = y.m(this);
    private final transient l e;
    private final transient l f;

    static {
        new z(j$.time.c.MONDAY, 4);
        f(j$.time.c.SUNDAY, 1);
        h = j.d;
    }

    private z(j$.time.c cVar, int i) {
        y.o(this);
        this.e = y.n(this);
        this.f = y.l(this);
        Objects.requireNonNull(cVar, "firstDayOfWeek");
        if (i < 1 || i > 7) {
            throw new IllegalArgumentException("Minimal number of days is invalid");
        }
        this.a = cVar;
        this.b = i;
    }

    public static z f(j$.time.c cVar, int i) {
        String str = cVar.toString() + i;
        ConcurrentMap concurrentMap = g;
        z zVar = (z) concurrentMap.get(str);
        if (zVar != null) {
            return zVar;
        }
        concurrentMap.putIfAbsent(str, new z(cVar, i));
        return (z) concurrentMap.get(str);
    }

    public l c() {
        return this.c;
    }

    public j$.time.c d() {
        return this.a;
    }

    public int e() {
        return this.b;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof z) && hashCode() == obj.hashCode();
    }

    public l g() {
        return this.f;
    }

    public l h() {
        return this.d;
    }

    public int hashCode() {
        return (this.a.ordinal() * 7) + this.b;
    }

    public l i() {
        return this.e;
    }

    public String toString() {
        StringBuilder a = j$.time.a.a("WeekFields[");
        a.append(this.a);
        a.append(',');
        a.append(this.b);
        a.append(']');
        return a.toString();
    }
}
