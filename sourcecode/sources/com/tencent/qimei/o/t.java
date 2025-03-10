package com.tencent.qimei.o;

import android.content.Context;
import com.tencent.qimei.sdk.IAsyncQimeiListener;
import com.tencent.qimei.sdk.Qimei;
import java.util.HashMap;

/* compiled from: QimeiSDKInfo.java */
/* loaded from: classes.dex */
public class t implements IAsyncQimeiListener {
    public final /* synthetic */ u a;

    public t(u uVar) {
        this.a = uVar;
    }

    @Override // com.tencent.qimei.sdk.IAsyncQimeiListener
    public void onQimeiDispatch(Qimei qimei) {
        String str;
        Context context;
        String str2;
        String str3;
        com.tencent.qimei.g.b a = com.tencent.qimei.g.b.a();
        str = this.a.g;
        a.a(str);
        com.tencent.qimei.g.b a2 = com.tencent.qimei.g.b.a();
        context = this.a.h;
        a2.b(context);
        com.tencent.qimei.m.b a3 = com.tencent.qimei.m.b.a();
        str2 = this.a.g;
        a3.a(str2);
        str3 = this.a.g;
        com.tencent.qimei.q.a a4 = com.tencent.qimei.q.a.a(str3);
        if (a4.a()) {
            int i = a4.e ? 2 : a4.d ? 1 : 0;
            com.tencent.qimei.k.a.b(com.tencent.qimei.q.a.a, "clone status: ByTime %b, ByAID %b", Boolean.valueOf(a4.d), Boolean.valueOf(a4.e));
            String str4 = a4.c;
            if (a4.f == null) {
                a4.f = new Qimei();
            }
            HashMap hashMap = new HashMap();
            hashMap.put("o16", a4.f.getQimei16());
            hashMap.put("o36", a4.f.getQimei36());
            com.tencent.qimei.u.a aVar = new com.tencent.qimei.u.a(a4.c);
            hashMap.put("n16", aVar.N());
            hashMap.put("n36", aVar.H());
            com.tencent.qimei.k.a.b("CloneDetect", "克隆事件上报: appKey: %s，上报参数： %s", str4, hashMap.toString());
            com.tencent.qimei.n.c a5 = com.tencent.qimei.n.i.a().a(com.tencent.qimei.n.e.REPORT_CLONE_CACHE_Q16.K, hashMap.get("o16")).a(com.tencent.qimei.n.e.REPORT_CLONE_CACHE_Q36.K, hashMap.get("o36")).a(com.tencent.qimei.n.e.REPORT_CLONE_CACHE_NEW_Q16.K, hashMap.get("n16")).a(com.tencent.qimei.n.e.REPORT_CLONE_CACHE_NEW_Q36.K, hashMap.get("n36")).a(com.tencent.qimei.n.e.REPORT_CLONE_DETECT_TYPE.K, Integer.valueOf(i));
            a5.a = str4;
            a5.c = "/report";
            a5.a("v7");
            com.tencent.qimei.k.a.b("CloneDetect", "%s可能被克隆，检测方式：%d", com.tencent.qimei.c.a.d(), Integer.valueOf(i));
        }
    }
}
