package com.tencent.bugly.proguard;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: BUGLY */
/* loaded from: classes.dex */
public final class w {
    public static boolean a = false;
    private static w b;
    private static x c;

    private w(Context context, List<o> list) {
        c = new x(context, list);
    }

    public static synchronized w a(Context context, List<o> list) {
        w wVar;
        synchronized (w.class) {
            if (b == null) {
                b = new w(context, list);
            }
            wVar = b;
        }
        return wVar;
    }

    public static synchronized w a() {
        w wVar;
        synchronized (w.class) {
            wVar = b;
        }
        return wVar;
    }

    public final Cursor a(String str, String[] strArr, String str2) {
        return a(str, strArr, str2, (String) null, (String) null);
    }

    public final Cursor a(String str, String[] strArr, String str2, String str3, String str4) {
        return a(false, str, strArr, str2, null, null, null, str3, str4, null);
    }

    public final int a(String str, String str2) {
        return a(str, str2, (String[]) null, (v) null);
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x004a, code lost:
    
        if (0 != 0) goto L15;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final synchronized long a(java.lang.String r10, android.content.ContentValues r11, com.tencent.bugly.proguard.v r12) {
        /*
            r9 = this;
            monitor-enter(r9)
            r0 = -1
            r2 = 0
            com.tencent.bugly.proguard.x r3 = com.tencent.bugly.proguard.w.c     // Catch: java.lang.Throwable -> L3c
            android.database.sqlite.SQLiteDatabase r2 = r3.getWritableDatabase()     // Catch: java.lang.Throwable -> L3c
            if (r2 == 0) goto L30
            if (r11 == 0) goto L30
            java.lang.String r3 = "_id"
            long r3 = r2.replace(r10, r3, r11)     // Catch: java.lang.Throwable -> L3c
            r5 = 0
            r11 = 0
            r7 = 1
            int r8 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r8 < 0) goto L26
            java.lang.String r5 = "[Database] insert %s success."
            java.lang.Object[] r6 = new java.lang.Object[r7]     // Catch: java.lang.Throwable -> L3c
            r6[r11] = r10     // Catch: java.lang.Throwable -> L3c
            com.tencent.bugly.proguard.al.c(r5, r6)     // Catch: java.lang.Throwable -> L3c
            goto L2f
        L26:
            java.lang.String r5 = "[Database] replace %s error."
            java.lang.Object[] r6 = new java.lang.Object[r7]     // Catch: java.lang.Throwable -> L3c
            r6[r11] = r10     // Catch: java.lang.Throwable -> L3c
            com.tencent.bugly.proguard.al.d(r5, r6)     // Catch: java.lang.Throwable -> L3c
        L2f:
            r0 = r3
        L30:
            boolean r10 = com.tencent.bugly.proguard.w.a     // Catch: java.lang.Throwable -> L3a
            if (r10 == 0) goto L4d
            if (r2 == 0) goto L4d
        L36:
            r2.close()     // Catch: java.lang.Throwable -> L3a
            goto L4d
        L3a:
            r10 = move-exception
            goto L5a
        L3c:
            r10 = move-exception
            boolean r11 = com.tencent.bugly.proguard.al.a(r10)     // Catch: java.lang.Throwable -> L4f
            if (r11 != 0) goto L46
            r10.printStackTrace()     // Catch: java.lang.Throwable -> L4f
        L46:
            boolean r10 = com.tencent.bugly.proguard.w.a     // Catch: java.lang.Throwable -> L3a
            if (r10 == 0) goto L4d
            if (r2 == 0) goto L4d
            goto L36
        L4d:
            monitor-exit(r9)
            return r0
        L4f:
            r10 = move-exception
            boolean r11 = com.tencent.bugly.proguard.w.a     // Catch: java.lang.Throwable -> L3a
            if (r11 == 0) goto L59
            if (r2 == 0) goto L59
            r2.close()     // Catch: java.lang.Throwable -> L3a
        L59:
            throw r10     // Catch: java.lang.Throwable -> L3a
        L5a:
            monitor-exit(r9)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.w.a(java.lang.String, android.content.ContentValues, com.tencent.bugly.proguard.v):long");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized Cursor a(boolean z, String str, String[] strArr, String str2, String[] strArr2, String str3, String str4, String str5, String str6, v vVar) {
        Cursor cursor;
        cursor = null;
        try {
            SQLiteDatabase writableDatabase = c.getWritableDatabase();
            if (writableDatabase != null) {
                cursor = writableDatabase.query(z, str, strArr, str2, strArr2, str3, str4, str5, str6);
            }
        } finally {
            try {
                return cursor;
            } finally {
            }
        }
        return cursor;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0029, code lost:
    
        if (r1 != null) goto L10;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized int a(java.lang.String r4, java.lang.String r5, java.lang.String[] r6, com.tencent.bugly.proguard.v r7) {
        /*
            r3 = this;
            monitor-enter(r3)
            r0 = 0
            r1 = 0
            com.tencent.bugly.proguard.x r2 = com.tencent.bugly.proguard.w.c     // Catch: java.lang.Throwable -> L1b
            android.database.sqlite.SQLiteDatabase r1 = r2.getWritableDatabase()     // Catch: java.lang.Throwable -> L1b
            if (r1 == 0) goto Lf
            int r0 = r1.delete(r4, r5, r6)     // Catch: java.lang.Throwable -> L1b
        Lf:
            boolean r4 = com.tencent.bugly.proguard.w.a     // Catch: java.lang.Throwable -> L19
            if (r4 == 0) goto L2c
            if (r1 == 0) goto L2c
        L15:
            r1.close()     // Catch: java.lang.Throwable -> L19
            goto L2c
        L19:
            r4 = move-exception
            goto L39
        L1b:
            r4 = move-exception
            boolean r5 = com.tencent.bugly.proguard.al.a(r4)     // Catch: java.lang.Throwable -> L2e
            if (r5 != 0) goto L25
            r4.printStackTrace()     // Catch: java.lang.Throwable -> L2e
        L25:
            boolean r4 = com.tencent.bugly.proguard.w.a     // Catch: java.lang.Throwable -> L19
            if (r4 == 0) goto L2c
            if (r1 == 0) goto L2c
            goto L15
        L2c:
            monitor-exit(r3)
            return r0
        L2e:
            r4 = move-exception
            boolean r5 = com.tencent.bugly.proguard.w.a     // Catch: java.lang.Throwable -> L19
            if (r5 == 0) goto L38
            if (r1 == 0) goto L38
            r1.close()     // Catch: java.lang.Throwable -> L19
        L38:
            throw r4     // Catch: java.lang.Throwable -> L19
        L39:
            monitor-exit(r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.w.a(java.lang.String, java.lang.String, java.lang.String[], com.tencent.bugly.proguard.v):int");
    }

    public final boolean a(int i, String str, byte[] bArr, boolean z) {
        if (!z) {
            a aVar = new a();
            aVar.a(i, str, bArr);
            ak.a().a(aVar);
            return true;
        }
        return a(i, str, bArr, (v) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(int i, String str, byte[] bArr, v vVar) {
        try {
            y yVar = new y();
            yVar.a = i;
            yVar.f = str;
            yVar.e = System.currentTimeMillis();
            yVar.g = bArr;
            return b(yVar);
        } catch (Throwable th) {
            if (!al.a(th)) {
                th.printStackTrace();
            }
            return false;
        }
    }

    public final Map<String, byte[]> a(int i, v vVar) {
        HashMap hashMap = null;
        try {
            List<y> c2 = c(i);
            if (c2 == null) {
                return null;
            }
            HashMap hashMap2 = new HashMap();
            try {
                for (y yVar : c2) {
                    byte[] bArr = yVar.g;
                    if (bArr != null) {
                        hashMap2.put(yVar.f, bArr);
                    }
                }
                return hashMap2;
            } catch (Throwable th) {
                th = th;
                hashMap = hashMap2;
                if (al.a(th)) {
                    return hashMap;
                }
                th.printStackTrace();
                return hashMap;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public final synchronized boolean a(y yVar) {
        ContentValues c2;
        SQLiteDatabase sQLiteDatabase = null;
        try {
            sQLiteDatabase = c.getWritableDatabase();
            if (sQLiteDatabase == null || (c2 = c(yVar)) == null) {
                return false;
            }
            long replace = sQLiteDatabase.replace("t_lr", "_id", c2);
            if (replace < 0) {
                if (a) {
                    sQLiteDatabase.close();
                }
                return false;
            }
            al.c("[Database] insert %s success.", "t_lr");
            yVar.a = replace;
            if (a) {
                sQLiteDatabase.close();
            }
            return true;
        } catch (Throwable th) {
            try {
                if (!al.a(th)) {
                    th.printStackTrace();
                }
                if (a && sQLiteDatabase != null) {
                    sQLiteDatabase.close();
                }
                return false;
            } finally {
                if (a && sQLiteDatabase != null) {
                    sQLiteDatabase.close();
                }
            }
        }
    }

    private synchronized boolean b(y yVar) {
        ContentValues d;
        SQLiteDatabase sQLiteDatabase = null;
        try {
            sQLiteDatabase = c.getWritableDatabase();
            if (sQLiteDatabase == null || (d = d(yVar)) == null) {
                return false;
            }
            long replace = sQLiteDatabase.replace("t_pf", "_id", d);
            if (replace < 0) {
                if (a) {
                    sQLiteDatabase.close();
                }
                return false;
            }
            al.c("[Database] insert %s success.", "t_pf");
            yVar.a = replace;
            if (a) {
                sQLiteDatabase.close();
            }
            return true;
        } catch (Throwable th) {
            try {
                if (!al.a(th)) {
                    th.printStackTrace();
                }
                if (a && sQLiteDatabase != null) {
                    sQLiteDatabase.close();
                }
                return false;
            } finally {
                if (a && sQLiteDatabase != null) {
                    sQLiteDatabase.close();
                }
            }
        }
    }

    /* JADX WARN: Finally extract failed */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00ad A[Catch: all -> 0x00bd, TRY_LEAVE, TryCatch #3 {all -> 0x00bd, blocks: (B:47:0x00a7, B:49:0x00ad), top: B:46:0x00a7, outer: #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00b2 A[Catch: all -> 0x00cd, TRY_ENTER, TryCatch #4 {, blocks: (B:3:0x0001, B:11:0x002d, B:12:0x0030, B:14:0x0034, B:39:0x0097, B:41:0x009e, B:52:0x00b2, B:53:0x00b5, B:55:0x00b9, B:58:0x00c0, B:59:0x00c3, B:61:0x00c7, B:62:0x00ca, B:47:0x00a7, B:49:0x00ad), top: B:2:0x0001, inners: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00b9 A[Catch: all -> 0x00cd, TryCatch #4 {, blocks: (B:3:0x0001, B:11:0x002d, B:12:0x0030, B:14:0x0034, B:39:0x0097, B:41:0x009e, B:52:0x00b2, B:53:0x00b5, B:55:0x00b9, B:58:0x00c0, B:59:0x00c3, B:61:0x00c7, B:62:0x00ca, B:47:0x00a7, B:49:0x00ad), top: B:2:0x0001, inners: #3 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final synchronized java.util.List<com.tencent.bugly.proguard.y> a(int r12) {
        /*
            r11 = this;
            monitor-enter(r11)
            com.tencent.bugly.proguard.x r0 = com.tencent.bugly.proguard.w.c     // Catch: java.lang.Throwable -> Lcd
            android.database.sqlite.SQLiteDatabase r0 = r0.getWritableDatabase()     // Catch: java.lang.Throwable -> Lcd
            r9 = 0
            if (r0 == 0) goto Lcb
            if (r12 < 0) goto L1c
            java.lang.String r1 = "_tp = "
            java.lang.String r12 = java.lang.String.valueOf(r12)     // Catch: java.lang.Throwable -> L18
            java.lang.String r12 = r1.concat(r12)     // Catch: java.lang.Throwable -> L18
            r4 = r12
            goto L1d
        L18:
            r12 = move-exception
            r1 = r9
            goto La7
        L1c:
            r4 = r9
        L1d:
            java.lang.String r2 = "t_lr"
            r3 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            r1 = r0
            android.database.Cursor r12 = r1.query(r2, r3, r4, r5, r6, r7, r8)     // Catch: java.lang.Throwable -> L18
            if (r12 != 0) goto L39
            if (r12 == 0) goto L30
            r12.close()     // Catch: java.lang.Throwable -> Lcd
        L30:
            boolean r12 = com.tencent.bugly.proguard.w.a     // Catch: java.lang.Throwable -> Lcd
            if (r12 == 0) goto L37
            r0.close()     // Catch: java.lang.Throwable -> Lcd
        L37:
            monitor-exit(r11)
            return r9
        L39:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> La3
            r1.<init>()     // Catch: java.lang.Throwable -> La3
            java.util.ArrayList r2 = new java.util.ArrayList     // Catch: java.lang.Throwable -> La3
            r2.<init>()     // Catch: java.lang.Throwable -> La3
        L43:
            boolean r3 = r12.moveToNext()     // Catch: java.lang.Throwable -> La3
            r4 = 0
            if (r3 == 0) goto L6f
            com.tencent.bugly.proguard.y r3 = a(r12)     // Catch: java.lang.Throwable -> La3
            if (r3 == 0) goto L54
            r2.add(r3)     // Catch: java.lang.Throwable -> La3
            goto L43
        L54:
            java.lang.String r3 = "_id"
            int r3 = r12.getColumnIndex(r3)     // Catch: java.lang.Throwable -> L67
            long r5 = r12.getLong(r3)     // Catch: java.lang.Throwable -> L67
            java.lang.String r3 = " or _id = "
            r1.append(r3)     // Catch: java.lang.Throwable -> L67
            r1.append(r5)     // Catch: java.lang.Throwable -> L67
            goto L43
        L67:
            java.lang.String r3 = "[Database] unknown id."
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch: java.lang.Throwable -> La3
            com.tencent.bugly.proguard.al.d(r3, r4)     // Catch: java.lang.Throwable -> La3
            goto L43
        L6f:
            java.lang.String r1 = r1.toString()     // Catch: java.lang.Throwable -> La3
            int r3 = r1.length()     // Catch: java.lang.Throwable -> La3
            if (r3 <= 0) goto L97
            r3 = 4
            java.lang.String r1 = r1.substring(r3)     // Catch: java.lang.Throwable -> La3
            java.lang.String r3 = "t_lr"
            int r1 = r0.delete(r3, r1, r9)     // Catch: java.lang.Throwable -> La3
            java.lang.String r3 = "[Database] deleted %s illegal data %d"
            r5 = 2
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch: java.lang.Throwable -> La3
            java.lang.String r6 = "t_lr"
            r5[r4] = r6     // Catch: java.lang.Throwable -> La3
            r4 = 1
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch: java.lang.Throwable -> La3
            r5[r4] = r1     // Catch: java.lang.Throwable -> La3
            com.tencent.bugly.proguard.al.d(r3, r5)     // Catch: java.lang.Throwable -> La3
        L97:
            r12.close()     // Catch: java.lang.Throwable -> Lcd
            boolean r12 = com.tencent.bugly.proguard.w.a     // Catch: java.lang.Throwable -> Lcd
            if (r12 == 0) goto La1
            r0.close()     // Catch: java.lang.Throwable -> Lcd
        La1:
            monitor-exit(r11)
            return r2
        La3:
            r1 = move-exception
            r10 = r1
            r1 = r12
            r12 = r10
        La7:
            boolean r2 = com.tencent.bugly.proguard.al.a(r12)     // Catch: java.lang.Throwable -> Lbd
            if (r2 != 0) goto Lb0
            r12.printStackTrace()     // Catch: java.lang.Throwable -> Lbd
        Lb0:
            if (r1 == 0) goto Lb5
            r1.close()     // Catch: java.lang.Throwable -> Lcd
        Lb5:
            boolean r12 = com.tencent.bugly.proguard.w.a     // Catch: java.lang.Throwable -> Lcd
            if (r12 == 0) goto Lcb
            r0.close()     // Catch: java.lang.Throwable -> Lcd
            goto Lcb
        Lbd:
            r12 = move-exception
            if (r1 == 0) goto Lc3
            r1.close()     // Catch: java.lang.Throwable -> Lcd
        Lc3:
            boolean r1 = com.tencent.bugly.proguard.w.a     // Catch: java.lang.Throwable -> Lcd
            if (r1 == 0) goto Lca
            r0.close()     // Catch: java.lang.Throwable -> Lcd
        Lca:
            throw r12     // Catch: java.lang.Throwable -> Lcd
        Lcb:
            monitor-exit(r11)
            return r9
        Lcd:
            r12 = move-exception
            monitor-exit(r11)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.w.a(int):java.util.List");
    }

    public final synchronized void a(List<y> list) {
        if (list != null) {
            if (list.size() != 0) {
                SQLiteDatabase writableDatabase = c.getWritableDatabase();
                if (writableDatabase != null) {
                    StringBuilder sb = new StringBuilder();
                    for (y yVar : list) {
                        sb.append(" or _id = ");
                        sb.append(yVar.a);
                    }
                    String sb2 = sb.toString();
                    if (sb2.length() > 0) {
                        sb2 = sb2.substring(4);
                    }
                    sb.setLength(0);
                    try {
                        al.c("[Database] deleted %s data %d", "t_lr", Integer.valueOf(writableDatabase.delete("t_lr", sb2, null)));
                    } catch (Throwable th) {
                        try {
                            if (!al.a(th)) {
                                th.printStackTrace();
                            }
                            if (a) {
                                writableDatabase.close();
                            }
                        } finally {
                            if (a) {
                                writableDatabase.close();
                            }
                        }
                    }
                }
            }
        }
    }

    public final synchronized void b(int i) {
        String concat;
        SQLiteDatabase writableDatabase = c.getWritableDatabase();
        if (writableDatabase != null) {
            if (i >= 0) {
                try {
                    concat = "_tp = ".concat(String.valueOf(i));
                } catch (Throwable th) {
                    try {
                        if (!al.a(th)) {
                            th.printStackTrace();
                        }
                        if (a) {
                            writableDatabase.close();
                            return;
                        }
                    } finally {
                        if (a) {
                            writableDatabase.close();
                        }
                    }
                }
            } else {
                concat = null;
            }
            al.c("[Database] deleted %s data %d", "t_lr", Integer.valueOf(writableDatabase.delete("t_lr", concat, null)));
        }
    }

    private static ContentValues c(y yVar) {
        if (yVar == null) {
            return null;
        }
        try {
            ContentValues contentValues = new ContentValues();
            long j = yVar.a;
            if (j > 0) {
                contentValues.put("_id", Long.valueOf(j));
            }
            contentValues.put("_tp", Integer.valueOf(yVar.b));
            contentValues.put("_pc", yVar.c);
            contentValues.put("_th", yVar.d);
            contentValues.put("_tm", Long.valueOf(yVar.e));
            byte[] bArr = yVar.g;
            if (bArr != null) {
                contentValues.put("_dt", bArr);
            }
            return contentValues;
        } catch (Throwable th) {
            if (!al.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    private static y a(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        try {
            y yVar = new y();
            yVar.a = cursor.getLong(cursor.getColumnIndex("_id"));
            yVar.b = cursor.getInt(cursor.getColumnIndex("_tp"));
            yVar.c = cursor.getString(cursor.getColumnIndex("_pc"));
            yVar.d = cursor.getString(cursor.getColumnIndex("_th"));
            yVar.e = cursor.getLong(cursor.getColumnIndex("_tm"));
            yVar.g = cursor.getBlob(cursor.getColumnIndex("_dt"));
            return yVar;
        } catch (Throwable th) {
            if (!al.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x00aa, code lost:
    
        r1.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x00c5, code lost:
    
        if (r1 != null) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x00a8, code lost:
    
        if (r1 != null) goto L40;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private synchronized java.util.List<com.tencent.bugly.proguard.y> c(int r12) {
        /*
            Method dump skipped, instructions count: 220
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.w.c(int):java.util.List");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized boolean a(int i, String str, v vVar) {
        boolean z;
        String str2;
        SQLiteDatabase sQLiteDatabase = null;
        z = false;
        try {
            SQLiteDatabase writableDatabase = c.getWritableDatabase();
            if (writableDatabase != null) {
                try {
                    if (ap.b(str)) {
                        str2 = "_id = ".concat(String.valueOf(i));
                    } else {
                        str2 = "_id = " + i + " and _tp = \"" + str + "\"";
                    }
                    int delete = writableDatabase.delete("t_pf", str2, null);
                    al.c("[Database] deleted %s data %d", "t_pf", Integer.valueOf(delete));
                    if (delete > 0) {
                        z = true;
                    }
                } catch (Throwable th) {
                    th = th;
                    sQLiteDatabase = writableDatabase;
                    try {
                        if (!al.a(th)) {
                            th.printStackTrace();
                        }
                        return z;
                    } finally {
                        if (a && sQLiteDatabase != null) {
                            sQLiteDatabase.close();
                        }
                    }
                }
            }
            if (a && writableDatabase != null) {
                writableDatabase.close();
            }
        } catch (Throwable th2) {
            th = th2;
        }
        return z;
    }

    private static ContentValues d(y yVar) {
        if (yVar != null && !ap.b(yVar.f)) {
            try {
                ContentValues contentValues = new ContentValues();
                long j = yVar.a;
                if (j > 0) {
                    contentValues.put("_id", Long.valueOf(j));
                }
                contentValues.put("_tp", yVar.f);
                contentValues.put("_tm", Long.valueOf(yVar.e));
                byte[] bArr = yVar.g;
                if (bArr != null) {
                    contentValues.put("_dt", bArr);
                }
                return contentValues;
            } catch (Throwable th) {
                if (!al.a(th)) {
                    th.printStackTrace();
                }
            }
        }
        return null;
    }

    private static y b(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        try {
            y yVar = new y();
            yVar.a = cursor.getLong(cursor.getColumnIndex("_id"));
            yVar.e = cursor.getLong(cursor.getColumnIndex("_tm"));
            yVar.f = cursor.getString(cursor.getColumnIndex("_tp"));
            yVar.g = cursor.getBlob(cursor.getColumnIndex("_dt"));
            return yVar;
        } catch (Throwable th) {
            if (!al.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    /* compiled from: BUGLY */
    class a extends Thread {
        private int b = 4;
        private v c = null;
        private String d;
        private ContentValues e;
        private boolean f;
        private String[] g;
        private String h;
        private String[] i;
        private String j;
        private String k;
        private String l;
        private String m;
        private String n;
        private String[] o;
        private int p;

        /* renamed from: q, reason: collision with root package name */
        private String f17q;
        private byte[] r;

        public a() {
        }

        public final void a(int i, String str, byte[] bArr) {
            this.p = i;
            this.f17q = str;
            this.r = bArr;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            switch (this.b) {
                case 1:
                    w.this.a(this.d, this.e, this.c);
                    break;
                case 2:
                    w.this.a(this.d, this.n, this.o, this.c);
                    break;
                case 3:
                    Cursor a = w.this.a(this.f, this.d, this.g, this.h, this.i, this.j, this.k, this.l, this.m, this.c);
                    if (a != null) {
                        a.close();
                        break;
                    }
                    break;
                case 4:
                    w.this.a(this.p, this.f17q, this.r, this.c);
                    break;
                case 5:
                    w.this.a(this.p, this.c);
                    break;
                case 6:
                    w.this.a(this.p, this.f17q, this.c);
                    break;
            }
        }
    }
}
