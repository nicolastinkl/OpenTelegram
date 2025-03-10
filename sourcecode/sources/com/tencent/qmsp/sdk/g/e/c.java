package com.tencent.qmsp.sdk.g.e;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;

/* loaded from: classes.dex */
public class c {
    public static volatile c g;
    public Boolean e;
    public BroadcastReceiver f;
    public b a = new b("udid");
    public b b = new b("oaid");
    public b d = new b("vaid");
    public b c = new b("aaid");

    public static final c a() {
        if (g == null) {
            synchronized (c.class) {
                g = new c();
            }
        }
        return g;
    }

    public static e a(Cursor cursor) {
        String str;
        e eVar = new e(null, 0);
        if (cursor == null) {
            str = "parseValue fail, cursor is null.";
        } else if (cursor.isClosed()) {
            str = "parseValue fail, cursor is closed.";
        } else {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex("value");
            if (columnIndex >= 0) {
                eVar.c = cursor.getString(columnIndex);
            } else {
                b("parseValue fail, index < 0.");
            }
            int columnIndex2 = cursor.getColumnIndex("code");
            if (columnIndex2 >= 0) {
                eVar.a = cursor.getInt(columnIndex2);
            } else {
                b("parseCode fail, index < 0.");
            }
            int columnIndex3 = cursor.getColumnIndex("expired");
            if (columnIndex3 >= 0) {
                eVar.b = cursor.getLong(columnIndex3);
                return eVar;
            }
            str = "parseExpired fail, index < 0.";
        }
        b(str);
        return eVar;
    }

    public static void b(String str) {
        com.tencent.qmsp.sdk.base.c.a("MzOpenIdManager " + str);
    }

    public b a(String str) {
        if ("oaid".equals(str)) {
            return this.b;
        }
        if ("vaid".equals(str)) {
            return this.d;
        }
        if ("aaid".equals(str)) {
            return this.c;
        }
        if ("udid".equals(str)) {
            return this.a;
        }
        return null;
    }

    public final String a(Context context, b bVar) {
        Cursor cursor;
        Cursor cursor2 = null;
        if (bVar == null) {
            return null;
        }
        if (bVar.a()) {
            return bVar.d;
        }
        b("queryId : " + bVar.c);
        try {
            cursor = context.getContentResolver().query(Uri.parse("content://com.meizu.flyme.openidsdk/"), null, null, new String[]{bVar.c}, null);
        } catch (Exception unused) {
            cursor = null;
        } catch (Throwable th) {
            th = th;
        }
        try {
            if (cursor == null) {
                try {
                    a(context, false);
                    boolean a = a(context, true);
                    StringBuilder sb = new StringBuilder();
                    sb.append("forceQuery isSupported : ");
                    sb.append(a);
                    b(sb.toString());
                    if (cursor != null) {
                        cursor.close();
                    }
                    return null;
                } catch (Exception unused2) {
                    if (cursor == null) {
                        return null;
                    }
                    cursor.close();
                    return null;
                }
            }
            try {
                e a2 = a(cursor);
                String str = a2.c;
                try {
                    bVar.a(str);
                    bVar.a(a2.b);
                    bVar.a(a2.a);
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(bVar.c);
                    sb2.append(" errorCode : ");
                    sb2.append(bVar.a);
                    b(sb2.toString());
                    if (a2.a == 1000) {
                        cursor.close();
                        return str;
                    }
                    a(context);
                    if (a(context, false)) {
                        cursor.close();
                        return str;
                    }
                    boolean a3 = a(context, true);
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("not support, forceQuery isSupported: ");
                    sb3.append(a3);
                    b(sb3.toString());
                    cursor.close();
                    return str;
                } catch (Exception unused3) {
                    cursor.close();
                    return null;
                }
            } catch (Exception unused4) {
                cursor.close();
                return null;
            }
        } catch (Throwable th2) {
            th = th2;
            cursor2 = cursor;
            if (cursor2 != null) {
                cursor2.close();
            }
            throw th;
        }
    }

