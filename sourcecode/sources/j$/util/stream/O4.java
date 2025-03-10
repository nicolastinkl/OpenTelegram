package j$.util.stream;

import j$.util.r;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes2.dex */
abstract class O4 {
    protected final j$.util.r a;
    protected final boolean b;
    private final long c;
    private final AtomicLong d;

    O4(j$.util.r rVar, long j, long j2) {
        this.a = rVar;
        this.b = j2 < 0;
        this.c = j2 >= 0 ? j2 : 0L;
        this.d = new AtomicLong(j2 >= 0 ? j + j2 : j);
    }

    O4(j$.util.r rVar, O4 o4) {
        this.a = rVar;
        this.b = o4.b;
        this.d = o4.d;
        this.c = o4.c;
    }

    public final int characteristics() {
        return this.a.characteristics() & (-16465);
    }

    public final long estimateSize() {
        return this.a.estimateSize();
    }

    protected final long p(long j) {
        long j2;
        long min;
        do {
            j2 = this.d.get();
            if (j2 != 0) {
                min = Math.min(j2, j);
                if (min <= 0) {
                    break;
                }
            } else {
                if (this.b) {
                    return j;
                }
                return 0L;
            }
        } while (!this.d.compareAndSet(j2, j2 - min));
        if (this.b) {
            return Math.max(j - min, 0L);
        }
        long j3 = this.c;
        return j2 > j3 ? Math.max(min - (j2 - j3), 0L) : min;
    }

    protected abstract j$.util.r q(j$.util.r rVar);

    protected final int r() {
        if (this.d.get() > 0) {
            return 2;
        }
        return this.b ? 3 : 1;
    }

    public /* bridge */ /* synthetic */ r.a trySplit() {
        return (r.a) m162trySplit();
    }

    /* renamed from: trySplit, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ r.b m160trySplit() {
        return (r.b) m162trySplit();
    }

    /* renamed from: trySplit, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ r.c m161trySplit() {
        return (r.c) m162trySplit();
    }

    /* renamed from: trySplit, reason: collision with other method in class */
    public final j$.util.r m162trySplit() {
        j$.util.r trySplit;
        if (this.d.get() == 0 || (trySplit = this.a.trySplit()) == null) {
            return null;
        }
        return q(trySplit);
    }

    /* renamed from: trySplit, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ j$.util.s m163trySplit() {
        return (j$.util.s) m162trySplit();
    }
}
