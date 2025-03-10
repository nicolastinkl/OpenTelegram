package j$.util.stream;

import j$.util.AbstractC0112a;
import j$.util.function.Consumer;
import j$.util.function.Supplier;
import j$.util.r;
import java.util.Objects;

/* loaded from: classes2.dex */
final class x4 extends AbstractC0188k4 implements r.c {
    x4(D2 d2, Supplier supplier, boolean z) {
        super(d2, supplier, z);
    }

    x4(D2 d2, j$.util.r rVar, boolean z) {
        super(d2, rVar, z);
    }

    @Override // j$.util.r
    public /* synthetic */ boolean b(Consumer consumer) {
        return AbstractC0112a.l(this, consumer);
    }

    @Override // j$.util.s
    /* renamed from: d */
    public void e(j$.util.function.s sVar) {
        if (this.h != null || this.i) {
            while (k(sVar)) {
            }
            return;
        }
        Objects.requireNonNull(sVar);
        h();
        this.b.r0(new w4(sVar), this.d);
        this.i = true;
    }

    @Override // j$.util.r
    public /* synthetic */ void forEachRemaining(Consumer consumer) {
        AbstractC0112a.d(this, consumer);
    }

    @Override // j$.util.s
    /* renamed from: i */
    public boolean k(j$.util.function.s sVar) {
        Objects.requireNonNull(sVar);
        boolean a = a();
        if (a) {
            C0146d4 c0146d4 = (C0146d4) this.h;
            long j = this.g;
            int w = c0146d4.w(j);
            sVar.accept((c0146d4.c == 0 && w == 0) ? ((long[]) c0146d4.e)[(int) j] : ((long[][]) c0146d4.f)[w][(int) (j - c0146d4.d[w])]);
        }
        return a;
    }

    @Override // j$.util.stream.AbstractC0188k4
    void j() {
        C0146d4 c0146d4 = new C0146d4();
        this.h = c0146d4;
        this.e = this.b.s0(new w4(c0146d4));
        this.f = new C0129b(this);
    }

    @Override // j$.util.stream.AbstractC0188k4
    AbstractC0188k4 l(j$.util.r rVar) {
        return new x4(this.b, rVar, this.a);
    }

    @Override // j$.util.stream.AbstractC0188k4, j$.util.r
    public r.c trySplit() {
        return (r.c) super.trySplit();
    }
}
