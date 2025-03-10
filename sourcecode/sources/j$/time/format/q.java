package j$.time.format;

import j$.time.temporal.TemporalAccessor;
import java.util.Locale;
import java.util.Objects;

/* loaded from: classes2.dex */
final class q implements h {
    private final j$.time.temporal.l a;
    private final B b;
    private final x c;
    private volatile l d;

    q(j$.time.temporal.l lVar, B b, x xVar) {
        this.a = lVar;
        this.b = b;
        this.c = xVar;
    }

    @Override // j$.time.format.h
    public boolean a(v vVar, StringBuilder sb) {
        String c;
        j$.time.chrono.h hVar;
        Long e = vVar.e(this.a);
        if (e == null) {
            return false;
        }
        TemporalAccessor d = vVar.d();
        int i = j$.time.temporal.t.a;
        j$.time.chrono.g gVar = (j$.time.chrono.g) d.g(j$.time.temporal.n.a);
        if (gVar == null || gVar == (hVar = j$.time.chrono.h.a)) {
            c = this.c.c(this.a, e.longValue(), this.b, vVar.c());
        } else {
            x xVar = this.c;
            j$.time.temporal.l lVar = this.a;
            long longValue = e.longValue();
            B b = this.b;
            Locale c2 = vVar.c();
            Objects.requireNonNull(xVar);
            c = (gVar == hVar || !(lVar instanceof j$.time.temporal.a)) ? xVar.c(lVar, longValue, b, c2) : null;
        }
        if (c != null) {
            sb.append(c);
            return true;
        }
        if (this.d == null) {
            this.d = new l(this.a, 1, 19, A.NORMAL);
        }
        return this.d.a(vVar, sb);
    }

    public String toString() {
        StringBuilder a;
        Object obj;
        if (this.b == B.FULL) {
            a = j$.time.a.a("Text(");
            obj = this.a;
        } else {
            a = j$.time.a.a("Text(");
            a.append(this.a);
            a.append(",");
            obj = this.b;
        }
        a.append(obj);
        a.append(")");
        return a.toString();
    }
}
