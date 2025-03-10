package com.tencent.qcloud.core.auth;

/* loaded from: classes.dex */
public class StaticCredentialProvider implements QCloudCredentialProvider {
    private QCloudCredentials mCredentials;

    public StaticCredentialProvider(QCloudCredentials qCloudCredentials) {
        this.mCredentials = qCloudCredentials;
    }

    @Override // com.tencent.qcloud.core.auth.QCloudCredentialProvider
    public QCloudCredentials getCredentials() {
        return this.mCredentials;
    }
}
