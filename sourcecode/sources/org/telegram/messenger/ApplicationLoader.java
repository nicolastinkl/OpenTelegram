package org.telegram.messenger;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Handler;
import android.os.PowerManager;
import android.os.Process;
import android.os.SystemClock;
import androidx.multidex.MultiDex;
import com.fm.openinstall.OpenInstall;
import com.github.gzuliyujiang.oaid.DeviceIdentifier;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.tencent.bugly.crashreport.CrashReport;
import com.xinstall.XInstall;
import cos.MyCOSService;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;
import org.telegram.messenger.PushListenerController;
import org.telegram.messenger.voip.VideoCapturerDevice;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLRPC$User;
import org.telegram.ui.Components.ForegroundDetector;
import org.telegram.ui.JMTFastShotManager;
import org.telegram.ui.LauncherIconController;

/* loaded from: classes3.dex */
public class ApplicationLoader extends Application {

    @SuppressLint({"StaticFieldLeak"})
    public static volatile Context applicationContext = null;
    public static volatile Handler applicationHandler = null;
    private static volatile boolean applicationInited = false;
    private static ApplicationLoader applicationLoaderInstance = null;
    public static boolean canDrawOverlays = false;
    private static ConnectivityManager connectivityManager = null;
    public static volatile NetworkInfo currentNetworkInfo = null;
    public static volatile boolean externalInterfacePaused = true;
    public static volatile boolean isScreenOn = false;
    private static int lastKnownNetworkType = -1;
    private static long lastNetworkCheckTypeTime = 0;
    private static ILocationServiceProvider locationServiceProvider = null;
    public static volatile boolean mainInterfacePaused = true;
    public static volatile boolean mainInterfacePausedStageQueue = true;
    public static volatile long mainInterfacePausedStageQueueTime = 0;
    public static volatile boolean mainInterfaceStopped = true;
    private static IMapsProvider mapsProvider;
    private static volatile ConnectivityManager.NetworkCallback networkCallback;
    private static PushListenerController.IPushListenerServiceProvider pushProvider;
    public static long startTime;

    protected void appCenterLogInternal(Throwable th) {
    }

    protected void checkForUpdatesInternal() {
    }

    protected boolean isHuaweiBuild() {
        return false;
    }

    protected String onGetApplicationId() {
        return null;
    }

    protected void startAppCenterInternal(Activity activity) {
    }

    @Override // android.content.ContextWrapper
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    public static ILocationServiceProvider getLocationServiceProvider() {
        if (locationServiceProvider == null) {
            ILocationServiceProvider onCreateLocationServiceProvider = applicationLoaderInstance.onCreateLocationServiceProvider();
            locationServiceProvider = onCreateLocationServiceProvider;
            onCreateLocationServiceProvider.init(applicationContext);
        }
        return locationServiceProvider;
    }

    protected ILocationServiceProvider onCreateLocationServiceProvider() {
        return new GoogleLocationProvider();
    }

    public static IMapsProvider getMapsProvider() {
        if (mapsProvider == null) {
            mapsProvider = applicationLoaderInstance.onCreateMapsProvider();
        }
        return mapsProvider;
    }

    protected IMapsProvider onCreateMapsProvider() {
        return new GoogleMapsProvider();
    }

    public static PushListenerController.IPushListenerServiceProvider getPushProvider() {
        if (pushProvider == null) {
            pushProvider = applicationLoaderInstance.onCreatePushProvider();
        }
        return pushProvider;
    }

    protected PushListenerController.IPushListenerServiceProvider onCreatePushProvider() {
        return PushListenerController.GooglePushListenerServiceProvider.INSTANCE;
    }

    public static String getApplicationId() {
        return applicationLoaderInstance.onGetApplicationId();
    }

    public static boolean isHuaweiStoreBuild() {
        return applicationLoaderInstance.isHuaweiBuild();
    }

    public static File getFilesDirFixed() {
        for (int i = 0; i < 10; i++) {
            File filesDir = applicationContext.getFilesDir();
            if (filesDir != null) {
                return filesDir;
            }
        }
        try {
            File file = new File(applicationContext.getApplicationInfo().dataDir, "files");
            file.mkdirs();
            return file;
        } catch (Exception e) {
            FileLog.e(e);
            return new File("/data/data/com.tglime.fqkaso/files");
        }
    }

