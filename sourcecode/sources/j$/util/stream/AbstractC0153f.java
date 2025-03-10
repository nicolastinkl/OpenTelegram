package j$.util.stream;

import java.util.concurrent.CountedCompleter;
import java.util.concurrent.ForkJoinPool;

/* renamed from: j$.util.stream.f, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
abstract class AbstractC0153f extends CountedCompleter {
    static final int g = ForkJoinPool.getCommonPoolParallelism() << 2;
    protected final D2 a;
    protected j$.util.r b;
    protected long c;
    protected AbstractC0153f d;
    protected AbstractC0153f e;
    private Object f;

    protected AbstractC0153f(D2 d2, j$.util.r rVar) {
        super(null);
        this.a = d2;
        this.b = rVar;
        this.c = 0L;
    }

    protected AbstractC0153f(AbstractC0153f abstractC0153f, j$.util.r rVar) {
        super(abstractC0153f);
        this.b = rVar;
        this.a = abstractC0153f.a;
        this.c = abstractC0153f.c;
    }

    public static long h(long j) {
        long j2 = j / g;
        if (j2 > 0) {
            return j2;
        }
        return 1L;
    }

    protected abstract Object a();

    protected Object b() {
        return this.f;
    }

    protected AbstractC0153f c() {
        return (AbstractC0153f) getCompleter();
    }

    @Override // java.util.concurrent.CountedCompleter
    public void compute() {
        j$.util.r trySplit;
        j$.util.r rVar = this.b;
        long estimateSize = rVar.estimateSize();
        long j = this.c;
        if (j == 0) {
            j = h(estimateSize);
            this.c = j;
        }
        boolean z = false;
        AbstractC0153f abstractC0153f = this;
        while (estimateSize > j && (trySplit = rVar.trySplit()) != null) {
            AbstractC0153f f = abstractC0153f.f(trySplit);
            abstractC0153f.d = f;
            AbstractC0153f f2 = abstractC0153f.f(rVar);
            abstractC0153f.e = f2;
            abstractC0153f.setPendingCount(1);
            if (z) {
                rVar = trySplit;
                abstractC0153f = f;
                f = f2;
            } else {
                abstractC0153f = f2;
            }
            z = !z;
            f.fork();
            estimateSize = rVar.estimateSize();
        }
        abstractC0153f.g(abstractC0153f.a());
        abstractC0153f.tryComplete();
    }

    protected boolean d() {
        return this.d == null;
    }

    protected boolean e() {
        return c() == null;
    }

    protected abstract AbstractC0153f f(j$.util.r rVar);

    protected void g(Object obj) {
        this.f = obj;
    }

    @Override // java.util.concurrent.CountedCompleter, java.util.concurrent.ForkJoinTask
    public Object getRawResult() {
        return this.f;
    }

    @Override // java.util.concurrent.CountedCompleter
    public void onCompletion(CountedCompleter countedCompleter) {
        this.b = null;
        this.e = null;
        this.d = null;
    }

    @Override // java.util.concurrent.CountedCompleter, java.util.concurrent.ForkJoinTask
    protected void setRawResult(Object obj) {
        if (obj != null) {
            throw new IllegalStateException();
        }
    }
}
