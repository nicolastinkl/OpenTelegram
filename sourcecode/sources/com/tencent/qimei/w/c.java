package com.tencent.qimei.w;

/* compiled from: StrategyProtocol.java */
/* loaded from: classes.dex */
public enum c {
    KEY_CIPHER_KEY("key"),
    KEY_PLATFORM_ID("platformId"),
    KEY_OS_VERSION("osVersion"),
    KEY_APP_VERSION("appVersion"),
    KEY_SDK_VERSION("sdkVersion"),
    KEY_AUDIT_VERSION("auditVersion"),
    KEY_APP_KEY("appKey"),
    KEY_CONFIG_VERSION("configVersion"),
    KEY_PACKAGE_NAME("packageName");

    public String k;

    c(String str) {
        this.k = str;
    }
}
