package j$.time.temporal;

/* loaded from: classes2.dex */
public final /* synthetic */ class s implements u {
    public static final /* synthetic */ s a = new s();

    private /* synthetic */ s() {
    }

    @Override // j$.time.temporal.u
    public final Object a(TemporalAccessor temporalAccessor) {
        int i = t.a;
        a aVar = a.NANO_OF_DAY;
        if (temporalAccessor.j(aVar)) {
            return j$.time.i.q(temporalAccessor.e(aVar));
        }
        return null;
    }
}
