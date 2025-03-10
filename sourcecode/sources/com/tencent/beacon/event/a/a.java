package com.tencent.beacon.event.a;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.tencent.beacon.a.b.g;
import com.tencent.beacon.a.d.c;
import com.tencent.beacon.a.d.d;
import com.tencent.beacon.event.EventBean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: EventDAO.java */
/* loaded from: classes.dex */
public class a implements d<EventBean> {
    private static volatile a a;
    private final SQLiteStatement b;
    private final SQLiteStatement c;
    private final Object d = new Object();
    private final Object e = new Object();
    private com.tencent.beacon.event.c.b f = com.tencent.beacon.event.c.b.a();
    private SQLiteDatabase g;
    private SQLiteDatabase h;
    private long i;
    private long j;

    private a() {
        c cVar = new c(com.tencent.beacon.a.c.b.c(com.tencent.beacon.a.c.c.d().c()));
        this.g = cVar.getWritableDatabase();
        SQLiteDatabase readableDatabase = cVar.getReadableDatabase();
        this.h = readableDatabase;
        this.b = readableDatabase.compileStatement("INSERT INTO t_r_e (_appKey,_time,_length,_data )VALUES(?,?,?,?)");
        this.c = this.h.compileStatement("INSERT INTO t_n_e (_appKey,_time,_length,_data )VALUES(?,?,?,?)");
        this.i = a("t_r_e");
        long a2 = a("t_n_e");
        this.j = a2;
        if (this.i == 0 && a2 == 0) {
            return;
        }
        String str = " realtime: " + this.i + ", normal: " + this.j;
        com.tencent.beacon.base.util.c.a("[EventDAO]", str, new Object[0]);
        g.e().a("607", "[EventDAO]" + str);
    }

    public static a a() {
        if (a == null) {
            synchronized (a.class) {
                if (a == null) {
                    a = new a();
                }
            }
        }
        return a;
    }

    public Map<String, Integer> b(String str) {
        HashMap hashMap = new HashMap();
        Cursor cursor = null;
        try {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append("SELECT _appKey,count(_appKey) FROM ");
                sb.append(str);
                sb.append(" GROUP BY ");
                sb.append("_appKey");
                cursor = this.h.rawQuery(sb.toString(), null);
                if (cursor.moveToFirst()) {
                    do {
                        hashMap.put(cursor.getString(0), Integer.valueOf(cursor.getInt(1)));
                    } while (cursor.moveToNext());
                }
                if (!cursor.isClosed()) {
                    cursor.close();
                }
            } catch (Exception e) {
                g e2 = g.e();
                StringBuilder sb2 = new StringBuilder();
                sb2.append("type: ");
                sb2.append(str);
                sb2.append(" query err: ");
                sb2.append(e.getMessage());
                e2.a("605", sb2.toString(), e);
                com.tencent.beacon.base.util.c.a(e);
                if (cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }
            }
            return hashMap;
        } catch (Throwable th) {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            throw th;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x007e, code lost:
    
        if (r12.c.executeInsert() >= 0) goto L18;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean a(com.tencent.beacon.event.EventBean r13) {
        /*
            Method dump skipped, instructions count: 354
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.beacon.event.a.a.a(com.tencent.beacon.event.EventBean):boolean");
    }

    public List<EventBean> a(String str, String str2, int i) {
        long currentTimeMillis = System.currentTimeMillis();
        List<EventBean> list = null;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT * FROM ");
            sb.append(str);
            sb.append(" WHERE ");
            sb.append("_id");
            sb.append(" NOT IN (");
            sb.append(str2);
            sb.append(") ORDER BY ");
            sb.append("_time");
            sb.append(" DESC LIMIT ");
            sb.append(i);
            list = a(this.h.rawQuery(sb.toString(), null));
        } catch (Exception e) {
            com.tencent.beacon.base.util.c.a(e);
            g.e().a("605", "type: " + str + " query err: " + e.getMessage(), e);
        }
        com.tencent.beacon.base.util.c.a("[EventDAO]", "query tableName: %s, args: %s", str, str2);
        com.tencent.beacon.base.util.c.a("[EventDAO]", "query cost time: %s", Long.valueOf(System.currentTimeMillis() - currentTimeMillis));
        return list;
    }

    public long a(String str) {
        long j;
        Cursor cursor = null;
        try {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append("SELECT count(?) FROM ");
                sb.append(str);
                String sb2 = sb.toString();
                cursor = this.h.rawQuery(sb2, new String[]{"_id"});
                cursor.moveToFirst();
                j = cursor.getLong(0);
                if (!cursor.isClosed()) {
                    cursor.close();
                }
            } catch (Exception e) {
                g e2 = g.e();
                StringBuilder sb3 = new StringBuilder();
                sb3.append("type: ");
                sb3.append(str);
                sb3.append(" query err: ");
                sb3.append(e.getMessage());
                e2.a("605", sb3.toString(), e);
                com.tencent.beacon.base.util.c.a(e);
                if (cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }
                j = -1;
            }
            return j;
        } catch (Throwable th) {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            throw th;
        }
    }

    public boolean a(String str, String str2) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("_id IN (");
            sb.append(str2);
            sb.append(")");
            int delete = this.g.delete(str, sb.toString(), null);
            a(str.equals("t_r_e"), false, delete);
            return delete >= 0;
        } catch (Exception e) {
            g.e().a("606", "type: " + str + " delete err: " + e.getMessage() + " target: " + str2, e);
            com.tencent.beacon.base.util.c.a(e);
            return false;
        }
    }

    private List<EventBean> a(Cursor cursor) {
        if (cursor == null || cursor.isClosed()) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        while (cursor.moveToNext()) {
            b bVar = new b();
            bVar.a = cursor.getLong(0);
            bVar.d = cursor.getString(1);
            bVar.b = cursor.getInt(2);
            bVar.c = cursor.getLong(3);
            bVar.e = cursor.getBlob(4);
            arrayList.add(this.f.c().a(bVar));
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        return arrayList;
    }

    private void a(boolean z, boolean z2, long j) {
        if (z) {
            synchronized (this.d) {
                if (z2) {
                    this.i += j;
                } else {
                    this.i -= j;
                }
                com.tencent.beacon.base.util.c.a("[EventDAO]", "current db realtime:%s", Long.valueOf(this.i));
            }
            return;
        }
        synchronized (this.e) {
            if (z2) {
                this.j += j;
            } else {
                this.j -= j;
            }
            com.tencent.beacon.base.util.c.a("[EventDAO]", "current db normal:%s", Long.valueOf(this.j));
        }
    }

    public boolean a(int i) {
        boolean z;
        if (i == 1) {
            synchronized (this.d) {
                z = this.i >= ((long) com.tencent.beacon.e.b.a().b());
            }
            return z;
        }
        synchronized (this.e) {
            z = this.j >= ((long) com.tencent.beacon.e.b.a().b());
        }
        return z;
    }
}
