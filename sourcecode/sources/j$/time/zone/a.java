package j$.time.zone;

import j$.time.Duration;
import j$.time.Instant;
import j$.time.ZoneOffset;
import j$.time.g;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* loaded from: classes2.dex */
public final class a implements Comparable, Serializable {
    private final g a;
    private final ZoneOffset b;
    private final ZoneOffset c;

    a(g gVar, ZoneOffset zoneOffset, ZoneOffset zoneOffset2) {
        this.a = gVar;
        this.b = zoneOffset;
        this.c = zoneOffset2;
    }

    public g a() {
        return this.a.x(this.c.o() - this.b.o());
    }

    public g b() {
        return this.a;
    }

    public Duration c() {
        return Duration.ofSeconds(this.c.o() - this.b.o());
    }

    @Override // java.lang.Comparable
    public int compareTo(Object obj) {
        return d().compareTo(((a) obj).d());
    }

    public Instant d() {
        return Instant.p(this.a.z(this.b), r0.C().o());
    }

    public ZoneOffset e() {
        return this.c;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof a)) {
            return false;
        }
        a aVar = (a) obj;
        return this.a.equals(aVar.a) && this.b.equals(aVar.b) && this.c.equals(aVar.c);
    }

    public ZoneOffset f() {
        return this.b;
    }

    List g() {
        return h() ? Collections.emptyList() : Arrays.asList(this.b, this.c);
    }

    public boolean h() {
        return this.c.o() > this.b.o();
    }

    public int hashCode() {
        return (this.a.hashCode() ^ this.b.hashCode()) ^ Integer.rotateLeft(this.c.hashCode(), 16);
    }

    public long i() {
        return this.a.z(this.b);
    }

    public String toString() {
        StringBuilder a = j$.time.a.a("Transition[");
        a.append(h() ? "Gap" : "Overlap");
        a.append(" at ");
        a.append(this.a);
        a.append(this.b);
        a.append(" to ");
        a.append(this.c);
        a.append(']');
        return a.toString();
    }
}
