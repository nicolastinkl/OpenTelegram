package j$.time.temporal;

/* loaded from: classes2.dex */
public final /* synthetic */ class r implements u {
    public static final /* synthetic */ r a = new r();

    private /* synthetic */ r() {
    }

    @Override // j$.time.temporal.u
    public final Object a(TemporalAccessor temporalAccessor) {
        int i = t.a;
        a aVar = a.EPOCH_DAY;
        if (temporalAccessor.j(aVar)) {
            return j$.time.e.x(temporalAccessor.e(aVar));
        }
        return null;
    }
}
