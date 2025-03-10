package io.openinstall.sdk;

import android.os.SystemClock;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class dx implements Delayed {
    private final long a;
    private final boolean b;

    private dx(long j, boolean z) {
        this.a = SystemClock.elapsedRealtime() + j;
        this.b = z;
    }

    public static dx a() {
        return new dx(0L, false);
    }

    public static dx b() {
        return new dx(800L, true);
    }

    public int a(dx dxVar) {
        long j = this.a;
        long j2 = dxVar.a;
        if (j < j2) {
            return -1;
        }
        return j > j2 ? 1 : 0;
    }

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(Delayed delayed) {
        return a((dx) delayed);
    }

    public boolean c() {
        return this.b;
    }

    @Override // java.util.concurrent.Delayed
    public long getDelay(TimeUnit timeUnit) {
        return timeUnit.convert(this.a - SystemClock.elapsedRealtime(), TimeUnit.MILLISECONDS);
    }
}
