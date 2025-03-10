package com.tencent.qmsp.sdk.g.i;

import android.content.Context;

/* loaded from: classes.dex */
public class a {
    private Context a;

    public a(Context context) {
        this.a = context;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0042  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String a(int r8, java.lang.String r9) {
        /*
            r7 = this;
            r0 = 0
            if (r8 == 0) goto L25
            r1 = 1
            if (r8 == r1) goto L13
            r1 = 2
            if (r8 == r1) goto Lb
            r2 = r0
            goto L2c
        Lb:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r1 = "content://com.vivo.vms.IdProvider/IdentifierId/AAID_"
            goto L1a
        L13:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r1 = "content://com.vivo.vms.IdProvider/IdentifierId/VAID_"
        L1a:
            r8.append(r1)
            r8.append(r9)
            java.lang.String r8 = r8.toString()
            goto L27
        L25:
            java.lang.String r8 = "content://com.vivo.vms.IdProvider/IdentifierId/OAID"
        L27:
            android.net.Uri r8 = android.net.Uri.parse(r8)
            r2 = r8
        L2c:
            android.content.Context r8 = r7.a
            android.content.ContentResolver r1 = r8.getContentResolver()
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            android.database.Cursor r8 = r1.query(r2, r3, r4, r5, r6)
            if (r8 != 0) goto L42
            java.lang.String r8 = "return cursor is null,return"
            com.tencent.qmsp.sdk.base.c.b(r8)
            goto L55
        L42:
            boolean r9 = r8.moveToNext()
            if (r9 == 0) goto L52
            java.lang.String r9 = "value"
            int r9 = r8.getColumnIndex(r9)
            java.lang.String r0 = r8.getString(r9)
        L52:
            r8.close()
        L55:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.qmsp.sdk.g.i.a.a(int, java.lang.String):java.lang.String");
    }
}
