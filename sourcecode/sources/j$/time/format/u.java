package j$.time.format;

import j$.time.ZoneId;
import j$.time.temporal.TemporalAccessor;

/* loaded from: classes2.dex */
class u implements TemporalAccessor {
    final /* synthetic */ j$.time.chrono.b a;
    final /* synthetic */ TemporalAccessor b;
    final /* synthetic */ j$.time.chrono.g c;
    final /* synthetic */ ZoneId d;

    u(j$.time.chrono.b bVar, TemporalAccessor temporalAccessor, j$.time.chrono.g gVar, ZoneId zoneId) {
        this.a = bVar;
        this.b = temporalAccessor;
        this.c = gVar;
        this.d = zoneId;
    }

    @Override // j$.time.temporal.TemporalAccessor
    public /* synthetic */ int c(j$.time.temporal.l lVar) {
        return j$.lang.d.b(this, lVar);
    }

    @Override // j$.time.temporal.TemporalAccessor
    public j$.time.temporal.x d(j$.time.temporal.l lVar) {
        return (this.a == null || !lVar.b()) ? this.b.d(lVar) : ((j$.time.e) this.a).d(lVar);
    }

    @Override // j$.time.temporal.TemporalAccessor
    public long e(j$.time.temporal.l lVar) {
        return (this.a == null || !lVar.b()) ? this.b.e(lVar) : ((j$.time.e) this.a).e(lVar);
    }

    @Override // j$.time.temporal.TemporalAccessor
    public Object g(j$.time.temporal.u uVar) {
        int i = j$.time.temporal.t.a;
        return uVar == j$.time.temporal.n.a ? this.c : uVar == j$.time.temporal.m.a ? this.d : uVar == j$.time.temporal.o.a ? this.b.g(uVar) : uVar.a(this);
    }

    @Override // j$.time.temporal.TemporalAccessor
    public boolean j(j$.time.temporal.l lVar) {
        return (this.a == null || !lVar.b()) ? this.b.j(lVar) : ((j$.time.e) this.a).j(lVar);
    }
}
