package j$.util.stream;

import j$.util.function.Consumer;
import java.util.Objects;
import java.util.concurrent.CountedCompleter;

/* renamed from: j$.util.stream.x2, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
abstract class AbstractC0262x2 extends CountedCompleter implements InterfaceC0228r3 {
    protected final j$.util.r a;
    protected final D2 b;
    protected final long c;
    protected long d;
    protected long e;
    protected int f;
    protected int g;

    AbstractC0262x2(j$.util.r rVar, D2 d2, int i) {
        this.a = rVar;
        this.b = d2;
        this.c = AbstractC0153f.h(rVar.estimateSize());
        this.d = 0L;
        this.e = i;
    }

    AbstractC0262x2(AbstractC0262x2 abstractC0262x2, j$.util.r rVar, long j, long j2, int i) {
        super(abstractC0262x2);
        this.a = rVar;
        this.b = abstractC0262x2.b;
        this.c = abstractC0262x2.c;
        this.d = j;
        this.e = j2;
        if (j < 0 || j2 < 0 || (j + j2) - 1 >= i) {
            throw new IllegalArgumentException(String.format("offset and length interval [%d, %d + %d) is not within array size interval [0, %d)", Long.valueOf(j), Long.valueOf(j), Long.valueOf(j2), Integer.valueOf(i)));
        }
    }

    public /* synthetic */ void accept(double d) {
        AbstractC0238t1.f(this);
        throw null;
    }

    public /* synthetic */ void accept(int i) {
        AbstractC0238t1.d(this);
        throw null;
    }

    public /* synthetic */ void accept(long j) {
        AbstractC0238t1.e(this);
        throw null;
    }

    @Override // j$.util.function.Consumer
    public /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer.CC.$default$andThen(this, consumer);
    }

    abstract AbstractC0262x2 b(j$.util.r rVar, long j, long j2);

    @Override // java.util.concurrent.CountedCompleter
    public void compute() {
        j$.util.r trySplit;
        j$.util.r rVar = this.a;
        AbstractC0262x2 abstractC0262x2 = this;
        while (rVar.estimateSize() > abstractC0262x2.c && (trySplit = rVar.trySplit()) != null) {
            abstractC0262x2.setPendingCount(1);
            long estimateSize = trySplit.estimateSize();
            abstractC0262x2.b(trySplit, abstractC0262x2.d, estimateSize).fork();
            abstractC0262x2 = abstractC0262x2.b(rVar, abstractC0262x2.d + estimateSize, abstractC0262x2.e - estimateSize);
        }
        AbstractC0135c abstractC0135c = (AbstractC0135c) abstractC0262x2.b;
        Objects.requireNonNull(abstractC0135c);
        abstractC0135c.k0(abstractC0135c.s0(abstractC0262x2), rVar);
        abstractC0262x2.propagateCompletion();
    }

    @Override // j$.util.stream.InterfaceC0228r3
    public /* synthetic */ void m() {
    }

    @Override // j$.util.stream.InterfaceC0228r3
    public void n(long j) {
        long j2 = this.e;
        if (j > j2) {
            throw new IllegalStateException("size passed to Sink.begin exceeds array length");
        }
        int i = (int) this.d;
        this.f = i;
        this.g = i + ((int) j2);
    }

    @Override // j$.util.stream.InterfaceC0228r3
    public /* synthetic */ boolean o() {
        return false;
    }
}
