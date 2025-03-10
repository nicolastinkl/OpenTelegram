package com.xinstall;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Process;
import android.text.TextUtils;
import androidx.annotation.Keep;
import androidx.core.content.ContextCompat;
import com.shubao.xinstall.a.b;
import com.shubao.xinstall.a.f.o;
import com.shubao.xinstall.a.f.p;
import com.xinstall.listener.XInstallListener;
import com.xinstall.listener.XWakeUpListener;
import com.xinstall.model.XAppError;
import java.util.ArrayList;
import java.util.List;

@Keep
/* loaded from: classes.dex */
public class XInstall {
    public static final int REQUEST_READ_PHONE_STATE = 1111;
    private static Activity aWakeUpActivty;
    private static Intent aWakeUpIntent;
    private static XWakeUpListener aWakeUpListener;
    private static boolean isInit;
    private static List<ActivityManager.RunningAppProcessInfo> mRunningAppProcessInfoList = new ArrayList();
    private static Context permissionActivityContext;
    private static Runnable permissionsRunnable;
    private static b realXInstall;

    private XInstall() {
    }

    public static String getAppKey() {
        return !isInit() ? "" : realXInstall.c.e();
    }

    public static XINConfiguration getConfiguration() {
        return b.a;
    }

    public static void getInstallParam(XInstallListener xInstallListener) {
        getInstallParam(xInstallListener, 0);
    }

    public static void getInstallParam(XInstallListener xInstallListener, int i) {
        if (!isInit()) {
            xInstallListener.onInstallFinish(null, null);
        } else {
            realXInstall.c.a(i, xInstallListener);
        }
    }

    public static void getWakeUpParam(Activity activity, Intent intent, XWakeUpListener xWakeUpListener) {
        if (isInit()) {
            realXInstall.a(activity, intent, xWakeUpListener);
        } else if (xWakeUpListener != null) {
            aWakeUpActivty = activity;
            aWakeUpIntent = intent;
            aWakeUpListener = xWakeUpListener;
        }
    }

    public static void getWakeUpParamEvenErrorAlsoCallBack(Activity activity, Intent intent, XWakeUpListener xWakeUpListener) {
        if (isInit()) {
            realXInstall.a(activity, intent, xWakeUpListener);
        } else if (xWakeUpListener != null) {
            xWakeUpListener.onWakeUpFinish(null, new XAppError(XAppError.ERROR_UN_INIT, "wakeUp的调用需要基于执行了init 方法之后"));
            aWakeUpActivty = activity;
            aWakeUpIntent = intent;
            aWakeUpListener = xWakeUpListener;
        }
    }

    public static void init(Context context) {
        if (TextUtils.isEmpty(p.a(context))) {
            o.c("请在AndroidManifest.xml配置Xinstall提供的appKey");
            throw new IllegalArgumentException("请在AndroidManifest.xml配置Xinstall提供的appKey");
        }
        XINConfiguration xINConfiguration = new XINConfiguration();
        xINConfiguration.canClip(Boolean.TRUE);
        init(context, xINConfiguration);
    }

    public static void init(Context context, XINConfiguration xINConfiguration) {
        Boolean bool;
        String a = p.a(context);
        if (TextUtils.isEmpty(a)) {
            o.c("请在AndroidManifest.xml配置Xinstall提供的appKey");
            throw new IllegalArgumentException("请在AndroidManifest.xml配置Xinstall提供的appKey");
        }
        if (xINConfiguration == null) {
            o.c("configuration 不能为空");
            throw new IllegalArgumentException("configuration 为空， configuration 为必传参数");
        }
        XINConfiguration xINConfiguration2 = b.a;
        b.a = xINConfiguration;
        if (xINConfiguration2 == null) {
            if (Build.VERSION.SDK_INT >= 23) {
                if (ContextCompat.checkSelfPermission(context, "android.permission.READ_PHONE_STATE") != -1) {
                    xINConfiguration = b.a;
                    bool = Boolean.FALSE;
                    xINConfiguration.setImeiEnable(bool);
                } else {
                    xINConfiguration = b.a;
                }
            }
            bool = Boolean.TRUE;
            xINConfiguration.setImeiEnable(bool);
        }
        init(context, a);
        XWakeUpListener xWakeUpListener = aWakeUpListener;
        if (xWakeUpListener != null) {
            realXInstall.a(aWakeUpActivty, aWakeUpIntent, xWakeUpListener);
        }
        aWakeUpListener = null;
        aWakeUpIntent = null;
        aWakeUpActivty = null;
        if (permissionsRunnable != null) {
            o.a("Runnable 被调用了");
            permissionsRunnable.run();
            permissionsRunnable = null;
        }
    }

