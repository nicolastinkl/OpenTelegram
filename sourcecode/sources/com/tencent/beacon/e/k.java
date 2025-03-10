package com.tencent.beacon.e;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import com.tencent.beacon.a.d.a;
import com.tencent.beacon.module.ModuleName;
import com.tencent.beacon.module.StrategyModule;
import java.util.Locale;

/* compiled from: StrategyUtils.java */
/* loaded from: classes.dex */
public final class k {
    private static String a;

    /* compiled from: StrategyUtils.java */
    public static class a extends SQLiteOpenHelper {
        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public a(android.content.Context r3, java.lang.String r4) {
            /*
                r2 = this;
                boolean r0 = android.text.TextUtils.isEmpty(r4)
                if (r0 == 0) goto L9
                java.lang.String r4 = "beacon_db"
                goto L1a
            L9:
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                r0.<init>()
                java.lang.String r1 = "beacon_db_"
                r0.append(r1)
                r0.append(r4)
                java.lang.String r4 = r0.toString()
            L1a:
                r0 = 0
                r1 = 30
                r2.<init>(r3, r4, r0, r1)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.tencent.beacon.e.k.a.<init>(android.content.Context, java.lang.String):void");
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            sQLiteDatabase.execSQL(String.format(Locale.US, "CREATE TABLE %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT, %s int unique , %s int , %s blob)", "t_strategy", "_id", "_key", "_ut", "_datas"));
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            com.tencent.beacon.base.util.c.a("[db] Upgrade a db  [%s] from v %d to v %d , deleted all tables!", "beacon_db", Integer.valueOf(i), Integer.valueOf(i2));
        }
    }

    public static String a() {
        if (!TextUtils.isEmpty(a)) {
            return a;
        }
        com.tencent.beacon.a.d.a a2 = com.tencent.beacon.a.d.a.a();
        a = a2.getString("initsdkdate", "");
        if (!com.tencent.beacon.base.util.b.d().equals(a)) {
            a.SharedPreferencesEditorC0016a edit = a2.edit();
            if (com.tencent.beacon.base.util.b.a((SharedPreferences.Editor) edit)) {
                edit.putString("initsdkdate", com.tencent.beacon.base.util.b.d());
            }
        }
        return a;
    }

    public static boolean b() {
        com.tencent.beacon.a.d.a a2 = com.tencent.beacon.a.d.a.a();
        int i = com.tencent.beacon.base.util.b.d().equals(a()) ? a2.getInt("QUERY_TIMES_KEY", 0) : 0;
        if (i > com.tencent.beacon.e.a.a().c()) {
            com.tencent.beacon.base.util.c.d("[strategy] sdk init max times", new Object[0]);
            return true;
        }
        int i2 = i + 1;
        a.SharedPreferencesEditorC0016a edit = a2.edit();
        if (!com.tencent.beacon.base.util.b.a((SharedPreferences.Editor) edit)) {
            return false;
        }
        edit.putInt("QUERY_TIMES_KEY", i2);
        return false;
    }

    public static boolean c() {
        b b = ((StrategyModule) com.tencent.beacon.a.c.c.d().a(ModuleName.STRATEGY)).b();
        if (b.n()) {
            com.tencent.beacon.a.d.a a2 = com.tencent.beacon.a.d.a.a();
            long currentTimeMillis = System.currentTimeMillis();
            long j = ((currentTimeMillis / 60000) + 480) % 1440;
            if (j >= 0 && j <= 30 && currentTimeMillis - a2.getLong("last_success_strategy_query_time", 0L) <= 90000000) {
                return true;
            }
            if (com.tencent.beacon.base.util.b.d().equals(a())) {
                return a2.getInt("today_success_strategy_query_times", 0) >= b.e();
            }
            a.SharedPreferencesEditorC0016a edit = a2.edit();
            if (com.tencent.beacon.base.util.b.a((SharedPreferences.Editor) edit)) {
                edit.putInt("today_success_strategy_query_times", 0);
            }
        }
        return false;
    }

    public static void d() {
        b b = ((StrategyModule) com.tencent.beacon.a.c.c.d().a(ModuleName.STRATEGY)).b();
        if (b == null || !b.n()) {
            return;
        }
        com.tencent.beacon.a.d.a a2 = com.tencent.beacon.a.d.a.a();
        int i = a2.getInt("today_success_strategy_query_times", 0) + 1;
        a.SharedPreferencesEditorC0016a edit = a2.edit();
        if (com.tencent.beacon.base.util.b.a((SharedPreferences.Editor) edit)) {
            edit.putInt("today_success_strategy_query_times", i).putLong("last_success_strategy_query_time", System.currentTimeMillis());
        }
    }

    public static synchronized j a(Context context, int i) {
        SQLiteDatabase sQLiteDatabase;
        j jVar;
        synchronized (k.class) {
            Cursor cursor = null;
            r3 = null;
            j jVar2 = null;
            r3 = null;
            Cursor cursor2 = null;
            cursor = null;
            if (context == null) {
                com.tencent.beacon.base.util.c.e("[db] context is null", new Object[0]);
                return null;
            }
            try {
                try {
                    sQLiteDatabase = new a(context, com.tencent.beacon.a.c.c.d().f()).getWritableDatabase();
                    try {
                    } catch (Exception e) {
                        e = e;
                        jVar = null;
                    }
                } catch (Exception e2) {
                    e = e2;
                    jVar = null;
                    sQLiteDatabase = null;
                } catch (Throwable th) {
                    th = th;
                    sQLiteDatabase = null;
                }
                if (sQLiteDatabase == null) {
                    com.tencent.beacon.base.util.c.e("[db] getWritableDatabase fail!", new Object[0]);
                    if (sQLiteDatabase != null && sQLiteDatabase.isOpen()) {
                        sQLiteDatabase.close();
                    }
                    return null;
                }
                Locale locale = Locale.US;
                Object[] objArr = new Object[2];
                objArr[0] = "_key";
                objArr[1] = Integer.valueOf(i);
                Cursor query = sQLiteDatabase.query("t_strategy", null, String.format(locale, " %s = %d ", objArr), null, null, null, null);
                if (query != null) {
                    try {
                        if (query.moveToNext() && (jVar2 = a(query)) != null) {
                            com.tencent.beacon.base.util.c.a("[db] read strategy key: %d", Integer.valueOf(jVar2.b));
                        }
                    } catch (Exception e3) {
                        e = e3;
                        jVar = null;
                        cursor2 = query;
                        com.tencent.beacon.a.b.g.e().a("605", "[db] TB: t_strategy query fail!");
                        com.tencent.beacon.base.util.c.a(e);
                        if (cursor2 != null && !cursor2.isClosed()) {
                            cursor2.close();
                        }
                        if (sQLiteDatabase != null && sQLiteDatabase.isOpen()) {
                            sQLiteDatabase.close();
                        }
                        jVar2 = jVar;
                        return jVar2;
                    } catch (Throwable th2) {
                        th = th2;
                        cursor = query;
                        if (cursor != null && !cursor.isClosed()) {
                            cursor.close();
                        }
                        if (sQLiteDatabase != null && sQLiteDatabase.isOpen()) {
                            sQLiteDatabase.close();
                        }
                        throw th;
                    }
                }
                if (query != null && !query.isClosed()) {
                    query.close();
                }
                if (sQLiteDatabase.isOpen()) {
                    sQLiteDatabase.close();
                }
                return jVar2;
            } catch (Throwable th3) {
                th = th3;
            }
        }
    }

    private static j a(Cursor cursor) {
        if (cursor == null || cursor.isBeforeFirst() || cursor.isAfterLast()) {
            return null;
        }
        com.tencent.beacon.base.util.c.a("[db] parse bean.", new Object[0]);
        j jVar = new j();
        jVar.a = cursor.getLong(cursor.getColumnIndex("_id"));
        jVar.b = cursor.getInt(cursor.getColumnIndex("_key"));
        jVar.c = cursor.getBlob(cursor.getColumnIndex("_datas"));
        return jVar;
    }
}
