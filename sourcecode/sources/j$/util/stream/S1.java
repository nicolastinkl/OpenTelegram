package j$.util.stream;

import java.util.concurrent.CountedCompleter;

/* loaded from: classes2.dex */
class S1 extends AbstractC0153f {
    protected final D2 h;
    protected final j$.util.function.t i;
    protected final j$.util.function.b j;

    S1(D2 d2, j$.util.r rVar, j$.util.function.t tVar, j$.util.function.b bVar) {
        super(d2, rVar);
        this.h = d2;
        this.i = tVar;
        this.j = bVar;
    }

    S1(S1 s1, j$.util.r rVar) {
        super(s1, rVar);
        this.h = s1.h;
        this.i = s1.i;
        this.j = s1.j;
    }

    @Override // j$.util.stream.AbstractC0153f
    protected Object a() {
        InterfaceC0261x1 interfaceC0261x1 = (InterfaceC0261x1) this.i.apply(this.h.n0(this.b));
        this.h.r0(interfaceC0261x1, this.b);
        return interfaceC0261x1.a();
    }

    @Override // j$.util.stream.AbstractC0153f
    protected AbstractC0153f f(j$.util.r rVar) {
        return new S1(this, rVar);
    }

    @Override // j$.util.stream.AbstractC0153f, java.util.concurrent.CountedCompleter
    public void onCompletion(CountedCompleter countedCompleter) {
        if (!d()) {
            g((F1) this.j.apply((F1) ((S1) this.d).b(), (F1) ((S1) this.e).b()));
        }
        this.b = null;
        this.e = null;
        this.d = null;
    }
}