    public final void a(Context context) {
        synchronized (this) {
            if (this.f == null) {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("com.meizu.flyme.openid.ACTION_OPEN_ID_CHANGE");
                a aVar = new a();
                this.f = aVar;
                context.registerReceiver(aVar, intentFilter, "com.meizu.flyme.openid.permission.OPEN_ID_CHANGE", null);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:46:0x0020, code lost:
    
        if (r10.resolveContentProvider("com.meizu.flyme.openidsdk", 0) == null) goto L7;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean a(android.content.Context r9, boolean r10) {
        /*
            r8 = this;
            java.lang.Boolean r0 = r8.e
            r1 = 0
            if (r0 == 0) goto Ld
            if (r10 != 0) goto Ld
            boolean r1 = r0.booleanValue()
            goto L91
        Ld:
            if (r9 != 0) goto L11
        Lf:
            r0 = 0
            goto L23
        L11:
            android.content.pm.PackageManager r10 = r9.getPackageManager()
            if (r10 != 0) goto L19
            r0 = 0
            goto L1a
        L19:
            r0 = 1
        L1a:
            java.lang.String r2 = "com.meizu.flyme.openidsdk"
            android.content.pm.ProviderInfo r10 = r10.resolveContentProvider(r2, r1)
            if (r10 != 0) goto L23
            goto Lf
        L23:
            if (r0 != 0) goto L2f
            java.lang.String r9 = "is not Supported, for isLegalProvider : false"
            b(r9)
            java.lang.Boolean r9 = java.lang.Boolean.FALSE
            r8.e = r9
            goto L91
        L2f:
            java.lang.String r10 = "content://com.meizu.flyme.openidsdk/"
            android.net.Uri r3 = android.net.Uri.parse(r10)
            r10 = 0
            android.content.ContentResolver r2 = r9.getContentResolver()     // Catch: java.lang.Exception -> L4e java.lang.Throwable -> L80
            r4 = 0
            r5 = 0
            java.lang.String r9 = "supported"
            java.lang.String[] r6 = new java.lang.String[]{r9}     // Catch: java.lang.Exception -> L4e java.lang.Throwable -> L80
            r7 = 0
            android.database.Cursor r10 = r2.query(r3, r4, r5, r6, r7)     // Catch: java.lang.Exception -> L4e java.lang.Throwable -> L80
            if (r10 != 0) goto L50
            java.lang.Boolean r9 = java.lang.Boolean.FALSE     // Catch: java.lang.Exception -> L4e java.lang.Throwable -> L80
            r8.e = r9     // Catch: java.lang.Exception -> L4e java.lang.Throwable -> L80
            goto L50
        L4e:
            goto L8c
        L50:
            com.tencent.qmsp.sdk.g.e.e r9 = a(r10)     // Catch: java.lang.Throwable -> L80 java.lang.Exception -> L82
            java.lang.String r9 = r9.c     // Catch: java.lang.Throwable -> L80 java.lang.Exception -> L82
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L80 java.lang.Exception -> L82
            r0.<init>()     // Catch: java.lang.Throwable -> L80 java.lang.Exception -> L82
            java.lang.String r2 = "querySupport, result : "
            r0.append(r2)     // Catch: java.lang.Throwable -> L80 java.lang.Exception -> L82
            r0.append(r9)     // Catch: java.lang.Throwable -> L80 java.lang.Exception -> L82
            java.lang.String r0 = r0.toString()     // Catch: java.lang.Throwable -> L80 java.lang.Exception -> L82
            b(r0)     // Catch: java.lang.Throwable -> L80 java.lang.Exception -> L82
            java.lang.String r0 = "0"
            boolean r9 = r0.equals(r9)     // Catch: java.lang.Throwable -> L80 java.lang.Exception -> L82
            java.lang.Boolean r9 = java.lang.Boolean.valueOf(r9)     // Catch: java.lang.Throwable -> L80 java.lang.Exception -> L82
            r8.e = r9     // Catch: java.lang.Throwable -> L80 java.lang.Exception -> L82
            boolean r9 = r9.booleanValue()     // Catch: java.lang.Throwable -> L80 java.lang.Exception -> L82
            if (r10 == 0) goto L7f
            r10.close()
        L7f:
            return r9
        L80:
            r9 = move-exception
            goto L86
        L82:
            if (r10 == 0) goto L91
            goto L8e
        L86:
            if (r10 == 0) goto L8b
            r10.close()
        L8b:
            throw r9
        L8c:
            if (r10 == 0) goto L91
        L8e:
            r10.close()
        L91:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.qmsp.sdk.g.e.c.a(android.content.Context, boolean):boolean");
    }
}
