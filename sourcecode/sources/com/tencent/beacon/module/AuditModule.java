package com.tencent.beacon.module;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.text.TextUtils;
import android.util.SparseArray;
import com.tencent.beacon.a.a.d;
import com.tencent.beacon.a.c.e;
import com.tencent.beacon.a.c.f;
import com.tencent.beacon.base.util.b;
import com.tencent.beacon.base.util.c;
import com.tencent.beacon.c.a;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public class AuditModule implements BeaconModule, d {
    private String c;
    private Set<String> d;
    private Context f;
    private boolean a = true;
    private boolean b = true;
    private int e = 2;

    public static String a() {
        ArrayList<String> a = b.a(new String[]{"/system/bin/sh", "-c", "getprop ro.build.fingerprint"});
        return (a == null || a.size() <= 0) ? "" : a.get(0);
    }

    @SuppressLint({"NewApi"})
    public static String b(Context context) {
        try {
            if (Integer.parseInt(Build.VERSION.SDK) < 9) {
                c.b("[audit] Api level < 9,return null!", new Object[0]);
                return "";
            }
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            StringBuilder sb = new StringBuilder();
            sb.append("[audit] get app_first_installed_time:");
            sb.append(String.valueOf(packageInfo.firstInstallTime));
            c.a(sb.toString(), new Object[0]);
            return String.valueOf(packageInfo.firstInstallTime);
        } catch (Throwable th) {
            c.a(th);
            return "";
        }
    }

    private String c() {
        String str;
        return (com.tencent.beacon.e.b.a() == null || (str = this.c) == null) ? "" : str;
    }

    private Activity d() {
        String e;
        SparseArray<WeakReference<Activity>> a;
        if (Integer.valueOf(Build.VERSION.SDK).intValue() < 16) {
            return null;
        }
        try {
            e = e();
        } catch (Exception e2) {
            c.a(e2);
        }
        if (e == null || (a = com.tencent.beacon.d.a.c.a()) == null) {
            return null;
        }
        for (int i = 0; i < a.size(); i++) {
            WeakReference<Activity> weakReference = a.get(a.keyAt(i));
            if (weakReference != null && weakReference.get() != null) {
                Activity activity = weakReference.get();
                if (activity.getClass().getName().equals(e)) {
                    return activity;
                }
            }
        }
        return null;
    }

    private String e() {
        return null;
    }

    @Override // com.tencent.beacon.module.BeaconModule
    public void a(Context context) {
        this.f = context;
        com.tencent.beacon.a.a.b.a().a(2, this);
        com.tencent.beacon.a.a.b.a().a(10, this);
    }

    @Override // com.tencent.beacon.a.a.d
    public void a(com.tencent.beacon.a.a.c cVar) {
        boolean z;
        int i = cVar.a;
        boolean z2 = false;
        if (i != 2) {
            if (i != 10) {
                return;
            }
            c.d("[module] native audit module > %s", Boolean.valueOf(this.b));
            if (this.b && com.tencent.beacon.e.b.a().f()) {
                b();
                return;
            }
            return;
        }
        Map map = (Map) cVar.b.get("d_m");
        if (map == null) {
            return;
        }
        this.a = b.a((String) map.get("upAc"), this.a);
        this.e = b.a((String) map.get("deleteSoCrashTime"), this.e, 1, 10);
        this.c = (String) map.get("appendXMeths");
        String str = (String) map.get("auditIgnore");
        if (!TextUtils.isEmpty(str)) {
            this.d = new HashSet(Arrays.asList(str.split(",")));
        }
        if (this.d != null) {
            z = !this.d.contains((f.e().h() + "_" + Build.VERSION.SDK).replaceAll(" ", ""));
        } else {
            z = true;
        }
        if (this.a && z) {
            z2 = true;
        }
        this.b = z2;
    }

    public void b() {
        Context context = this.f;
        if (context != null && com.tencent.beacon.a.c.b.g(context)) {
            c.a("[audit] start upload ac event", new Object[0]);
            e l = e.l();
            HashMap hashMap = new HashMap();
            hashMap.put("A19", l.q());
            hashMap.put("A58", l.m() ? "Y" : "N");
            hashMap.put("A82", a());
            hashMap.put("A85", com.tencent.beacon.a.c.b.d ? "Y" : "N");
            hashMap.put("A88", b(this.f));
            hashMap.put("A89", l.a(this.f));
            hashMap.put("A90", "");
            hashMap.put("A91", "");
            hashMap.put("A92", "");
            hashMap.put("B13", a(a.a(this.f, Integer.valueOf(Build.VERSION.SDK).intValue(), d(), c(), this.e)));
            hashMap.put("A31", "" + l.p());
            ((StatModule) com.tencent.beacon.a.c.c.d().a(ModuleName.STAT)).a(hashMap);
        }
    }

    private String a(String str) {
        if (str == null) {
            return "";
        }
        String replace = str.replace("=", "%3D").replace("/", "%2F").replace("+", "%2B");
        if (replace.length() <= 1024) {
            return replace;
        }
        return replace + ";";
    }
}
