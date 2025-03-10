package com.shubao.xinstall.a.a;

import android.content.Context;
import android.content.SharedPreferences;

/* loaded from: classes.dex */
public final class g {
    public SharedPreferences a;
    private Context b;

    public g(Context context) {
        this.b = context;
        this.a = context.getSharedPreferences("x_config", 0);
    }

    public final c a(String str) {
        try {
            return c.a(this.a.getInt(str, c.b.f));
        } catch (Exception unused) {
            return c.b;
        }
    }

    public final String a() {
        try {
            return this.a.getString("init_data", "");
        } catch (Exception unused) {
            return "";
        }
    }

    public final void a(int i) {
        try {
            SharedPreferences.Editor edit = this.a.edit();
            edit.putInt("prohibit_count", i);
            edit.apply();
        } catch (Exception unused) {
        }
    }

    public final void a(long j) {
        try {
            SharedPreferences.Editor edit = this.a.edit();
            edit.putLong("prohibit_time", j);
            edit.apply();
        } catch (Exception unused) {
        }
    }

    public final void a(com.shubao.xinstall.a.b.a aVar) {
        try {
            SharedPreferences.Editor edit = this.a.edit();
            edit.putString("config_data", aVar.toString());
            edit.apply();
        } catch (Exception unused) {
        }
    }

    public final void a(String str, c cVar) {
        try {
            SharedPreferences.Editor edit = this.a.edit();
            edit.putInt(str, cVar.f);
            edit.apply();
        } catch (Exception unused) {
        }
    }

    public final void b(String str) {
        try {
            SharedPreferences.Editor edit = this.a.edit();
            edit.putString("init_msg", str);
            edit.apply();
        } catch (Exception unused) {
        }
    }

    public final boolean b() {
        try {
            return this.a.getBoolean("first_fetch", true);
        } catch (Exception unused) {
            return false;
        }
    }

    public final int c() {
        try {
            return this.a.getInt("prohibit_count", 0);
        } catch (Exception unused) {
            return 0;
        }
    }

    public final String d() {
        try {
            return this.a.getString("init_msg", "");
        } catch (Exception unused) {
            return "";
        }
    }

    public final void e() {
        try {
            SharedPreferences.Editor edit = this.a.edit();
            edit.putBoolean("prohibit_forever", true);
            edit.apply();
        } catch (Exception unused) {
        }
    }

    public final boolean f() {
        try {
            return this.a.getBoolean("prohibit_forever", false);
        } catch (Exception unused) {
            return false;
        }
    }

    public final long g() {
        try {
            return this.a.getLong("prohibit_time", 0L);
        } catch (Exception unused) {
            return 0L;
        }
    }

    public final com.shubao.xinstall.a.b.a h() {
        try {
            return com.shubao.xinstall.a.b.a.a(this.a.getString("config_data", ""));
        } catch (Exception unused) {
            return new com.shubao.xinstall.a.b.a();
        }
    }

    public final com.shubao.xinstall.a.b.e i() {
        try {
            return com.shubao.xinstall.a.b.e.a(this.a.getString("xk_data", ""));
        } catch (Exception unused) {
            return null;
        }
    }
}
