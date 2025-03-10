package j$.time.temporal;

import j$.time.ZoneOffset;

/* loaded from: classes2.dex */
public final /* synthetic */ class p implements u {
    public static final /* synthetic */ p a = new p();

    private /* synthetic */ p() {
    }

    @Override // j$.time.temporal.u
    public final Object a(TemporalAccessor temporalAccessor) {
        int i = t.a;
        a aVar = a.OFFSET_SECONDS;
        if (temporalAccessor.j(aVar)) {
            return ZoneOffset.p(temporalAccessor.c(aVar));
        }
        return null;
    }
}
