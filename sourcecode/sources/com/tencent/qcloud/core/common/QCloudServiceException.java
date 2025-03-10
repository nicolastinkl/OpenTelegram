package com.tencent.qcloud.core.common;

/* loaded from: classes.dex */
public class QCloudServiceException extends Exception {
    public static final String ERR0R_REQUEST_IS_EXPIRED = "RequestIsExpired";
    public static final String ERR0R_REQUEST_TIME_TOO_SKEWED = "RequestTimeTooSkewed";
    private static final long serialVersionUID = 1;
    private String errorCode;
    private String errorMessage;
    private String requestId;
    private String serviceName;
    private int statusCode;

    public QCloudServiceException(String str) {
        super(str);
        this.errorMessage = str;
    }

    public QCloudServiceException(String str, Exception exc) {
        super(null, exc);
        this.errorMessage = str;
    }

    public void setRequestId(String str) {
        this.requestId = str;
    }

    public String getRequestId() {
        return this.requestId;
    }

    public void setServiceName(String str) {
        this.serviceName = str;
    }

    public String getServiceName() {
        return this.serviceName;
    }

    public QCloudServiceException setErrorCode(String str) {
        this.errorCode = str;
        return this;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setStatusCode(int i) {
        this.statusCode = i;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        return getErrorMessage() + " (Service: " + getServiceName() + "; Status Code: " + getStatusCode() + "; Error Code: " + getErrorCode() + "; Request ID: " + getRequestId() + ")";
    }

    public void setErrorMessage(String str) {
        this.errorMessage = str;
    }
}
