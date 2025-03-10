package j$.util.stream;

import j$.util.function.Consumer;

/* renamed from: j$.util.stream.t0, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
abstract class AbstractC0237t0 implements S4, T4 {
    private final boolean a;

    protected AbstractC0237t0(boolean z) {
        this.a = z;
    }

    public /* synthetic */ void accept(double d) {
        AbstractC0238t1.f(this);
        throw null;
    }

    public /* synthetic */ void accept(int i) {
        AbstractC0238t1.d(this);
        throw null;
    }

    public /* synthetic */ void accept(long j) {
        AbstractC0238t1.e(this);
        throw null;
    }

    @Override // j$.util.function.Consumer
    public /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer.CC.$default$andThen(this, consumer);
    }

    @Override // j$.util.stream.S4
    public int b() {
        if (this.a) {
            return 0;
        }
        return EnumC0176i4.r;
    }

    @Override // j$.util.stream.S4
    public Object c(D2 d2, j$.util.r rVar) {
        (this.a ? new C0249v0(d2, rVar, this) : new C0255w0(d2, rVar, d2.s0(this))).invoke();
        return null;
    }

    @Override // j$.util.stream.S4
    public Object d(D2 d2, j$.util.r rVar) {
        AbstractC0135c abstractC0135c = (AbstractC0135c) d2;
        abstractC0135c.k0(abstractC0135c.s0(this), rVar);
        return null;
    }

    @Override // j$.util.function.Supplier
    public /* bridge */ /* synthetic */ Object get() {
        return null;
    }

    @Override // j$.util.stream.InterfaceC0228r3
    public /* synthetic */ void m() {
    }

    @Override // j$.util.stream.InterfaceC0228r3
    public /* synthetic */ void n(long j) {
    }

    @Override // j$.util.stream.InterfaceC0228r3
    public /* synthetic */ boolean o() {
        return false;
    }
}
