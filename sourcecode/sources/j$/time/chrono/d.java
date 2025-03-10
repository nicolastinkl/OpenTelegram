package j$.time.chrono;

import j$.time.p;
import j$.time.temporal.TemporalAccessor;
import j$.time.temporal.l;
import j$.time.temporal.n;
import j$.time.temporal.t;
import j$.time.temporal.w;
import java.util.Objects;

/* loaded from: classes2.dex */
public abstract /* synthetic */ class d {
    public static int a(f fVar, l lVar) {
        if (!(lVar instanceof j$.time.temporal.a)) {
            return j$.lang.d.b(fVar, lVar);
        }
        int i = e.a[((j$.time.temporal.a) lVar).ordinal()];
        if (i == 1) {
            throw new w("Invalid field 'InstantSeconds' for get() method, use getLong() instead");
        }
        p pVar = (p) fVar;
        return i != 2 ? ((j$.time.g) pVar.u()).c(lVar) : pVar.l().o();
    }

    public static g b(TemporalAccessor temporalAccessor) {
        Objects.requireNonNull(temporalAccessor, "temporal");
        int i = t.a;
        g gVar = (g) temporalAccessor.g(n.a);
        return gVar != null ? gVar : h.a;
    }
}
