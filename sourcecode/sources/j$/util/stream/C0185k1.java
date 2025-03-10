package j$.util.stream;

import j$.util.function.Predicate;

/* renamed from: j$.util.stream.k1, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
class C0185k1 extends AbstractC0209o1 {
    final /* synthetic */ EnumC0215p1 c;
    final /* synthetic */ Predicate d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C0185k1(EnumC0215p1 enumC0215p1, Predicate predicate) {
        super(enumC0215p1);
        this.c = enumC0215p1;
        this.d = predicate;
    }

    @Override // j$.util.function.Consumer
    public void accept(Object obj) {
        boolean z;
        boolean z2;
        if (this.a) {
            return;
        }
        boolean test = this.d.test(obj);
        z = this.c.a;
        if (test == z) {
            this.a = true;
            z2 = this.c.b;
            this.b = z2;
        }
    }
}
