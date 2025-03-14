package org.telegram.tgnet;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.SystemClock;
import android.text.TextUtils;
import cos.MyCOSService;
import java.io.File;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.telegram.messenger.AccountInstance;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.BaseController;
import org.telegram.messenger.BuildVars;
import org.telegram.messenger.EmuDetector;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.KeepAliveJob;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.SharedConfig;
import org.telegram.messenger.StatsController;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.Utilities;

/* loaded from: classes3.dex */
public class ConnectionsManager extends BaseController {
    private static final int CORE_POOL_SIZE;
    public static final int CPU_COUNT;
    public static final int ConnectionStateConnected = 3;
    public static final int ConnectionStateConnecting = 1;
    public static final int ConnectionStateConnectingToProxy = 4;
    public static final int ConnectionStateUpdating = 5;
    public static final int ConnectionStateWaitingForNetwork = 2;
    public static final int ConnectionTypeDownload = 2;
    public static final int ConnectionTypeDownload2 = 65538;
    public static final int ConnectionTypeGeneric = 1;
    public static final int ConnectionTypePush = 8;
    public static final int ConnectionTypeUpload = 4;
    public static final int DEFAULT_DATACENTER_ID = Integer.MAX_VALUE;
    public static final Executor DNS_THREAD_POOL_EXECUTOR;
    public static final int FileTypeAudio = 50331648;
    public static final int FileTypeFile = 67108864;
    public static final int FileTypePhoto = 16777216;
    public static final int FileTypeVideo = 33554432;
    private static final ConnectionsManager[] Instance;
    private static final int KEEP_ALIVE_SECONDS = 30;
    private static final int MAXIMUM_POOL_SIZE;
    public static final int RequestFlagCanCompress = 4;
    public static final int RequestFlagDoNotWaitFloodWait = 1024;
    public static final int RequestFlagEnableUnauthorized = 1;
    public static final int RequestFlagFailOnServerErrors = 2;
    public static final int RequestFlagForceDownload = 32;
    public static final int RequestFlagInvokeAfter = 64;
    public static final int RequestFlagNeedQuickAck = 128;
    public static final int RequestFlagTryDifferentDc = 16;
    public static final int RequestFlagWithoutLogin = 8;
    public static final byte USE_IPV4_IPV6_RANDOM = 2;
    public static final byte USE_IPV4_ONLY = 0;
    public static final byte USE_IPV6_ONLY = 1;
    public static String connectedCurrentIp;
    public static int connectedCurrentPort;
    private static AsyncTask currentTask;
    private static HashMap<String, ResolvedDomain> dnsCache;
    private static int lastClassGuid;
    private static long lastDnsRequestTime;
    private static HashMap<String, ResolveHostByNameTask> resolvingHostnameTasks = new HashMap<>();
    private static final BlockingQueue<Runnable> sPoolWorkQueue;
    private static final ThreadFactory sThreadFactory;
    private boolean appPaused;
    private int appResumeCount;
    private int connectionState;
    private boolean forceTryIpV6;
    private boolean isUpdating;
    private long lastPauseTime;
    private AtomicInteger lastRequestToken;
    private long requestTime;

    public static native void native_applyDatacenterAddress(int i, int i2, String str, int i3);

    public static native void native_applyDatacenterAddresses(int i, int i2, String[] strArr, int[] iArr);

    public static native void native_applyDnsConfig(int i, long j, String str, int i2);

    public static native void native_bindRequestToGuid(int i, int i2, int i3);

    public static native void native_cancelRequest(int i, int i2, boolean z);

    public static native void native_cancelRequestsForGuid(int i, int i2);

    public static native long native_checkProxy(int i, String str, int i2, String str2, String str3, String str4, RequestTimeDelegate requestTimeDelegate);

    public static native void native_cleanUp(int i, boolean z);

    public static native String native_decryptHex(String str);

    public static native int native_getConnectionState(int i);

    public static native int native_getCurrentDatacenterId(int i);

    public static native int native_getCurrentTime(int i);

    public static native long native_getCurrentTimeMillis(int i);

    public static native int native_getTimeDifference(int i);

    public static native void native_init(int i, int i2, int i3, int i4, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, int i5, long j, boolean z, boolean z2, int i6, int i7);

    public static native void native_initDatacenterAddresses(int i, String[] strArr, int[] iArr);

    public static native int native_isTestBackend(int i);

    public static native void native_onHostNameResolved(String str, long j, String str2);

    public static native void native_pauseNetwork(int i);

    public static native void native_resumeNetwork(int i, boolean z);

    public static native void native_seSystemLangCode(int i, String str);

    public static native void native_sendRequest(int i, long j, RequestDelegateInternal requestDelegateInternal, QuickAckDelegate quickAckDelegate, WriteToSocketDelegate writeToSocketDelegate, int i2, int i3, int i4, boolean z, int i5, String str);

    public static native void native_setIpStrategy(int i, byte b);

    public static native void native_setJava(boolean z);

    public static native void native_setLangCode(int i, String str);

    public static native void native_setNetworkAvailable(int i, boolean z, int i2, boolean z2);

