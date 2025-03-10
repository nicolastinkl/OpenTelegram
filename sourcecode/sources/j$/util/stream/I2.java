package j$.util.stream;

/* loaded from: classes2.dex */
class I2 extends Z2 {
    public final /* synthetic */ int b = 1;
    final /* synthetic */ Object c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public I2(EnumC0182j4 enumC0182j4, j$.util.function.b bVar) {
        super(enumC0182j4);
        this.c = bVar;
    }

    @Override // j$.util.stream.Z2
    public X2 a() {
        switch (this.b) {
            case 0:
                return new J2((j$.util.function.d) this.c);
            case 1:
                return new M2((j$.util.function.b) this.c);
            case 2:
                return new S2((j$.util.function.k) this.c);
            default:
                return new W2((j$.util.function.q) this.c);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public I2(EnumC0182j4 enumC0182j4, j$.util.function.d dVar) {
        super(enumC0182j4);
        this.c = dVar;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public I2(EnumC0182j4 enumC0182j4, j$.util.function.k kVar) {
        super(enumC0182j4);
        this.c = kVar;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public I2(EnumC0182j4 enumC0182j4, j$.util.function.q qVar) {
        super(enumC0182j4);
        this.c = qVar;
    }
}
