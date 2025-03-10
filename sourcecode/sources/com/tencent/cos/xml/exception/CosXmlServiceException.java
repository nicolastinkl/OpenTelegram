package com.tencent.cos.xml.exception;

import com.tencent.qcloud.core.common.QCloudServiceException;

/* loaded from: classes.dex */
public class CosXmlServiceException extends QCloudServiceException {
    private static final long serialVersionUID = 1;
    private String httpMsg;

    public CosXmlServiceException(String str) {
        super(null);
        this.httpMsg = str;
    }

    public CosXmlServiceException(String str, Exception exc) {
        super(str, exc);
    }

    public String getHttpMessage() {
        return this.httpMsg;
    }

    public CosXmlServiceException(QCloudServiceException qCloudServiceException) {
        super(null);
        setErrorCode(qCloudServiceException.getErrorCode());
        setErrorMessage(qCloudServiceException.getErrorMessage());
        setRequestId(qCloudServiceException.getRequestId());
        setServiceName(qCloudServiceException.getServiceName());
        setStatusCode(qCloudServiceException.getStatusCode());
    }

    @Override // com.tencent.qcloud.core.common.QCloudServiceException, java.lang.Throwable
    public String getMessage() {
        return getErrorMessage() + " (Service: " + getServiceName() + "; Status Code: " + getStatusCode() + "; Status Message: " + this.httpMsg + "; Error Code: " + getErrorCode() + "; Request ID: " + getRequestId() + ")";
    }

    @Override // java.lang.Throwable
    public String toString() {
        return "CosXmlServiceException{" + getMessage() + '}';
    }
}
