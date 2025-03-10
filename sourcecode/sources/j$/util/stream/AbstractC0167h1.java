package j$.util.stream;

/* renamed from: j$.util.stream.h1, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
abstract class AbstractC0167h1 extends AbstractC0173i1 {
    AbstractC0167h1(AbstractC0135c abstractC0135c, EnumC0182j4 enumC0182j4, int i) {
        super(abstractC0135c, i);
    }

    @Override // j$.util.stream.AbstractC0135c
    final boolean D0() {
        return false;
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
