package cn.jzvd;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;
import android.view.Window;
import java.util.Formatter;
import java.util.Locale;

/* loaded from: classes.dex */
public class JZUtils {
    public static int SYSTEM_UI;

    public static String stringForTime(long timeMs) {
        if (timeMs <= 0 || timeMs >= 86400000) {
            return "00:00";
        }
        long j = timeMs / 1000;
        int i = (int) (j % 60);
        int i2 = (int) ((j / 60) % 60);
        int i3 = (int) (j / 3600);
        Formatter formatter = new Formatter(new StringBuilder(), Locale.getDefault());
        return i3 > 0 ? formatter.format("%d:%02d:%02d", Integer.valueOf(i3), Integer.valueOf(i2), Integer.valueOf(i)).toString() : formatter.format("%02d:%02d", Integer.valueOf(i2), Integer.valueOf(i)).toString();
    }

    public static boolean isWifiConnected(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.getType() == 1;
    }

    public static Activity scanForActivity(Context context) {
        if (context == null) {
            return null;
        }
        if (context instanceof Activity) {
            return (Activity) context;
        }
        if (context instanceof ContextWrapper) {
            return scanForActivity(((ContextWrapper) context).getBaseContext());
        }
        return null;
    }

    public static void setRequestedOrientation(Context context, int orientation) {
        if (scanForActivity(context) != null) {
            scanForActivity(context).setRequestedOrientation(orientation);
        } else {
            scanForActivity(context).setRequestedOrientation(orientation);
        }
    }

    public static Window getWindow(Context context) {
        if (scanForActivity(context) != null) {
            return scanForActivity(context).getWindow();
        }
        return scanForActivity(context).getWindow();
    }

    public static int dip2px(Context context, float dpValue) {
        return (int) ((dpValue * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static void saveProgress(Context context, Object url, long progress) {
        if (Jzvd.SAVE_PROGRESS) {
            Log.i(Jzvd.TAG, "saveProgress: " + progress);
            if (progress < 5000) {
                progress = 0;
            }
            context.getSharedPreferences("JZVD_PROGRESS", 0).edit().putLong("newVersion:" + url.toString(), progress).apply();
        }
    }

    public static long getSavedProgress(Context context, Object url) {
        if (!Jzvd.SAVE_PROGRESS) {
            return 0L;
        }
        return context.getSharedPreferences("JZVD_PROGRESS", 0).getLong("newVersion:" + url.toString(), 0L);
    }

    public static void clearSavedProgress(Context context, Object url) {
        if (url == null) {
            context.getSharedPreferences("JZVD_PROGRESS", 0).edit().clear().apply();
            return;
        }
        context.getSharedPreferences("JZVD_PROGRESS", 0).edit().putLong("newVersion:" + url.toString(), 0L).apply();
    }

    @SuppressLint({"RestrictedApi"})
    public static void showStatusBar(Context context) {
        if (Jzvd.TOOL_BAR_EXIST) {
            getWindow(context).clearFlags(1024);
        }
    }

    @SuppressLint({"RestrictedApi"})
    public static void hideStatusBar(Context context) {
        if (Jzvd.TOOL_BAR_EXIST) {
            getWindow(context).setFlags(1024, 1024);
        }
    }

    @SuppressLint({"NewApi"})
    public static void hideSystemUI(Context context) {
        int i = Build.VERSION.SDK_INT >= 19 ? 5638 : 1542;
        SYSTEM_UI = getWindow(context).getDecorView().getSystemUiVisibility();
        getWindow(context).getDecorView().setSystemUiVisibility(i);
    }

    @SuppressLint({"NewApi"})
    public static void showSystemUI(Context context) {
        getWindow(context).getDecorView().setSystemUiVisibility(SYSTEM_UI);
    }

    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        return resources.getDimensionPixelSize(resources.getIdentifier("status_bar_height", "dimen", "android"));
    }

    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }
}
