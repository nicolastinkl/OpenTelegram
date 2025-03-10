package androidx.core.app;

import android.app.AppOpsManager;
import android.content.Context;
import android.os.Binder;
import android.os.Build;

/* loaded from: classes.dex */
public final class AppOpsManagerCompat {
    public static String permissionToOp(String str) {
        if (Build.VERSION.SDK_INT >= 23) {
            return Api23Impl.permissionToOp(str);
        }
        return null;
    }

    public static int noteProxyOpNoThrow(Context context, String str, String str2) {
        if (Build.VERSION.SDK_INT >= 23) {
            return Api23Impl.noteProxyOpNoThrow((AppOpsManager) Api23Impl.getSystemService(context, AppOpsManager.class), str, str2);
        }
        return 1;
    }

    public static int checkOrNoteProxyOp(Context context, int i, String str, String str2) {
        if (Build.VERSION.SDK_INT >= 29) {
            AppOpsManager systemService = Api29Impl.getSystemService(context);
            int checkOpNoThrow = Api29Impl.checkOpNoThrow(systemService, str, Binder.getCallingUid(), str2);
            return checkOpNoThrow != 0 ? checkOpNoThrow : Api29Impl.checkOpNoThrow(systemService, str, i, Api29Impl.getOpPackageName(context));
        }
        return noteProxyOpNoThrow(context, str, str2);
    }

    static class Api29Impl {
        static AppOpsManager getSystemService(Context context) {
            return (AppOpsManager) context.getSystemService(AppOpsManager.class);
        }

        static int checkOpNoThrow(AppOpsManager appOpsManager, String str, int i, String str2) {
            if (appOpsManager == null) {
                return 1;
            }
            return appOpsManager.checkOpNoThrow(str, i, str2);
        }

        static String getOpPackageName(Context context) {
            return context.getOpPackageName();
        }
    }

    static class Api23Impl {
        static String permissionToOp(String str) {
            return AppOpsManager.permissionToOp(str);
        }

        static <T> T getSystemService(Context context, Class<T> cls) {
            return (T) context.getSystemService(cls);
        }

        static int noteProxyOp(AppOpsManager appOpsManager, String str, String str2) {
            return appOpsManager.noteProxyOp(str, str2);
        }

        static int noteProxyOpNoThrow(AppOpsManager appOpsManager, String str, String str2) {
            return appOpsManager.noteProxyOpNoThrow(str, str2);
        }
    }
}