    private static void init(Context context, String str) {
        if (context == null) {
            throw new IllegalArgumentException("context不能为空");
        }
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("请前往Xinstall控制台的 Android集成 -> Android应用配 中获AppKey");
        }
        o.a("SDK VERSION :1.7.4");
        synchronized (XInstall.class) {
            if (!isInit) {
                if (realXInstall == null) {
                    b a = b.a(context);
                    realXInstall = a;
                    a.c.b(str);
                    a.d.b(str);
                    a.c.m();
                }
                isInit = true;
            }
        }
    }

    public static void initWithPermission(Context context, XINConfiguration xINConfiguration) {
        initWithPermission(context, xINConfiguration, null);
    }

    public static void initWithPermission(Context context, XINConfiguration xINConfiguration, Runnable runnable) {
        o.a("执行了initWithPermission方法");
        b.a = xINConfiguration;
        if (Build.VERSION.SDK_INT < 23) {
            init(context, xINConfiguration);
            if (runnable != null) {
                o.a("Runnable 被调用了");
                runnable.run();
                permissionsRunnable = null;
                return;
            }
            return;
        }
        Activity activity = (Activity) context;
        if (activity.checkSelfPermission("android.permission.READ_PHONE_STATE") != 0) {
            o.a("权限未通过，发起权限请求");
            permissionActivityContext = context;
            permissionsRunnable = runnable;
            activity.requestPermissions(new String[]{"android.permission.READ_PHONE_STATE"}, REQUEST_READ_PHONE_STATE);
            return;
        }
        init(context, xINConfiguration);
        if (runnable != null) {
            runnable.run();
            o.a("Runnable 被调用了");
            permissionsRunnable = null;
        }
    }

    private static boolean isInit() {
        if (isInit) {
            return true;
        }
        o.c("请先调用 init(Context) 初始化");
        return false;
    }

    public static boolean isMainProcess(Context context) {
        int myPid = Process.myPid();
        List<ActivityManager.RunningAppProcessInfo> obtainRunningAppProcessInfoList = obtainRunningAppProcessInfoList(context);
        if (obtainRunningAppProcessInfoList == null) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : obtainRunningAppProcessInfoList) {
            if (runningAppProcessInfo.pid == myPid) {
                return context.getApplicationInfo().packageName.equals(runningAppProcessInfo.processName);
            }
        }
        return false;
    }

    private static List<ActivityManager.RunningAppProcessInfo> obtainRunningAppProcessInfoList(Context context) {
        if (mRunningAppProcessInfoList.isEmpty()) {
            ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
            if (activityManager == null) {
                return mRunningAppProcessInfoList;
            }
            mRunningAppProcessInfoList = activityManager.getRunningAppProcesses();
        }
        return mRunningAppProcessInfoList;
    }

    public static void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        String str;
        if (i == 1111 && permissionActivityContext != null) {
            if (iArr.length <= 0 || iArr[0] != 0) {
                XINConfiguration xINConfiguration = b.a;
                if (xINConfiguration != null) {
                    xINConfiguration.setImeiEnable(Boolean.FALSE);
                }
                str = "imei 同意权限申请: 被拒绝";
            } else {
                XINConfiguration xINConfiguration2 = b.a;
                if (xINConfiguration2 != null) {
                    xINConfiguration2.setImeiEnable(Boolean.TRUE);
                }
                str = "imei 同意权限申请: 成功";
            }
            o.c(str);
            init(permissionActivityContext, b.a);
            permissionActivityContext = null;
        }
    }

    public static void reportEvent(String str, int i) {
        if (isInit()) {
            b bVar = realXInstall;
            bVar.d.a(str, Integer.valueOf(i), "");
        }
    }

    public static void reportEvent(String str, int i, long j) {
        if (isInit()) {
            realXInstall.a(str, Integer.valueOf(i), Long.valueOf(j));
        }
    }

    public static void reportEventWhenOpenDetailInfo(String str, int i, String str2) {
        if (isInit()) {
            realXInstall.a(str, Integer.valueOf(i), str2);
        }
    }

    public static void reportRegister() {
        if (isInit()) {
            realXInstall.d.k();
        }
    }

    public static void reportShareByXinShareId(String str) {
        if (isInit()) {
            realXInstall.d.a("XinShareId", str);
        }
    }

    public static void saveInfoAndByLauncherActivityAndIntent(Activity activity, Intent intent) {
        b.a(activity, intent);
    }

    public static void setDebug(boolean z) {
        o.a = z;
    }

    public static Boolean whenNoLauncherisYYB(Activity activity, Intent intent) {
        return b.b(activity, intent) ? Boolean.TRUE : (!b.a(intent) || b.b(intent)) ? Boolean.FALSE : Boolean.TRUE;
    }
}
