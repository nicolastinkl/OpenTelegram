package com.tencent.qmsp.oaid2;

import android.content.Context;

/* loaded from: classes.dex */
public class l0 {
    public Context a;

    public l0(Context context) {
        this.a = context;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x004e  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0054  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String a(int r8, java.lang.String r9) {
        /*
            r7 = this;
            r0 = 0
            if (r8 == 0) goto L37
            r1 = 1
            if (r8 == r1) goto L21
            r1 = 2
            if (r8 == r1) goto Lb
            r2 = r0
            goto L3e
        Lb:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r1 = "content://com.vivo.vms.IdProvider/IdentifierId/AAID_"
            r8.append(r1)
            r8.append(r9)
            java.lang.String r8 = r8.toString()
            android.net.Uri r8 = android.net.Uri.parse(r8)
            goto L3d
        L21:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r1 = "content://com.vivo.vms.IdProvider/IdentifierId/VAID_"
            r8.append(r1)
            r8.append(r9)
            java.lang.String r8 = r8.toString()
            android.net.Uri r8 = android.net.Uri.parse(r8)
            goto L3d
        L37:
            java.lang.String r8 = "content://com.vivo.vms.IdProvider/IdentifierId/OAID"
            android.net.Uri r8 = android.net.Uri.parse(r8)
        L3d:
            r2 = r8
        L3e:
            android.content.Context r8 = r7.a
            android.content.ContentResolver r1 = r8.getContentResolver()
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            android.database.Cursor r8 = r1.query(r2, r3, r4, r5, r6)
            if (r8 != 0) goto L54
            java.lang.String r8 = "return cursor is null,return"
            com.tencent.qmsp.oaid2.c.b(r8)
            goto L67
        L54:
            boolean r9 = r8.moveToNext()
            if (r9 == 0) goto L64
            java.lang.String r9 = "value"
            int r9 = r8.getColumnIndex(r9)
            java.lang.String r0 = r8.getString(r9)
        L64:
            r8.close()
        L67:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.qmsp.oaid2.l0.a(int, java.lang.String):java.lang.String");
    }
}
