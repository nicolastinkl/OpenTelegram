package j$.util.stream;

import j$.util.function.Consumer;
import java.util.Objects;

/* loaded from: classes2.dex */
class H2 implements X2, InterfaceC0211o3 {
    private double a;
    final /* synthetic */ double b;
    final /* synthetic */ j$.util.function.d c;

    H2(double d, j$.util.function.d dVar) {
        this.b = d;
        this.c = dVar;
    }

    @Override // j$.util.stream.InterfaceC0228r3, j$.util.function.f
    public void accept(double d) {
        this.a = this.c.applyAsDouble(this.a, d);
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
    public /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer.CC.$default$andThen(this, consumer);
    }

    @Override // j$.util.function.Consumer
    /* renamed from: b, reason: merged with bridge method [inline-methods] */
    public /* synthetic */ void accept(Double d) {
        AbstractC0238t1.a(this, d);
    }

    @Override // j$.util.function.Supplier
    public Object get() {
        return Double.valueOf(this.a);
    }

    @Override // j$.util.stream.X2
    public void h(X2 x2) {
        accept(((H2) x2).a);
    }

    @Override // j$.util.function.f
    public j$.util.function.f j(j$.util.function.f fVar) {
        Objects.requireNonNull(fVar);
        return new j$.util.function.e(this, fVar);
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
