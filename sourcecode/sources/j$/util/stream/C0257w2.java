package j$.util.stream;

/* renamed from: j$.util.stream.w2, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
final class C0257w2 extends AbstractC0262x2 {
    private final Object[] h;

    C0257w2(j$.util.r rVar, D2 d2, Object[] objArr) {
        super(rVar, d2, objArr.length);
        this.h = objArr;
    }

    C0257w2(C0257w2 c0257w2, j$.util.r rVar, long j, long j2) {
        super(c0257w2, rVar, j, j2, c0257w2.h.length);
        this.h = c0257w2.h;
    }

    @Override // j$.util.function.Consumer
    public void accept(Object obj) {
        int i = this.f;
        if (i >= this.g) {
            throw new IndexOutOfBoundsException(Integer.toString(this.f));
        }
        Object[] objArr = this.h;
        this.f = i + 1;
        objArr[i] = obj;
    }

    @Override // j$.util.stream.AbstractC0262x2
    AbstractC0262x2 b(j$.util.r rVar, long j, long j2) {
        return new C0257w2(this, rVar, j, j2);
    }
}
