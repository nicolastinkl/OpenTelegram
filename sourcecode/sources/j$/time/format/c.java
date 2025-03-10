package j$.time.format;

import java.util.Locale;

/* loaded from: classes2.dex */
class c extends x {
    final /* synthetic */ w d;

    c(g gVar, w wVar) {
        this.d = wVar;
    }

    @Override // j$.time.format.x
    public String c(j$.time.temporal.l lVar, long j, B b, Locale locale) {
        return this.d.a(j, b);
    }
}
