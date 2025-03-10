package com.tencent.qcloud.core.common;

/* loaded from: classes.dex */
public interface QCloudResultListener<T> {
    void onFailure(QCloudClientException qCloudClientException, QCloudServiceException qCloudServiceException);

    void onSuccess(T t);
}
