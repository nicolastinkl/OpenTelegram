package com.tencent.qcloud.core.auth;

import com.tencent.qcloud.core.http.HttpConfiguration;

/* loaded from: classes.dex */
public class BasicQCloudCredentials implements QCloudLifecycleCredentials, QCloudCredentials {
    private final String keyTime;
    private final String secretId;
    private final String secretKey;
    private final String signKey;

    public BasicQCloudCredentials(String str, String str2, String str3, String str4) {
        if (str == null) {
            throw new IllegalArgumentException("secretId cannot be null.");
        }
        if (str2 == null) {
            throw new IllegalArgumentException("secretKey cannot be null.");
        }
        if (str3 == null) {
            throw new IllegalArgumentException("signKey cannot be null.");
        }
        if (str4 == null) {
            throw new IllegalArgumentException("keyTime cannot be null.");
        }
        this.secretId = str;
        this.secretKey = str2;
        this.signKey = str3;
        this.keyTime = str4;
    }

    @Override // com.tencent.qcloud.core.auth.QCloudLifecycleCredentials
    public boolean isValid() {
        long deviceTimeWithOffset = HttpConfiguration.getDeviceTimeWithOffset();
        long[] parseKeyTimes = Utils.parseKeyTimes(this.keyTime);
        return deviceTimeWithOffset > parseKeyTimes[0] && deviceTimeWithOffset < parseKeyTimes[1] - 60;
    }

    @Override // com.tencent.qcloud.core.auth.QCloudLifecycleCredentials
    public String getKeyTime() {
        return this.keyTime;
    }

    @Override // com.tencent.qcloud.core.auth.QCloudLifecycleCredentials
    public String getSignKey() {
        return this.signKey;
    }

    @Override // com.tencent.qcloud.core.auth.QCloudCredentials
    public String getSecretId() {
        return this.secretId;
    }

    public String getSecretKey() {
        return this.secretKey;
    }
}
