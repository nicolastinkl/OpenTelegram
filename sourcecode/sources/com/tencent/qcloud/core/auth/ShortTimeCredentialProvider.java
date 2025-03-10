package com.tencent.qcloud.core.auth;

import com.tencent.qcloud.core.common.QCloudClientException;
import com.tencent.qcloud.core.http.HttpConfiguration;

/* loaded from: classes.dex */
public class ShortTimeCredentialProvider extends BasicLifecycleCredentialProvider {
    private long duration;
    private String secretId;
    private String secretKey;

    @Deprecated
    public ShortTimeCredentialProvider(String str, String str2, long j) {
        this.secretId = str;
        this.secretKey = str2;
        this.duration = j;
    }

    @Override // com.tencent.qcloud.core.auth.BasicLifecycleCredentialProvider
    protected QCloudLifecycleCredentials fetchNewCredentials() throws QCloudClientException {
        long deviceTimeWithOffset = HttpConfiguration.getDeviceTimeWithOffset();
        String str = deviceTimeWithOffset + ";" + (this.duration + deviceTimeWithOffset);
        return new BasicQCloudCredentials(this.secretId, this.secretKey, secretKey2SignKey(this.secretKey, str), str);
    }

    private String secretKey2SignKey(String str, String str2) {
        byte[] hmacSha1 = Utils.hmacSha1(str2, str);
        if (hmacSha1 != null) {
            return new String(Utils.encodeHex(hmacSha1));
        }
        return null;
    }
}
