package j$.util.stream;

import java.util.Objects;

/* renamed from: j$.util.stream.r0, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
final class C0225r0 extends AbstractC0237t0 implements InterfaceC0223q3 {
    final j$.util.function.s b;

    C0225r0(j$.util.function.s sVar, boolean z) {
        super(z);
        this.b = sVar;
    }

    @Override // j$.util.stream.AbstractC0237t0, j$.util.stream.InterfaceC0228r3, j$.util.stream.InterfaceC0223q3, j$.util.function.s
    public void accept(long j) {
        this.b.accept(j);
    }

    @Override // j$.util.function.Consumer
    /* renamed from: e, reason: merged with bridge method [inline-methods] */
    public /* synthetic */ void accept(Long l) {
        AbstractC0238t1.c(this, l);
    }

    @Override // j$.util.function.s
    public j$.util.function.s f(j$.util.function.s sVar) {
        Objects.requireNonNull(sVar);
        return new j$.util.function.r(this, sVar);
    }
}
