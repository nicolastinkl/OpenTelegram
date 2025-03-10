package j$.util.stream;

import java.util.concurrent.CountedCompleter;

/* renamed from: j$.util.stream.o0, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
final class C0208o0 extends AbstractC0141d {
    private final C0172i0 j;

    C0208o0(C0172i0 c0172i0, D2 d2, j$.util.r rVar) {
        super(d2, rVar);
        this.j = c0172i0;
    }

    C0208o0(C0208o0 c0208o0, j$.util.r rVar) {
        super(c0208o0, rVar);
        this.j = c0208o0.j;
    }

    private void m(Object obj) {
        boolean z;
        C0208o0 c0208o0 = this;
        while (true) {
            if (c0208o0 != null) {
                AbstractC0153f c = c0208o0.c();
                if (c != null && c.d != c0208o0) {
                    z = false;
                    break;
                }
                c0208o0 = c;
            } else {
                z = true;
                break;
            }
        }
        if (z) {
            l(obj);
        } else {
            j();
        }
    }

    @Override // j$.util.stream.AbstractC0153f
    protected Object a() {
        D2 d2 = this.a;
        T4 t4 = (T4) this.j.e.get();
        d2.r0(t4, this.b);
        Object obj = t4.get();
        if (!this.j.b) {
            if (obj != null) {
                l(obj);
            }
            return null;
        }
        if (obj == null) {
            return null;
        }
        m(obj);
        return obj;
    }

    @Override // j$.util.stream.AbstractC0153f
    protected AbstractC0153f f(j$.util.r rVar) {
        return new C0208o0(this, rVar);
    }

    @Override // j$.util.stream.AbstractC0141d
    protected Object k() {
        return this.j.c;
    }

    @Override // j$.util.stream.AbstractC0153f, java.util.concurrent.CountedCompleter
    public void onCompletion(CountedCompleter countedCompleter) {
        if (this.j.b) {
            C0208o0 c0208o0 = (C0208o0) this.d;
            C0208o0 c0208o02 = null;
            while (true) {
                if (c0208o0 != c0208o02) {
                    Object b = c0208o0.b();
                    if (b != null && this.j.d.test(b)) {
                        g(b);
                        m(b);
                        break;
                    } else {
                        c0208o02 = c0208o0;
                        c0208o0 = (C0208o0) this.e;
                    }
                } else {
                    break;
                }
            }
        }
        this.b = null;
        this.e = null;
        this.d = null;
    }
}
