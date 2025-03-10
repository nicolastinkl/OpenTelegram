package j$.util.stream;

import j$.wrappers.C0287g0;
import java.util.Objects;

/* renamed from: j$.util.stream.m1, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
class C0197m1 extends AbstractC0209o1 implements InterfaceC0223q3 {
    final /* synthetic */ EnumC0215p1 c;
    final /* synthetic */ C0287g0 d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C0197m1(EnumC0215p1 enumC0215p1, C0287g0 c0287g0) {
        super(enumC0215p1);
        this.c = enumC0215p1;
        this.d = c0287g0;
    }

    @Override // j$.util.stream.AbstractC0209o1, j$.util.stream.InterfaceC0228r3, j$.util.stream.InterfaceC0223q3, j$.util.function.s
    public void accept(long j) {
        boolean z;
        boolean z2;
        if (this.a) {
            return;
        }
        boolean b = this.d.b(j);
        z = this.c.a;
        if (b == z) {
            this.a = true;
            z2 = this.c.b;
            this.b = z2;
        }
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
}
