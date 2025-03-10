package com.tencent.qcloud.core.auth;

import com.tencent.qcloud.core.http.HttpConfiguration;

/* loaded from: classes.dex */
public class SessionQCloudCredentials implements QCloudLifecycleCredentials, QCloudCredentials {
    private final long expiredTime;
    private final String secretId;
    private final String secretKey;
    private final long startTime;
    private final String token;

    private String getSignKey(String str, String str2) {
        byte[] hmacSha1 = Utils.hmacSha1(str2, str);
        if (hmacSha1 != null) {
            return new String(Utils.encodeHex(hmacSha1));
        }
        return null;
    }

    @Override // com.tencent.qcloud.core.auth.QCloudLifecycleCredentials
    public boolean isValid() {
        return HttpConfiguration.getDeviceTimeWithOffset() <= this.expiredTime - 60;
    }

    public String getToken() {
        return this.token;
    }

    @Override // com.tencent.qcloud.core.auth.QCloudLifecycleCredentials
    public String getKeyTime() {
        return Utils.handleTimeAccuracy(this.startTime) + ";" + Utils.handleTimeAccuracy(this.expiredTime);
    }

    @Override // com.tencent.qcloud.core.auth.QCloudCredentials
    public String getSecretId() {
        return this.secretId;
    }

    public String getSecretKey() {
        return this.secretKey;
    }

    @Override // com.tencent.qcloud.core.auth.QCloudLifecycleCredentials
    public String getSignKey() {
        return getSignKey(this.secretKey, getKeyTime());
    }
}