    public static native void native_setProxySettings(int i, String str, int i2, String str2, String str3, String str4);

    public static native void native_setPushConnectionEnabled(int i, boolean z);

    public static native void native_setRegId(int i, String str);

    public static native void native_setSystemLangCode(int i, String str);

    public static native void native_setUserId(int i, long j);

    public static native void native_switchBackend(int i, boolean z);

    public static native void native_switchNextDownloadLine(int i);

    public static native void native_switchNextLine(int i);

    public static native void native_updateDcSettings(int i);

    static {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        CPU_COUNT = availableProcessors;
        int max = Math.max(2, Math.min(availableProcessors - 1, 4));
        CORE_POOL_SIZE = max;
        int i = (availableProcessors * 2) + 1;
        MAXIMUM_POOL_SIZE = i;
        LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue(128);
        sPoolWorkQueue = linkedBlockingQueue;
        ThreadFactory threadFactory = new ThreadFactory() { // from class: org.telegram.tgnet.ConnectionsManager.1
            private final AtomicInteger mCount = new AtomicInteger(1);

            @Override // java.util.concurrent.ThreadFactory
            public Thread newThread(Runnable runnable) {
                return new Thread(runnable, "DnsAsyncTask #" + this.mCount.getAndIncrement());
            }
        };
        sThreadFactory = threadFactory;
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(max, i, 30L, TimeUnit.SECONDS, linkedBlockingQueue, threadFactory);
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        DNS_THREAD_POOL_EXECUTOR = threadPoolExecutor;
        dnsCache = new HashMap<>();
        lastClassGuid = 1;
        Instance = new ConnectionsManager[1];
        connectedCurrentIp = "";
        connectedCurrentPort = 0;
    }

    public void setForceTryIpV6(boolean z) {
        if (this.forceTryIpV6 != z) {
            this.forceTryIpV6 = z;
            checkConnection();
        }
    }

    private static class ResolvedDomain {
        public ArrayList<String> addresses;
        long ttl;

        public ResolvedDomain(ArrayList<String> arrayList, long j) {
            this.addresses = arrayList;
            this.ttl = j;
        }

        public String getAddress() {
            ArrayList<String> arrayList = this.addresses;
            return arrayList.get(Utilities.random.nextInt(arrayList.size()));
        }
    }

    public static ConnectionsManager getInstance(int i) {
        ConnectionsManager[] connectionsManagerArr = Instance;
        ConnectionsManager connectionsManager = connectionsManagerArr[i];
        if (connectionsManager == null) {
            synchronized (ConnectionsManager.class) {
                connectionsManager = connectionsManagerArr[i];
                if (connectionsManager == null) {
                    connectionsManager = new ConnectionsManager(i);
                    connectionsManagerArr[i] = connectionsManager;
                }
            }
        }
        return connectionsManager;
    }

