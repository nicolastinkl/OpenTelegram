package j$.util.stream;

import j$.util.function.Consumer;
import j$.util.function.Supplier;
import java.util.Objects;

/* loaded from: classes2.dex */
class T2 extends Y2 implements X2, InterfaceC0217p3 {
    final /* synthetic */ Supplier b;
    final /* synthetic */ j$.util.function.x c;
    final /* synthetic */ j$.util.function.b d;

    T2(Supplier supplier, j$.util.function.x xVar, j$.util.function.b bVar) {
        this.b = supplier;
        this.c = xVar;
        this.d = bVar;
    }

    @Override // j$.util.stream.InterfaceC0228r3, j$.util.function.f
    public /* synthetic */ void accept(double d) {
        AbstractC0238t1.f(this);
        throw null;
    }

    @Override // j$.util.stream.InterfaceC0228r3
    public void accept(int i) {
        this.c.accept(this.a, i);
    }

    @Override // j$.util.stream.InterfaceC0228r3, j$.util.stream.InterfaceC0223q3, j$.util.function.s
    public /* synthetic */ void accept(long j) {
        AbstractC0238t1.e(this);
        throw null;
    }

    @Override // j$.util.function.Consumer
    public /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer.CC.$default$andThen(this, consumer);
    }

    @Override // j$.util.function.Consumer
    /* renamed from: b, reason: merged with bridge method [inline-methods] */
    public /* synthetic */ void accept(Integer num) {
        AbstractC0238t1.b(this, num);
    }

    @Override // j$.util.stream.X2
    public void h(X2 x2) {
        this.a = this.d.apply(this.a, ((T2) x2).a);
    }

    @Override // j$.util.function.m
    public j$.util.function.m l(j$.util.function.m mVar) {
        Objects.requireNonNull(mVar);
        return new j$.util.function.l(this, mVar);
    }

    @Override // j$.util.stream.InterfaceC0228r3
    public /* synthetic */ void m() {
    }

    @Override // j$.util.stream.InterfaceC0228r3
    public void n(long j) {
        this.a = this.b.get();
    }

    @Override // j$.util.stream.InterfaceC0228r3
    public /* synthetic */ boolean o() {
        return false;
    }
}
