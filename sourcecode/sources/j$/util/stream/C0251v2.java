package j$.util.stream;

import java.util.Objects;

/* renamed from: j$.util.stream.v2, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
final class C0251v2 extends AbstractC0262x2 implements InterfaceC0223q3 {
    private final long[] h;

    C0251v2(j$.util.r rVar, D2 d2, long[] jArr) {
        super(rVar, d2, jArr.length);
        this.h = jArr;
    }

    C0251v2(C0251v2 c0251v2, j$.util.r rVar, long j, long j2) {
        super(c0251v2, rVar, j, j2, c0251v2.h.length);
        this.h = c0251v2.h;
    }

    @Override // j$.util.stream.AbstractC0262x2, j$.util.stream.InterfaceC0228r3, j$.util.stream.InterfaceC0223q3, j$.util.function.s
    public void accept(long j) {
        int i = this.f;
        if (i >= this.g) {
            throw new IndexOutOfBoundsException(Integer.toString(this.f));
        }
        long[] jArr = this.h;
        this.f = i + 1;
        jArr[i] = j;
    }

    @Override // j$.util.stream.AbstractC0262x2
    AbstractC0262x2 b(j$.util.r rVar, long j, long j2) {
        return new C0251v2(this, rVar, j, j2);
    }

    @Override // j$.util.function.Consumer
    /* renamed from: c, reason: merged with bridge method [inline-methods] */
    public /* synthetic */ void accept(Long l) {
        AbstractC0238t1.c(this, l);
    }

    @Override // j$.util.function.s
    public j$.util.function.s f(j$.util.function.s sVar) {
        Objects.requireNonNull(sVar);
        return new j$.util.function.r(this, sVar);
    }
}
