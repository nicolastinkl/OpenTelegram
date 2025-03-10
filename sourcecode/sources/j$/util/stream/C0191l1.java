package j$.util.stream;

import java.util.Objects;

/* renamed from: j$.util.stream.l1, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
class C0191l1 extends AbstractC0209o1 implements InterfaceC0217p3 {
    final /* synthetic */ EnumC0215p1 c;
    final /* synthetic */ j$.wrappers.S d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C0191l1(EnumC0215p1 enumC0215p1, j$.wrappers.S s) {
        super(enumC0215p1);
        this.c = enumC0215p1;
        this.d = s;
    }

    @Override // j$.util.stream.AbstractC0209o1, j$.util.stream.InterfaceC0228r3
    public void accept(int i) {
        boolean z;
        boolean z2;
        if (this.a) {
            return;
        }
        boolean b = this.d.b(i);
        z = this.c.a;
        if (b == z) {
            this.a = true;
            z2 = this.c.b;
            this.b = z2;
        }
    }

    @Override // j$.util.function.Consumer
    /* renamed from: b, reason: merged with bridge method [inline-methods] */
    public /* synthetic */ void accept(Integer num) {
        AbstractC0238t1.b(this, num);
    }

    @Override // j$.util.function.m
    public j$.util.function.m l(j$.util.function.m mVar) {
        Objects.requireNonNull(mVar);
        return new j$.util.function.l(this, mVar);
    }
}
