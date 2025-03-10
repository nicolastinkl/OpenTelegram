package j$.util.stream;

/* loaded from: classes2.dex */
class A2 extends B2 {
    public final /* synthetic */ int c;
    private final Object d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public A2(E1 e1, Object obj, int i) {
        super(e1, i);
        this.c = 0;
        this.d = obj;
    }

    @Override // j$.util.stream.B2
    void a() {
        switch (this.c) {
            case 0:
                ((E1) this.a).d(this.d, this.b);
                break;
            default:
                this.a.i((Object[]) this.d, this.b);
                break;
        }
    }

    @Override // j$.util.stream.B2
    B2 b(int i, int i2) {
        switch (this.c) {
            case 0:
                return new A2(this, ((E1) this.a).b(i), i2);
            default:
                return new A2(this, this.a.b(i), i2);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ A2(E1 e1, Object obj, int i, G1 g1) {
        this(e1, obj, i);
        this.c = 0;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public A2(F1 f1, Object[] objArr, int i, G1 g1) {
        super(f1, i);
        this.c = 1;
        this.c = 1;
        this.d = objArr;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public A2(A2 a2, E1 e1, int i) {
        super(a2, e1, i);
        this.c = 0;
        this.d = a2.d;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public A2(A2 a2, F1 f1, int i) {
        super(a2, f1, i);
        this.c = 1;
        this.d = (Object[]) a2.d;
    }
}
