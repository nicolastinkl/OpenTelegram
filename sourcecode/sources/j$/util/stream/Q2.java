package j$.util.stream;

/* loaded from: classes2.dex */
class Q2 extends Z2 {
    final /* synthetic */ j$.util.function.k b;
    final /* synthetic */ int c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    Q2(EnumC0182j4 enumC0182j4, j$.util.function.k kVar, int i) {
        super(enumC0182j4);
        this.b = kVar;
        this.c = i;
    }

    @Override // j$.util.stream.Z2
    public X2 a() {
        return new R2(this.c, this.b);
    }
}
