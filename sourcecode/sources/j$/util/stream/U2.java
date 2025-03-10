package j$.util.stream;

/* loaded from: classes2.dex */
class U2 extends Z2 {
    final /* synthetic */ j$.util.function.q b;
    final /* synthetic */ long c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    U2(EnumC0182j4 enumC0182j4, j$.util.function.q qVar, long j) {
        super(enumC0182j4);
        this.b = qVar;
        this.c = j;
    }

    @Override // j$.util.stream.Z2
    public X2 a() {
        return new V2(this.c, this.b);
    }
}
