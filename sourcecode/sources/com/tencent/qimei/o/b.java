package com.tencent.qimei.o;

import android.os.Build;
import android.os.Process;
import android.os.SystemClock;
import android.text.TextUtils;
import com.tencent.qimei.sdk.Qimei;

/* compiled from: AsyInitTask.java */
/* loaded from: classes.dex */
public class b implements Runnable {
    public final /* synthetic */ d a;

    public b(d dVar) {
        this.a = dVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        com.tencent.qimei.j.f fVar;
        String str;
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        fVar = this.a.i;
        fVar.b();
        com.tencent.qimei.k.a.b(d.a, "collectionRateTask is running", new Object[0]);
        str = this.a.c;
        if (TextUtils.isEmpty(com.tencent.qimei.i.f.a(str).c("is_first"))) {
            d dVar = this.a;
            long elapsedRealtime = SystemClock.elapsedRealtime();
            long j = 0;
            if (Build.VERSION.SDK_INT >= 24) {
                j = Process.getStartElapsedRealtime();
            } else {
                elapsedRealtime = -1;
            }
            dVar.h = String.valueOf(elapsedRealtime - j);
            int nextInt = com.tencent.qimei.s.e.a.nextInt(3) + 1;
            this.a.a(nextInt);
            str2 = this.a.c;
            Qimei d = com.tencent.qimei.a.a.d(str2);
            if (d != null) {
                this.a.f = TextUtils.isEmpty(d.a()) ? "0" : "1";
                this.a.g = TextUtils.isEmpty(d.b()) ? "0" : "1";
            }
            str3 = this.a.c;
            str4 = this.a.f;
            str5 = this.a.g;
            str6 = this.a.h;
            com.tencent.qimei.n.c a = com.tencent.qimei.n.i.a().a(com.tencent.qimei.n.e.REPORT_COLLECT_RATE_Q16.K, str4).a(com.tencent.qimei.n.e.REPORT_COLLECT_RATE_Q36.K, str5).a(com.tencent.qimei.n.e.REPORT_COLLECT_RATE_DELAY.K, String.valueOf(nextInt)).a(com.tencent.qimei.n.e.REPORT_STARTUP_DURAtION.K, str6);
            a.a = str3;
            a.c = "/report";
            a.a("v8");
        }
    }
}
