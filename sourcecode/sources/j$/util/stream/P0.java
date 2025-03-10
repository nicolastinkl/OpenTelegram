package j$.util.stream;

/* loaded from: classes2.dex */
abstract class P0 extends Q0 {
    P0(AbstractC0135c abstractC0135c, EnumC0182j4 enumC0182j4, int i) {
        super(abstractC0135c, i);
    }

    @Override // j$.util.stream.AbstractC0135c
    final boolean D0() {
        return false;
    }

    @Override // j$.util.stream.AbstractC0135c, j$.util.stream.BaseStream
    public /* bridge */ /* synthetic */ IntStream parallel() {
        parallel();
        return this;
    }

    @Override // j$.util.stream.AbstractC0135c, j$.util.stream.BaseStream
    public /* bridge */ /* synthetic */ IntStream sequential() {
        sequential();
        return this;
    }
}
