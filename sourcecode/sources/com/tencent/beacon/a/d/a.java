package com.tencent.beacon.a.d;

import android.content.Context;
import android.content.SharedPreferences;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/* compiled from: BeaconProperties.java */
/* loaded from: classes.dex */
public class a implements SharedPreferences {
    private static volatile a a;
    private boolean b;
    private g c;
    private SharedPreferencesEditorC0016a d;
    private SharedPreferences e;

    /* compiled from: BeaconProperties.java */
    /* renamed from: com.tencent.beacon.a.d.a$a, reason: collision with other inner class name */
    public static class SharedPreferencesEditorC0016a implements SharedPreferences.Editor {
        private g a;

        SharedPreferencesEditorC0016a(g gVar) {
            this.a = gVar;
        }

        @Override // android.content.SharedPreferences.Editor
        public void apply() {
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor clear() {
            this.a.a();
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public boolean commit() {
            return true;
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor putBoolean(String str, boolean z) {
            this.a.b(str, Boolean.valueOf(z));
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor putFloat(String str, float f) {
            this.a.b(str, Float.valueOf(f));
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor putInt(String str, int i) {
            this.a.b(str, Integer.valueOf(i));
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor putLong(String str, long j) {
            this.a.b(str, Long.valueOf(j));
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor putString(String str, String str2) {
            this.a.b(str, str2);
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor putStringSet(String str, Set<String> set) {
            this.a.b(str, (Set) set);
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor remove(String str) {
            this.a.b(str);
            return this;
        }
    }

    private a() {
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

    @Override // android.content.SharedPreferences
    public boolean contains(String str) {
        return this.c.a(str);
    }

    @Override // android.content.SharedPreferences
    public Map<String, ?> getAll() {
        return this.c.b();
    }

    @Override // android.content.SharedPreferences
    public boolean getBoolean(String str, boolean z) {
        Object a2 = a(str, Boolean.valueOf(z));
        return a2 instanceof Boolean ? ((Boolean) a2).booleanValue() : z;
    }

    @Override // android.content.SharedPreferences
    public float getFloat(String str, float f) {
        Object a2 = a(str, Float.valueOf(f));
        return a2 instanceof Number ? ((Number) a2).floatValue() : f;
    }

    @Override // android.content.SharedPreferences
    public int getInt(String str, int i) {
        Object a2 = a(str, Integer.valueOf(i));
        return a2 instanceof Number ? ((Number) a2).intValue() : i;
    }

    @Override // android.content.SharedPreferences
    public long getLong(String str, long j) {
        Object a2 = a(str, Long.valueOf(j));
        return a2 instanceof Number ? ((Number) a2).longValue() : j;
    }

    @Override // android.content.SharedPreferences
    public String getString(String str, String str2) {
        Object a2 = a(str, str2);
        return a2 instanceof String ? (String) a2 : str2;
    }

    @Override // android.content.SharedPreferences
    public Set<String> getStringSet(String str, Set<String> set) {
        return !this.b ? set : this.c.a(str, (Set) set);
    }

    @Override // android.content.SharedPreferences
    public void registerOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
    }

    @Override // android.content.SharedPreferences
    public void unregisterOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
    }

    @Override // android.content.SharedPreferences
    public SharedPreferencesEditorC0016a edit() {
        if (!this.b) {
            com.tencent.beacon.base.util.e.a("BeaconProperties has not init!");
            a(com.tencent.beacon.a.c.c.d().c());
        }
        return this.d;
    }

    public synchronized void a(Context context) {
        if (this.b || context == null) {
            return;
        }
        try {
            String replace = com.tencent.beacon.a.c.b.c(context).replace(context.getPackageName(), "");
            StringBuilder sb = new StringBuilder();
            sb.append("prop_");
            sb.append(replace);
            g a2 = g.a(context, sb.toString());
            this.c = a2;
            this.d = new SharedPreferencesEditorC0016a(a2);
            this.b = true;
        } catch (IOException e) {
            com.tencent.beacon.base.util.c.a(e);
            com.tencent.beacon.a.b.g.e().a("504", "[properties] PropertiesFile create error!", e);
            this.b = false;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private <T> Object a(String str, T t) {
        if (!this.b) {
            return t;
        }
        Object a2 = this.c.a(str, (String) t);
        if (a2 == null || a2 == t) {
            Context c = com.tencent.beacon.a.c.c.d().c();
            if (this.e == null) {
                this.e = c.getSharedPreferences("DENGTA_META", 0);
            }
            if (t instanceof Boolean) {
                a2 = Boolean.valueOf(this.e.getBoolean(str, ((Boolean) t).booleanValue()));
            } else if (t instanceof String) {
                a2 = this.e.getString(str, (String) t);
            } else if (t instanceof Integer) {
                a2 = Integer.valueOf(this.e.getInt(str, ((Integer) t).intValue()));
            } else if (t instanceof Long) {
                a2 = Long.valueOf(this.e.getLong(str, ((Long) t).longValue()));
            } else if (t instanceof Float) {
                a2 = Float.valueOf(this.e.getFloat(str, ((Float) t).floatValue()));
            }
            if (a2 != null && a2 != t) {
                this.c.b(str, a2);
            }
        }
        return a2 == null ? t : a2;
    }
}
