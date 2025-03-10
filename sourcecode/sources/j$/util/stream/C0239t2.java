package j$.util.stream;

import java.util.Objects;

/* renamed from: j$.util.stream.t2, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
final class C0239t2 extends AbstractC0262x2 implements InterfaceC0211o3 {
    private final double[] h;

    C0239t2(j$.util.r rVar, D2 d2, double[] dArr) {
        super(rVar, d2, dArr.length);
        this.h = dArr;
    }

    C0239t2(C0239t2 c0239t2, j$.util.r rVar, long j, long j2) {
        super(c0239t2, rVar, j, j2, c0239t2.h.length);
        this.h = c0239t2.h;
    }

    @Override // j$.util.stream.AbstractC0262x2, j$.util.stream.InterfaceC0228r3, j$.util.function.f
    public void accept(double d) {
        int i = this.f;
        if (i >= this.g) {
            throw new IndexOutOfBoundsException(Integer.toString(this.f));
        }
        double[] dArr = this.h;
        this.f = i + 1;
        dArr[i] = d;
    }

    @Override // j$.util.stream.AbstractC0262x2
    AbstractC0262x2 b(j$.util.r rVar, long j, long j2) {
        return new C0239t2(this, rVar, j, j2);
    }

    @Override // j$.util.function.Consumer
    /* renamed from: c, reason: merged with bridge method [inline-methods] */
    public /* synthetic */ void accept(Double d) {
        AbstractC0238t1.a(this, d);
    }

    @Override // j$.util.function.f
    public j$.util.function.f j(j$.util.function.f fVar) {
        Objects.requireNonNull(fVar);
        return new j$.util.function.e(this, fVar);
    }
}
