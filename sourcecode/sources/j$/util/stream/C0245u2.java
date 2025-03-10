package j$.util.stream;

import java.util.Objects;

/* renamed from: j$.util.stream.u2, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
final class C0245u2 extends AbstractC0262x2 implements InterfaceC0217p3 {
    private final int[] h;

    C0245u2(j$.util.r rVar, D2 d2, int[] iArr) {
        super(rVar, d2, iArr.length);
        this.h = iArr;
    }

    C0245u2(C0245u2 c0245u2, j$.util.r rVar, long j, long j2) {
        super(c0245u2, rVar, j, j2, c0245u2.h.length);
        this.h = c0245u2.h;
    }

    @Override // j$.util.stream.AbstractC0262x2, j$.util.stream.InterfaceC0228r3
    public void accept(int i) {
        int i2 = this.f;
        if (i2 >= this.g) {
            throw new IndexOutOfBoundsException(Integer.toString(this.f));
        }
        int[] iArr = this.h;
        this.f = i2 + 1;
        iArr[i2] = i;
    }

    @Override // j$.util.stream.AbstractC0262x2
    AbstractC0262x2 b(j$.util.r rVar, long j, long j2) {
        return new C0245u2(this, rVar, j, j2);
    }

    @Override // j$.util.function.Consumer
    /* renamed from: c, reason: merged with bridge method [inline-methods] */
    public /* synthetic */ void accept(Integer num) {
        AbstractC0238t1.b(this, num);
    }

    @Override // j$.util.function.m
    public j$.util.function.m l(j$.util.function.m mVar) {
        Objects.requireNonNull(mVar);
        return new j$.util.function.l(this, mVar);
    }
}
