package j$.util;

import androidx.recyclerview.widget.LinearLayoutManager;
import java.util.Objects;

/* renamed from: j$.util.g, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0118g implements j$.util.function.m {
    private long count;
    private long sum;
    private int min = Integer.MAX_VALUE;
    private int max = LinearLayoutManager.INVALID_OFFSET;

    @Override // j$.util.function.m
    public void accept(int i) {
        this.count++;
        this.sum += i;
        this.min = Math.min(this.min, i);
        this.max = Math.max(this.max, i);
    }

    public void b(C0118g c0118g) {
        this.count += c0118g.count;
        this.sum += c0118g.sum;
        this.min = Math.min(this.min, c0118g.min);
        this.max = Math.max(this.max, c0118g.max);
    }

    @Override // j$.util.function.m
    public j$.util.function.m l(j$.util.function.m mVar) {
        Objects.requireNonNull(mVar);
        return new j$.util.function.l(this, mVar);
    }

    public String toString() {
        Object[] objArr = new Object[6];
        objArr[0] = C0118g.class.getSimpleName();
        objArr[1] = Long.valueOf(this.count);
        objArr[2] = Long.valueOf(this.sum);
        objArr[3] = Integer.valueOf(this.min);
        long j = this.count;
        objArr[4] = Double.valueOf(j > 0 ? this.sum / j : 0.0d);
        objArr[5] = Integer.valueOf(this.max);
        return String.format("%s{count=%d, sum=%d, min=%d, average=%f, max=%d}", objArr);
    }
}
