package com.tencent.beacon.module;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.beacon.a.a.d;
import com.tencent.beacon.a.b.f;
import com.tencent.beacon.a.b.g;
import com.tencent.beacon.a.c.j;
import com.tencent.beacon.base.net.b.e;
import com.tencent.beacon.e.c;
import com.tencent.beacon.event.EventBean;
import com.tencent.beacon.event.b.a;
import com.tencent.beacon.event.b.b;
import com.tencent.beacon.event.h;
import com.tencent.beacon.event.immediate.IBeaconImmediateReport;
import com.tencent.beacon.event.open.BeaconEvent;
import com.tencent.beacon.event.open.BeaconReport;
import com.tencent.beacon.event.open.EventResult;
import com.youth.banner.config.BannerConfig;
import j$.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes.dex */
public class EventModule implements BeaconModule, d, e.a, c {
    private static final Map<String, Map<String, String>> a = new ConcurrentHashMap(3);
    private static final Map<String, String> b = new ConcurrentHashMap(3);
    private static final Map<String, String> c = new ConcurrentHashMap(3);
    private final List<com.tencent.beacon.event.b.c> d = new ArrayList(3);
    private AtomicInteger e = new AtomicInteger(0);
    private AtomicBoolean f = new AtomicBoolean(false);
    private StrategyModule g;
    private h h;
    private volatile boolean i;

    private String d(String str) {
        return TextUtils.isEmpty(str) ? com.tencent.beacon.a.c.c.d().f() : str;
    }

    private void g() {
        this.d.add(new b());
        this.d.add(new a());
        for (int i = 1; i < this.d.size(); i++) {
            this.d.get(i - 1).a(this.d.get(i));
        }
    }

    private void h() {
        if (com.tencent.beacon.e.b.a().h()) {
            com.tencent.beacon.event.d dVar = new com.tencent.beacon.event.d();
            this.h = dVar;
            dVar.a();
        }
    }

    private void i() {
        com.tencent.beacon.a.a.b.a().a(3, this);
        com.tencent.beacon.a.a.b.a().a(4, this);
        com.tencent.beacon.a.a.b.a().a(6, this);
        com.tencent.beacon.a.a.b.a().a(1, this);
        com.tencent.beacon.a.a.b.a().a(7, this);
    }

