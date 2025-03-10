package com.tencent.bugly.proguard;

import android.content.Context;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import java.util.List;
import java.util.Map;

/* compiled from: BUGLY */
/* loaded from: classes.dex */
public final class ac {
    public static int a = 1000;
    public static long b = 259200000;
    private static ac d;
    private static String i;
    public final ak c;
    private final List<o> e;
    private final StrategyBean f;
    private StrategyBean g = null;
    private Context h;

    private ac(Context context, List<o> list) {
        this.h = context;
        if (aa.a(context) != null) {
            String str = aa.a(context).H;
            if ("oversea".equals(str)) {
                StrategyBean.a = "https://astat.bugly.qcloud.com/rqd/async";
                StrategyBean.b = "https://astat.bugly.qcloud.com/rqd/async";
            } else if ("na_https".equals(str)) {
                StrategyBean.a = "https://astat.bugly.cros.wr.pvp.net/:8180/rqd/async";
                StrategyBean.b = "https://astat.bugly.cros.wr.pvp.net/:8180/rqd/async";
            }
        }
        this.f = new StrategyBean();
        this.e = list;
        this.c = ak.a();
    }

    public static synchronized ac a(Context context, List<o> list) {
        ac acVar;
        synchronized (ac.class) {
            if (d == null) {
                d = new ac(context, list);
            }
            acVar = d;
        }
        return acVar;
    }

    public static synchronized ac a() {
        ac acVar;
        synchronized (ac.class) {
            acVar = d;
        }
        return acVar;
    }

    public final synchronized boolean b() {
        return this.g != null;
    }

    public final StrategyBean c() {
        StrategyBean strategyBean = this.g;
        if (strategyBean != null) {
            if (!ap.d(strategyBean.f9q)) {
                this.g.f9q = StrategyBean.a;
            }
            if (!ap.d(this.g.r)) {
                this.g.r = StrategyBean.b;
            }
            return this.g;
        }
        if (!ap.b(i) && ap.d(i)) {
            StrategyBean strategyBean2 = this.f;
            String str = i;
            strategyBean2.f9q = str;
            strategyBean2.r = str;
        }
        return this.f;
    }

    protected final void a(StrategyBean strategyBean, boolean z) {
        al.c("[Strategy] Notify %s", s.class.getName());
        s.a(strategyBean, z);
        for (o oVar : this.e) {
            try {
                al.c("[Strategy] Notify %s", oVar.getClass().getName());
                oVar.onServerStrategyChanged(strategyBean);
            } catch (Throwable th) {
                if (!al.a(th)) {
                    th.printStackTrace();
                }
            }
        }
    }

    public static void a(String str) {
        if (ap.b(str) || !ap.d(str)) {
            al.d("URL user set is invalid.", new Object[0]);
        } else {
            i = str;
        }
    }

    public final void a(bt btVar) {
        if (btVar == null) {
            return;
        }
        StrategyBean strategyBean = this.g;
        if (strategyBean == null || btVar.h != strategyBean.o) {
            StrategyBean strategyBean2 = new StrategyBean();
            strategyBean2.f = btVar.a;
            strategyBean2.h = btVar.c;
            strategyBean2.g = btVar.b;
            if (ap.b(i) || !ap.d(i)) {
                if (ap.d(btVar.d)) {
                    al.c("[Strategy] Upload url changes to %s", btVar.d);
                    strategyBean2.f9q = btVar.d;
                }
                if (ap.d(btVar.e)) {
                    al.c("[Strategy] Exception upload url changes to %s", btVar.e);
                    strategyBean2.r = btVar.e;
                }
            }
            bs bsVar = btVar.f;
            if (bsVar != null && !ap.b(bsVar.a)) {
                strategyBean2.s = btVar.f.a;
            }
            long j = btVar.h;
            if (j != 0) {
                strategyBean2.o = j;
            }
            Map<String, String> map = btVar.g;
            if (map != null && map.size() > 0) {
                Map<String, String> map2 = btVar.g;
                strategyBean2.t = map2;
                String str = map2.get("B11");
                strategyBean2.i = str != null && str.equals("1");
                String str2 = btVar.g.get("B3");
                if (str2 != null) {
                    strategyBean2.w = Long.parseLong(str2);
                }
                int i2 = btVar.l;
                strategyBean2.p = i2;
                strategyBean2.v = i2;
                String str3 = btVar.g.get("B27");
                if (str3 != null && str3.length() > 0) {
                    try {
                        int parseInt = Integer.parseInt(str3);
                        if (parseInt > 0) {
                            strategyBean2.u = parseInt;
                        }
                    } catch (Exception e) {
                        if (!al.a(e)) {
                            e.printStackTrace();
                        }
                    }
                }
                String str4 = btVar.g.get("B25");
                strategyBean2.k = str4 != null && str4.equals("1");
            }
            al.a("[Strategy] enableCrashReport:%b, enableQuery:%b, enableUserInfo:%b, enableAnr:%b, enableBlock:%b, enableSession:%b, enableSessionTimer:%b, sessionOverTime:%d, enableCocos:%b, strategyLastUpdateTime:%d", Boolean.valueOf(strategyBean2.f), Boolean.valueOf(strategyBean2.h), Boolean.valueOf(strategyBean2.g), Boolean.valueOf(strategyBean2.i), Boolean.valueOf(strategyBean2.j), Boolean.valueOf(strategyBean2.m), Boolean.valueOf(strategyBean2.n), Long.valueOf(strategyBean2.p), Boolean.valueOf(strategyBean2.k), Long.valueOf(strategyBean2.o));
            this.g = strategyBean2;
            if (!ap.d(btVar.d)) {
                al.c("[Strategy] download url is null", new Object[0]);
                this.g.f9q = "";
            }
            if (!ap.d(btVar.e)) {
                al.c("[Strategy] download crashurl is null", new Object[0]);
                this.g.r = "";
            }
            w.a().b(2);
            y yVar = new y();
            yVar.b = 2;
            yVar.a = strategyBean2.d;
            yVar.e = strategyBean2.e;
            yVar.g = ap.a(strategyBean2);
            w.a().a(yVar);
            a(strategyBean2, true);
        }
    }

    public static StrategyBean d() {
        byte[] bArr;
        List<y> a2 = w.a().a(2);
        if (a2 == null || a2.size() <= 0 || (bArr = a2.get(0).g) == null) {
            return null;
        }
        return (StrategyBean) ap.a(bArr, StrategyBean.CREATOR);
    }
}
