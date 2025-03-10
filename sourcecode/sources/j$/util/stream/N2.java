package j$.util.stream;

import j$.util.function.BiConsumer;
import j$.util.function.Supplier;
import j$.util.stream.Collector;

/* loaded from: classes2.dex */
class N2 extends Z2 {
    final /* synthetic */ j$.util.function.b b;
    final /* synthetic */ BiConsumer c;
    final /* synthetic */ Supplier d;
    final /* synthetic */ Collector e;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    N2(EnumC0182j4 enumC0182j4, j$.util.function.b bVar, BiConsumer biConsumer, Supplier supplier, Collector collector) {
        super(enumC0182j4);
        this.b = bVar;
        this.c = biConsumer;
        this.d = supplier;
        this.e = collector;
    }

    @Override // j$.util.stream.Z2
    public X2 a() {
        return new O2(this.d, this.c, this.b);
    }

    @Override // j$.util.stream.Z2, j$.util.stream.S4
    public int b() {
        if (this.e.characteristics().contains(Collector.a.UNORDERED)) {
            return EnumC0176i4.r;
        }
        return 0;
    }
}