    private void j() {
        com.tencent.beacon.a.b.a.a().a(BannerConfig.LOOP_TIME).postAtFrontOfQueue(new Runnable() { // from class: com.tencent.beacon.module.EventModule.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    com.tencent.beacon.event.a.a a2 = com.tencent.beacon.event.a.a.a();
                    Map<String, Integer> b2 = a2.b("t_r_e");
                    EventModule.this.a(b2, a2.b("t_n_e"));
                } catch (Throwable th) {
                    com.tencent.beacon.base.util.c.a(th);
                    g.e().a("204", "error while storageReport", th);
                }
            }
        });
    }

    public void b(String str, String str2) {
        b.put(d(str), com.tencent.beacon.event.c.d.e(str2));
    }

    public String c(String str) {
        String str2;
        String d = d(str);
        return (TextUtils.isEmpty(d) || (str2 = b.get(d)) == null) ? "10000" : str2;
    }

    public boolean e() {
        return this.i;
    }

    public void f() {
        com.tencent.beacon.base.util.c.a("[EventModule]", "resume report by user.", new Object[0]);
        com.tencent.beacon.a.b.a.a().d();
        com.tencent.beacon.base.net.c.c().e();
    }

    @Override // com.tencent.beacon.module.BeaconModule
    public void a(Context context) {
        j();
        this.g = (StrategyModule) BeaconModule.a.get(ModuleName.STRATEGY);
        g();
        h();
        i();
        e.a(context, this);
        com.tencent.beacon.e.b.a().a(this);
        this.i = true;
    }

    public String b(String str) {
        String str2;
        String d = d(str);
        return (TextUtils.isEmpty(d) || (str2 = c.get(d)) == null) ? "" : str2;
    }

    public h d() {
        return this.h;
    }

    @Override // com.tencent.beacon.e.c
    public void c() {
        if (com.tencent.beacon.e.b.a().h()) {
            if (this.h == null) {
                h();
                return;
            } else {
                f();
                return;
            }
        }
        b(true);
    }

    @Override // com.tencent.beacon.base.net.b.e.a
    public void b() {
        this.h.c();
    }

    public void b(boolean z) {
        com.tencent.beacon.base.util.c.a("[EventModule]", "pause report by user.", new Object[0]);
        com.tencent.beacon.a.b.a.a().a(z);
        com.tencent.beacon.base.net.c.c().close();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Map<String, Integer> map, Map<String, Integer> map2) {
        HashSet<String> hashSet = new HashSet();
        hashSet.addAll(map.keySet());
        hashSet.addAll(map2.keySet());
        if (hashSet.isEmpty()) {
            f.e().a("702", String.format("real_logid_count=%s&normal_logid_count=%s&appkey=%s", 0, 0, com.tencent.beacon.a.c.c.d().f()));
            return;
        }
        for (String str : hashSet) {
            f.e().a("702", String.format("real_logid_count=%s&normal_logid_count=%s&appkey=%s", Integer.valueOf(map.containsKey(str) ? map.get(str).intValue() : 0), Integer.valueOf(map2.containsKey(str) ? map2.get(str).intValue() : 0), str));
        }
    }

    @Override // com.tencent.beacon.a.a.d
    public void a(com.tencent.beacon.a.a.c cVar) {
        int i = cVar.a;
        if (i == 1) {
            com.tencent.beacon.a.a.b.a().a(12, this);
            return;
        }
        if (i == 12) {
            Object obj = cVar.b.get("e_q_e_k");
            if (obj instanceof BeaconEvent) {
                BeaconEvent beaconEvent = (BeaconEvent) obj;
                beaconEvent.getParams().put("A93", "Y");
                com.tencent.beacon.base.util.c.a("qimei empty cache report , appKey: %s, event: %s", beaconEvent.getAppKey(), beaconEvent.getCode());
                a(beaconEvent);
                return;
            }
            return;
        }
        if (i == 3) {
            a((String) cVar.b.get("i_c_ak"), (HashMap) cVar.b.get("i_c_ad"));
            return;
        }
        if (i == 4) {
            b((String) cVar.b.get("i_c_ak"), (String) cVar.b.get("i_c_u_i"));
            return;
        }
        if (i == 5) {
            a((String) cVar.b.get("i_c_ak"), (String) cVar.b.get("i_c_o_i"));
            return;
        }
        if (i != 6) {
            if (i != 7) {
                return;
            }
            com.tencent.beacon.e.b.a().a(((Boolean) cVar.b.get("s_e_e")).booleanValue(), false);
        } else {
            Object obj2 = cVar.b.get("b_e");
            if (obj2 instanceof BeaconEvent) {
                a((BeaconEvent) obj2);
            }
        }
    }

    public void a(boolean z) {
        h hVar = this.h;
        if (hVar != null) {
            hVar.a(z);
        }
    }

    public EventResult a(BeaconEvent beaconEvent) {
        StrategyModule strategyModule;
        com.tencent.beacon.base.util.c.a("[EventModule]", 0, "event: %s. go in EventModule", beaconEvent.getCode());
        if (!com.tencent.beacon.e.b.a().h()) {
            com.tencent.beacon.base.util.c.a("[EventModule]", 1, "event: %s. EventModule is not enable", beaconEvent.getCode());
            return EventResult.a.a(102);
        }
        com.tencent.beacon.base.util.e.a(beaconEvent.getParams());
        StrategyModule strategyModule2 = this.g;
        if (strategyModule2 != null && strategyModule2.b().a(beaconEvent.getCode())) {
            com.tencent.beacon.base.util.c.a("[EventModule]", 1, "event: %s.  is not allowed in strategy (false)", beaconEvent.getCode());
            return EventResult.a.a(100);
        }
        if (beaconEvent.isSucceed() && (strategyModule = this.g) != null && !strategyModule.b().b(beaconEvent.getCode())) {
            com.tencent.beacon.base.util.c.a("[EventModule]", 1, "event: %s. is sampled by svr rate (false)", beaconEvent.getCode());
            return EventResult.a.a(101);
        }
        beaconEvent.setAppKey(d(beaconEvent.getAppKey()));
        EventBean b2 = this.d.get(0).b(beaconEvent);
        if (b2 == null) {
            return EventResult.a.a(105);
        }
        if (TextUtils.isEmpty(j.c()) && TextUtils.isEmpty(j.d())) {
            if (this.e.addAndGet(1) > 64) {
                String format = String.format("qimei empty cache count over max , appKey: %s, event: %s", beaconEvent.getAppKey(), beaconEvent.getCode());
                com.tencent.beacon.base.util.c.a(format, new Object[0]);
                if (this.f.compareAndSet(false, true)) {
                    g.e().a("510", format);
                }
            } else {
                com.tencent.beacon.base.util.c.a("qimei empty and add to cache , appKey: %s, event: %s", beaconEvent.getAppKey(), beaconEvent.getCode());
                BeaconEvent build = BeaconEvent.newBuilder(beaconEvent).build();
                HashMap hashMap = new HashMap();
                hashMap.put("e_q_e_k", build);
                com.tencent.beacon.a.a.b.a().a(new com.tencent.beacon.a.a.c(12, hashMap));
            }
        }
        int eventType = b2.getEventType();
        if (eventType == 2 || eventType == 3) {
            IBeaconImmediateReport immediateReport = BeaconReport.getInstance().getImmediateReport();
            com.tencent.beacon.base.util.e.a("immediateReport", immediateReport);
            if (immediateReport != null) {
                return this.h.a(beaconEvent.getParams().get("A100"), b2);
            }
            com.tencent.beacon.base.util.c.a("immediateReport is null!", new Object[0]);
            g.e().a("515", "immediateReport is null!");
        }
        return this.h.b(beaconEvent.getParams().get("A100"), b2);
    }

    public void a(String str, String str2) {
        c.put(d(str), com.tencent.beacon.event.c.d.c(str2));
    }

    public void a(String str, Map<String, String> map) {
        if (map == null || map.isEmpty() || map.size() >= 50) {
            Object[] objArr = new Object[1];
            objArr[0] = Integer.valueOf(map == null ? 0 : map.size());
            com.tencent.beacon.base.util.c.a("setAdditionalParams error , params.size: %s", objArr);
            return;
        }
        String d = d(str);
        Map<String, Map<String, String>> map2 = a;
        Map<String, String> map3 = map2.get(d);
        if (map3 != null) {
            if (map3.size() + map.size() >= 50) {
                com.tencent.beacon.base.util.c.a("setAdditionalParams error , params.size: can not more than 50", new Object[0]);
                return;
            }
            HashMap hashMap = new HashMap();
            hashMap.putAll(map3);
            hashMap.putAll(map);
            map2.put(d, hashMap);
            return;
        }
        map2.put(d, new HashMap(map));
    }

    public Map<String, String> a(String str) {
        return a.get(d(str));
    }

    @Override // com.tencent.beacon.base.net.b.e.a
    public void a() {
        this.h.b();
    }
}
