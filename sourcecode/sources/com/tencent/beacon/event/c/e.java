package com.tencent.beacon.event.c;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.concurrent.atomic.AtomicLong;

/* compiled from: LogIDGenerator.java */
/* loaded from: classes.dex */
class e implements Runnable {
    private volatile long a = 0;
    private volatile long b = 0;
    private volatile long c = 0;
    final /* synthetic */ g d;

    e(g gVar) {
        this.d = gVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        AtomicLong atomicLong;
        AtomicLong atomicLong2;
        AtomicLong atomicLong3;
        Context context;
        SharedPreferences a;
        String str;
        long j;
        long j2;
        long j3;
        long j4;
        long j5;
        long j6;
        long j7;
        long j8;
        synchronized (this.d) {
            atomicLong = this.d.r;
            long j9 = atomicLong.get();
            atomicLong2 = this.d.f4q;
            long j10 = atomicLong2.get();
            atomicLong3 = this.d.s;
            long j11 = atomicLong3.get();
            if (this.a == j9 && this.b == j10 && this.c == j11) {
                return;
            }
            this.a = j9;
            this.b = j10;
            this.c = j11;
            g gVar = this.d;
            context = gVar.m;
            a = gVar.a(context);
            SharedPreferences.Editor edit = a.edit();
            if (com.tencent.beacon.base.util.b.a(edit)) {
                j2 = this.d.p;
                SharedPreferences.Editor putLong = edit.putLong("on_date", j2).putLong("realtime_log_id", this.a).putLong("normal_log_id", this.b).putLong("immediate_log_id", this.c);
                j3 = this.d.t;
                SharedPreferences.Editor putLong2 = putLong.putLong("realtime_min_log_id", j3);
                j4 = this.d.u;
                SharedPreferences.Editor putLong3 = putLong2.putLong("realtime_max_log_id", j4);
                j5 = this.d.v;
                SharedPreferences.Editor putLong4 = putLong3.putLong("normal_min_log_id", j5);
                j6 = this.d.w;
                SharedPreferences.Editor putLong5 = putLong4.putLong("normal_max_log_id", j6);
                j7 = this.d.x;
                SharedPreferences.Editor putLong6 = putLong5.putLong("immediate_min_log_id", j7);
                j8 = this.d.y;
                putLong6.putLong("immediate_max_log_id", j8).apply();
            }
            StringBuilder sb = new StringBuilder();
            sb.append("[LogID ");
            str = this.d.n;
            sb.append(str);
            sb.append("]");
            String sb2 = sb.toString();
            j = this.d.p;
            com.tencent.beacon.base.util.c.a(sb2, "  write serial to sp, date: %s ,realtime: %d, normal: %d, immediate: %d ", Long.valueOf(j), Long.valueOf(this.a), Long.valueOf(this.b), Long.valueOf(this.c));
        }
    }
}
