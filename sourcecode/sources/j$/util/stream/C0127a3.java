package j$.util.stream;

import java.util.concurrent.CountedCompleter;

/* renamed from: j$.util.stream.a3, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
final class C0127a3 extends AbstractC0153f {
    private final Z2 h;

    C0127a3(Z2 z2, D2 d2, j$.util.r rVar) {
        super(d2, rVar);
        this.h = z2;
    }

    C0127a3(C0127a3 c0127a3, j$.util.r rVar) {
        super(c0127a3, rVar);
        this.h = c0127a3.h;
    }

    @Override // j$.util.stream.AbstractC0153f
    protected Object a() {
        D2 d2 = this.a;
        X2 a = this.h.a();
        d2.r0(a, this.b);
        return a;
    }

    @Override // j$.util.stream.AbstractC0153f
    protected AbstractC0153f f(j$.util.r rVar) {
        return new C0127a3(this, rVar);
    }

    @Override // j$.util.stream.AbstractC0153f, java.util.concurrent.CountedCompleter
    public void onCompletion(CountedCompleter countedCompleter) {
        if (!d()) {
            X2 x2 = (X2) ((C0127a3) this.d).b();
            x2.h((X2) ((C0127a3) this.e).b());
            g(x2);
        }
        this.b = null;
        this.e = null;
        this.d = null;
    }
}
