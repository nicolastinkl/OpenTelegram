package com.xinstall.model;

/* loaded from: classes.dex */
public class XAppError {
    public static final String ERROR_GETWAKEUPPARAMS_REQUEST_FAIL = "1011";
    public static final String ERROR_GETWAKEUP_TOO_OFTEN = "1010";
    public static final String ERROR_NOT_XINSTALL_WAKEUP = "1013";
    public static final String ERROR_NO_HAS_ACTIVITY = "1007";
    public static final String ERROR_NO_WAKEUP = "1009";
    public static final String ERROR_REPEAT_GETPARAMS = "1012";
    public static final String ERROR_SCHEME_NO_URL = "1014";
    public static final String ERROR_UNKNOWN_ACTION = "1008";
    public static final String ERROR_UN_INIT = "1006";
    public static final String INIT_FAIL = "1000";
    public static final String JSON_EXCEPTION = "1005";
    public static final String NO_PERMISSION = "1004";
    public static final String REQUEST_FAIL = "1001";
    public static final String TIMEOUT = "1003";
    private String errorCode;
    private String errorMsg;

    public XAppError() {
    }

    public XAppError(String str, String str2) {
        this.errorCode = str;
        this.errorMsg = str2;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorCode(String str) {
        this.errorCode = str;
    }

    public void setErrorMsg(String str) {
        this.errorMsg = str;
    }

    public String toString() {
        return "Error{errorCode=" + this.errorCode + ", errorMsg='" + this.errorMsg + "'}";
    }
}
