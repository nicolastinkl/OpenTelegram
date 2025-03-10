package j$.util.stream;

/* renamed from: j$.util.stream.g1, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
abstract class AbstractC0161g1 extends AbstractC0173i1 {
    AbstractC0161g1(AbstractC0135c abstractC0135c, EnumC0182j4 enumC0182j4, int i) {
        super(abstractC0135c, i);
    }

    @Override // j$.util.stream.AbstractC0135c
    final boolean D0() {
        return true;
    }

    @Override // j$.util.stream.AbstractC0135c, j$.util.stream.BaseStream
    public /* bridge */ /* synthetic */ InterfaceC0179j1 parallel() {
        parallel();
        return this;
    }

    @Override // j$.util.stream.AbstractC0135c, j$.util.stream.BaseStream
    public /* bridge */ /* synthetic */ InterfaceC0179j1 sequential() {
        sequential();
        return this;
    }
}
