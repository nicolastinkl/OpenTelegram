package com.tencent.qimei.i;

import android.content.Context;
import android.content.SharedPreferences;
import j$.util.concurrent.ConcurrentHashMap;
import java.util.Map;

/* compiled from: QimeiSp.java */
/* loaded from: classes.dex */
public class f {
    public static final Map<String, f> a = new ConcurrentHashMap();
    public SharedPreferences b;
    public final String c;
    public Context d;

    public f(String str) {
        this.c = str;
    }

    public static synchronized f a(String str) {
        f fVar;
        synchronized (f.class) {
            Map<String, f> map = a;
            fVar = map.get(str);
            if (fVar == null) {
                fVar = new f(str);
                map.put(str, fVar);
            }
        }
        return fVar;
    }

    public long b(String str) {
        SharedPreferences sharedPreferences = this.b;
        if (sharedPreferences == null) {
            return 0L;
        }
        return sharedPreferences.getLong(str, 0L);
    }

    public String c(String str) {
        String string;
        SharedPreferences sharedPreferences = this.b;
        return (sharedPreferences == null || (string = sharedPreferences.getString(str, "")) == null) ? "" : string;
    }

    public void a(String str, String str2) {
        SharedPreferences sharedPreferences = this.b;
        if (sharedPreferences == null) {
            return;
        }
        sharedPreferences.edit().putString(str, str2).apply();
    }

    public void a(String str, long j) {
        SharedPreferences sharedPreferences = this.b;
        if (sharedPreferences == null) {
            return;
        }
        sharedPreferences.edit().putLong(str, j).apply();
    }
}
