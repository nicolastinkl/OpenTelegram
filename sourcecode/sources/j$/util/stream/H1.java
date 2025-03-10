package j$.util.stream;

/* loaded from: classes2.dex */
abstract class H1 implements F1 {
    protected final F1 a;
    protected final F1 b;
    private final long c;

    H1(F1 f1, F1 f12) {
        this.a = f1;
        this.b = f12;
        this.c = f1.count() + f12.count();
    }

    @Override // j$.util.stream.F1
    public /* bridge */ /* synthetic */ E1 b(int i) {
        return (E1) b(i);
    }

    @Override // j$.util.stream.F1
    public F1 b(int i) {
        if (i == 0) {
            return this.a;
        }
        if (i == 1) {
            return this.b;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override // j$.util.stream.F1
    public long count() {
        return this.c;
    }

    @Override // j$.util.stream.F1
    public int p() {
        return 2;
    }
}
