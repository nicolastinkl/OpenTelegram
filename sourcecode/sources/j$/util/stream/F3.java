package j$.util.stream;

/* loaded from: classes2.dex */
final class F3 extends AbstractC0141d {
    private final AbstractC0135c j;
    private final j$.util.function.n k;
    private final long l;
    private final long m;
    private long n;
    private volatile boolean o;

    F3(F3 f3, j$.util.r rVar) {
        super(f3, rVar);
        this.j = f3.j;
        this.k = f3.k;
        this.l = f3.l;
        this.m = f3.m;
    }

    F3(AbstractC0135c abstractC0135c, D2 d2, j$.util.r rVar, j$.util.function.n nVar, long j, long j2) {
        super(d2, rVar);
        this.j = abstractC0135c;
        this.k = nVar;
        this.l = j;
        this.m = j2;
    }

    private long m(long j) {
        if (this.o) {
            return this.n;
        }
        F3 f3 = (F3) this.d;
        F3 f32 = (F3) this.e;
        if (f3 == null || f32 == null) {
            return this.n;
        }
        long m = f3.m(j);
        return m >= j ? m : m + f32.m(j);
    }

    @Override // j$.util.stream.AbstractC0153f
    protected Object a() {
        if (e()) {
            InterfaceC0261x1 q0 = this.j.q0(EnumC0176i4.SIZED.e(this.j.c) ? this.j.n0(this.b) : -1L, this.k);
            InterfaceC0228r3 E0 = this.j.E0(this.a.p0(), q0);
            D2 d2 = this.a;
            d2.l0(d2.s0(E0), this.b);
            return q0.a();
        }
        D2 d22 = this.a;
        InterfaceC0261x1 q02 = d22.q0(-1L, this.k);
        d22.r0(q02, this.b);
        F1 a = q02.a();
        this.n = a.count();
        this.o = true;
        this.b = null;
        return a;
    }

    @Override // j$.util.stream.AbstractC0153f
    protected AbstractC0153f f(j$.util.r rVar) {
        return new F3(this, rVar);
    }

    @Override // j$.util.stream.AbstractC0141d
    protected void i() {
        this.i = true;
        if (this.o) {
            g(k());
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // j$.util.stream.AbstractC0141d
    /* renamed from: n, reason: merged with bridge method [inline-methods] */
    public final F1 k() {
        return C2.k(this.j.y0());
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0065  */
    @Override // j$.util.stream.AbstractC0153f, java.util.concurrent.CountedCompleter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onCompletion(java.util.concurrent.CountedCompleter r12) {
        /*
            Method dump skipped, instructions count: 228
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.util.stream.F3.onCompletion(java.util.concurrent.CountedCompleter):void");
    }
}
