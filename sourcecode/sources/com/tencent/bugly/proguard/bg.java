package com.tencent.bugly.proguard;

import android.os.Handler;
import android.os.Looper;

/* compiled from: BUGLY */
/* loaded from: classes.dex */
public final class bg extends Thread {
    public bf a;
    private a g;
    private boolean c = false;
    private boolean d = true;
    private boolean e = false;
    private int f = 1;
    public boolean b = true;

    /* compiled from: BUGLY */
    public interface a {
    }

    public final boolean a() {
        this.c = true;
        if (!isAlive()) {
            return false;
        }
        try {
            interrupt();
        } catch (Exception e) {
            al.b(e);
        }
        al.d("MainHandlerChecker is reset to null.", new Object[0]);
        this.a = null;
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:38:0x006e, code lost:
    
        r2.d();
     */
    @Override // java.lang.Thread, java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void run() {
        /*
            r10 = this;
            long r0 = java.lang.System.currentTimeMillis()
        L4:
            boolean r2 = r10.c
            if (r2 != 0) goto L99
            com.tencent.bugly.proguard.bf r2 = r10.a     // Catch: java.lang.OutOfMemoryError -> L8d java.lang.Exception -> L93
            r3 = 0
            if (r2 != 0) goto L15
            java.lang.String r2 = "Main handler checker is null. Stop thread monitor."
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch: java.lang.OutOfMemoryError -> L8d java.lang.Exception -> L93
            com.tencent.bugly.proguard.al.c(r2, r3)     // Catch: java.lang.OutOfMemoryError -> L8d java.lang.Exception -> L93
            return
        L15:
            boolean r4 = r2.c     // Catch: java.lang.OutOfMemoryError -> L8d java.lang.Exception -> L93
            if (r4 == 0) goto L26
            r2.c = r3     // Catch: java.lang.OutOfMemoryError -> L8d java.lang.Exception -> L93
            long r4 = android.os.SystemClock.uptimeMillis()     // Catch: java.lang.OutOfMemoryError -> L8d java.lang.Exception -> L93
            r2.d = r4     // Catch: java.lang.OutOfMemoryError -> L8d java.lang.Exception -> L93
            android.os.Handler r4 = r2.a     // Catch: java.lang.OutOfMemoryError -> L8d java.lang.Exception -> L93
            r4.post(r2)     // Catch: java.lang.OutOfMemoryError -> L8d java.lang.Exception -> L93
        L26:
            r10.a(r2)     // Catch: java.lang.OutOfMemoryError -> L8d java.lang.Exception -> L93
            boolean r4 = r10.b     // Catch: java.lang.OutOfMemoryError -> L8d java.lang.Exception -> L93
            r5 = 1
            if (r4 == 0) goto L6c
            boolean r4 = r10.d     // Catch: java.lang.OutOfMemoryError -> L8d java.lang.Exception -> L93
            if (r4 != 0) goto L33
            goto L6c
        L33:
            long r6 = r2.b()     // Catch: java.lang.OutOfMemoryError -> L8d java.lang.Exception -> L93
            r8 = 1510(0x5e6, double:7.46E-321)
            int r4 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r4 <= 0) goto L6c
            r8 = 199990(0x30d36, double:9.8808E-319)
            int r4 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r4 < 0) goto L45
            goto L6c
        L45:
            r8 = 5010(0x1392, double:2.4753E-320)
            int r4 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r4 > 0) goto L56
            r10.f = r5     // Catch: java.lang.OutOfMemoryError -> L8d java.lang.Exception -> L93
            java.lang.String r4 = "timeSinceMsgSent in [2s, 5s], record stack"
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch: java.lang.OutOfMemoryError -> L8d java.lang.Exception -> L93
            com.tencent.bugly.proguard.al.c(r4, r3)     // Catch: java.lang.OutOfMemoryError -> L8d java.lang.Exception -> L93
            r3 = 1
            goto L6c
        L56:
            int r4 = r10.f     // Catch: java.lang.OutOfMemoryError -> L8d java.lang.Exception -> L93
            int r4 = r4 + r5
            r10.f = r4     // Catch: java.lang.OutOfMemoryError -> L8d java.lang.Exception -> L93
            int r6 = r4 + (-1)
            r4 = r4 & r6
            if (r4 != 0) goto L61
            goto L62
        L61:
            r5 = 0
        L62:
            if (r5 == 0) goto L6b
            java.lang.String r4 = "timeSinceMsgSent in (5s, 200s), should record stack:true"
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch: java.lang.OutOfMemoryError -> L8d java.lang.Exception -> L93
            com.tencent.bugly.proguard.al.c(r4, r3)     // Catch: java.lang.OutOfMemoryError -> L8d java.lang.Exception -> L93
        L6b:
            r3 = r5
        L6c:
            if (r3 == 0) goto L71
            r2.d()     // Catch: java.lang.OutOfMemoryError -> L8d java.lang.Exception -> L93
        L71:
            com.tencent.bugly.proguard.bg$a r3 = r10.g     // Catch: java.lang.OutOfMemoryError -> L8d java.lang.Exception -> L93
            if (r3 == 0) goto L7f
            boolean r3 = r10.d     // Catch: java.lang.OutOfMemoryError -> L8d java.lang.Exception -> L93
            if (r3 == 0) goto L7f
            r2.a()     // Catch: java.lang.OutOfMemoryError -> L8d java.lang.Exception -> L93
            r2.b()     // Catch: java.lang.OutOfMemoryError -> L8d java.lang.Exception -> L93
        L7f:
            long r2 = java.lang.System.currentTimeMillis()     // Catch: java.lang.OutOfMemoryError -> L8d java.lang.Exception -> L93
            long r2 = r2 - r0
            r4 = 500(0x1f4, double:2.47E-321)
            long r2 = r2 % r4
            long r4 = r4 - r2
            com.tencent.bugly.proguard.ap.b(r4)     // Catch: java.lang.OutOfMemoryError -> L8d java.lang.Exception -> L93
            goto L4
        L8d:
            r2 = move-exception
            com.tencent.bugly.proguard.al.b(r2)
            goto L4
        L93:
            r2 = move-exception
            com.tencent.bugly.proguard.al.b(r2)
            goto L4
        L99:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.bg.run():void");
    }

    private synchronized void a(bf bfVar) {
        if (this.d) {
            return;
        }
        if (this.e && !bfVar.a()) {
            al.c("Restart getting main stack trace.", new Object[0]);
            this.d = true;
            this.e = false;
        }
    }

    public final synchronized void c() {
        this.d = false;
        al.c("Record stack trace is disabled.", new Object[0]);
    }

    public final synchronized void d() {
        this.e = true;
    }

    public final boolean b() {
        Handler handler = new Handler(Looper.getMainLooper());
        bf bfVar = this.a;
        if (bfVar == null) {
            this.a = new bf(handler, handler.getLooper().getThread().getName());
        } else {
            bfVar.b = 5000L;
        }
        if (isAlive()) {
            return false;
        }
        try {
            start();
            return true;
        } catch (Exception e) {
            al.b(e);
            return false;
        }
    }
}