    public ConnectionsManager(int i) {
        super(i);
        String str;
        String str2;
        String str3;
        String str4;
        String str5;
        SharedPreferences sharedPreferences;
        this.lastPauseTime = System.currentTimeMillis();
        this.appPaused = true;
        this.lastRequestToken = new AtomicInteger(1);
        this.requestTime = 0L;
        this.connectionState = native_getConnectionState(this.currentAccount);
        File filesDirFixed = ApplicationLoader.getFilesDirFixed();
        if (i != 0) {
            File file = new File(filesDirFixed, "account" + i);
            file.mkdirs();
            filesDirFixed = file;
        }
        String file2 = filesDirFixed.toString();
        boolean isPushConnectionEnabled = isPushConnectionEnabled();
        try {
            str5 = LocaleController.getSystemLocaleStringIso639().toLowerCase();
            String lowerCase = LocaleController.getLocaleStringIso639().toLowerCase();
            str3 = Build.MANUFACTURER + Build.MODEL;
            PackageInfo packageInfo = ApplicationLoader.applicationContext.getPackageManager().getPackageInfo(ApplicationLoader.applicationContext.getPackageName(), 0);
            String str6 = packageInfo.versionName + " (" + packageInfo.versionCode + ")";
            if (BuildVars.DEBUG_PRIVATE_VERSION) {
                str6 = str6 + " pbeta";
            } else if (BuildVars.DEBUG_VERSION) {
                str6 = str6 + " beta";
            }
            str = "SDK " + Build.VERSION.SDK_INT;
            String str7 = str6;
            str4 = lowerCase;
            str2 = str7;
        } catch (Exception unused) {
            str = "SDK " + Build.VERSION.SDK_INT;
            str2 = "App version unknown";
            str3 = "Android unknown";
            str4 = "";
            str5 = "en";
        }
        String str8 = str5.trim().length() == 0 ? "en" : str5;
        String str9 = str3.trim().length() == 0 ? "Android unknown" : str3;
        str2 = str2.trim().length() == 0 ? "App version unknown" : str2;
        String str10 = str.trim().length() == 0 ? "SDK Unknown" : str;
        getUserConfig().loadConfig();
        String regId = getRegId();
        String certificateSHA256Fingerprint = AndroidUtilities.getCertificateSHA256Fingerprint();
        int rawOffset = (TimeZone.getDefault().getRawOffset() + TimeZone.getDefault().getDSTSavings()) / 1000;
        if (this.currentAccount == 0) {
            sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0);
        } else {
            sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig" + this.currentAccount, 0);
        }
        this.forceTryIpV6 = sharedPreferences.getBoolean("forceTryIpV6", false);
        init(BuildVars.BUILD_VERSION, 158, BuildVars.APP_ID, str9, str10, str2, str4, str8, file2, FileLog.getNetworkLogPath(), regId, certificateSHA256Fingerprint, rawOffset, getUserConfig().getClientUserId(), isPushConnectionEnabled);
    }

    private String getRegId() {
        String str = SharedConfig.pushString;
        if (!TextUtils.isEmpty(str) && SharedConfig.pushType == 13) {
            str = "huawei://" + str;
        }
        if (TextUtils.isEmpty(str) && !TextUtils.isEmpty(SharedConfig.pushStringStatus)) {
            str = SharedConfig.pushStringStatus;
        }
        if (!TextUtils.isEmpty(str)) {
            return str;
        }
        String str2 = "__" + (SharedConfig.pushType == 2 ? "FIREBASE" : "HUAWEI") + "_GENERATING_SINCE_" + getCurrentTime() + "__";
        SharedConfig.pushStringStatus = str2;
        return str2;
    }

    public boolean isPushConnectionEnabled() {
        SharedPreferences globalNotificationsSettings = MessagesController.getGlobalNotificationsSettings();
        if (globalNotificationsSettings.contains("pushConnection")) {
            return globalNotificationsSettings.getBoolean("pushConnection", true);
        }
        return MessagesController.getMainSettings(UserConfig.selectedAccount).getBoolean("backgroundConnection", false);
    }

    public long getCurrentTimeMillis() {
        return native_getCurrentTimeMillis(this.currentAccount);
    }

    public int getCurrentTime() {
        return native_getCurrentTime(this.currentAccount);
    }

    public int getCurrentDatacenterId() {
        return native_getCurrentDatacenterId(this.currentAccount);
    }

    public int getTimeDifference() {
        return native_getTimeDifference(this.currentAccount);
    }

    public int sendRequest(TLObject tLObject, RequestDelegate requestDelegate) {
        return sendRequest(tLObject, requestDelegate, (QuickAckDelegate) null, 0);
    }

    public int sendRequest(TLObject tLObject, RequestDelegate requestDelegate, int i) {
        return sendRequest(tLObject, requestDelegate, null, null, null, i, Integer.MAX_VALUE, 1, true);
    }

    public int sendRequest(TLObject tLObject, RequestDelegate requestDelegate, int i, int i2) {
        return sendRequest(tLObject, requestDelegate, null, null, null, i, Integer.MAX_VALUE, i2, true);
    }

    public int sendRequest(TLObject tLObject, RequestDelegateTimestamp requestDelegateTimestamp, int i, int i2, int i3) {
        return sendRequest(tLObject, null, requestDelegateTimestamp, null, null, i, i3, i2, true);
    }

    public int sendRequest(TLObject tLObject, RequestDelegate requestDelegate, QuickAckDelegate quickAckDelegate, int i) {
        return sendRequest(tLObject, requestDelegate, null, quickAckDelegate, null, i, Integer.MAX_VALUE, 1, true);
    }

    public int sendRequest(TLObject tLObject, RequestDelegate requestDelegate, QuickAckDelegate quickAckDelegate, WriteToSocketDelegate writeToSocketDelegate, int i, int i2, int i3, boolean z) {
        return sendRequest(tLObject, requestDelegate, null, quickAckDelegate, writeToSocketDelegate, i, i2, i3, z);
    }

    public int sendRequestSync(TLObject tLObject, RequestDelegate requestDelegate, QuickAckDelegate quickAckDelegate, WriteToSocketDelegate writeToSocketDelegate, int i, int i2, int i3, boolean z) {
        int andIncrement = this.lastRequestToken.getAndIncrement();
        lambda$sendRequest$0(tLObject, requestDelegate, null, quickAckDelegate, writeToSocketDelegate, i, i2, i3, z, andIncrement);
        return andIncrement;
    }

    public int sendRequest(final TLObject tLObject, final RequestDelegate requestDelegate, final RequestDelegateTimestamp requestDelegateTimestamp, final QuickAckDelegate quickAckDelegate, final WriteToSocketDelegate writeToSocketDelegate, final int i, final int i2, final int i3, final boolean z) {
        final int andIncrement = this.lastRequestToken.getAndIncrement();
        Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.tgnet.ConnectionsManager$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                ConnectionsManager.this.lambda$sendRequest$0(tLObject, requestDelegate, requestDelegateTimestamp, quickAckDelegate, writeToSocketDelegate, i, i2, i3, z, andIncrement);
            }
        });
        return andIncrement;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: sendRequestInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$sendRequest$0(final TLObject tLObject, final RequestDelegate requestDelegate, final RequestDelegateTimestamp requestDelegateTimestamp, final QuickAckDelegate quickAckDelegate, final WriteToSocketDelegate writeToSocketDelegate, final int i, final int i2, final int i3, final boolean z, final int i4) {
        if (BuildVars.LOGS_ENABLED) {
            FileLog.d("send request " + tLObject + " with token = " + i4);
        }
        try {
            if (tLObject instanceof TLRPC$TL_messages_getStickerSet) {
                return;
            }
            NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(tLObject.getObjectSize());
            tLObject.serializeToStream(nativeByteBuffer);
            tLObject.freeResources();
            long j = 0;
            if (BuildVars.DEBUG_PRIVATE_VERSION && BuildVars.LOGS_ENABLED) {
                j = System.currentTimeMillis();
            }
            final long j2 = j;
            native_sendRequest(this.currentAccount, nativeByteBuffer.address, new RequestDelegateInternal() { // from class: org.telegram.tgnet.ConnectionsManager$$ExternalSyntheticLambda13
                @Override // org.telegram.tgnet.RequestDelegateInternal
                public final void run(long j3, int i5, String str, int i6, long j4, long j5) {
                    ConnectionsManager.this.lambda$sendRequestInternal$2(tLObject, requestDelegate, requestDelegateTimestamp, quickAckDelegate, writeToSocketDelegate, i, i2, i3, z, j2, i4, j3, i5, str, i6, j4, j5);
                }
            }, quickAckDelegate, writeToSocketDelegate, i, i2, i3, z, i4, tLObject.getClass().getName());
        } catch (Exception e) {
            FileLog.e(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendRequestInternal$2(TLObject tLObject, final RequestDelegate requestDelegate, final RequestDelegateTimestamp requestDelegateTimestamp, QuickAckDelegate quickAckDelegate, WriteToSocketDelegate writeToSocketDelegate, int i, int i2, int i3, boolean z, long j, int i4, long j2, int i5, String str, int i6, final long j3, long j4) {
        TLRPC$TL_error tLRPC$TL_error;
        TLObject tLObject2;
        try {
            if (j2 != 0) {
                NativeByteBuffer wrap = NativeByteBuffer.wrap(j2);
                wrap.reused = true;
                try {
                    tLObject2 = tLObject.deserializeResponse(wrap, wrap.readInt32(true), true);
                    tLRPC$TL_error = null;
                } catch (Exception e) {
                    if (BuildVars.DEBUG_PRIVATE_VERSION) {
                        throw e;
                    }
                    FileLog.fatal(e);
                    return;
                }
            } else if (str != null) {
                TLRPC$TL_error tLRPC$TL_error2 = new TLRPC$TL_error();
                tLRPC$TL_error2.code = i5;
                tLRPC$TL_error2.text = str;
                if (BuildVars.LOGS_ENABLED) {
                    FileLog.e(tLObject + " got error " + tLRPC$TL_error2.code + " " + tLRPC$TL_error2.text);
                }
                tLRPC$TL_error = tLRPC$TL_error2;
                tLObject2 = null;
            } else {
                tLRPC$TL_error = null;
                tLObject2 = null;
            }
            if (BuildVars.DEBUG_PRIVATE_VERSION && !getUserConfig().isClientActivated() && tLRPC$TL_error != null && tLRPC$TL_error.code == 400 && Objects.equals(tLRPC$TL_error.text, "CONNECTION_NOT_INITED")) {
                if (BuildVars.LOGS_ENABLED) {
                    FileLog.d("Cleanup keys for " + this.currentAccount + " because of CONNECTION_NOT_INITED");
                }
                cleanup(true);
                sendRequest(tLObject, requestDelegate, requestDelegateTimestamp, quickAckDelegate, writeToSocketDelegate, i, i2, i3, z);
                return;
            }
            if (tLObject2 != null) {
                tLObject2.networkType = i6;
            }
            if (BuildVars.LOGS_ENABLED) {
                FileLog.d("java received " + tLObject2 + " error = " + tLRPC$TL_error);
            }
            FileLog.dumpResponseAndRequest(tLObject, tLObject2, tLRPC$TL_error, j4, j, i4);
            final TLObject tLObject3 = tLObject2;
            final TLRPC$TL_error tLRPC$TL_error3 = tLRPC$TL_error;
            Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.tgnet.ConnectionsManager$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    ConnectionsManager.lambda$sendRequestInternal$1(RequestDelegate.this, tLObject3, tLRPC$TL_error3, requestDelegateTimestamp, j3);
                }
            });
        } catch (Exception e2) {
            FileLog.e(e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$sendRequestInternal$1(RequestDelegate requestDelegate, TLObject tLObject, TLRPC$TL_error tLRPC$TL_error, RequestDelegateTimestamp requestDelegateTimestamp, long j) {
        if (requestDelegate != null) {
            requestDelegate.run(tLObject, tLRPC$TL_error);
        } else if (requestDelegateTimestamp != null) {
            requestDelegateTimestamp.run(tLObject, tLRPC$TL_error, j);
        }
        if (tLObject != null) {
            tLObject.freeResources();
        }
    }

    public void cancelRequest(final int i, final boolean z) {
        Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.tgnet.ConnectionsManager$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                ConnectionsManager.this.lambda$cancelRequest$3(i, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$cancelRequest$3(int i, boolean z) {
        native_cancelRequest(this.currentAccount, i, z);
    }

    public void cleanup(boolean z) {
        native_cleanUp(this.currentAccount, z);
    }

    public void cancelRequestsForGuid(final int i) {
        Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.tgnet.ConnectionsManager$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                ConnectionsManager.this.lambda$cancelRequestsForGuid$4(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$cancelRequestsForGuid$4(int i) {
        native_cancelRequestsForGuid(this.currentAccount, i);
    }

    public void bindRequestToGuid(int i, int i2) {
        native_bindRequestToGuid(this.currentAccount, i, i2);
    }

    public void applyDatacenterAddress(int i, String str, int i2) {
        native_applyDatacenterAddress(this.currentAccount, i, str, i2);
    }

    public void applyDatacenterAddresses(int i, String[] strArr, int[] iArr) {
        native_applyDatacenterAddresses(this.currentAccount, i, strArr, iArr);
    }

    public void initDatacenterAddresses(String[] strArr, int[] iArr) {
        native_initDatacenterAddresses(this.currentAccount, strArr, iArr);
    }

    public int getConnectionState() {
        int i = this.connectionState;
        if (i == 3 && this.isUpdating) {
            return 5;
        }
        return i;
    }

    public void setUserId(long j) {
        native_setUserId(this.currentAccount, j);
    }

    public void checkConnection() {
        byte ipStrategy = getIpStrategy();
        if (BuildVars.LOGS_ENABLED) {
            FileLog.d("selected ip strategy " + ((int) ipStrategy));
        }
        native_setIpStrategy(this.currentAccount, ipStrategy);
        native_setNetworkAvailable(this.currentAccount, ApplicationLoader.isNetworkOnline(), ApplicationLoader.getCurrentNetworkType(), ApplicationLoader.isConnectionSlow());
    }

    public void setPushConnectionEnabled(boolean z) {
        native_setPushConnectionEnabled(this.currentAccount, z);
    }

    public void init(int i, int i2, int i3, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, int i4, long j, boolean z) {
        String str10;
        String str11;
        SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0);
        String string = sharedPreferences.getString("proxy_ip", "");
        String string2 = sharedPreferences.getString("proxy_user", "");
        String string3 = sharedPreferences.getString("proxy_pass", "");
        String string4 = sharedPreferences.getString("proxy_secret", "");
        int i5 = sharedPreferences.getInt("proxy_port", 1080);
        if (sharedPreferences.getBoolean("proxy_enabled", false) && !TextUtils.isEmpty(string)) {
            native_setProxySettings(this.currentAccount, string, i5, string2, string3, string4);
        }
        try {
            str10 = ApplicationLoader.applicationContext.getPackageManager().getInstallerPackageName(ApplicationLoader.applicationContext.getPackageName());
        } catch (Throwable unused) {
            str10 = "";
        }
        String str12 = str10 == null ? "" : str10;
        try {
            str11 = ApplicationLoader.applicationContext.getPackageName();
        } catch (Throwable unused2) {
            str11 = "";
        }
        native_init(this.currentAccount, i, i2, i3, str, str2, str3, str4, str5, str6, str7, str8, str9, str12, str11 == null ? "" : str11, i4, j, z, ApplicationLoader.isNetworkOnline(), ApplicationLoader.getCurrentNetworkType(), SharedConfig.measureDevicePerformanceClass());
        checkConnection();
    }

    public static void setLangCode(String str) {
        String lowerCase = str.replace('_', '-').toLowerCase();
        for (int i = 0; i < 1; i++) {
            native_setLangCode(i, lowerCase);
        }
    }

    public static void setRegId(String str, int i, String str2) {
        if (!TextUtils.isEmpty(str) && i == 13) {
            str = "huawei://" + str;
        }
        if (!TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            str2 = str;
        }
        if (TextUtils.isEmpty(str2)) {
            str2 = "__" + (i == 2 ? "FIREBASE" : "HUAWEI") + "_GENERATING_SINCE_" + getInstance(0).getCurrentTime() + "__";
            SharedConfig.pushStringStatus = str2;
        }
        for (int i2 = 0; i2 < 1; i2++) {
            native_setRegId(i2, str2);
        }
    }

    public static void setSystemLangCode(String str) {
        String lowerCase = str.replace('_', '-').toLowerCase();
        for (int i = 0; i < 1; i++) {
            native_setSystemLangCode(i, lowerCase);
        }
    }

    public void switchBackend(boolean z) {
        MessagesController.getGlobalMainSettings().edit().remove("language_showed2").commit();
        native_switchBackend(this.currentAccount, z);
    }

    public boolean isTestBackend() {
        return native_isTestBackend(this.currentAccount) != 0;
    }

    public void resumeNetworkMaybe() {
        native_resumeNetwork(this.currentAccount, true);
    }

    public void updateDcSettings() {
        native_updateDcSettings(this.currentAccount);
    }

    public long getPauseTime() {
        return this.lastPauseTime;
    }

    public long checkProxy(String str, int i, String str2, String str3, String str4, RequestTimeDelegate requestTimeDelegate) {
        if (TextUtils.isEmpty(str)) {
            return 0L;
        }
        return native_checkProxy(this.currentAccount, str == null ? "" : str, i, str2 == null ? "" : str2, str3 == null ? "" : str3, str4 == null ? "" : str4, requestTimeDelegate);
    }

    public void setAppPaused(boolean z, boolean z2) {
        if (!z2) {
            this.appPaused = z;
            if (BuildVars.LOGS_ENABLED) {
                FileLog.d("app paused = " + z);
            }
            if (z) {
                this.appResumeCount--;
            } else {
                this.appResumeCount++;
            }
            if (BuildVars.LOGS_ENABLED) {
                FileLog.d("app resume count " + this.appResumeCount);
            }
            if (this.appResumeCount < 0) {
                this.appResumeCount = 0;
            }
        }
        if (this.appResumeCount == 0) {
            if (this.lastPauseTime == 0) {
                this.lastPauseTime = System.currentTimeMillis();
            }
            native_pauseNetwork(this.currentAccount);
        } else {
            if (this.appPaused) {
                return;
            }
            if (BuildVars.LOGS_ENABLED) {
                FileLog.d("reset app pause time");
            }
            if (this.lastPauseTime != 0 && System.currentTimeMillis() - this.lastPauseTime > 5000) {
                getContactsController().checkContacts();
            }
            this.lastPauseTime = 0L;
            native_resumeNetwork(this.currentAccount, false);
        }
    }

    public static void onUnparsedMessageReceived(long j, final int i, long j2) {
        try {
            NativeByteBuffer wrap = NativeByteBuffer.wrap(j);
            wrap.reused = true;
            int readInt32 = wrap.readInt32(true);
            final TLObject TLdeserialize = TLClassStore.Instance().TLdeserialize(wrap, readInt32, true);
            FileLog.dumpUnparsedMessage(TLdeserialize, j2);
            if (TLdeserialize instanceof TLRPC$Updates) {
                if (BuildVars.LOGS_ENABLED) {
                    FileLog.d("java received " + TLdeserialize);
                }
                KeepAliveJob.finishJob();
                Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.tgnet.ConnectionsManager$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        ConnectionsManager.lambda$onUnparsedMessageReceived$5(i, TLdeserialize);
                    }
                });
                return;
            }
            if (BuildVars.LOGS_ENABLED) {
                FileLog.d(String.format("java received unknown constructor 0x%x", Integer.valueOf(readInt32)));
            }
        } catch (Exception e) {
            FileLog.e(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onUnparsedMessageReceived$5(int i, TLObject tLObject) {
        AccountInstance.getInstance(i).getMessagesController().processUpdates((TLRPC$Updates) tLObject, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onUpdate$6(int i) {
        AccountInstance.getInstance(i).getMessagesController().updateTimerProc();
    }

    public static void onUpdate(final int i) {
        Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.tgnet.ConnectionsManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ConnectionsManager.lambda$onUpdate$6(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onSessionCreated$7(int i) {
        AccountInstance.getInstance(i).getMessagesController().getDifference();
    }

    public static void onSessionCreated(final int i) {
        Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.tgnet.ConnectionsManager$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                ConnectionsManager.lambda$onSessionCreated$7(i);
            }
        });
    }

    public static void onConnectionStateChanged(final int i, final int i2) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.tgnet.ConnectionsManager$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                ConnectionsManager.lambda$onConnectionStateChanged$8(i2, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onConnectionStateChanged$8(int i, int i2) {
        getInstance(i).connectionState = i2;
        AccountInstance.getInstance(i).getNotificationCenter().postNotificationName(NotificationCenter.didUpdateConnectionState, new Object[0]);
    }

    public static void onLogout(final int i) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.tgnet.ConnectionsManager$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                ConnectionsManager.lambda$onLogout$9(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onLogout$9(int i) {
        AccountInstance accountInstance = AccountInstance.getInstance(i);
        if (accountInstance.getUserConfig().getClientUserId() != 0) {
            accountInstance.getUserConfig().clearConfig();
            accountInstance.getMessagesController().performLogout(0);
        }
    }

    public static int getInitFlags() {
        if (!EmuDetector.with(ApplicationLoader.applicationContext).detect()) {
            return 0;
        }
        if (BuildVars.LOGS_ENABLED) {
            FileLog.d("detected emu");
        }
        return 1024;
    }

    public static void onBytesSent(int i, int i2, int i3) {
        try {
            AccountInstance.getInstance(i3).getStatsController().incrementSentBytesCount(i2, 6, i);
        } catch (Exception e) {
            FileLog.e(e);
        }
    }

    public static void onRequestNewServerIpAndPort(int i, final int i2) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.tgnet.ConnectionsManager.2
            @Override // java.lang.Runnable
            public void run() {
                if (BuildVars.isDownloadLineFromOnline) {
                    if (BuildVars.isOpenCloudDefense) {
                        MyCOSService.getInstance().connectProxy(i2);
                        return;
                    } else if (BuildVars.isDownloadLineFromDNS) {
                        MyCOSService.getInstance().downloadDNS(i2);
                        return;
                    } else {
                        MyCOSService.getInstance().downloadFile(i2);
                        return;
                    }
                }
                MyCOSService.getInstance().setDefaultLine(i2, true);
            }
        });
    }

    public static void updateCurrentIpAndPort(final String str, final int i, final int i2) {
        connectedCurrentIp = str;
        connectedCurrentPort = i;
        if (BuildVars.LOGS_ENABLED) {
            FileLog.d("updateCurrentIpAndPort: " + str + " - " + i);
        }
        getInstance(i2).setIsUpdating(false);
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.tgnet.ConnectionsManager.3
            @Override // java.lang.Runnable
            public void run() {
                if (!TextUtils.isEmpty(str) && i != 0 && BuildVars.isDownloadLineFromOnline) {
                    MyCOSService.getInstance().downloadData(i2);
                }
                AccountInstance.getInstance(i2).getNotificationCenter().postNotificationName(NotificationCenter.updateCurrentIpAndPort, new Object[0]);
            }
        });
    }

    public static int getSendNewProtocol() {
        return BuildVars.sendNewProtocol ? 1 : 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onProxyError$10() {
        NotificationCenter.getGlobalInstance().postNotificationName(NotificationCenter.needShowAlert, 3);
    }

    public static void onProxyError() {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.tgnet.ConnectionsManager$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                ConnectionsManager.lambda$onProxyError$10();
            }
        });
    }

    public static void getHostByName(final String str, final long j) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.tgnet.ConnectionsManager$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                ConnectionsManager.lambda$getHostByName$11(str, j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getHostByName$11(String str, long j) {
        ResolvedDomain resolvedDomain = dnsCache.get(str);
        if (resolvedDomain != null && SystemClock.elapsedRealtime() - resolvedDomain.ttl < 300000) {
            native_onHostNameResolved(str, j, resolvedDomain.getAddress());
            return;
        }
        ResolveHostByNameTask resolveHostByNameTask = resolvingHostnameTasks.get(str);
        if (resolveHostByNameTask == null) {
            resolveHostByNameTask = new ResolveHostByNameTask(str);
            try {
                resolveHostByNameTask.executeOnExecutor(DNS_THREAD_POOL_EXECUTOR, null, null, null);
                resolvingHostnameTasks.put(str, resolveHostByNameTask);
            } catch (Throwable th) {
                FileLog.e(th);
                native_onHostNameResolved(str, j, "");
                return;
            }
        }
        resolveHostByNameTask.addAddress(j);
    }

    public static void onBytesReceived(int i, int i2, int i3) {
        try {
            StatsController.getInstance(i3).incrementReceivedBytesCount(i2, 6, i);
        } catch (Exception e) {
            FileLog.e(e);
        }
    }

    public static void onUpdateConfig(long j, final int i) {
        try {
            NativeByteBuffer wrap = NativeByteBuffer.wrap(j);
            wrap.reused = true;
            final TLRPC$TL_config TLdeserialize = TLRPC$TL_config.TLdeserialize(wrap, wrap.readInt32(true), true);
            if (TLdeserialize != null) {
                Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.tgnet.ConnectionsManager$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        ConnectionsManager.lambda$onUpdateConfig$12(i, TLdeserialize);
                    }
                });
            }
        } catch (Exception e) {
            FileLog.e(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onUpdateConfig$12(int i, TLRPC$TL_config tLRPC$TL_config) {
        AccountInstance.getInstance(i).getMessagesController().updateConfig(tLRPC$TL_config);
    }

    public static void onInternalPushReceived(int i) {
        KeepAliveJob.startJob();
    }

    public static void setProxySettings(boolean z, String str, int i, String str2, String str3, String str4) {
        if (str == null) {
            str = "";
        }
        if (str2 == null) {
            str2 = "";
        }
        if (str3 == null) {
            str3 = "";
        }
        if (str4 == null) {
            str4 = "";
        }
        for (int i2 = 0; i2 < 1; i2++) {
            if (z && !TextUtils.isEmpty(str)) {
                native_setProxySettings(i2, str, i, str2, str3, str4);
            } else {
                native_setProxySettings(i2, "", 1080, "", "", "");
            }
            AccountInstance accountInstance = AccountInstance.getInstance(i2);
            if (accountInstance.getUserConfig().isClientActivated()) {
                accountInstance.getMessagesController().checkPromoInfo(true);
            }
        }
    }

    public String decryptHex(String str) {
        return native_decryptHex(str);
    }

    public void switchNextLine() {
        native_switchNextLine(this.currentAccount);
    }

    public void switchNextDownloadLine() {
        native_switchNextDownloadLine(this.currentAccount);
    }

    public static int generateClassGuid() {
        int i = lastClassGuid;
        lastClassGuid = i + 1;
        return i;
    }

    public void setIsUpdating(final boolean z) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.tgnet.ConnectionsManager$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                ConnectionsManager.this.lambda$setIsUpdating$13(z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setIsUpdating$13(boolean z) {
        if (this.isUpdating == z) {
            return;
        }
        this.isUpdating = z;
        if (this.connectionState == 3) {
            AccountInstance.getInstance(this.currentAccount).getNotificationCenter().postNotificationName(NotificationCenter.didUpdateConnectionState, new Object[0]);
        }
    }

    @SuppressLint({"NewApi"})
    protected byte getIpStrategy() {
        if (Build.VERSION.SDK_INT < 19) {
            return (byte) 0;
        }
        if (BuildVars.LOGS_ENABLED) {
            try {
                Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
                while (networkInterfaces.hasMoreElements()) {
                    NetworkInterface nextElement = networkInterfaces.nextElement();
                    if (nextElement.isUp() && !nextElement.isLoopback() && !nextElement.getInterfaceAddresses().isEmpty()) {
                        if (BuildVars.LOGS_ENABLED) {
                            FileLog.d("valid interface: " + nextElement);
                        }
                        List<InterfaceAddress> interfaceAddresses = nextElement.getInterfaceAddresses();
                        for (int i = 0; i < interfaceAddresses.size(); i++) {
                            InetAddress address = interfaceAddresses.get(i).getAddress();
                            if (BuildVars.LOGS_ENABLED) {
                                FileLog.d("address: " + address.getHostAddress());
                            }
                            if (!address.isLinkLocalAddress() && !address.isLoopbackAddress() && !address.isMulticastAddress() && BuildVars.LOGS_ENABLED) {
                                FileLog.d("address is good");
                            }
                        }
                    }
                }
            } catch (Throwable th) {
                FileLog.e(th);
            }
        }
        try {
            Enumeration<NetworkInterface> networkInterfaces2 = NetworkInterface.getNetworkInterfaces();
            boolean z = false;
            boolean z2 = false;
            boolean z3 = false;
            while (networkInterfaces2.hasMoreElements()) {
                NetworkInterface nextElement2 = networkInterfaces2.nextElement();
                if (nextElement2.isUp() && !nextElement2.isLoopback()) {
                    List<InterfaceAddress> interfaceAddresses2 = nextElement2.getInterfaceAddresses();
                    for (int i2 = 0; i2 < interfaceAddresses2.size(); i2++) {
                        InetAddress address2 = interfaceAddresses2.get(i2).getAddress();
                        if (!address2.isLinkLocalAddress() && !address2.isLoopbackAddress() && !address2.isMulticastAddress()) {
                            if (address2 instanceof Inet6Address) {
                                z = true;
                            } else if (address2 instanceof Inet4Address) {
                                if (address2.getHostAddress().startsWith("192.0.0.")) {
                                    z2 = true;
                                } else {
                                    z3 = true;
                                }
                            }
                        }
                    }
                }
            }
            if (z) {
                if (this.forceTryIpV6) {
                    return (byte) 1;
                }
                if (z2) {
                    return (byte) 2;
                }
                if (!z3) {
                    return (byte) 1;
                }
            }
        } catch (Throwable th2) {
            FileLog.e(th2);
        }
        return (byte) 0;
    }

    private static class ResolveHostByNameTask extends AsyncTask<Void, Void, ResolvedDomain> {
        private ArrayList<Long> addresses = new ArrayList<>();
        private String currentHostName;

        public ResolveHostByNameTask(String str) {
            this.currentHostName = str;
        }

        public void addAddress(long j) {
            if (this.addresses.contains(Long.valueOf(j))) {
                return;
            }
            this.addresses.add(Long.valueOf(j));
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Removed duplicated region for block: B:41:0x00ce A[EXC_TOP_SPLITTER, SYNTHETIC] */
        @Override // android.os.AsyncTask
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public org.telegram.tgnet.ConnectionsManager.ResolvedDomain doInBackground(java.lang.Void... r11) {
            /*
                Method dump skipped, instructions count: 256
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.tgnet.ConnectionsManager.ResolveHostByNameTask.doInBackground(java.lang.Void[]):org.telegram.tgnet.ConnectionsManager$ResolvedDomain");
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(ResolvedDomain resolvedDomain) {
            int i = 0;
            if (resolvedDomain != null) {
                ConnectionsManager.dnsCache.put(this.currentHostName, resolvedDomain);
                int size = this.addresses.size();
                while (i < size) {
                    ConnectionsManager.native_onHostNameResolved(this.currentHostName, this.addresses.get(i).longValue(), resolvedDomain.getAddress());
                    i++;
                }
            } else {
                int size2 = this.addresses.size();
                while (i < size2) {
                    ConnectionsManager.native_onHostNameResolved(this.currentHostName, this.addresses.get(i).longValue(), "");
                    i++;
                }
            }
            ConnectionsManager.resolvingHostnameTasks.remove(this.currentHostName);
        }
    }
}
