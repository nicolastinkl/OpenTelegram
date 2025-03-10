package com.lxj.xpopup.util;

import android.annotation.SuppressLint;

@SuppressLint({"InlinedApi"})
/* loaded from: classes.dex */
public final class PermissionConstants {
    private static final String[] GROUP_STORAGE = {"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};

    public static String[] getPermissions(final String permission) {
        return permission == null ? new String[0] : !permission.equals("STORAGE") ? new String[]{permission} : GROUP_STORAGE;
    }
}
