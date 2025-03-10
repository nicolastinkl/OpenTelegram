package com.tencent.beacon.event.open;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.tencent.beacon.a.a.c;
import com.tencent.beacon.a.b.g;
import com.tencent.beacon.a.c.f;
import com.tencent.beacon.a.c.j;
import com.tencent.beacon.base.util.BeaconLogger;
import com.tencent.beacon.base.util.e;
import com.tencent.beacon.core.info.BeaconPubParams;
import com.tencent.beacon.event.c.d;
import com.tencent.beacon.event.immediate.IBeaconImmediateReport;
import com.tencent.beacon.event.open.EventResult;
import com.tencent.beacon.module.BeaconModule;
import com.tencent.beacon.module.EventModule;
import com.tencent.beacon.module.ModuleName;
import com.tencent.qimei.sdk.IAsyncQimeiListener;
import com.tencent.qimei.sdk.Qimei;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public final class BeaconReport {
    private static volatile BeaconReport a = null;
    private static String b = "";
    private Context c;
    private boolean d;
    private IBeaconImmediateReport e;
    private EventModule f;

    private BeaconReport() {
    }

    private void b(BeaconConfig beaconConfig) {
        HashMap hashMap = new HashMap();
        hashMap.put("u_c_r_p", Long.valueOf(beaconConfig.getRealtimePollingTime()));
        hashMap.put("u_c_n_p", Long.valueOf(beaconConfig.getNormalPollingTIme()));
        com.tencent.beacon.a.a.b.a().b(new c(11, hashMap));
    }

    private void c(BeaconConfig beaconConfig) {
        if (beaconConfig != null) {
            try {
                HashMap hashMap = new HashMap();
                hashMap.put("u_c_a_e", Boolean.valueOf(beaconConfig.isAuditEnable()));
                hashMap.put("u_c_b_e", Boolean.valueOf(beaconConfig.isBidEnable()));
                hashMap.put("u_c_d_s", Integer.valueOf(beaconConfig.getMaxDBCount()));
                hashMap.put("u_c_p_s", Boolean.valueOf(beaconConfig.isPagePathEnable()));
                hashMap.put("u_s_o_h", Boolean.valueOf(beaconConfig.isSocketMode()));
                com.tencent.beacon.a.a.b.a().b(new c(8, hashMap));
            } catch (Throwable th) {
                g.e().a("202", "sdk init error! package name: " + com.tencent.beacon.a.c.b.b() + " , msg:" + th.getMessage(), th);
                com.tencent.beacon.base.util.c.a(th);
            }
        }
    }

    private void d(BeaconConfig beaconConfig) {
        f e = f.e();
        if (!TextUtils.isEmpty(beaconConfig.getAndroidID())) {
            e.a(beaconConfig.getAndroidID());
        }
        if (!TextUtils.isEmpty(beaconConfig.getImei())) {
            e.b(beaconConfig.getImei());
        }
        if (!TextUtils.isEmpty(beaconConfig.getImei2())) {
            e.c(beaconConfig.getImei2());
        }
        if (!TextUtils.isEmpty(beaconConfig.getImsi())) {
            e.d(beaconConfig.getImsi());
        }
        if (!TextUtils.isEmpty(beaconConfig.getMeid())) {
            e.f(beaconConfig.getMeid());
        }
        if (!TextUtils.isEmpty(beaconConfig.getModel())) {
            e.g(beaconConfig.getModel());
        }
        if (!TextUtils.isEmpty(beaconConfig.getMac())) {
            e.e(beaconConfig.getMac());
        }
        if (!TextUtils.isEmpty(beaconConfig.getWifiMacAddress())) {
            e.i(beaconConfig.getWifiMacAddress());
        }
        if (!TextUtils.isEmpty(beaconConfig.getWifiSSID())) {
            e.j(beaconConfig.getWifiSSID());
        }
        if (TextUtils.isEmpty(beaconConfig.getOaid())) {
            return;
        }
        e.h(beaconConfig.getOaid());
    }

    public static BeaconReport getInstance() {
        if (a == null) {
            synchronized (BeaconReport.class) {
                if (a == null) {
                    a = new BeaconReport();
                }
            }
        }
        return a;
    }

    public static String getSoPath() {
        return b;
    }

    public static void setSoPath(String str) {
        b = str;
    }

    public void disableReport() {
        com.tencent.beacon.e.b.a().a(false, true);
    }

    public void enableReport() {
        com.tencent.beacon.e.b.a().a(true, true);
    }

    public BeaconPubParams getCommonParams(Context context) {
        if (context == null) {
            return null;
        }
        return BeaconPubParams.getPubParams(context);
    }

    public IBeaconImmediateReport getImmediateReport() {
        return this.e;
    }

    @Deprecated
    public String getOAID() {
        com.tencent.beacon.base.util.c.b("this method do not collect OAID, use method in qmsp instead", new Object[0]);
        return f.e().i();
    }

    @Deprecated
    public Qimei getQimei() {
        return j.b();
    }

    @Deprecated
    public Qimei getRtQimei(Context context) {
        return j.a(context);
    }

    public String getSDKVersion() {
        return "4.2.80.6";
    }

    public void pauseUpload(boolean z) {
        EventModule eventModule = this.f;
        if (eventModule == null || !eventModule.e()) {
            return;
        }
        this.f.b(z);
    }

    public EventResult report(BeaconEvent beaconEvent) {
        try {
            if (!com.tencent.beacon.e.b.a().h()) {
                com.tencent.beacon.base.util.c.e("BeaconReport not enable report! event: %s", beaconEvent.getCode());
                return EventResult.a.a(102);
            }
            if (TextUtils.isEmpty(beaconEvent.getCode())) {
                return EventResult.a.a(106);
            }
            BeaconEvent build = BeaconEvent.newBuilder(beaconEvent).build();
            EventModule eventModule = this.f;
            if (eventModule != null && eventModule.e()) {
                return this.f.a(build);
            }
            HashMap hashMap = new HashMap();
            hashMap.put("b_e", build);
            com.tencent.beacon.a.a.b.a().a(new c(6, hashMap));
            return new EventResult(0, -1L, "Beacon SDK not init beaconEvent add to cache!");
        } catch (Throwable th) {
            com.tencent.beacon.base.util.c.a(th);
            g.e().a("598", "error while report", th);
            return new EventResult(EventResult.ERROR_CODE_OTHER, -1L, "REPORT ERROR");
        }
    }

    @Deprecated
    public void resumeReport() {
        resumeUpload();
    }

    public void resumeUpload() {
        EventModule eventModule = this.f;
        if (eventModule == null || !eventModule.e()) {
            return;
        }
        this.f.f();
    }

    @Deprecated
    public void setAdditionalParams(Map<String, String> map) {
        setAdditionalParams(com.tencent.beacon.a.c.c.d().f(), map);
    }

    public void setAndroidID(String str) {
        f.e().a(str);
    }

    public void setAppVersion(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        com.tencent.beacon.a.c.b.a = str;
    }

    public void setChannelID(String str) {
        com.tencent.beacon.a.c.c.d().a(str);
    }

    @Deprecated
    public void setCollectAndroidID(boolean z) {
        com.tencent.beacon.base.util.c.b("setCollectAndroidID has been Deprecated", new Object[0]);
    }

    @Deprecated
    public void setCollectImei(boolean z) {
        com.tencent.beacon.base.util.c.b("setCollectImei has been Deprecated", new Object[0]);
    }

    @Deprecated
    public void setCollectMac(boolean z) {
        com.tencent.beacon.base.util.c.b("setCollectMac has been Deprecated", new Object[0]);
    }

    @Deprecated
    public void setCollectModel(boolean z) {
        com.tencent.beacon.base.util.c.b("setCollectModel has been Deprecated", new Object[0]);
    }

    @Deprecated
    public void setCollectOAID(boolean z) {
        com.tencent.beacon.base.util.c.b("setCollectOAID has been Deprecated", new Object[0]);
    }

    @Deprecated
    public void setCollectPersonalInfo(boolean z) {
        com.tencent.beacon.base.util.c.b("setCollectPersonalInfo has been Deprecated", new Object[0]);
    }

    public void setCollectProcessInfo(boolean z) {
        com.tencent.beacon.e.b.a().b(z);
    }

    public void setImei(String str) {
        f.e().b(str);
    }

    public void setImei2(String str) {
        f.e().c(str);
    }

    public void setImmediateReport(IBeaconImmediateReport iBeaconImmediateReport) {
        this.e = iBeaconImmediateReport;
    }

    public void setImsi(String str) {
        f.e().d(str);
    }

    public void setLogAble(boolean z) {
        com.tencent.beacon.base.util.c.a(z);
    }

    public void setLogger(BeaconLogger beaconLogger) {
        com.tencent.beacon.base.util.c.a(beaconLogger);
    }

    public void setMac(String str) {
        f.e().e(str);
    }

    public void setMeid(String str) {
        f.e().f(str);
    }

    public void setModel(String str) {
        f.e().g(str);
    }

    public void setOAID(String str) {
        f.e().h(str);
    }

    @Deprecated
    public void setOaid(String str) {
        f.e().h(str);
        com.tencent.beacon.base.util.c.b("setOaid has been Deprecated, please use setOAID", new Object[0]);
    }

    public void setOmgID(String str) {
        com.tencent.beacon.a.c.c.d().e(str);
    }

    public void setOpenID(String str, String str2) {
        HashMap hashMap = new HashMap();
        hashMap.put("i_c_ak", str);
        hashMap.put("i_c_o_i", str2);
        com.tencent.beacon.a.a.b.a().b(new c(5, hashMap));
    }

    public void setQQ(String str) {
        com.tencent.beacon.a.c.b.a(str);
    }

    public void setQimei(String str, String str2) {
        j.a(str, str2);
    }

    public void setQimeiSdkVersion(String str) {
        j.c(str);
    }

    public void setStrictMode(boolean z) {
        e.a.set(z);
    }

    @Deprecated
    public void setUserID(String str) {
        setUserID(com.tencent.beacon.a.c.c.d().f(), str);
    }

    public void setWifiMacAddress(String str) {
        f.e().i(str);
    }

    public void setWifiSSID(String str) {
        f.e().j(str);
    }

    public synchronized void start(Context context, String str, BeaconConfig beaconConfig) {
        if (this.d) {
            return;
        }
        Log.i("beacon", "logAble: " + com.tencent.beacon.base.util.c.b() + " , SDKVersion: " + getSDKVersion());
        e.a("Context", context);
        boolean z = false;
        if (context == null) {
            com.tencent.beacon.base.util.c.b("fail to start beacon, context is null", new Object[0]);
            return;
        }
        Context applicationContext = context.getApplicationContext();
        this.c = applicationContext;
        e.a("ApplicationContext", applicationContext);
        if (this.c == null) {
            com.tencent.beacon.base.util.c.b("fail to start beacon, application context is null", new Object[0]);
            return;
        }
        com.tencent.beacon.a.c.c.d().a(this.c);
        e.a("AppKey", str);
        if (TextUtils.isEmpty(str)) {
            com.tencent.beacon.base.util.c.b("fail to start beacon, appkey is empty", new Object[0]);
            return;
        }
        com.tencent.beacon.a.c.c.d().d(str);
        g.e().a(beaconConfig != null && beaconConfig.isForceEnableAtta());
        com.tencent.beacon.a.b.f e = com.tencent.beacon.a.b.f.e();
        if (beaconConfig != null && beaconConfig.isForceEnableAtta()) {
            z = true;
        }
        e.a(z);
        ((Application) this.c).registerActivityLifecycleCallbacks(new com.tencent.beacon.b.a());
        c(beaconConfig);
        if (beaconConfig != null) {
            d(beaconConfig);
            j.a(beaconConfig.isNeedInitQimei());
            com.tencent.beacon.e.b.a().a(beaconConfig.isNeedReportRqdEvent());
        }
        com.tencent.beacon.a.b.a.a().a(new b(this, beaconConfig));
        this.d = true;
    }

    @Deprecated
    public void stopReport(boolean z) {
        pauseUpload(z);
    }

    public Qimei getQimei(String str) {
        return j.b(str);
    }

    public Qimei getRtQimei(Context context, String str) {
        return j.b(context, str);
    }

    public void setAdditionalParams(String str, Map<String, String> map) {
        HashMap hashMap = new HashMap();
        hashMap.put("i_c_ad", new HashMap(map));
        hashMap.put("i_c_ak", str);
        com.tencent.beacon.a.a.b.a().b(new c(3, hashMap));
    }

    public void setUserID(String str, String str2) {
        HashMap hashMap = new HashMap();
        hashMap.put("i_c_ak", str);
        hashMap.put("i_c_u_i", str2);
        com.tencent.beacon.a.a.b.a().b(new c(4, hashMap));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(BeaconConfig beaconConfig) {
        if (beaconConfig != null) {
            com.tencent.beacon.base.util.c.a("BeaconReport", beaconConfig.toString(), new Object[0]);
            com.tencent.beacon.base.net.b.b.a(beaconConfig.getConfigHost(), beaconConfig.getUploadHost());
            b(beaconConfig);
            com.tencent.beacon.a.c.c.d().a(beaconConfig.isEnableQmsp());
            com.tencent.beacon.e.b.a().b(beaconConfig.getRealtimeUploadNum());
            com.tencent.beacon.e.b.a().a(beaconConfig.getNormalUploadNum());
        }
        com.tencent.beacon.base.net.c.c().a(this.c, beaconConfig == null ? null : beaconConfig.getHttpAdapter());
        com.tencent.beacon.a.d.a.a().a(this.c);
        com.tencent.beacon.a.c.b.f();
        com.tencent.beacon.a.c.e.l().B();
    }

    @Deprecated
    public void getQimei(IAsyncQimeiListener iAsyncQimeiListener) throws NullPointerException {
        j.a(iAsyncQimeiListener);
    }

    public void getQimei(String str, Context context, IAsyncQimeiListener iAsyncQimeiListener) {
        j.a(str, context, iAsyncQimeiListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        ModuleName[] values = ModuleName.values();
        for (ModuleName moduleName : values) {
            try {
                BeaconModule.a.put(moduleName, d.f(moduleName.getClassName()));
            } catch (Exception e) {
                com.tencent.beacon.base.util.c.b("init Module error: " + e.getMessage(), new Object[0]);
                com.tencent.beacon.base.util.c.a(e);
            }
        }
        for (ModuleName moduleName2 : values) {
            BeaconModule beaconModule = BeaconModule.a.get(moduleName2);
            if (beaconModule != null) {
                beaconModule.a(this.c);
            }
        }
        this.f = (EventModule) com.tencent.beacon.a.c.c.d().a(ModuleName.EVENT);
    }
}
