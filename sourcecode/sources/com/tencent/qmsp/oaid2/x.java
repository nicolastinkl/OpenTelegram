package com.tencent.qmsp.oaid2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;

/* loaded from: classes.dex */
public class x {
    public static volatile x g;
    public Boolean e;
    public BroadcastReceiver f;
    public w a = new w("udid");
    public w b = new w("oaid");
    public w d = new w("vaid");
    public w c = new w("aaid");

    public static z a(Cursor cursor) {
        z zVar = new z(null, 0);
        if (cursor == null) {
            b("parseValue fail, cursor is null.");
        } else if (cursor.isClosed()) {
            b("parseValue fail, cursor is closed.");
        } else {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex("value");
            if (columnIndex >= 0) {
                zVar.c = cursor.getString(columnIndex);
            } else {
                b("parseValue fail, index < 0.");
            }
            int columnIndex2 = cursor.getColumnIndex("code");
            if (columnIndex2 >= 0) {
                zVar.a = cursor.getInt(columnIndex2);
            } else {
                b("parseCode fail, index < 0.");
            }
            int columnIndex3 = cursor.getColumnIndex("expired");
            if (columnIndex3 >= 0) {
                zVar.b = cursor.getLong(columnIndex3);
            } else {
                b("parseExpired fail, index < 0.");
            }
        }
        return zVar;
    }

    public static void b(String str) {
        c.a("MzOpenIdManager " + str);
    }

    public final String a(Context context, w wVar) {
        Cursor cursor;
        Cursor cursor2 = null;
        if (wVar == null) {
            return null;
        }
        if (!wVar.a()) {
            b("queryId : " + wVar.c);
            try {
                cursor = context.getContentResolver().query(Uri.parse("content://com.meizu.flyme.openidsdk/"), null, null, new String[]{wVar.c}, null);
            } catch (Exception unused) {
                cursor = null;
            } catch (Throwable th) {
                th = th;
            }
            try {
                if (cursor == null) {
                    try {
                        a(context, false);
                        b("forceQuery isSupported : " + a(context, true));
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
                    z a = a(cursor);
                    String str = a.c;
                    try {
                        wVar.a(str);
                        wVar.a(a.b);
                        wVar.a(a.a);
                        b(wVar.c + " errorCode : " + wVar.a);
                        if (a.a == 1000) {
                            cursor.close();
                            return str;
                        }
                        a(context);
                        if (a(context, false)) {
                            cursor.close();
                            return str;
                        }
                        b("not support, forceQuery isSupported: " + a(context, true));
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
        return wVar.d;
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x0020, code lost:
    
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
            goto L90
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
            goto L90
        L2f:
            java.lang.String r10 = "content://com.meizu.flyme.openidsdk/"
            android.net.Uri r3 = android.net.Uri.parse(r10)
            r10 = 0
            android.content.ContentResolver r2 = r9.getContentResolver()     // Catch: java.lang.Exception -> L4e java.lang.Throwable -> L84
            r4 = 0
            r5 = 0
            java.lang.String r9 = "supported"
            java.lang.String[] r6 = new java.lang.String[]{r9}     // Catch: java.lang.Exception -> L4e java.lang.Throwable -> L84
            r7 = 0
            android.database.Cursor r10 = r2.query(r3, r4, r5, r6, r7)     // Catch: java.lang.Exception -> L4e java.lang.Throwable -> L84
            if (r10 != 0) goto L50
            java.lang.Boolean r9 = java.lang.Boolean.FALSE     // Catch: java.lang.Exception -> L4e java.lang.Throwable -> L84
            r8.e = r9     // Catch: java.lang.Exception -> L4e java.lang.Throwable -> L84
            goto L50
        L4e:
            goto L8b
        L50:
            com.tencent.qmsp.oaid2.z r9 = a(r10)     // Catch: java.lang.Exception -> L80 java.lang.Throwable -> L84
            java.lang.String r9 = r9.c     // Catch: java.lang.Exception -> L80 java.lang.Throwable -> L84
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L80 java.lang.Throwable -> L84
            r0.<init>()     // Catch: java.lang.Exception -> L80 java.lang.Throwable -> L84
            java.lang.String r2 = "querySupport, result : "
            r0.append(r2)     // Catch: java.lang.Exception -> L80 java.lang.Throwable -> L84
            r0.append(r9)     // Catch: java.lang.Exception -> L80 java.lang.Throwable -> L84
            java.lang.String r0 = r0.toString()     // Catch: java.lang.Exception -> L80 java.lang.Throwable -> L84
            b(r0)     // Catch: java.lang.Exception -> L80 java.lang.Throwable -> L84
            java.lang.String r0 = "0"
            boolean r9 = r0.equals(r9)     // Catch: java.lang.Exception -> L80 java.lang.Throwable -> L84
            java.lang.Boolean r9 = java.lang.Boolean.valueOf(r9)     // Catch: java.lang.Exception -> L80 java.lang.Throwable -> L84
            r8.e = r9     // Catch: java.lang.Exception -> L80 java.lang.Throwable -> L84
            boolean r9 = r9.booleanValue()     // Catch: java.lang.Exception -> L80 java.lang.Throwable -> L84
            if (r10 == 0) goto L7f
            r10.close()
        L7f:
            return r9
        L80:
            if (r10 == 0) goto L90
            goto L8d
        L84:
            r9 = move-exception
            if (r10 == 0) goto L8a
            r10.close()
        L8a:
            throw r9
        L8b:
            if (r10 == 0) goto L90
        L8d:
            r10.close()
        L90:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.qmsp.oaid2.x.a(android.content.Context, boolean):boolean");
    }

    public final void a(Context context) {
        synchronized (this) {
            if (this.f == null) {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("com.meizu.flyme.openid.ACTION_OPEN_ID_CHANGE");
                v vVar = new v();
                this.f = vVar;
                context.registerReceiver(vVar, intentFilter, "com.meizu.flyme.openid.permission.OPEN_ID_CHANGE", null);
            }
        }
    }

    public static final x a() {
        if (g == null) {
            synchronized (x.class) {
                g = new x();
            }
        }
        return g;
    }

    public w a(String str) {
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
}
