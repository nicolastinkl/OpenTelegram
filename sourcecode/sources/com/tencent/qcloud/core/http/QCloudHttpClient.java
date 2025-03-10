package com.tencent.qcloud.core.http;

import com.tencent.qcloud.core.auth.QCloudCredentialProvider;
import com.tencent.qcloud.core.logger.QCloudLogger;
import com.tencent.qcloud.core.task.QCloudTask;
import com.tencent.qcloud.core.task.RetryStrategy;
import com.tencent.qcloud.core.task.TaskManager;
import j$.util.concurrent.ConcurrentHashMap;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import okhttp3.Call;
import okhttp3.Dns;
import okhttp3.EventListener;
import okhttp3.OkHttpClient;

/* loaded from: classes.dex */
public final class QCloudHttpClient {
    private static volatile QCloudHttpClient gDefault;
    private static Map<Integer, NetworkClient> networkClientMap = new ConcurrentHashMap(2);
    private final ConnectionRepository connectionRepository;
    private boolean dnsCache;
    private final Map<String, List<InetAddress>> dnsMap;
    private final HttpLogger httpLogger;
    private Dns mDns;
    private HostnameVerifier mHostnameVerifier;
    private String networkClientType;
    private final TaskManager taskManager;
    private final Set<String> verifiedHost;

    public static QCloudHttpClient getDefault() {
        if (gDefault == null) {
            synchronized (QCloudHttpClient.class) {
                if (gDefault == null) {
                    gDefault = new Builder().build();
                }
            }
        }
        return gDefault;
    }

    public void addVerifiedHost(String str) {
        if (str != null) {
            this.verifiedHost.add(str);
        }
    }

    public void addDnsRecord(String str, String[] strArr) throws UnknownHostException {
        if (strArr.length > 0) {
            ArrayList arrayList = new ArrayList(strArr.length);
            for (String str2 : strArr) {
                arrayList.add(InetAddress.getByName(str2));
            }
            this.dnsMap.put(str, arrayList);
        }
    }

    public void setDebuggable(boolean z) {
        this.httpLogger.setDebug(z);
    }

    private QCloudHttpClient(Builder builder) {
        this.networkClientType = OkHttpClientImpl.class.getName();
        this.dnsCache = true;
        this.mHostnameVerifier = new HostnameVerifier() { // from class: com.tencent.qcloud.core.http.QCloudHttpClient.1
            @Override // javax.net.ssl.HostnameVerifier
            public boolean verify(String str, SSLSession sSLSession) {
                if (QCloudHttpClient.this.verifiedHost.size() > 0) {
                    Iterator it = QCloudHttpClient.this.verifiedHost.iterator();
                    while (it.hasNext()) {
                        if (HttpsURLConnection.getDefaultHostnameVerifier().verify((String) it.next(), sSLSession)) {
                            return true;
                        }
                    }
                }
                return HttpsURLConnection.getDefaultHostnameVerifier().verify(str, sSLSession);
            }
        };
        this.mDns = new Dns() { // from class: com.tencent.qcloud.core.http.QCloudHttpClient.2
            @Override // okhttp3.Dns
            public List<InetAddress> lookup(String str) throws UnknownHostException {
                List<InetAddress> list = QCloudHttpClient.this.dnsMap.containsKey(str) ? (List) QCloudHttpClient.this.dnsMap.get(str) : null;
                if (list == null) {
                    try {
                        list = Dns.SYSTEM.lookup(str);
                    } catch (UnknownHostException unused) {
                        QCloudLogger.w("QCloudHttp", "system dns failed, retry cache dns records.", new Object[0]);
                    }
                }
                if (list == null && !QCloudHttpClient.this.dnsCache) {
                    throw new UnknownHostException("can not resolve host name " + str);
                }
                if (list == null) {
                    try {
                        list = QCloudHttpClient.this.connectionRepository.getDnsRecord(str);
                    } catch (UnknownHostException unused2) {
                        QCloudLogger.w("QCloudHttp", "Not found dns in cache records.", new Object[0]);
                    }
                }
                if (list != null) {
                    ConnectionRepository.getInstance().insertDnsRecordCache(str, list);
                    return list;
                }
                throw new UnknownHostException(str);
            }
        };
        new EventListener.Factory(this) { // from class: com.tencent.qcloud.core.http.QCloudHttpClient.3
            @Override // okhttp3.EventListener.Factory
            public EventListener create(Call call) {
                return new CallMetricsListener(call);
            }
        };
        this.verifiedHost = new HashSet(5);
        this.dnsMap = new ConcurrentHashMap(3);
        this.taskManager = TaskManager.getInstance();
        ConnectionRepository connectionRepository = ConnectionRepository.getInstance();
        this.connectionRepository = connectionRepository;
        HttpLogger httpLogger = new HttpLogger(false);
        this.httpLogger = httpLogger;
        setDebuggable(false);
        NetworkClient networkClient = builder.networkClient;
        networkClient = networkClient == null ? new OkHttpClientImpl() : networkClient;
        String name = networkClient.getClass().getName();
        this.networkClientType = name;
        int hashCode = name.hashCode();
        if (!networkClientMap.containsKey(Integer.valueOf(hashCode))) {
            networkClient.init(builder, hostnameVerifier(), this.mDns, httpLogger);
            networkClientMap.put(Integer.valueOf(hashCode), networkClient);
        }
        connectionRepository.addPrefetchHosts(builder.prefetchHost);
        connectionRepository.init();
    }

