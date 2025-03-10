package com.tencent.qcloud.core.auth;

import android.text.TextUtils;

/* loaded from: classes.dex */
public class STSCredentialScope {
    public final String action;
    public final String bucket;
    public final String prefix;
    public final String region;

    public static STSCredentialScope[] toArray(STSCredentialScope... sTSCredentialScopeArr) {
        return sTSCredentialScopeArr;
    }

    static {
        new STSCredentialScope(null, null, null, null);
    }

    public STSCredentialScope(String str, String str2, String str3, String str4) {
        this.action = str;
        this.bucket = str2;
        this.region = str3;
        if (str4 != null && str4.charAt(0) == '/') {
            this.prefix = str4.substring(1);
        } else {
            this.prefix = str4;
        }
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof STSCredentialScope)) {
            return false;
        }
        STSCredentialScope sTSCredentialScope = (STSCredentialScope) obj;
        return TextUtils.equals(this.action, sTSCredentialScope.action) && TextUtils.equals(this.bucket, sTSCredentialScope.bucket) && TextUtils.equals(this.prefix, sTSCredentialScope.prefix) && TextUtils.equals(this.region, sTSCredentialScope.region);
    }

    public STSCredentialScope[] toArray() {
        return toArray(this);
    }
}
