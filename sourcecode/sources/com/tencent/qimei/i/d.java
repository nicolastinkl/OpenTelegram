package com.tencent.qimei.i;

/* compiled from: PropertiesFile.java */
/* loaded from: classes.dex */
public class d implements Runnable {
    public final /* synthetic */ Runnable a;
    public final /* synthetic */ e b;

    public d(e eVar, Runnable runnable) {
        this.b = eVar;
        this.a = runnable;
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x001d, code lost:
    
        r1.release();
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x001b, code lost:
    
        if (r1 == null) goto L15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x0011, code lost:
    
        if (r1 != null) goto L29;
     */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void run() {
        /*
            r3 = this;
            com.tencent.qimei.i.e r0 = r3.b
            monitor-enter(r0)
            r1 = 0
            com.tencent.qimei.i.e r2 = r3.b     // Catch: java.lang.Exception -> L14 java.lang.Throwable -> L16
            java.nio.channels.FileChannel r2 = r2.b     // Catch: java.lang.Exception -> L14 java.lang.Throwable -> L16
            java.nio.channels.FileLock r1 = r2.lock()     // Catch: java.lang.Exception -> L14 java.lang.Throwable -> L16
            java.lang.Runnable r2 = r3.a     // Catch: java.lang.Exception -> L14 java.lang.Throwable -> L16
            r2.run()     // Catch: java.lang.Exception -> L14 java.lang.Throwable -> L16
            if (r1 == 0) goto L20
            goto L1d
        L14:
            r2 = move-exception
            goto L18
        L16:
            r2 = move-exception
            goto L22
        L18:
            r2.getMessage()     // Catch: java.lang.Throwable -> L16
            if (r1 == 0) goto L20
        L1d:
            r1.release()     // Catch: java.io.IOException -> L20 java.lang.Throwable -> L28
        L20:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L28
            return
        L22:
            if (r1 == 0) goto L27
            r1.release()     // Catch: java.io.IOException -> L27 java.lang.Throwable -> L28
        L27:
            throw r2     // Catch: java.lang.Throwable -> L28
        L28:
            r1 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L28
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.qimei.i.d.run():void");
    }
}
