package com.tencent.qimei.m;

import com.tencent.qimei.c.c;
import com.tencent.qimei.codez.FalconSdk;
import com.tencent.qimei.codez.shell.UserInfoType;
import com.tencent.qimei.v.d;

/* compiled from: QimeiSec.java */
/* loaded from: classes.dex */
public class b {
    public static b a;
    public boolean b = false;

    public static b a() {
        if (a == null) {
            synchronized (b.class) {
                if (a == null) {
                    a = new b();
                }
            }
        }
        return a;
    }

    public String b() {
        try {
            return FalconSdk.getInstance().getVersion();
        } catch (Throwable unused) {
            return "";
        }
    }

    public synchronized boolean b(String str) {
        if (!d.a(str).v()) {
            return false;
        }
        if (this.b) {
            return true;
        }
        try {
            this.b = FalconSdk.getInstance().report();
        } catch (Throwable th) {
            this.b = false;
            if (com.tencent.qimei.j.b.a) {
                com.tencent.qimei.k.a.a(th);
            }
        }
        com.tencent.qimei.k.a.b("上报", "Falcon report(appKey: %s)，r:%b", str, Boolean.valueOf(this.b));
        return this.b;
    }

    public synchronized boolean a(String str) {
        boolean z;
        if (!d.a(str).v()) {
            return false;
        }
        com.tencent.qimei.l.d a2 = com.tencent.qimei.l.d.a(str);
        c j = c.j();
        com.tencent.qimei.u.d b = com.tencent.qimei.u.d.b();
        com.tencent.qimei.u.a aVar = new com.tencent.qimei.u.a(str);
        try {
            z = FalconSdk.getInstance().setUserInfo(UserInfoType.TYPE_APP_KEY.toString(), str).setUserInfo(UserInfoType.TYPE_Q16.toString(), aVar.N()).setUserInfo(UserInfoType.TYPE_Q36.toString(), aVar.H()).setUserInfo(UserInfoType.TYPE_CHANNEL_ID.toString(), aVar.a() == null ? "" : aVar.a().I()).setUserInfo(UserInfoType.TYPE_USER_ID_PARAM.toString(), aVar.a() == null ? "" : aVar.a().K()).setUserInfo(UserInfoType.TYPE_EI.toString(), a2.f()).setUserInfo(UserInfoType.TYPE_SI.toString(), a2.g()).setUserInfo(UserInfoType.TYPE_MC.toString(), a2.h()).setUserInfo(UserInfoType.TYPE_CD.toString(), a2.i()).setUserInfo(UserInfoType.TYPE_NET_WORK_TYPE.toString(), j.m()).setUserInfo(UserInfoType.TYPE_AD.toString(), a2.c()).setUserInfo(UserInfoType.TYPE_LOCAL_IP.toString(), j.k()).setUserInfo(UserInfoType.TYPE_APP_VERSION.toString(), com.tencent.qimei.c.a.a()).setUserInfo(UserInfoType.TYPE_QM_VERSION.toString(), b.getSdkVersion()).setUserInfo(UserInfoType.TYPE_OD.toString(), a2.j()).init(b.J());
        } catch (Throwable th) {
            if (com.tencent.qimei.j.b.a) {
                com.tencent.qimei.k.a.a(th);
            }
            z = false;
        }
        com.tencent.qimei.k.a.b("SDK_INIT", "Falcon %s init(appKey: %s)，r:%b", b(), str, Boolean.valueOf(z));
        if (z) {
            com.tencent.qimei.b.a.a().a(d.a(str).s() * 1000, new a(this, str));
        }
        return z;
    }
}
