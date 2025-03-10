package j$.util;

import java.util.Objects;

/* renamed from: j$.util.h, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0119h implements j$.util.function.s, j$.util.function.m {
    private long count;
    private long sum;
    private long min = Long.MAX_VALUE;
    private long max = Long.MIN_VALUE;

    @Override // j$.util.function.m
    public void accept(int i) {
        accept(i);
    }

    @Override // j$.util.function.s
    public void accept(long j) {
        this.count++;
        this.sum += j;
        this.min = Math.min(this.min, j);
        this.max = Math.max(this.max, j);
    }

    public void b(C0119h c0119h) {
        this.count += c0119h.count;
        this.sum += c0119h.sum;
        this.min = Math.min(this.min, c0119h.min);
        this.max = Math.max(this.max, c0119h.max);
    }

    @Override // j$.util.function.s
    public j$.util.function.s f(j$.util.function.s sVar) {
        Objects.requireNonNull(sVar);
        return new j$.util.function.r(this, sVar);
    }

    @Override // j$.util.function.m
    public j$.util.function.m l(j$.util.function.m mVar) {
        Objects.requireNonNull(mVar);
        return new j$.util.function.l(this, mVar);
    }

    public String toString() {
        Object[] objArr = new Object[6];
        objArr[0] = C0119h.class.getSimpleName();
        objArr[1] = Long.valueOf(this.count);
        objArr[2] = Long.valueOf(this.sum);
        objArr[3] = Long.valueOf(this.min);
        long j = this.count;
        objArr[4] = Double.valueOf(j > 0 ? this.sum / j : 0.0d);
        objArr[5] = Long.valueOf(this.max);
        return String.format("%s{count=%d, sum=%d, min=%d, average=%f, max=%d}", objArr);
    }
}
