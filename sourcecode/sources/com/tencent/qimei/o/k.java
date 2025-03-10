package com.tencent.qimei.o;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.qimei.sdk.Qimei;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: QimeiComp.java */
/* loaded from: classes.dex */
public class k {
    public static final String a = com.tencent.qimei.a.b.a(2);
    public static final String b = com.tencent.qimei.a.b.a(3);
    public static final String c = com.tencent.qimei.a.b.a(4);

    public static synchronized Qimei a() {
        String a2;
        HashMap hashMap;
        synchronized (k.class) {
            Context J = com.tencent.qimei.u.d.b().J();
            if (J == null) {
                a2 = "";
            } else {
                com.tencent.qimei.i.e a3 = a(J);
                if (a3 != null) {
                    String str = (String) a3.a("Q_V3", "");
                    a3.a();
                    if (!TextUtils.isEmpty(str)) {
                        a2 = str;
                    }
                }
                com.tencent.qimei.i.b b2 = com.tencent.qimei.i.b.b();
                String str2 = a;
                String a4 = b2.a(str2, c, "");
                if (TextUtils.isEmpty(a4)) {
                    a4 = b2.a(b, "");
                }
                a2 = TextUtils.isEmpty(a4) ? b2.a(str2, "") : a4;
            }
            if (a2 != null && !a2.isEmpty()) {
                if (TextUtils.isEmpty(a2)) {
                    hashMap = null;
                } else {
                    hashMap = new HashMap(3);
                    try {
                        JSONObject jSONObject = new JSONObject(a2);
                        Iterator<String> keys = jSONObject.keys();
                        if (keys != null) {
                            while (keys.hasNext()) {
                                String next = keys.next();
                                hashMap.put(next, jSONObject.getString(next));
                            }
                        }
                    } catch (JSONException e) {
                        e.getMessage();
                        hashMap.put("A3", a2);
                    }
                }
                if (hashMap == null) {
                    return null;
                }
                String str3 = (String) hashMap.get("A3");
                String str4 = (String) hashMap.get("A153");
                if (TextUtils.isEmpty(str3) && TextUtils.isEmpty(str4)) {
                    return null;
                }
                Qimei qimei = new Qimei();
                if (!TextUtils.isEmpty(str3)) {
                    qimei.a(str3);
                }
                if (!TextUtils.isEmpty(str4)) {
                    qimei.b(str4);
                }
                return qimei;
            }
            return null;
        }
    }

    public static synchronized com.tencent.qimei.i.e a(Context context) {
        com.tencent.qimei.i.e eVar;
        synchronized (k.class) {
            eVar = null;
            try {
                eVar = com.tencent.qimei.i.e.a(context, "Q_V3");
            } catch (Exception e) {
                e.getMessage();
            }
        }
        return eVar;
    }
}
