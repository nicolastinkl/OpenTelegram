package com.tencent.qcloud.core.auth;

import com.tencent.qcloud.core.common.QCloudClientException;

/* loaded from: classes.dex */
public interface ScopeLimitCredentialProvider extends QCloudCredentialProvider {
    SessionQCloudCredentials getCredentials(STSCredentialScope[] sTSCredentialScopeArr) throws QCloudClientException;
}
