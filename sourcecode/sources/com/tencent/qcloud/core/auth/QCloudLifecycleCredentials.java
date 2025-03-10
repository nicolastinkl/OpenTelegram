package com.tencent.qcloud.core.auth;

/* loaded from: classes.dex */
public interface QCloudLifecycleCredentials extends QCloudCredentials {
    String getKeyTime();

    String getSignKey();

    boolean isValid();
}
