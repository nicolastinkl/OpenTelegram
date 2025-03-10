package j$.util.stream;

import j$.util.AbstractC0112a;
import j$.util.function.Consumer;
import j$.util.r;
import j$.util.stream.AbstractC0152e4;

/* loaded from: classes2.dex */
class Y3 extends AbstractC0152e4.a implements r.a {
    final /* synthetic */ Z3 g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    Y3(Z3 z3, int i, int i2, int i3, int i4) {
        super(i, i2, i3, i4);
        this.g = z3;
    }

    @Override // j$.util.stream.AbstractC0152e4.a
    void a(Object obj, int i, Object obj2) {
        ((j$.util.function.f) obj2).accept(((double[]) obj)[i]);
    }

    @Override // j$.util.r
    public /* synthetic */ boolean b(Consumer consumer) {
        return AbstractC0112a.j(this, consumer);
    }

    @Override // j$.util.stream.AbstractC0152e4.a
    j$.util.s f(Object obj, int i, int i2) {
        return j$.util.H.j((double[]) obj, i, i2 + i, 1040);
    }

    @Override // j$.util.r
    public /* synthetic */ void forEachRemaining(Consumer consumer) {
        AbstractC0112a.b(this, consumer);
    }

    @Override // j$.util.stream.AbstractC0152e4.a
    j$.util.s h(int i, int i2, int i3, int i4) {
        return new Y3(this.g, i, i2, i3, i4);
    }
}
