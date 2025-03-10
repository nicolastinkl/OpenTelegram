package j$.util.stream;

import j$.util.AbstractC0112a;
import j$.util.function.Consumer;
import j$.util.function.Supplier;
import j$.util.r;
import java.util.Objects;

/* renamed from: j$.util.stream.v4, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
final class C0253v4 extends AbstractC0188k4 implements r.b {
    C0253v4(D2 d2, Supplier supplier, boolean z) {
        super(d2, supplier, z);
    }

    C0253v4(D2 d2, j$.util.r rVar, boolean z) {
        super(d2, rVar, z);
    }

    @Override // j$.util.r
    public /* synthetic */ boolean b(Consumer consumer) {
        return AbstractC0112a.k(this, consumer);
    }

    @Override // j$.util.s
    /* renamed from: c */
    public void e(j$.util.function.m mVar) {
        if (this.h != null || this.i) {
            while (k(mVar)) {
            }
            return;
        }
        Objects.requireNonNull(mVar);
        h();
        this.b.r0(new C0247u4(mVar), this.d);
        this.i = true;
    }

    @Override // j$.util.r
    public /* synthetic */ void forEachRemaining(Consumer consumer) {
        AbstractC0112a.c(this, consumer);
    }

    @Override // j$.util.s
    /* renamed from: g */
    public boolean k(j$.util.function.m mVar) {
        Objects.requireNonNull(mVar);
        boolean a = a();
        if (a) {
            C0134b4 c0134b4 = (C0134b4) this.h;
            long j = this.g;
            int w = c0134b4.w(j);
            mVar.accept((c0134b4.c == 0 && w == 0) ? ((int[]) c0134b4.e)[(int) j] : ((int[][]) c0134b4.f)[w][(int) (j - c0134b4.d[w])]);
        }
        return a;
    }

    @Override // j$.util.stream.AbstractC0188k4
    void j() {
        C0134b4 c0134b4 = new C0134b4();
        this.h = c0134b4;
        this.e = this.b.s0(new C0247u4(c0134b4));
        this.f = new C0129b(this);
    }

    @Override // j$.util.stream.AbstractC0188k4
    AbstractC0188k4 l(j$.util.r rVar) {
        return new C0253v4(this.b, rVar, this.a);
    }

    @Override // j$.util.stream.AbstractC0188k4, j$.util.r
    public r.b trySplit() {
        return (r.b) super.trySplit();
    }
}