    public void setNetworkClientType(Builder builder) {
        NetworkClient networkClient = builder.networkClient;
        if (networkClient != null) {
            String name = networkClient.getClass().getName();
            int hashCode = name.hashCode();
            if (!networkClientMap.containsKey(Integer.valueOf(hashCode))) {
                networkClient.init(builder, hostnameVerifier(), this.mDns, this.httpLogger);
                networkClientMap.put(Integer.valueOf(hashCode), networkClient);
            }
            this.networkClientType = name;
        }
    }

    public List<HttpTask> getTasksByTag(String str) {
        ArrayList arrayList = new ArrayList();
        if (str == null) {
            return arrayList;
        }
        for (QCloudTask qCloudTask : this.taskManager.snapshot()) {
            if ((qCloudTask instanceof HttpTask) && str.equals(qCloudTask.getTag())) {
                arrayList.add((HttpTask) qCloudTask);
            }
        }
        return arrayList;
    }

    public <T> HttpTask<T> resolveRequest(HttpRequest<T> httpRequest) {
        return handleRequest(httpRequest, null);
    }

    public <T> HttpTask<T> resolveRequest(QCloudHttpRequest<T> qCloudHttpRequest, QCloudCredentialProvider qCloudCredentialProvider) {
        return handleRequest(qCloudHttpRequest, qCloudCredentialProvider);
    }

    private HostnameVerifier hostnameVerifier() {
        return this.mHostnameVerifier;
    }

    private <T> HttpTask<T> handleRequest(HttpRequest<T> httpRequest, QCloudCredentialProvider qCloudCredentialProvider) {
        return new HttpTask<>(httpRequest, qCloudCredentialProvider, networkClientMap.get(Integer.valueOf(this.networkClientType.hashCode())));
    }

    public static final class Builder {
        OkHttpClient.Builder mBuilder;
        NetworkClient networkClient;
        QCloudHttpRetryHandler qCloudHttpRetryHandler;
        RetryStrategy retryStrategy;
        int connectionTimeout = 15000;
        int socketTimeout = 30000;
        boolean enableDebugLog = false;
        List<String> prefetchHost = new LinkedList();

        public Builder dnsCache(boolean z) {
            return this;
        }

        public Builder setConnectionTimeout(int i) {
            if (i < 3000) {
                throw new IllegalArgumentException("connection timeout must be larger than 3 seconds.");
            }
            this.connectionTimeout = i;
            return this;
        }

        public Builder setSocketTimeout(int i) {
            if (i < 3000) {
                throw new IllegalArgumentException("socket timeout must be larger than 3 seconds.");
            }
            this.socketTimeout = i;
            return this;
        }

        public Builder setRetryStrategy(RetryStrategy retryStrategy) {
            this.retryStrategy = retryStrategy;
            return this;
        }

        public Builder setQCloudHttpRetryHandler(QCloudHttpRetryHandler qCloudHttpRetryHandler) {
            this.qCloudHttpRetryHandler = qCloudHttpRetryHandler;
            return this;
        }

        public Builder setNetworkClient(NetworkClient networkClient) {
            this.networkClient = networkClient;
            return this;
        }

        public Builder enableDebugLog(boolean z) {
            this.enableDebugLog = z;
            return this;
        }

        public Builder addPrefetchHost(String str) {
            this.prefetchHost.add(str);
            return this;
        }

        public QCloudHttpClient build() {
            if (this.retryStrategy == null) {
                this.retryStrategy = RetryStrategy.DEFAULT;
            }
            QCloudHttpRetryHandler qCloudHttpRetryHandler = this.qCloudHttpRetryHandler;
            if (qCloudHttpRetryHandler != null) {
                this.retryStrategy.setRetryHandler(qCloudHttpRetryHandler);
            }
            if (this.mBuilder == null) {
                this.mBuilder = new OkHttpClient.Builder();
            }
            return new QCloudHttpClient(this);
        }
    }
}
