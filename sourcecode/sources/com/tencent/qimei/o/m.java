package com.tencent.qimei.o;

import android.os.Process;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.qimei.beaconid.U;
import com.tencent.qimei.sdk.Qimei;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: QimeiProtocol.java */
/* loaded from: classes.dex */
public class m {
    public static final m a = new m();

    /* compiled from: QimeiProtocol.java */
    public enum a {
        KEY_ENCRYPT_KEY("key"),
        KEY_PARAMS("params"),
        KEY_TIME("time"),
        KEY_NONCE("nonce"),
        KEY_SIGN("sign"),
        KEY_EXTRA("extra"),
        KEY_PARAMS_AD(com.tencent.qimei.a.b.a(11)),
        KEY_PARAMS_PLATFORM_ID("platformId"),
        KEY_PARAMS_APP_KEY("appKey"),
        KEY_PARAMS_APP_VERSION("appVersion"),
        KEY_PARAMS_BEACON_ID_SRC("beaconIdSrc"),
        KEY_PARAMS_BRAND("brand"),
        KEY_PARAMS_CHANNEL_ID("channelId"),
        KEY_PARAMS_CD(com.tencent.qimei.a.b.a(9)),
        KEY_PARAMS_EI(com.tencent.qimei.a.b.a(6)),
        KEY_PARAMS_SI(com.tencent.qimei.a.b.a(7)),
        KEY_PARAMS_MC(com.tencent.qimei.a.b.a(8)),
        KEY_PARAMS_MODEL("model"),
        KEY_PARAMS_NETWORK_TYPE("networkType"),
        KEY_PARAMS_OD(com.tencent.qimei.a.b.a(5)),
        KEY_PARAMS_OS_VERSION("osVersion"),
        KEY_PARAMS_Q16(com.tencent.qimei.a.b.a(0)),
        KEY_PARAMS_Q36(com.tencent.qimei.a.b.a(1)),
        KEY_PARAMS_RESERVED("reserved"),
        KEY_PARAMS_SDKVERSION("sdkVersion"),
        KEY_PARAMS_TARGETSDKVERSION("targetSdkVersion"),
        KEY_PARAMS_TRUSTEDENV("audit"),
        KEY_PARAMS_USERID("userId"),
        KEY_PARAMS_DEVICE_TYPE("deviceType"),
        KEY_PARAMS_PACKAGE_ID("packageId"),
        KEY_PARAMS_SKDNAME("sdkName"),
        KEY_RESERVED_HARMONY("harmony"),
        KEY_RESERVED_CLONE("clone"),
        KEY_RESERVED_CONTAINE("containe"),
        KEY_RESERVED_OZ("oz"),
        KEY_RESERVED_OO("oo"),
        KEY_RESERVED_KELONG("kelong"),
        KEY_RESERVED_UPTIMES("uptimes"),
        KEY_RESERVED_USERS("multiUser"),
        KEY_RESERVED_BOD("bod"),
        KEY_RESERVED_BRD("brd"),
        KEY_RESERVED_DV("dv"),
        KEY_RESERVED_FAL("firstLevel"),
        KEY_RESERVED_MT("manufact"),
        KEY_RESERVED_NAME("name"),
        KEY_RESERVED_HOST("host"),
        KEY_RESERVED_KL("kernel");

        public String W;

        a(String str) {
            this.W = str;
        }
    }

    public String a(String str, String str2) {
        if (!str.isEmpty()) {
            return str + "/android";
        }
        if (str2.isEmpty()) {
            return com.tencent.qimei.e.a.a() + "/android";
        }
        return str2 + "/android";
    }

    public String a(String str, String str2, Qimei qimei, String str3) {
        return a(str, str2, qimei, System.currentTimeMillis(), str3);
    }

    /* compiled from: QimeiProtocol.java */
    public enum b {
        KEY_CODE("code"),
        KEY_DATA("data"),
        KEY_DATA_QM_16("q16"),
        KEY_DATA_QM_36("q36");

        public String f;

        b(String str) {
            this.f = str;
        }

