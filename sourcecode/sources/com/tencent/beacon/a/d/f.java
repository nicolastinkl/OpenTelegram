package com.tencent.beacon.a.d;

/* compiled from: PropertiesFile.java */
/* loaded from: classes.dex */
class f implements Runnable {
    final /* synthetic */ Runnable a;
    final /* synthetic */ g b;

    f(g gVar, Runnable runnable) {
        this.b = gVar;
        this.a = runnable;
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0047, code lost:
    
        r1.release();
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0045, code lost:
    
        if (r1 == null) goto L20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x0013, code lost:
    
        if (r1 != null) goto L30;
     */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void run() {
        /*
            r8 = this;
            com.tencent.beacon.a.d.g r0 = r8.b
            monitor-enter(r0)
            r1 = 0
            com.tencent.beacon.a.d.g r2 = r8.b     // Catch: java.lang.Exception -> L16 java.lang.Throwable -> L18
            java.nio.channels.FileChannel r2 = com.tencent.beacon.a.d.g.e(r2)     // Catch: java.lang.Exception -> L16 java.lang.Throwable -> L18
            java.nio.channels.FileLock r1 = r2.lock()     // Catch: java.lang.Exception -> L16 java.lang.Throwable -> L18
            java.lang.Runnable r2 = r8.a     // Catch: java.lang.Exception -> L16 java.lang.Throwable -> L18
            r2.run()     // Catch: java.lang.Exception -> L16 java.lang.Throwable -> L18
            if (r1 == 0) goto L4a
            goto L47
        L16:
            r2 = move-exception
            goto L1a
        L18:
            r2 = move-exception
            goto L4c
        L1a:
            java.lang.String r3 = "[properties]"
            r4 = 1
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch: java.lang.Throwable -> L18
            r5 = 0
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L18
            r6.<init>()     // Catch: java.lang.Throwable -> L18
            java.lang.String r7 = "file get lock error:"
            r6.append(r7)     // Catch: java.lang.Throwable -> L18
            java.lang.String r7 = r2.getMessage()     // Catch: java.lang.Throwable -> L18
            r6.append(r7)     // Catch: java.lang.Throwable -> L18
            java.lang.String r6 = r6.toString()     // Catch: java.lang.Throwable -> L18
            r4[r5] = r6     // Catch: java.lang.Throwable -> L18
            com.tencent.beacon.base.util.c.b(r3, r4)     // Catch: java.lang.Throwable -> L18
            com.tencent.beacon.a.b.g r3 = com.tencent.beacon.a.b.g.e()     // Catch: java.lang.Throwable -> L18
            java.lang.String r4 = "504"
            java.lang.String r5 = "[properties] File get lock error!"
            r3.a(r4, r5, r2)     // Catch: java.lang.Throwable -> L18
            if (r1 == 0) goto L4a
        L47:
            r1.release()     // Catch: java.io.IOException -> L4a java.lang.Throwable -> L52
        L4a:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L52
            return
        L4c:
            if (r1 == 0) goto L51
            r1.release()     // Catch: java.io.IOException -> L51 java.lang.Throwable -> L52
        L51:
            throw r2     // Catch: java.lang.Throwable -> L52
        L52:
            r1 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L52
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.beacon.a.d.f.run():void");
    }
}
