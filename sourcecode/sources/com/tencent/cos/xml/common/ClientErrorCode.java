package com.tencent.cos.xml.common;

import org.telegram.messenger.OneUIUtilities;
import org.telegram.messenger.XiaomiUtilities;

/* loaded from: classes.dex */
public enum ClientErrorCode {
    UNKNOWN(-10000, "Unknown Error"),
    INVALID_ARGUMENT(10000, "InvalidArgument"),
    INVALID_CREDENTIALS(XiaomiUtilities.OP_WIFI_CHANGE, "InvalidCredentials"),
    BAD_REQUEST(XiaomiUtilities.OP_BLUETOOTH_CHANGE, "BadRequest"),
    SINK_SOURCE_NOT_FOUND(XiaomiUtilities.OP_DATA_CONNECT_CHANGE, "SinkSourceNotFound"),
    ETAG_NOT_FOUND(XiaomiUtilities.OP_SEND_MMS, "ETagNotFound"),
    INTERNAL_ERROR(20000, "InternalError"),
    SERVERERROR(20001, "ServerError"),
    IO_ERROR(20002, "IOError"),
    POOR_NETWORK(20003, "NetworkError"),
    NETWORK_NOT_CONNECTED(20004, "NetworkNotConnected"),
    USER_CANCELLED(30000, "UserCancelled"),
    ALREADY_FINISHED(30001, "AlreadyFinished"),
    DUPLICATE_TASK(30002, "DuplicateTask"),
    KMS_ERROR(OneUIUtilities.ONE_UI_4_0, "KMSError");

    private int code;
    private String errorMsg;

    ClientErrorCode(int i, String str) {
        this.code = i;
        this.errorMsg = str;
    }

    public static ClientErrorCode to(int i) {
        for (ClientErrorCode clientErrorCode : values()) {
            if (clientErrorCode.code == i) {
                return clientErrorCode;
            }
        }
        throw new IllegalArgumentException("not error code defined");
    }

    public void setErrorMsg(String str) {
        this.errorMsg = str;
    }

    public int getCode() {
        return this.code;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }
}
