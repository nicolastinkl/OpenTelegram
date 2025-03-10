package j$.util.stream;

import j$.util.OptionalInt;
import j$.util.function.Consumer;
import java.util.Objects;

/* loaded from: classes2.dex */
class S2 implements X2, InterfaceC0217p3 {
    private boolean a;
    private int b;
    final /* synthetic */ j$.util.function.k c;

    S2(j$.util.function.k kVar) {
        this.c = kVar;
    }

    @Override // j$.util.stream.InterfaceC0228r3, j$.util.function.f
    public /* synthetic */ void accept(double d) {
        AbstractC0238t1.f(this);
        throw null;
    }

    @Override // j$.util.stream.InterfaceC0228r3
    public void accept(int i) {
        if (this.a) {
            this.a = false;
        } else {
            i = this.c.applyAsInt(this.b, i);
        }
        this.b = i;
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

    @Override // j$.util.function.Supplier
    public Object get() {
        return this.a ? OptionalInt.empty() : OptionalInt.of(this.b);
    }

    @Override // j$.util.stream.X2
    public void h(X2 x2) {
        S2 s2 = (S2) x2;
        if (s2.a) {
            return;
        }
        accept(s2.b);
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
        this.a = true;
        this.b = 0;
    }

    @Override // j$.util.stream.InterfaceC0228r3
    public /* synthetic */ boolean o() {
        return false;
    }
}
