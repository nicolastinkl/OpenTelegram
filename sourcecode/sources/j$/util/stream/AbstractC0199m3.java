package j$.util.stream;

import j$.util.function.Consumer;
import java.util.Objects;

/* renamed from: j$.util.stream.m3, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public abstract class AbstractC0199m3 implements InterfaceC0223q3 {
    protected final InterfaceC0228r3 a;

    public AbstractC0199m3(InterfaceC0228r3 interfaceC0228r3) {
        Objects.requireNonNull(interfaceC0228r3);
        this.a = interfaceC0228r3;
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

    @Override // j$.util.stream.InterfaceC0228r3
    public void m() {
        this.a.m();
    }

    @Override // j$.util.stream.InterfaceC0228r3
    public boolean o() {
        return this.a.o();
    }
}