        public String a(String str, b... bVarArr) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                for (b bVar : bVarArr) {
                    if (jSONObject == null) {
                        return "";
                    }
                    jSONObject = jSONObject.optJSONObject(bVar.f);
                }
                return jSONObject == null ? "" : jSONObject.optString(this.f);
            } catch (JSONException e2) {
                e2.printStackTrace();
                return "";
            }
        }

        public static Qimei a(String str) {
            return new Qimei(KEY_DATA_QM_16.a(str, new b[0]), KEY_DATA_QM_36.a(str, new b[0]));
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(20:2|3|(1:5)(1:41)|6|(1:8)(1:40)|9|(1:11)(1:39)|12|(11:38|16|(1:18)(1:35)|19|(1:21)(1:34)|22|23|24|25|26|27)|15|16|(0)(0)|19|(0)(0)|22|23|24|25|26|27) */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x019b, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x019d, code lost:
    
        r0.printStackTrace();
        r0 = "";
     */
    /* JADX WARN: Removed duplicated region for block: B:18:0x014b  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0169  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x016b A[Catch: JSONException -> 0x01b0, TryCatch #1 {JSONException -> 0x01b0, blocks: (B:3:0x0024, B:6:0x0079, B:9:0x00e0, B:12:0x00ef, B:16:0x0140, B:19:0x0151, B:22:0x0173, B:25:0x01a1, B:33:0x019d, B:34:0x016b, B:35:0x014d, B:36:0x0130, B:38:0x0138, B:39:0x00eb, B:40:0x00dc, B:41:0x0071, B:24:0x0196), top: B:2:0x0024, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x014d A[Catch: JSONException -> 0x01b0, TryCatch #1 {JSONException -> 0x01b0, blocks: (B:3:0x0024, B:6:0x0079, B:9:0x00e0, B:12:0x00ef, B:16:0x0140, B:19:0x0151, B:22:0x0173, B:25:0x01a1, B:33:0x019d, B:34:0x016b, B:35:0x014d, B:36:0x0130, B:38:0x0138, B:39:0x00eb, B:40:0x00dc, B:41:0x0071, B:24:0x0196), top: B:2:0x0024, inners: #0 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String a(java.lang.String r15, java.lang.String r16, com.tencent.qimei.sdk.Qimei r17, long r18, java.lang.String r20) {
        /*
            Method dump skipped, instructions count: 437
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.qimei.o.m.a(java.lang.String, java.lang.String, com.tencent.qimei.sdk.Qimei, long, java.lang.String):java.lang.String");
    }

    public void a(JSONObject jSONObject, long j, String str) throws JSONException {
        String a2 = com.tencent.qimei.j.a.a();
        Object b2 = com.tencent.qimei.j.a.b(jSONObject.optString(a.KEY_ENCRYPT_KEY.W) + jSONObject.optString(a.KEY_PARAMS.W) + j + a2 + com.tencent.qimei.c.c.j().s() + str);
        jSONObject.put(a.KEY_TIME.W, String.valueOf(j));
        jSONObject.put(a.KEY_NONCE.W, a2);
        jSONObject.put(a.KEY_SIGN.W, b2);
        jSONObject.put(a.KEY_EXTRA.W, str);
    }

    public final String a(com.tencent.qimei.c.c cVar, com.tencent.qimei.l.d dVar, com.tencent.qimei.q.a aVar, String str) {
        JSONObject jSONObject = new JSONObject();
        try {
            String str2 = "1";
            jSONObject.put(a.KEY_RESERVED_HARMONY.W, cVar.u() ? "1" : "0");
            jSONObject.put(a.KEY_RESERVED_CLONE.W, Process.myUid() / BuglyStrategy.a.MAX_USERDATA_VALUE_LENGTH != 0 ? "1" : "0");
            jSONObject.put(a.KEY_RESERVED_CONTAINE.W, cVar.f());
            String str3 = "";
            jSONObject.put(a.KEY_RESERVED_OZ.W, !com.tencent.qimei.v.d.a(dVar.b).f() ? "" : com.tencent.qimei.c.c.j().q());
            String str4 = a.KEY_RESERVED_OO.W;
            if (com.tencent.qimei.v.d.a(dVar.b).w()) {
                str3 = com.tencent.qimei.c.c.j().o();
            }
            jSONObject.put(str4, str3);
            String str5 = a.KEY_RESERVED_KELONG.W;
            if (!aVar.a()) {
                str2 = "0";
            }
            jSONObject.put(str5, str2);
            String a2 = new com.tencent.qimei.j.d(com.tencent.qimei.u.d.b().J(), str).a();
            String b2 = com.tencent.qimei.a.a.b(com.tencent.qimei.u.d.b().J());
            jSONObject.put(a.KEY_RESERVED_UPTIMES.W, a2);
            jSONObject.put(a.KEY_RESERVED_USERS.W, b2);
            jSONObject.put(a.KEY_RESERVED_BOD.W, U.a(com.tencent.qimei.a.b.a(12)));
            jSONObject.put(a.KEY_RESERVED_BRD.W, U.a(com.tencent.qimei.a.b.a(13)));
            jSONObject.put(a.KEY_RESERVED_DV.W, U.a(com.tencent.qimei.a.b.a(14)));
            jSONObject.put(a.KEY_RESERVED_FAL.W, U.a(com.tencent.qimei.a.b.a(15)));
            jSONObject.put(a.KEY_RESERVED_MT.W, U.a(com.tencent.qimei.a.b.a(16)));
            jSONObject.put(a.KEY_RESERVED_NAME.W, U.a(com.tencent.qimei.a.b.a(17)));
            jSONObject.put(a.KEY_RESERVED_HOST.W, U.a(com.tencent.qimei.a.b.a(18)));
            jSONObject.put(a.KEY_RESERVED_KL.W, com.tencent.qimei.c.c.j().t());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }
}
