package io.openinstall.sdk;

/* loaded from: classes.dex */
public class ah implements aa {
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0061, code lost:
    
        if (r10.isClosed() == false) goto L34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x0047, code lost:
    
        if (r10.isClosed() == false) goto L34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0063, code lost:
    
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
            java.lang.String r1 = "content://com.meizu.flyme.openidsdk/"
            android.net.Uri r3 = android.net.Uri.parse(r1)     // Catch: java.lang.Throwable -> L4a java.lang.Exception -> L5a
            android.content.ContentResolver r2 = r10.getContentResolver()     // Catch: java.lang.Throwable -> L4a java.lang.Exception -> L5a
            r4 = 0
            r5 = 0
            java.lang.String r10 = "oaid"
            java.lang.String[] r6 = new java.lang.String[]{r10}     // Catch: java.lang.Throwable -> L4a java.lang.Exception -> L5a
            r7 = 0
            android.database.Cursor r10 = r2.query(r3, r4, r5, r6, r7)     // Catch: java.lang.Throwable -> L4a java.lang.Exception -> L5a
            if (r10 == 0) goto L41
            r10.moveToFirst()     // Catch: java.lang.Throwable -> L3d java.lang.Exception -> L3f
            java.lang.String r1 = "value"
            int r1 = r10.getColumnIndex(r1)     // Catch: java.lang.Throwable -> L3d java.lang.Exception -> L3f
            if (r1 >= 0) goto L2f
            boolean r1 = r10.isClosed()
            if (r1 != 0) goto L2e
            r10.close()
        L2e:
            return r0
        L2f:
            java.lang.String r0 = r10.getString(r1)     // Catch: java.lang.Throwable -> L3d java.lang.Exception -> L3f
            boolean r1 = r10.isClosed()
            if (r1 != 0) goto L3c
            r10.close()
        L3c:
            return r0
        L3d:
            r0 = move-exception
            goto L4e
        L3f:
            goto L5b
        L41:
            if (r10 == 0) goto L66
            boolean r1 = r10.isClosed()
            if (r1 != 0) goto L66
            goto L63
        L4a:
            r10 = move-exception
            r8 = r0
            r0 = r10
            r10 = r8
        L4e:
            if (r10 == 0) goto L59
            boolean r1 = r10.isClosed()
            if (r1 != 0) goto L59
            r10.close()
        L59:
            throw r0
        L5a:
            r10 = r0
        L5b:
            if (r10 == 0) goto L66
            boolean r1 = r10.isClosed()
            if (r1 != 0) goto L66
        L63:
            r10.close()
        L66:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.openinstall.sdk.ah.a(android.content.Context):java.lang.String");
    }
}
