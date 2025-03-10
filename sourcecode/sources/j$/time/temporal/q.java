package j$.time.temporal;

import j$.time.ZoneId;

/* loaded from: classes2.dex */
public final /* synthetic */ class q implements u {
    public static final /* synthetic */ q a = new q();

    private /* synthetic */ q() {
    }

    @Override // j$.time.temporal.u
    public final Object a(TemporalAccessor temporalAccessor) {
        int i = t.a;
        ZoneId zoneId = (ZoneId) temporalAccessor.g(m.a);
        return zoneId != null ? zoneId : (ZoneId) temporalAccessor.g(p.a);
    }
}
