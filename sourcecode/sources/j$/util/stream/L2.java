package j$.util.stream;

import j$.util.function.BiFunction;
import j$.util.function.Consumer;

/* loaded from: classes2.dex */
class L2 extends Y2 implements X2 {
    final /* synthetic */ Object b;
    final /* synthetic */ BiFunction c;
    final /* synthetic */ j$.util.function.b d;

    L2(Object obj, BiFunction biFunction, j$.util.function.b bVar) {
        this.b = obj;
        this.c = biFunction;
        this.d = bVar;
    }

    @Override // j$.util.stream.InterfaceC0228r3, j$.util.function.f
    public /* synthetic */ void accept(double d) {
        AbstractC0238t1.f(this);
        throw null;
    }

    @Override // j$.util.stream.InterfaceC0228r3
    public /* synthetic */ void accept(int i) {
        AbstractC0238t1.d(this);
        throw null;
    }

    @Override // j$.util.stream.InterfaceC0228r3, j$.util.stream.InterfaceC0223q3, j$.util.function.s
    public /* synthetic */ void accept(long j) {
        AbstractC0238t1.e(this);
        throw null;
    }

    @Override // j$.util.function.Consumer
    public void accept(Object obj) {
        this.a = this.c.apply(this.a, obj);
    }

    @Override // j$.util.function.Consumer
    public /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer.CC.$default$andThen(this, consumer);
    }

    @Override // j$.util.stream.X2
    public void h(X2 x2) {
        this.a = this.d.apply(this.a, ((L2) x2).a);
    }

    @Override // j$.util.stream.InterfaceC0228r3
    public /* synthetic */ void m() {
    }

    @Override // j$.util.stream.InterfaceC0228r3
    public void n(long j) {
        this.a = this.b;
    }

    @Override // j$.util.stream.InterfaceC0228r3
    public /* synthetic */ boolean o() {
        return false;
    }
}
