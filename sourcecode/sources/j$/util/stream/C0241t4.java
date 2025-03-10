package j$.util.stream;

import j$.util.AbstractC0112a;
import j$.util.function.Consumer;
import j$.util.function.Supplier;
import j$.util.r;
import java.util.Objects;

/* renamed from: j$.util.stream.t4, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
final class C0241t4 extends AbstractC0188k4 implements r.a {
    C0241t4(D2 d2, Supplier supplier, boolean z) {
        super(d2, supplier, z);
    }

    C0241t4(D2 d2, j$.util.r rVar, boolean z) {
        super(d2, rVar, z);
    }

    @Override // j$.util.r
    public /* synthetic */ boolean b(Consumer consumer) {
        return AbstractC0112a.j(this, consumer);
    }

    @Override // j$.util.s
    public void e(j$.util.function.f fVar) {
        if (this.h != null || this.i) {
            while (k(fVar)) {
            }
            return;
        }
        Objects.requireNonNull(fVar);
        h();
        this.b.r0(new C0235s4(fVar), this.d);
        this.i = true;
    }

    @Override // j$.util.r
    public /* synthetic */ void forEachRemaining(Consumer consumer) {
        AbstractC0112a.b(this, consumer);
    }

    @Override // j$.util.stream.AbstractC0188k4
    void j() {
        Z3 z3 = new Z3();
        this.h = z3;
        this.e = this.b.s0(new C0235s4(z3));
        this.f = new C0129b(this);
    }

    @Override // j$.util.s
    public boolean k(j$.util.function.f fVar) {
        Objects.requireNonNull(fVar);
        boolean a = a();
        if (a) {
            Z3 z3 = (Z3) this.h;
            long j = this.g;
            int w = z3.w(j);
            fVar.accept((z3.c == 0 && w == 0) ? ((double[]) z3.e)[(int) j] : ((double[][]) z3.f)[w][(int) (j - z3.d[w])]);
        }
        return a;
    }

    @Override // j$.util.stream.AbstractC0188k4
    AbstractC0188k4 l(j$.util.r rVar) {
        return new C0241t4(this.b, rVar, this.a);
    }

    @Override // j$.util.stream.AbstractC0188k4, j$.util.r
    public r.a trySplit() {
        return (r.a) super.trySplit();
    }
}
