package j$.util.stream;

/* loaded from: classes2.dex */
class G2 extends Z2 {
    final /* synthetic */ j$.util.function.d b;
    final /* synthetic */ double c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    G2(EnumC0182j4 enumC0182j4, j$.util.function.d dVar, double d) {
        super(enumC0182j4);
        this.b = dVar;
        this.c = d;
    }

    @Override // j$.util.stream.Z2
    public X2 a() {
        return new H2(this.c, this.b);
    }
}
