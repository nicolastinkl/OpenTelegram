package androidx.core.app;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import org.telegram.tgnet.ConnectionsManager;

/* loaded from: classes.dex */
public final class NavUtils {
    public static boolean shouldUpRecreateTask(Activity activity, Intent intent) {
        if (Build.VERSION.SDK_INT >= 16) {
            return Api16Impl.shouldUpRecreateTask(activity, intent);
        }
        String action = activity.getIntent().getAction();
        return (action == null || action.equals("android.intent.action.MAIN")) ? false : true;
    }

    public static void navigateUpTo(Activity activity, Intent intent) {
        if (Build.VERSION.SDK_INT >= 16) {
            Api16Impl.navigateUpTo(activity, intent);
            return;
        }
        intent.addFlags(ConnectionsManager.FileTypeFile);
        activity.startActivity(intent);
        activity.finish();
    }

    public static Intent getParentActivityIntent(Activity activity) {
        Intent parentActivityIntent;
        if (Build.VERSION.SDK_INT >= 16 && (parentActivityIntent = Api16Impl.getParentActivityIntent(activity)) != null) {
            return parentActivityIntent;
        }
        String parentActivityName = getParentActivityName(activity);
        if (parentActivityName == null) {
            return null;
        }
        ComponentName componentName = new ComponentName(activity, parentActivityName);
        try {
            if (getParentActivityName(activity, componentName) == null) {
                return Intent.makeMainActivity(componentName);
            }
            return new Intent().setComponent(componentName);
        } catch (PackageManager.NameNotFoundException unused) {
            Log.e("NavUtils", "getParentActivityIntent: bad parentActivityName '" + parentActivityName + "' in manifest");
            return null;
        }
    }

    public static Intent getParentActivityIntent(Context context, ComponentName componentName) throws PackageManager.NameNotFoundException {
        String parentActivityName = getParentActivityName(context, componentName);
        if (parentActivityName == null) {
            return null;
        }
        ComponentName componentName2 = new ComponentName(componentName.getPackageName(), parentActivityName);
        if (getParentActivityName(context, componentName2) == null) {
            return Intent.makeMainActivity(componentName2);
        }
        return new Intent().setComponent(componentName2);
    }

    public static String getParentActivityName(Activity activity) {
        try {
            return getParentActivityName(activity, activity.getComponentName());
        } catch (PackageManager.NameNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static String getParentActivityName(Context context, ComponentName componentName) throws PackageManager.NameNotFoundException {
        String string;
        String str;
        PackageManager packageManager = context.getPackageManager();
        int i = Build.VERSION.SDK_INT;
        int i2 = 640;
        if (i >= 29) {
            i2 = 269222528;
        } else if (i >= 24) {
            i2 = 787072;
        }
        ActivityInfo activityInfo = packageManager.getActivityInfo(componentName, i2);
        if (i >= 16 && (str = activityInfo.parentActivityName) != null) {
            return str;
        }
        Bundle bundle = activityInfo.metaData;
        if (bundle == null || (string = bundle.getString("android.support.PARENT_ACTIVITY")) == null) {
            return null;
        }
        if (string.charAt(0) != '.') {
            return string;
        }
        return context.getPackageName() + string;
    }

    static class Api16Impl {
        static boolean shouldUpRecreateTask(Activity activity, Intent intent) {
            return activity.shouldUpRecreateTask(intent);
        }

        static boolean navigateUpTo(Activity activity, Intent intent) {
            return activity.navigateUpTo(intent);
        }

        static Intent getParentActivityIntent(Activity activity) {
            return activity.getParentActivityIntent();
        }
    }
}
