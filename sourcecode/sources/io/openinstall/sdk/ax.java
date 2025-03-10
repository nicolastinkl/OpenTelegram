package io.openinstall.sdk;

import android.content.SharedPreferences;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/* loaded from: classes.dex */
public class ax {
    private Future<SharedPreferences> a;
    private final String b = "";

    public ax(Future<SharedPreferences> future) {
        this.a = future;
    }

    private void a(String str, int i) {
        try {
            SharedPreferences.Editor edit = this.a.get().edit();
            edit.putInt(str, i);
            edit.apply();
        } catch (InterruptedException | ExecutionException unused) {
        }
    }

    private void a(String str, long j) {
        try {
            SharedPreferences.Editor edit = this.a.get().edit();
            edit.putLong(str, j);
            edit.apply();
        } catch (InterruptedException | ExecutionException unused) {
        }
    }

    private void a(String str, String str2) {
        try {
            SharedPreferences.Editor edit = this.a.get().edit();
            edit.putString(str, str2);
            edit.apply();
        } catch (InterruptedException | ExecutionException unused) {
        }
    }

    private void a(String str, boolean z) {
        try {
            SharedPreferences.Editor edit = this.a.get().edit();
            edit.putBoolean(str, z);
            edit.apply();
        } catch (InterruptedException | ExecutionException unused) {
        }
    }

    private int b(String str, int i) {
        try {
            return this.a.get().getInt(str, i);
        } catch (InterruptedException | ExecutionException unused) {
            return i;
        }
    }

    private long b(String str, long j) {
        try {
            return this.a.get().getLong(str, j);
        } catch (InterruptedException | ExecutionException unused) {
            return j;
        }
    }

    private String b(String str, String str2) {
        try {
            return this.a.get().getString(str, str2);
        } catch (InterruptedException | ExecutionException unused) {
            return str2;
        }
    }

    private boolean b(String str, boolean z) {
        try {
            return this.a.get().getBoolean(str, z);
        } catch (InterruptedException | ExecutionException unused) {
            return z;
        }
    }

    public av a(String str) {
        return av.a(b(str, av.a.a()));
    }

    public String a() {
        return b("FM_init_data", "");
    }

    public void a(int i) {
        a("FM_dns_index", i);
    }

    public void a(long j) {
        a("FM_last_time", j);
    }

    public void a(ba baVar) {
        a("FM_config_data", baVar.i());
    }

    public void a(dw dwVar) {
        a("FM_pb_data", dwVar == null ? "" : dwVar.d());
    }

    public void a(String str, av avVar) {
        a(str, avVar.a());
    }

    public void a(boolean z) {
        a("FM_dynamic_fetch", z);
    }

    public String b() {
        return b("FM_init_msg", "");
    }

    public void b(String str) {
        a("FM_init_data", str);
    }

    public void b(boolean z) {
        a("FM_request_forbid", z);
    }

    public ba c() {
        return ba.b(b("FM_config_data", ""));
    }

    public void c(String str) {
        a("FM_init_msg", str);
    }

    public long d() {
        return b("FM_last_time", 0L);
    }

    public void d(String str) {
        a("FM_android_id", str);
    }

    public dw e() {
        return dw.c(b("FM_pb_data", ""));
    }

    public void e(String str) {
        a("FM_serial_number", str);
    }

    public String f() {
        return b("FM_android_id", (String) null);
    }

    public String g() {
        return b("FM_serial_number", (String) null);
    }

    public boolean h() {
        return b("FM_dynamic_fetch", true);
    }

    public int i() {
        return b("FM_dns_index", 0);
    }

    public boolean j() {
        return b("FM_request_forbid", false);
    }

    public void k() {
        try {
            SharedPreferences.Editor edit = this.a.get().edit();
            edit.clear();
            edit.apply();
        } catch (InterruptedException | ExecutionException unused) {
        }
    }
}
