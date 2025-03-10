package com.tencent.qmsp.sdk.a;

import android.os.Build;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class d {
    public static JSONObject a(int i) {
        String appID;
        String devId;
        String uid;
        String qImeiVer;
        String str;
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(e.a(0), i);
            String a = e.a(1);
            appID = com.tencent.qmsp.sdk.app.a.getAppID();
            jSONObject.put(a, appID);
            String a2 = e.a(2);
            devId = com.tencent.qmsp.sdk.app.a.getDevId();
            jSONObject.put(a2, devId);
            String a3 = e.a(3);
            uid = com.tencent.qmsp.sdk.app.a.getUid();
            jSONObject.put(a3, uid);
            String a4 = e.a(4);
            qImeiVer = com.tencent.qmsp.sdk.app.a.getQImeiVer();
            jSONObject.put(a4, qImeiVer);
            jSONObject.put(e.a(5), Build.VERSION.SDK_INT);
            jSONObject.put(e.a(6), c.c());
            jSONObject.put(e.a(7), c.e());
            jSONObject.put(e.a(8), System.currentTimeMillis());
            jSONObject.put(e.a(9), 1);
            jSONObject.put(e.a(10), c.h() ? "1" : "0");
            String a5 = e.a(11);
            str = com.tencent.qmsp.sdk.app.a.getmOAID();
            jSONObject.put(a5, str);
            return jSONObject;
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }
}
