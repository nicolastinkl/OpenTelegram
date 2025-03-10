package com.tencent.bugly.proguard;

import android.content.Context;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.crashreport.crash.CrashDetailBean;
import java.util.LinkedHashMap;
import java.util.Map;

/* compiled from: BUGLY */
/* loaded from: classes.dex */
public final class au {
    private static au a;
    private ac b;
    private aa c;
    private as d;
    private Context e;

    private au(Context context) {
        at a2 = at.a();
        if (a2 == null) {
            return;
        }
        this.b = ac.a();
        this.c = aa.a(context);
        this.d = a2.s;
        this.e = context;
        ak.a().a(new Runnable() { // from class: com.tencent.bugly.proguard.au.1
            @Override // java.lang.Runnable
            public final void run() {
                au.a(au.this);
            }
        });
    }

    public static au a(Context context) {
        if (a == null) {
            a = new au(context);
        }
        return a;
    }

    public static void a(final Thread thread, final int i, final String str, final String str2, final String str3, final Map<String, String> map) {
        ak.a().a(new Runnable() { // from class: com.tencent.bugly.proguard.au.2
            @Override // java.lang.Runnable
            public final void run() {
                try {
                    if (au.a != null) {
                        au.a(au.a, thread, i, str, str2, str3, map);
                    } else {
                        al.e("[ExtraCrashManager] Extra crash manager has not been initialized.", new Object[0]);
                    }
                } catch (Throwable th) {
                    if (!al.b(th)) {
                        th.printStackTrace();
                    }
                    al.e("[ExtraCrashManager] Crash error %s %s %s", str, str2, str3);
                }
            }
        });
    }

    static /* synthetic */ void a(au auVar) {
        al.c("[ExtraCrashManager] Trying to notify Bugly agents.", new Object[0]);
        try {
            Class<?> cls = Class.forName("com.tencent.bugly.agent.GameAgent");
            auVar.c.getClass();
            ap.a(cls, "sdkPackageName", "com.tencent.bugly");
            al.c("[ExtraCrashManager] Bugly game agent has been notified.", new Object[0]);
        } catch (Throwable unused) {
            al.a("[ExtraCrashManager] no game agent", new Object[0]);
        }
    }

    static /* synthetic */ void a(au auVar, Thread thread, int i, String str, String str2, String str3, Map map) {
        String str4;
        String str5;
        String str6;
        Thread currentThread = thread == null ? Thread.currentThread() : thread;
        if (i == 4) {
            str4 = "Unity";
        } else if (i == 5 || i == 6) {
            str4 = "Cocos";
        } else {
            if (i != 8) {
                al.d("[ExtraCrashManager] Unknown extra crash type: %d", Integer.valueOf(i));
                return;
            }
            str4 = "H5";
        }
        al.e("[ExtraCrashManager] %s Crash Happen", str4);
        try {
            if (!auVar.b.b()) {
                al.d("[ExtraCrashManager] There is no remote strategy, but still store it.", new Object[0]);
            }
            StrategyBean c = auVar.b.c();
            if (!c.f && auVar.b.b()) {
                al.e("[ExtraCrashManager] Crash report was closed by remote. Will not upload to Bugly , print local for helpful!", new Object[0]);
                as.a(str4, ap.a(), auVar.c.d, currentThread.getName(), str + "\n" + str2 + "\n" + str3, null);
                al.e("[ExtraCrashManager] Successfully handled.", new Object[0]);
                return;
            }
            if (i == 5 || i == 6) {
                if (!c.k) {
                    al.e("[ExtraCrashManager] %s report is disabled.", str4);
                    al.e("[ExtraCrashManager] Successfully handled.", new Object[0]);
                    return;
                }
            } else if (i == 8 && !c.l) {
                al.e("[ExtraCrashManager] %s report is disabled.", str4);
                al.e("[ExtraCrashManager] Successfully handled.", new Object[0]);
                return;
            }
            int i2 = i != 8 ? i : 5;
            CrashDetailBean crashDetailBean = new CrashDetailBean();
            crashDetailBean.C = ab.j();
            crashDetailBean.D = ab.f();
            crashDetailBean.E = ab.l();
            crashDetailBean.F = auVar.c.k();
            crashDetailBean.G = auVar.c.j();
            crashDetailBean.H = auVar.c.l();
            crashDetailBean.I = ab.b(auVar.e);
            crashDetailBean.J = ab.g();
            crashDetailBean.K = ab.h();
            crashDetailBean.b = i2;
            crashDetailBean.e = auVar.c.g();
            aa aaVar = auVar.c;
            crashDetailBean.f = aaVar.o;
            crashDetailBean.g = aaVar.q();
            crashDetailBean.m = auVar.c.f();
            crashDetailBean.n = String.valueOf(str);
            crashDetailBean.o = String.valueOf(str2);
            str5 = "";
            if (str3 != null) {
                String[] split = str3.split("\n");
                str5 = split.length > 0 ? split[0] : "";
                str6 = str3;
            } else {
                str6 = "";
            }
            crashDetailBean.p = str5;
            crashDetailBean.f10q = str6;
            crashDetailBean.r = System.currentTimeMillis();
            crashDetailBean.u = ap.c(crashDetailBean.f10q.getBytes());
            crashDetailBean.z = ap.a(auVar.c.Q, at.h);
            crashDetailBean.A = auVar.c.d;
            crashDetailBean.B = currentThread.getName() + "(" + currentThread.getId() + ")";
            crashDetailBean.L = auVar.c.s();
            crashDetailBean.h = auVar.c.p();
            aa aaVar2 = auVar.c;
            crashDetailBean.Q = aaVar2.a;
            crashDetailBean.R = aaVar2.a();
            crashDetailBean.U = auVar.c.z();
            aa aaVar3 = auVar.c;
            crashDetailBean.V = aaVar3.x;
            crashDetailBean.W = aaVar3.t();
            crashDetailBean.X = auVar.c.y();
            crashDetailBean.y = ao.a();
            if (crashDetailBean.S == null) {
                crashDetailBean.S = new LinkedHashMap();
            }
            if (map != null) {
                crashDetailBean.S.putAll(map);
            }
            as.a(str4, ap.a(), auVar.c.d, currentThread.getName(), str + "\n" + str2 + "\n" + str3, crashDetailBean);
            if (!auVar.d.a(crashDetailBean, !at.a().C)) {
                auVar.d.b(crashDetailBean, false);
            }
            al.e("[ExtraCrashManager] Successfully handled.", new Object[0]);
        } catch (Throwable th) {
            try {
                if (!al.a(th)) {
                    th.printStackTrace();
                }
                al.e("[ExtraCrashManager] Successfully handled.", new Object[0]);
            } catch (Throwable th2) {
                al.e("[ExtraCrashManager] Successfully handled.", new Object[0]);
                throw th2;
            }
        }
    }
}
