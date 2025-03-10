package com.tencent.qcloud.core.auth;

import com.tencent.qcloud.core.common.QCloudClientException;

/* loaded from: classes.dex */
public interface QCloudCredentialProvider {
    QCloudCredentials getCredentials() throws QCloudClientException;
}
