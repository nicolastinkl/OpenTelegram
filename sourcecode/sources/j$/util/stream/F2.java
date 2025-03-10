package j$.util.stream;

import j$.util.function.Consumer;
import j$.util.function.Supplier;
import java.util.Objects;

/* loaded from: classes2.dex */
class F2 extends Y2 implements X2, InterfaceC0223q3 {
    final /* synthetic */ Supplier b;
    final /* synthetic */ j$.util.function.y c;
    final /* synthetic */ j$.util.function.b d;

    F2(Supplier supplier, j$.util.function.y yVar, j$.util.function.b bVar) {
        this.b = supplier;
        this.c = yVar;
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
    public void accept(long j) {
        this.c.accept(this.a, j);
    }

    @Override // j$.util.function.Consumer
    public /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer.CC.$default$andThen(this, consumer);
    }

    @Override // j$.util.function.Consumer
    /* renamed from: b, reason: merged with bridge method [inline-methods] */
    public /* synthetic */ void accept(Long l) {
        AbstractC0238t1.c(this, l);
    }

    @Override // j$.util.function.s
    public j$.util.function.s f(j$.util.function.s sVar) {
        Objects.requireNonNull(sVar);
        return new j$.util.function.r(this, sVar);
    }

    @Override // j$.util.stream.X2
    public void h(X2 x2) {
        this.a = this.d.apply(this.a, ((F2) x2).a);
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
