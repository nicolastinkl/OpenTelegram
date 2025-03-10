package j$.util.stream;

import java.util.Objects;

/* renamed from: j$.util.stream.q0, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
final class C0220q0 extends AbstractC0237t0 implements InterfaceC0217p3 {
    final j$.util.function.m b;

    C0220q0(j$.util.function.m mVar, boolean z) {
        super(z);
        this.b = mVar;
    }

    @Override // j$.util.stream.AbstractC0237t0, j$.util.stream.InterfaceC0228r3
    public void accept(int i) {
        this.b.accept(i);
    }

    @Override // j$.util.function.Consumer
    /* renamed from: e, reason: merged with bridge method [inline-methods] */
    public /* synthetic */ void accept(Integer num) {
        AbstractC0238t1.b(this, num);
    }

    @Override // j$.util.function.m
    public j$.util.function.m l(j$.util.function.m mVar) {
        Objects.requireNonNull(mVar);
        return new j$.util.function.l(this, mVar);
    }
}
