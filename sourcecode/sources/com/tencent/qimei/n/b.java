package com.tencent.qimei.n;

/* compiled from: ReportEvent.java */
/* loaded from: classes.dex */
public class b {
    public static final String a = "b";

    public static void a(String str, String str2, String str3, String str4, String str5) {
        c a2 = i.a().a(e.REPORT_JS_VERSION.K, str2).a(e.REPORT_JS_BROWSER_TYPE.K, str3).a(e.REPORT_JS_H5ID.K, str4).a(e.REPORT_JS_UA.K, str5);
        a2.a = str;
        a2.c = "/bind";
        a2.a("j1");
        com.tencent.qimei.k.a.b(a, "H5ID has been reported over,h5id = %s", str4);
    }
}