    public static void postInitApplication() {
        if (applicationInited || applicationContext == null) {
            return;
        }
        applicationInited = true;
        try {
            LocaleController.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            connectivityManager = (ConnectivityManager) applicationContext.getSystemService("connectivity");
            applicationContext.registerReceiver(new BroadcastReceiver() { // from class: org.telegram.messenger.ApplicationLoader.1
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent) {
                    try {
                        ApplicationLoader.currentNetworkInfo = ApplicationLoader.connectivityManager.getActiveNetworkInfo();
                    } catch (Throwable unused) {
                    }
                    boolean isConnectionSlow = ApplicationLoader.isConnectionSlow();
                    for (int i = 0; i < 1; i++) {
                        ConnectionsManager.getInstance(i).checkConnection();
                        FileLoader.getInstance(i).onNetworkChanged(isConnectionSlow);
                    }
                }
            }, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            IntentFilter intentFilter = new IntentFilter("android.intent.action.SCREEN_ON");
            intentFilter.addAction("android.intent.action.SCREEN_OFF");
            applicationContext.registerReceiver(new ScreenReceiver(), intentFilter);
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        try {
            isScreenOn = ((PowerManager) applicationContext.getSystemService("power")).isScreenOn();
            if (BuildVars.LOGS_ENABLED) {
                FileLog.d("screen state = " + isScreenOn);
            }
        } catch (Exception e4) {
            e4.printStackTrace();
        }
        SharedConfig.loadConfig();
        SharedPrefsHelper.init(applicationContext);
        for (int i = 0; i < 1; i++) {
            UserConfig.getInstance(i).loadConfig();
            MessagesController.getInstance(i);
            if (i == 0) {
                SharedConfig.pushStringStatus = "__FIREBASE_GENERATING_SINCE_" + ConnectionsManager.getInstance(i).getCurrentTime() + "__";
            } else {
                ConnectionsManager.getInstance(i);
            }
            TLRPC$User currentUser = UserConfig.getInstance(i).getCurrentUser();
            if (currentUser != null) {
                MessagesController.getInstance(i).putUser(currentUser, true);
                SendMessagesHelper.getInstance(i).checkUnsentMessages();
            }
        }
        ((ApplicationLoader) applicationContext).initPushServices();
        if (BuildVars.LOGS_ENABLED) {
            FileLog.d("app initied");
        }
        MediaController.getInstance();
        for (int i2 = 0; i2 < 1; i2++) {
            ContactsController.getInstance(i2).checkAppAccount();
            DownloadController.getInstance(i2);
        }
        ChatThemeController.init();
        BillingController.getInstance().startConnection();
    }

    @Override // android.app.Application
    public void onCreate() {
        applicationLoaderInstance = this;
        try {
            applicationContext = getApplicationContext();
        } catch (Throwable unused) {
        }
        super.onCreate();
        if (BuildVars.LOGS_ENABLED) {
            StringBuilder sb = new StringBuilder();
            sb.append("app start time = ");
            long elapsedRealtime = SystemClock.elapsedRealtime();
            startTime = elapsedRealtime;
            sb.append(elapsedRealtime);
            FileLog.d(sb.toString());
            FileLog.d("buildVersion = " + BuildVars.BUILD_VERSION);
        }
        if (applicationContext == null) {
            applicationContext = getApplicationContext();
        }
        NativeLoader.initNativeLibs(applicationContext);
        try {
            ConnectionsManager.native_setJava(false);
            new ForegroundDetector(this) { // from class: org.telegram.messenger.ApplicationLoader.2
                @Override // org.telegram.ui.Components.ForegroundDetector, android.app.Application.ActivityLifecycleCallbacks
                public void onActivityStarted(Activity activity) {
                    boolean isBackground = isBackground();
                    super.onActivityStarted(activity);
                    if (isBackground) {
                        ApplicationLoader.ensureCurrentNetworkGet(true);
                    }
                }
            };
            if (BuildVars.LOGS_ENABLED) {
                FileLog.d("load libs time = " + (SystemClock.elapsedRealtime() - startTime));
            }
            applicationHandler = new Handler(applicationContext.getMainLooper());
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.ApplicationLoader$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    ApplicationLoader.startPushService();
                }
            });
            LauncherIconController.tryFixLauncherIconIfNeeded();
            ProxyRotationController.init();
            int i = BuildVars.controlInstallSDK;
            if (i == 1) {
                if (isMainProcess()) {
                    OpenInstall.preInit(this);
                    OpenInstall.init(this);
                }
            } else if (i == 2 && isMainProcess()) {
                XInstall.init(this);
            }
            CrashReport.initCrashReport(getApplicationContext(), BuildVars.buglyAppId, false);
            DeviceIdentifier.register(this);
            MyCOSService.getInstance().init(this);
            int i2 = UserConfig.selectedAccount;
            if (BuildVars.isDownloadLineFromOnline) {
                if (BuildVars.isOpenCloudDefense) {
                    MyCOSService.getInstance().connectProxy(i2);
                } else if (BuildVars.isDownloadLineFromDNS) {
                    MyCOSService.getInstance().downloadDNS(i2);
                } else {
                    MyCOSService.getInstance().downloadFile(i2);
                }
            } else {
                MyCOSService.getInstance().setDefaultLine(i2, true);
            }
            BuildVars.isSubmitLogsTime = System.currentTimeMillis();
            new Timer().schedule(new TimerTask() { // from class: org.telegram.messenger.ApplicationLoader.3
                @Override // java.util.TimerTask, java.lang.Runnable
                public void run() {
                    int i3 = UserConfig.selectedAccount;
                    if (UserConfig.getInstance(i3).isClientActivated()) {
                        MessagesController.getInstance(i3).getDifference();
                    }
                }
            }, 0L, 10000L);
            JMTFastShotManager.getInstance().startCountry();
            ConnectivityManager connectivityManager2 = (ConnectivityManager) getSystemService("connectivity");
            NetworkRequest build = new NetworkRequest.Builder().addTransportType(1).build();
            networkCallback = new ConnectivityManager.NetworkCallback() { // from class: org.telegram.messenger.ApplicationLoader.4
                @Override // android.net.ConnectivityManager.NetworkCallback
                public void onAvailable(Network network) {
                    JMTFastShotManager.getInstance().startFastShot();
                }

                @Override // android.net.ConnectivityManager.NetworkCallback
                public void onLost(Network network) {
                    JMTFastShotManager.getInstance().stopFastShot();
                }
            };
            connectivityManager2.registerNetworkCallback(build, networkCallback);
        } catch (UnsatisfiedLinkError unused2) {
            throw new RuntimeException("can't load native libraries " + Build.CPU_ABI + " lookup folder " + NativeLoader.getAbiFolder());
        }
    }

    public boolean isMainProcess() {
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) getSystemService("activity")).getRunningAppProcesses()) {
            if (runningAppProcessInfo.pid == myPid) {
                return getApplicationInfo().packageName.equals(runningAppProcessInfo.processName);
            }
        }
        return false;
    }

    public static void startPushService() {
        boolean z;
        SharedPreferences globalNotificationsSettings = MessagesController.getGlobalNotificationsSettings();
        if (globalNotificationsSettings.contains("pushService")) {
            z = globalNotificationsSettings.getBoolean("pushService", true);
        } else {
            z = MessagesController.getMainSettings(UserConfig.selectedAccount).getBoolean("keepAliveService", true);
        }
        if (z) {
            try {
                applicationContext.startService(new Intent(applicationContext, (Class<?>) NotificationsService.class));
            } catch (Throwable unused) {
            }
        } else {
            applicationContext.stopService(new Intent(applicationContext, (Class<?>) NotificationsService.class));
        }
    }

    @Override // android.app.Application, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        try {
            LocaleController.getInstance().onDeviceConfigurationChange(configuration);
            AndroidUtilities.checkDisplaySize(applicationContext, configuration);
            VideoCapturerDevice.checkScreenCapturerSize();
            AndroidUtilities.resetTabletFlag();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initPushServices() {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.ApplicationLoader$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ApplicationLoader.lambda$initPushServices$0();
            }
        }, 1000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$initPushServices$0() {
        if (getPushProvider().hasServices()) {
            getPushProvider().onRequestPushToken();
            return;
        }
        if (BuildVars.LOGS_ENABLED) {
            FileLog.d("No valid " + getPushProvider().getLogTitle() + " APK found.");
        }
        SharedConfig.pushStringStatus = "__NO_GOOGLE_PLAY_SERVICES__";
        PushListenerController.sendRegistrationToServer(getPushProvider().getPushType(), null);
    }

    private boolean checkPlayServices() {
        try {
            return GooglePlayServicesUtil.isGooglePlayServicesAvailable(this) == 0;
        } catch (Exception e) {
            FileLog.e(e);
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void ensureCurrentNetworkGet(boolean z) {
        if (z || currentNetworkInfo == null) {
            try {
                if (connectivityManager == null) {
                    connectivityManager = (ConnectivityManager) applicationContext.getSystemService("connectivity");
                }
                currentNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (Build.VERSION.SDK_INT < 24 || networkCallback != null) {
                    return;
                }
                networkCallback = new ConnectivityManager.NetworkCallback() { // from class: org.telegram.messenger.ApplicationLoader.5
                    @Override // android.net.ConnectivityManager.NetworkCallback
                    public void onAvailable(Network network) {
                        int unused = ApplicationLoader.lastKnownNetworkType = -1;
                    }

                    @Override // android.net.ConnectivityManager.NetworkCallback
                    public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
                        int unused = ApplicationLoader.lastKnownNetworkType = -1;
                    }
                };
                connectivityManager.registerDefaultNetworkCallback(networkCallback);
            } catch (Throwable unused) {
            }
        }
    }

    public static boolean isRoaming() {
        try {
            ensureCurrentNetworkGet(false);
            if (currentNetworkInfo != null) {
                return currentNetworkInfo.isRoaming();
            }
            return false;
        } catch (Exception e) {
            FileLog.e(e);
            return false;
        }
    }

    public static boolean isConnectedOrConnectingToWiFi() {
        try {
            ensureCurrentNetworkGet(false);
            if (currentNetworkInfo != null && (currentNetworkInfo.getType() == 1 || currentNetworkInfo.getType() == 9)) {
                NetworkInfo.State state = currentNetworkInfo.getState();
                if (state != NetworkInfo.State.CONNECTED && state != NetworkInfo.State.CONNECTING) {
                    if (state == NetworkInfo.State.SUSPENDED) {
                    }
                }
                return true;
            }
        } catch (Exception e) {
            FileLog.e(e);
        }
        return false;
    }

    public static boolean isConnectedToWiFi() {
        try {
            ensureCurrentNetworkGet(false);
            if (currentNetworkInfo != null && (currentNetworkInfo.getType() == 1 || currentNetworkInfo.getType() == 9)) {
                if (currentNetworkInfo.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        } catch (Exception e) {
            FileLog.e(e);
        }
        return false;
    }

    public static boolean isConnectionSlow() {
        try {
            ensureCurrentNetworkGet(false);
            if (currentNetworkInfo != null && currentNetworkInfo.getType() == 0) {
                int subtype = currentNetworkInfo.getSubtype();
                if (subtype == 1 || subtype == 2 || subtype == 4 || subtype == 7 || subtype == 11) {
                    return true;
                }
            }
        } catch (Throwable unused) {
        }
        return false;
    }

    public static int getAutodownloadNetworkType() {
        int i;
        try {
            ensureCurrentNetworkGet(false);
        } catch (Exception e) {
            FileLog.e(e);
        }
        if (currentNetworkInfo == null) {
            return 0;
        }
        if (currentNetworkInfo.getType() != 1 && currentNetworkInfo.getType() != 9) {
            return currentNetworkInfo.isRoaming() ? 2 : 0;
        }
        if (Build.VERSION.SDK_INT >= 24 && (((i = lastKnownNetworkType) == 0 || i == 1) && System.currentTimeMillis() - lastNetworkCheckTypeTime < 5000)) {
            return lastKnownNetworkType;
        }
        if (connectivityManager.isActiveNetworkMetered()) {
            lastKnownNetworkType = 0;
        } else {
            lastKnownNetworkType = 1;
        }
        lastNetworkCheckTypeTime = System.currentTimeMillis();
        return lastKnownNetworkType;
    }

    public static int getCurrentNetworkType() {
        if (isConnectedOrConnectingToWiFi()) {
            return 1;
        }
        return isRoaming() ? 2 : 0;
    }

    public static boolean isNetworkOnlineFast() {
        try {
            ensureCurrentNetworkGet(false);
            if (currentNetworkInfo != null && !currentNetworkInfo.isConnectedOrConnecting() && !currentNetworkInfo.isAvailable()) {
                NetworkInfo networkInfo = connectivityManager.getNetworkInfo(0);
                if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
                    return true;
                }
                NetworkInfo networkInfo2 = connectivityManager.getNetworkInfo(1);
                if (networkInfo2 != null) {
                    if (networkInfo2.isConnectedOrConnecting()) {
                        return true;
                    }
                }
                return false;
            }
            return true;
        } catch (Exception e) {
            FileLog.e(e);
            return true;
        }
    }

    public static boolean isNetworkOnlineRealtime() {
        try {
            ConnectivityManager connectivityManager2 = (ConnectivityManager) applicationContext.getSystemService("connectivity");
            NetworkInfo activeNetworkInfo = connectivityManager2.getActiveNetworkInfo();
            if (activeNetworkInfo != null && (activeNetworkInfo.isConnectedOrConnecting() || activeNetworkInfo.isAvailable())) {
                return true;
            }
            NetworkInfo networkInfo = connectivityManager2.getNetworkInfo(0);
            if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
                return true;
            }
            NetworkInfo networkInfo2 = connectivityManager2.getNetworkInfo(1);
            if (networkInfo2 != null) {
                if (networkInfo2.isConnectedOrConnecting()) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            FileLog.e(e);
            return true;
        }
    }

    public static boolean isNetworkOnline() {
        boolean isNetworkOnlineRealtime = isNetworkOnlineRealtime();
        if (BuildVars.DEBUG_PRIVATE_VERSION && isNetworkOnlineRealtime != isNetworkOnlineFast()) {
            FileLog.d("network online mismatch");
        }
        return isNetworkOnlineRealtime;
    }

    public static void startAppCenter(Activity activity) {
        applicationLoaderInstance.startAppCenterInternal(activity);
    }

    public static void checkForUpdates() {
        applicationLoaderInstance.checkForUpdatesInternal();
    }

    public static void appCenterLog(Throwable th) {
        applicationLoaderInstance.appCenterLogInternal(th);
    }
}
