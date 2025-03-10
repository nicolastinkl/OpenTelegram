package j$.util.stream;

import j$.util.function.BiConsumer;
import j$.util.function.BiFunction;
import j$.util.function.Supplier;

/* loaded from: classes2.dex */
class E2 extends Z2 {
    public final /* synthetic */ int b = 3;
    final /* synthetic */ Object c;
    final /* synthetic */ Object d;
    final /* synthetic */ Object e;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public E2(EnumC0182j4 enumC0182j4, BiConsumer biConsumer, BiConsumer biConsumer2, Supplier supplier) {
        super(enumC0182j4);
        this.c = biConsumer;
        this.d = biConsumer2;
        this.e = supplier;
    }

    @Override // j$.util.stream.Z2
    public X2 a() {
        switch (this.b) {
            case 0:
                return new F2((Supplier) this.e, (j$.util.function.y) this.d, (j$.util.function.b) this.c);
            case 1:
                return new K2((Supplier) this.e, (j$.util.function.w) this.d, (j$.util.function.b) this.c);
            case 2:
                return new L2(this.e, (BiFunction) this.d, (j$.util.function.b) this.c);
            case 3:
                return new P2((Supplier) this.e, (BiConsumer) this.d, (BiConsumer) this.c);
            default:
                return new T2((Supplier) this.e, (j$.util.function.x) this.d, (j$.util.function.b) this.c);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public E2(EnumC0182j4 enumC0182j4, j$.util.function.b bVar, BiFunction biFunction, Object obj) {
        super(enumC0182j4);
        this.c = bVar;
        this.d = biFunction;
        this.e = obj;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public E2(EnumC0182j4 enumC0182j4, j$.util.function.b bVar, j$.util.function.w wVar, Supplier supplier) {
        super(enumC0182j4);
        this.c = bVar;
        this.d = wVar;
        this.e = supplier;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public E2(EnumC0182j4 enumC0182j4, j$.util.function.b bVar, j$.util.function.x xVar, Supplier supplier) {
        super(enumC0182j4);
        this.c = bVar;
        this.d = xVar;
        this.e = supplier;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public E2(EnumC0182j4 enumC0182j4, j$.util.function.b bVar, j$.util.function.y yVar, Supplier supplier) {
        super(enumC0182j4);
        this.c = bVar;
        this.d = yVar;
        this.e = supplier;
    }
}
