package com.tencent.qimei.n;

/* compiled from: ReportRequestApi.java */
/* loaded from: classes.dex */
public enum e {
    REPORT_TYPE("type"),
    REPORT_DATA("params"),
    REPORT_DATA_Q16("q16"),
    REPORT_DATA_Q36("q36"),
    REPORT_DATA_IP("ip"),
    REPORT_PLATFORM_ID("platformId"),
    REPORT_AD(com.tencent.qimei.a.b.a(11)),
    REPORT_DATA_NET_TYPE("networkType"),
    REPORT_APPKEY("appKey"),
    REPORT_SDKVERSION("sdkVersion"),
    REPORT_QM_USED_TIME("p1"),
    REPORT_QM_LOCAL_USED_TIME("p2"),
    REPORT_OD_USED_TIME("p3"),
    REPORT_QM_INIT_TIME("p4"),
    REPORT_QM_ERROR_CODE("p1"),
    REPORT_QM_ERROR_DESC("p2"),
    REPORT_QM_CHANGE_OLD_Q16("p1"),
    REPORT_QM_CHANGE_OLD_Q36("p2"),
    REPORT_QM_CHANGE_NEW_Q16("p3"),
    REPORT_QM_CHANGE_NEW_Q36("p4"),
    REPORT_QM_FROM_BEACON("p5"),
    REPORT_CLONE_CACHE_Q16("p1"),
    REPORT_CLONE_CACHE_Q36("p2"),
    REPORT_CLONE_DETECT_TYPE("p3"),
    REPORT_CLONE_CACHE_NEW_Q16("p4"),
    REPORT_CLONE_CACHE_NEW_Q36("p5"),
    REPORT_CACHE("p1"),
    REPORT_JS_H5ID("hid"),
    REPORT_JS_UA("userAgent"),
    REPORT_JS_BROWSER_TYPE("browser"),
    REPORT_JS_VERSION("version"),
    REPORT_COLLECT_RATE_Q16("p1"),
    REPORT_COLLECT_RATE_Q36("p2"),
    REPORT_COLLECT_RATE_DELAY("p3"),
    REPORT_STARTUP_DURAtION("p4");

    public String K;

    e(String str) {
        this.K = str;
    }
}
