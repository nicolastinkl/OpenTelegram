package j$.util.stream;

/* loaded from: classes2.dex */
abstract class X extends Y {
    X(AbstractC0135c abstractC0135c, EnumC0182j4 enumC0182j4, int i) {
        super(abstractC0135c, i);
    }

    @Override // j$.util.stream.AbstractC0135c
    final boolean D0() {
        return false;
    }

    @Override // j$.util.stream.AbstractC0135c, j$.util.stream.BaseStream
    public /* bridge */ /* synthetic */ Z parallel() {
        parallel();
        return this;
    }

    @Override // j$.util.stream.AbstractC0135c, j$.util.stream.BaseStream
    public /* bridge */ /* synthetic */ Z sequential() {
        sequential();
        return this;
    }
}
