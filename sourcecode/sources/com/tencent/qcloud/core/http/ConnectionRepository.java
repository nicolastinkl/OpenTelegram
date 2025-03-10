package com.tencent.qcloud.core.http;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.qcloud.core.util.ContextHolder;
import com.tencent.qcloud.core.util.QCloudUtils;
import j$.util.concurrent.ConcurrentHashMap;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import okhttp3.Dns;

/* loaded from: classes.dex */
public class ConnectionRepository {
    private static volatile ConnectionRepository instance;
    private LocalDnsCache localDnsCache = new LocalDnsCache(ContextHolder.getAppContext());
    private DnsFetcher dnsFetcher = new DnsFetcher();
    private Map<String, List<InetAddress>> dnsRecords = new ConcurrentHashMap();
    private Executor singleExecutor = Executors.newSingleThreadExecutor();

    interface AsyncExecuteCompleteListener {
        void onComplete();
    }

    public static ConnectionRepository getInstance() {
        if (instance == null) {
            synchronized (ConnectionRepository.class) {
                if (instance == null) {
                    instance = new ConnectionRepository();
                }
            }
        }
        return instance;
    }

    private ConnectionRepository() {
    }

    public void addPrefetchHosts(List<String> list) {
        this.dnsFetcher.addHosts(list);
    }

    public void init() {
        init(null);
    }

    public void insertDnsRecordCache(String str, List<InetAddress> list) {
        insertDnsRecordCache(str, list, null);
    }

    public List<InetAddress> getDnsRecord(String str) throws UnknownHostException {
        if (!this.dnsRecords.containsKey(str)) {
            throw new UnknownHostException(str);
        }
        return this.dnsRecords.get(str);
    }

    void init(final AsyncExecuteCompleteListener asyncExecuteCompleteListener) {
        this.singleExecutor.execute(new Runnable() { // from class: com.tencent.qcloud.core.http.ConnectionRepository.1
            @Override // java.lang.Runnable
            public void run() {
                ConnectionRepository connectionRepository = ConnectionRepository.this;
                connectionRepository.addDnsRecordsMap(connectionRepository.localDnsCache.loadFromLocal());
                ConnectionRepository connectionRepository2 = ConnectionRepository.this;
                connectionRepository2.addDnsRecordsMap(connectionRepository2.dnsFetcher.fetchAll());
                ConnectionRepository.this.localDnsCache.save2Local(ConnectionRepository.this.dnsRecords);
                AsyncExecuteCompleteListener asyncExecuteCompleteListener2 = asyncExecuteCompleteListener;
                if (asyncExecuteCompleteListener2 != null) {
                    asyncExecuteCompleteListener2.onComplete();
                }
            }
        });
    }

    void insertDnsRecordCache(final String str, final List<InetAddress> list, final AsyncExecuteCompleteListener asyncExecuteCompleteListener) {
        this.singleExecutor.execute(new Runnable() { // from class: com.tencent.qcloud.core.http.ConnectionRepository.2
            @Override // java.lang.Runnable
            public void run() {
                if (!ConnectionRepository.this.sameInetAddresses((List) ConnectionRepository.this.dnsRecords.get(str), list)) {
                    ConnectionRepository.this.dnsRecords.put(str, list);
                    ConnectionRepository.this.localDnsCache.save2Local(ConnectionRepository.this.dnsRecords);
                }
                AsyncExecuteCompleteListener asyncExecuteCompleteListener2 = asyncExecuteCompleteListener;
                if (asyncExecuteCompleteListener2 != null) {
                    asyncExecuteCompleteListener2.onComplete();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addDnsRecordsMap(Map<String, List<InetAddress>> map) {
        if (map != null) {
            this.dnsRecords.putAll(map);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean sameInetAddresses(List<InetAddress> list, List<InetAddress> list2) {
        if (list == null || list2 == null || list.size() != list2.size()) {
            return false;
        }
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).getHostAddress().equals(list2.get(i).getHostAddress())) {
                return false;
            }
        }
        return true;
    }

    static class LocalDnsCache {
        private String cacheFilePath;

        LocalDnsCache(Context context) {
            if (context != null) {
                this.cacheFilePath = context.getCacheDir().getAbsolutePath().concat("/cosSdkDnsCache.db");
            }
        }

        synchronized void save2Local(Map<String, List<InetAddress>> map) {
            if (this.cacheFilePath == null) {
                return;
            }
            QCloudUtils.writeToFile(this.cacheFilePath, QCloudUtils.toBytes(map));
        }

        synchronized Map<String, List<InetAddress>> loadFromLocal() {
            String str = this.cacheFilePath;
            if (str == null) {
                return null;
            }
            byte[] readBytesFromFile = QCloudUtils.readBytesFromFile(str);
            if (readBytesFromFile != null) {
                Object object = QCloudUtils.toObject(readBytesFromFile);
                if (object instanceof Map) {
                    return (Map) object;
                }
            }
            return null;
        }
    }

    static class DnsFetcher {
        private int maxRetry = 2;
        private List<String> hosts = new LinkedList();

        DnsFetcher() {
        }

        synchronized void addHosts(List<String> list) {
            this.hosts.addAll(list);
        }

        synchronized Map<String, List<InetAddress>> fetchAll() {
            HashMap hashMap;
            List<InetAddress> fetch;
            hashMap = new HashMap();
            Iterator it = new LinkedList(this.hosts).iterator();
            while (it.hasNext()) {
                String str = (String) it.next();
                if (!TextUtils.isEmpty(str) && (fetch = fetch(str, this.maxRetry)) != null) {
                    hashMap.put(str, fetch);
                }
            }
            return hashMap;
        }

        private List<InetAddress> fetch(String str, int i) {
            if (i < 0) {
                return null;
            }
            try {
                return Dns.SYSTEM.lookup(str);
            } catch (UnknownHostException e) {
                e.printStackTrace();
                return fetch(str, i - 1);
            }
        }
    }
}
