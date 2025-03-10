package io.openinstall.sdk;

/* loaded from: classes.dex */
public class al implements aa {
    /* JADX WARN: Code restructure failed: missing block: B:27:0x005c, code lost:
    
        if (r10.isClosed() == false) goto L34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x0042, code lost:
    
        if (r10.isClosed() == false) goto L34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x005e, code lost:
    
        r10.close();
     */
    @Override // io.openinstall.sdk.aa
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String a(android.content.Context r10) {
        /*
            r9 = this;
            r0 = 0
            java.lang.String r1 = "content://com.vivo.vms.IdProvider/IdentifierId/OAID"
            android.net.Uri r3 = android.net.Uri.parse(r1)     // Catch: java.lang.Throwable -> L45 java.lang.Exception -> L55
            android.content.ContentResolver r2 = r10.getContentResolver()     // Catch: java.lang.Throwable -> L45 java.lang.Exception -> L55
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            android.database.Cursor r10 = r2.query(r3, r4, r5, r6, r7)     // Catch: java.lang.Throwable -> L45 java.lang.Exception -> L55
            if (r10 == 0) goto L3c
            r10.moveToFirst()     // Catch: java.lang.Throwable -> L38 java.lang.Exception -> L3a
            java.lang.String r1 = "value"
            int r1 = r10.getColumnIndex(r1)     // Catch: java.lang.Throwable -> L38 java.lang.Exception -> L3a
            if (r1 >= 0) goto L2a
            boolean r1 = r10.isClosed()
            if (r1 != 0) goto L29
            r10.close()
        L29:
            return r0
        L2a:
            java.lang.String r0 = r10.getString(r1)     // Catch: java.lang.Throwable -> L38 java.lang.Exception -> L3a
            boolean r1 = r10.isClosed()
            if (r1 != 0) goto L37
            r10.close()
        L37:
            return r0
        L38:
            r0 = move-exception
            goto L49
        L3a:
            goto L56
        L3c:
            if (r10 == 0) goto L61
            boolean r1 = r10.isClosed()
            if (r1 != 0) goto L61
            goto L5e
        L45:
            r10 = move-exception
            r8 = r0
            r0 = r10
            r10 = r8
        L49:
            if (r10 == 0) goto L54
            boolean r1 = r10.isClosed()
            if (r1 != 0) goto L54
            r10.close()
        L54:
            throw r0
        L55:
            r10 = r0
        L56:
            if (r10 == 0) goto L61
            boolean r1 = r10.isClosed()
            if (r1 != 0) goto L61
        L5e:
            r10.close()
        L61:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.openinstall.sdk.al.a(android.content.Context):java.lang.String");
    }
}
