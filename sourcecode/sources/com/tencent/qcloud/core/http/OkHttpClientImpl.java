package com.tencent.qcloud.core.http;

import com.tencent.qcloud.core.http.HttpLoggingInterceptor;
import com.tencent.qcloud.core.http.QCloudHttpClient;
import com.tencent.qcloud.core.http.interceptor.HttpMetricsInterceptor;
import com.tencent.qcloud.core.http.interceptor.RetryInterceptor;
import com.tencent.qcloud.core.http.interceptor.TrafficControlInterceptor;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import okhttp3.Call;
import okhttp3.Dns;
import okhttp3.EventListener;
import okhttp3.OkHttpClient;

/* loaded from: classes.dex */
public class OkHttpClientImpl extends NetworkClient {
    private EventListener.Factory mEventListenerFactory = new EventListener.Factory(this) { // from class: com.tencent.qcloud.core.http.OkHttpClientImpl.1
        @Override // okhttp3.EventListener.Factory
        public EventListener create(Call call) {
            return new CallMetricsListener(call);
        }
    };
    private OkHttpClient okHttpClient;

    @Override // com.tencent.qcloud.core.http.NetworkClient
    public void init(QCloudHttpClient.Builder builder, HostnameVerifier hostnameVerifier, Dns dns, HttpLogger httpLogger) {
        super.init(builder, hostnameVerifier, dns, httpLogger);
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(httpLogger);
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        OkHttpClient.Builder dns2 = builder.mBuilder.followRedirects(true).followSslRedirects(true).hostnameVerifier(hostnameVerifier).dns(dns);
        long j = builder.connectionTimeout;
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        this.okHttpClient = dns2.connectTimeout(j, timeUnit).readTimeout(builder.socketTimeout, timeUnit).writeTimeout(builder.socketTimeout, timeUnit).eventListenerFactory(this.mEventListenerFactory).addNetworkInterceptor(new HttpMetricsInterceptor()).addInterceptor(httpLoggingInterceptor).addInterceptor(new RetryInterceptor(builder.retryStrategy)).addInterceptor(new TrafficControlInterceptor()).build();
    }

    @Override // com.tencent.qcloud.core.http.NetworkClient
    public NetworkProxy getNetworkProxy() {
        return new OkHttpProxy(this.okHttpClient);
    }
}
