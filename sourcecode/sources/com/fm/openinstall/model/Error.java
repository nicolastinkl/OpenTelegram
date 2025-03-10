package com.fm.openinstall.model;

import android.text.TextUtils;
import io.openinstall.sdk.ew;

/* loaded from: classes.dex */
public final class Error {
    public static final int INIT_ERROR = -12;
    public static final int INVALID_INTENT = -7;
    public static final int NOT_INIT = -8;
    public static final int REQUEST_ERROR = -2;
    public static final int REQUEST_FAIL = -1;
    public static final int TIMEOUT = -4;
    private int a;
    private String b;

    public Error() {
    }

    public Error(int i, String str) {
        this.a = i;
        this.b = str;
    }

    public static Error fromInner(ew.a aVar) {
        if (aVar == null) {
            return null;
        }
        String str = aVar.j;
        if (!TextUtils.isEmpty(aVar.l)) {
            str = str + "：" + aVar.l;
        }
        return new Error(aVar.i, str);
    }

    public int getErrorCode() {
        return this.a;
    }

    public String getErrorMsg() {
        return this.b;
    }

    public boolean shouldRetry() {
        return getErrorCode() == -1 || getErrorCode() == -4 || getErrorCode() == -8;
    }

    public String toString() {
        return "Error {code=" + this.a + ", msg='" + this.b + "'}";
    }
}
