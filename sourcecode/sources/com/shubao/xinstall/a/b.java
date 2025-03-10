package com.shubao.xinstall.a;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import com.shubao.xinstall.a.a.d;
import com.shubao.xinstall.a.a.f;
import com.shubao.xinstall.a.a.g;
import com.shubao.xinstall.a.a.h;
import com.shubao.xinstall.a.f.o;
import com.shubao.xinstall.a.f.s;
import com.xinstall.XINConfiguration;
import com.xinstall.listener.XWakeUpListener;
import com.xinstall.model.XAppError;
import java.lang.reflect.Field;
import java.util.IdentityHashMap;
import java.util.Set;

/* loaded from: classes.dex */
public class b {
    public static XINConfiguration a = null;
    public static volatile b b = null;
    private static String g = "install";
    private static String h = "install";
    private static String i;
    private static String j;
    private static Intent k;
    private static Intent l;
    public com.shubao.xinstall.a.a.b c;
    public f d;
    String e = "";
    private d f;
    private BroadcastReceiver m;

    class a extends c {
        private b b;
        private Application c;

        a(b bVar, Application application) {
            this.b = bVar;
            this.c = application;
        }

        @Override // com.shubao.xinstall.a.c, android.app.Application.ActivityLifecycleCallbacks
        public final void onActivityCreated(Activity activity, Bundle bundle) {
        }

        @Override // com.shubao.xinstall.a.c, android.app.Application.ActivityLifecycleCallbacks
        public final void onActivityDestroyed(Activity activity) {
            this.c.unregisterActivityLifecycleCallbacks(this);
        }

        @Override // com.shubao.xinstall.a.c, android.app.Application.ActivityLifecycleCallbacks
        public final void onActivityResumed(Activity activity) {
        }
    }

    static {
        i = com.shubao.xinstall.b.a.booleanValue() ? g : h;
    }

