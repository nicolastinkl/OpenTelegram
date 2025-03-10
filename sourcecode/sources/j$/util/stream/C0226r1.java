package j$.util.stream;

/* renamed from: j$.util.stream.r1, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
final class C0226r1 extends AbstractC0141d {
    private final C0221q1 j;

    C0226r1(C0221q1 c0221q1, D2 d2, j$.util.r rVar) {
        super(d2, rVar);
        this.j = c0221q1;
    }

    C0226r1(C0226r1 c0226r1, j$.util.r rVar) {
        super(c0226r1, rVar);
        this.j = c0226r1.j;
    }

    @Override // j$.util.stream.AbstractC0153f
    protected Object a() {
        boolean z;
        D2 d2 = this.a;
        AbstractC0209o1 abstractC0209o1 = (AbstractC0209o1) this.j.c.get();
        d2.r0(abstractC0209o1, this.b);
        boolean z2 = abstractC0209o1.b;
        z = this.j.b.b;
        if (z2 != z) {
            return null;
        }
        l(Boolean.valueOf(z2));
        return null;
    }

    @Override // j$.util.stream.AbstractC0153f
    protected AbstractC0153f f(j$.util.r rVar) {
        return new C0226r1(this, rVar);
    }

    @Override // j$.util.stream.AbstractC0141d
    protected Object k() {
        boolean z;
        z = this.j.b.b;
        return Boolean.valueOf(!z);
    }
}
