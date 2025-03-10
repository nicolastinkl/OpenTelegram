package com.tencent.qcloud.core.auth;

import com.tencent.qcloud.core.common.QCloudClientException;
import com.tencent.qcloud.core.http.QCloudHttpRequest;

/* loaded from: classes.dex */
public interface QCloudSigner {
    void sign(QCloudHttpRequest qCloudHttpRequest, QCloudCredentials qCloudCredentials) throws QCloudClientException;
}
