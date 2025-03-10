package j$.util.stream;

import java.util.concurrent.CountedCompleter;

/* loaded from: classes2.dex */
abstract class B2 extends CountedCompleter {
    protected final F1 a;
    protected final int b;

    B2(B2 b2, F1 f1, int i) {
        super(b2);
        this.a = f1;
        this.b = i;
    }

    B2(F1 f1, int i) {
        this.a = f1;
        this.b = i;
    }

    abstract void a();

    abstract B2 b(int i, int i2);

    @Override // java.util.concurrent.CountedCompleter
    public void compute() {
        B2 b2 = this;
        while (b2.a.p() != 0) {
            b2.setPendingCount(b2.a.p() - 1);
            int i = 0;
            int i2 = 0;
            while (i < b2.a.p() - 1) {
                B2 b = b2.b(i, b2.b + i2);
                i2 = (int) (i2 + b.a.count());
                b.fork();
                i++;
            }
            b2 = b2.b(i, b2.b + i2);
        }
        b2.a();
        b2.propagateCompletion();
    }
}
