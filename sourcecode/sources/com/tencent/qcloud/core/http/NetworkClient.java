package com.tencent.qcloud.core.http;

import com.tencent.qcloud.core.http.QCloudHttpClient;
import com.tencent.qcloud.core.task.RetryStrategy;
import javax.net.ssl.HostnameVerifier;
import okhttp3.Dns;

/* loaded from: classes.dex */
public abstract class NetworkClient {
    protected Dns dns;

    public abstract NetworkProxy getNetworkProxy();

    public void init(QCloudHttpClient.Builder builder, HostnameVerifier hostnameVerifier, Dns dns, HttpLogger httpLogger) {
        RetryStrategy retryStrategy = builder.retryStrategy;
        this.dns = dns;
    }
}
