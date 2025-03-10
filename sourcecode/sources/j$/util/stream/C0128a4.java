package j$.util.stream;

import j$.util.AbstractC0112a;
import j$.util.function.Consumer;
import j$.util.r;
import j$.util.stream.AbstractC0152e4;

/* renamed from: j$.util.stream.a4, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
class C0128a4 extends AbstractC0152e4.a implements r.b {
    final /* synthetic */ C0134b4 g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C0128a4(C0134b4 c0134b4, int i, int i2, int i3, int i4) {
        super(i, i2, i3, i4);
        this.g = c0134b4;
    }

    @Override // j$.util.stream.AbstractC0152e4.a
    void a(Object obj, int i, Object obj2) {
        ((j$.util.function.m) obj2).accept(((int[]) obj)[i]);
    }

    @Override // j$.util.r
    public /* synthetic */ boolean b(Consumer consumer) {
        return AbstractC0112a.k(this, consumer);
    }

    @Override // j$.util.stream.AbstractC0152e4.a
    j$.util.s f(Object obj, int i, int i2) {
        return j$.util.H.k((int[]) obj, i, i2 + i, 1040);
    }

    @Override // j$.util.r
    public /* synthetic */ void forEachRemaining(Consumer consumer) {
        AbstractC0112a.c(this, consumer);
    }

    @Override // j$.util.stream.AbstractC0152e4.a
    j$.util.s h(int i, int i2, int i3, int i4) {
        return new C0128a4(this.g, i, i2, i3, i4);
    }
}
