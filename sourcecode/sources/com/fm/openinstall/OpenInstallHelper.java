package com.fm.openinstall;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import io.openinstall.sdk.dw;
import io.openinstall.sdk.fw;
import io.openinstall.sdk.k;

/* loaded from: classes.dex */
public final class OpenInstallHelper {
    private OpenInstallHelper() {
    }

    public static boolean checkSimulator(Context context) {
        return k.a().a(context);
    }

    public static boolean isLauncherFromYYB(Activity activity, Intent intent) {
        Uri referrer;
        if (activity == null || intent == null || TextUtils.isEmpty(intent.getAction()) || intent.getCategories() == null || !intent.getAction().equals("android.intent.action.MAIN") || !intent.getCategories().contains("android.intent.category.LAUNCHER") || Build.VERSION.SDK_INT < 22 || (referrer = activity.getReferrer()) == null) {
            return false;
        }
        String authority = referrer.getAuthority();
        if (TextUtils.isEmpty(authority)) {
            return false;
        }
        boolean z = authority.equalsIgnoreCase(fw.o) || authority.equalsIgnoreCase(fw.p) || authority.equalsIgnoreCase(fw.n);
        if (authority.equalsIgnoreCase(fw.f26q) || authority.equalsIgnoreCase(fw.r) || authority.equalsIgnoreCase(fw.s)) {
            return true;
        }
        return z;
    }

    @Deprecated
    public static boolean isSchemeWakeup(Intent intent) {
        if (intent != null && intent.getData() != null && intent.getAction() != null) {
            String action = intent.getAction();
            if (!TextUtils.isEmpty(intent.getData().getHost()) && action.equals("android.intent.action.VIEW")) {
                return true;
            }
        }
        return false;
    }

    public static boolean isTrackData(ClipData clipData) {
        dw a = dw.a(clipData);
        if (a == null) {
            return false;
        }
        return a.c(1) || a.c(2);
    }
}
