package j$.util.stream;

import j$.util.r;

/* loaded from: classes2.dex */
abstract class I4 {
    final long a;
    final long b;
    j$.util.r c;
    long d;
    long e;

    I4(j$.util.r rVar, long j, long j2, long j3, long j4) {
        this.c = rVar;
        this.a = j;
        this.b = j2;
        this.d = j3;
        this.e = j4;
    }

    protected abstract j$.util.r a(j$.util.r rVar, long j, long j2, long j3, long j4);

    public int characteristics() {
        return this.c.characteristics();
    }

    public long estimateSize() {
        long j = this.a;
        long j2 = this.e;
        if (j < j2) {
            return j2 - Math.max(j, this.d);
        }
        return 0L;
    }

    public /* bridge */ /* synthetic */ r.a trySplit() {
        return (r.a) m158trySplit();
    }

    /* renamed from: trySplit, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ r.b m156trySplit() {
        return (r.b) m158trySplit();
    }

    /* renamed from: trySplit, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ r.c m157trySplit() {
        return (r.c) m158trySplit();
    }

    /* renamed from: trySplit, reason: collision with other method in class */
    public j$.util.r m158trySplit() {
        long j = this.a;
        long j2 = this.e;
        if (j >= j2 || this.d >= j2) {
            return null;
        }
        while (true) {
            j$.util.r trySplit = this.c.trySplit();
            if (trySplit == null) {
                return null;
            }
            long estimateSize = trySplit.estimateSize() + this.d;
            long min = Math.min(estimateSize, this.b);
            long j3 = this.a;
            if (j3 >= min) {
                this.d = min;
            } else {
                long j4 = this.b;
                if (min < j4) {
                    long j5 = this.d;
                    if (j5 < j3 || estimateSize > j4) {
                        this.d = min;
                        return a(trySplit, j3, j4, j5, min);
                    }
                    this.d = min;
                    return trySplit;
                }
                this.c = trySplit;
                this.e = min;
            }
        }
    }

    /* renamed from: trySplit, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ j$.util.s m159trySplit() {
        return (j$.util.s) m158trySplit();
    }
}