    private b(Context context) {
        h hVar = new h();
        com.shubao.xinstall.a.b.a aVar = new com.shubao.xinstall.a.b.a();
        g gVar = new g(context);
        this.c = new com.shubao.xinstall.a.a.b(context, hVar, aVar, gVar);
        f fVar = new f(context, hVar, aVar, gVar);
        this.d = fVar;
        this.f = d.a(context, fVar);
        Application application = (Application) context.getApplicationContext();
        application.registerActivityLifecycleCallbacks(new a(this, application));
        if (this.m == null) {
            this.m = new BroadcastReceiver() { // from class: com.shubao.xinstall.a.b.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context2, Intent intent) {
                    NetworkInfo activeNetworkInfo = ((ConnectivityManager) context2.getSystemService("connectivity")).getActiveNetworkInfo();
                    if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                        b.this.c.l();
                    }
                }
            };
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            context.registerReceiver(this.m, intentFilter);
        }
    }

    public static b a(Context context) {
        if (b == null) {
            synchronized (b.class) {
                if (b == null) {
                    b = new b(context);
                }
            }
        }
        return b;
    }

    private static String a(Activity activity) {
        try {
            Field declaredField = Class.forName("android.app.Activity").getDeclaredField("mReferrer");
            declaredField.setAccessible(true);
            return (String) declaredField.get(activity);
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException e) {
            if (!o.a) {
                return "No referrer";
            }
            e.printStackTrace();
            return "No referrer";
        }
    }

    public static IdentityHashMap<String, String> a() {
        return (b == null || b.c == null || b.c.g() == null) ? new IdentityHashMap<>() : b.c.g().a();
    }

    public static void a(Activity activity, Intent intent) {
        if (c(intent)) {
            if (activity == null) {
                return;
            }
            if (b(activity, intent)) {
                if (Build.VERSION.SDK_INT >= 22) {
                    String a2 = a(activity);
                    if (a2 == null) {
                        return;
                    } else {
                        j = a2;
                    }
                }
                k = intent;
                return;
            }
        }
        j = null;
        k = null;
    }

    private void a(XWakeUpListener xWakeUpListener) {
        this.c.a(xWakeUpListener);
    }

    public static boolean a(Intent intent) {
        return intent.getAction() != null && "android.intent.action.VIEW".equals(intent.getAction());
    }

    private static boolean a(String str, Intent intent) {
        if (str != null) {
            return str.contains("com.tencent.android.qqdownloader") || str.contains("android-app://com.tencent.mobileqq") || ((str.contains("com.tencent.mtt") || str.contains("com.tencent.mm") || str.contains("com.miui.securitycenter")) && intent.getPackage() != null);
        }
        return false;
    }

    private void b(Activity activity, Intent intent, XWakeUpListener xWakeUpListener) {
        if (b(activity, intent)) {
            a(xWakeUpListener);
        } else if (xWakeUpListener != null) {
            xWakeUpListener.onWakeUpFinish(null, new XAppError(XAppError.ERROR_NO_WAKEUP, "不是调起事件触发的方法"));
        }
    }

    public static boolean b(Activity activity, Intent intent) {
        if (Build.VERSION.SDK_INT < 22) {
            Intent intent2 = k;
            if (intent2 == null) {
                j = null;
                return e(intent);
            }
            Boolean valueOf = Boolean.valueOf(e(intent2));
            k = null;
            j = null;
            return valueOf.booleanValue();
        }
        if (activity == null && (k == null || j == null)) {
            return false;
        }
        Intent intent3 = k;
        if (intent3 == null ? !(intent == null || !d(intent)) : d(intent3)) {
            j = null;
            k = null;
            return false;
        }
        Intent intent4 = k;
        if (intent4 != null) {
            k = null;
            intent = intent4;
        }
        String str = j;
        if (str != null) {
            j = null;
        } else {
            str = a(activity);
        }
        return a(str, intent);
    }

    public static boolean b(Intent intent) {
        Uri data;
        return (intent == null || (data = intent.getData()) == null || data.getHost() == null || data.getHost().contains(i)) ? false : true;
    }

    private static boolean c(Intent intent) {
        return intent.getAction() != null && "android.intent.action.MAIN".equals(intent.getAction()) && intent.getCategories() != null && intent.getCategories().contains("android.intent.category.LAUNCHER");
    }

    private static boolean d(Intent intent) {
        Uri data;
        return (intent == null || (data = intent.getData()) == null || data.getHost() == null || s.a(data.getHost())) ? false : true;
    }

    private static boolean e(Intent intent) {
        if (intent == null) {
            return false;
        }
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.getBoolean("xinstall_intent", false)) {
                return false;
            }
            String string = extras.getString(com.shubao.xinstall.a.f.f.j);
            if (com.shubao.xinstall.a.f.f.k.equalsIgnoreCase(string) || com.shubao.xinstall.a.f.f.l.equalsIgnoreCase(string) || com.shubao.xinstall.a.f.f.m.equalsIgnoreCase(string)) {
                return true;
            }
        }
        String action = intent.getAction();
        Set<String> categories = intent.getCategories();
        return action == null || categories == null || !action.equals("android.intent.action.MAIN") || !categories.contains("android.intent.category.LAUNCHER");
    }

    public final void a(Activity activity, Intent intent, XWakeUpListener xWakeUpListener) {
        boolean z;
        Uri data;
        Boolean bool = Boolean.FALSE;
        if (Build.VERSION.SDK_INT < 22 ? k != null : !(k == null || j == null)) {
            bool = Boolean.TRUE;
        }
        if (bool.booleanValue()) {
            Intent intent2 = k;
            if (c(intent2)) {
                b(activity, intent2, xWakeUpListener);
                return;
            } else {
                k = null;
                j = null;
                return;
            }
        }
        if (c(intent)) {
            if (activity != null) {
                b(activity, intent, xWakeUpListener);
                return;
            } else {
                if (xWakeUpListener != null) {
                    xWakeUpListener.onWakeUpFinish(null, new XAppError(XAppError.ERROR_NO_HAS_ACTIVITY, "未传入activity，activity 未比传参数"));
                    return;
                }
                return;
            }
        }
        if (!a(intent)) {
            if (xWakeUpListener != null) {
                xWakeUpListener.onWakeUpFinish(null, new XAppError(XAppError.ERROR_UNKNOWN_ACTION, "用户未知操作 不处理。通常为错误使用，请联系技术客服，指导接入。"));
                return;
            }
            return;
        }
        Intent intent3 = l;
        if (intent3 != null && intent3 == intent) {
            if (xWakeUpListener != null) {
                xWakeUpListener.onWakeUpFinish(null, new XAppError(XAppError.ERROR_REPEAT_GETPARAMS, "重复获取调起参数，Xinstall进行了过滤"));
                return;
            }
            return;
        }
        l = intent;
        if (b(intent)) {
            if (xWakeUpListener != null) {
                xWakeUpListener.onWakeUpFinish(null, new XAppError(XAppError.ERROR_NOT_XINSTALL_WAKEUP, "本次调起并非为XInstall的调起"));
                return;
            }
            return;
        }
        if (intent == null || (data = intent.getData()) == null || data.getHost() == null) {
            z = false;
        } else {
            if (this.e.equals("dddd")) {
                i = g;
            }
            z = data.getHost().contains(i);
        }
        Uri data2 = z ? intent.getData() : null;
        if (data2 != null) {
            this.c.a(data2, xWakeUpListener);
        } else if (xWakeUpListener != null) {
            xWakeUpListener.onWakeUpFinish(null, new XAppError(XAppError.ERROR_SCHEME_NO_URL, "本次调起并非为XInstall的调起"));
        }
    }

    public final void a(String str, Object obj, Object obj2) {
        this.d.a(str, obj, obj2);
    }
}
