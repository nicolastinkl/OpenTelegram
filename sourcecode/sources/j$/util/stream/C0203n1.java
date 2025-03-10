package j$.util.stream;

import java.util.Objects;

/* renamed from: j$.util.stream.n1, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
class C0203n1 extends AbstractC0209o1 implements InterfaceC0211o3 {
    final /* synthetic */ EnumC0215p1 c;
    final /* synthetic */ j$.wrappers.C d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C0203n1(EnumC0215p1 enumC0215p1, j$.wrappers.C c) {
        super(enumC0215p1);
        this.c = enumC0215p1;
        this.d = c;
    }

    @Override // j$.util.stream.AbstractC0209o1, j$.util.stream.InterfaceC0228r3, j$.util.function.f
    public void accept(double d) {
        boolean z;
        boolean z2;
        if (this.a) {
            return;
        }
        boolean b = this.d.b(d);
        z = this.c.a;
        if (b == z) {
            this.a = true;
            z2 = this.c.b;
            this.b = z2;
        }
    }

    @Override // j$.util.function.Consumer
    /* renamed from: b, reason: merged with bridge method [inline-methods] */
    public /* synthetic */ void accept(Double d) {
        AbstractC0238t1.a(this, d);
    }

    @Override // j$.util.function.f
    public j$.util.function.f j(j$.util.function.f fVar) {
        Objects.requireNonNull(fVar);
        return new j$.util.function.e(this, fVar);
    }
}
