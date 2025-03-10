package com.tencent.qmsp.oaid2;

import android.content.ContentProviderClient;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

/* loaded from: classes.dex */
public class c0 {
    public static final Uri a = Uri.parse("content://cn.nubia.identity/identity");

    public static String a(Context context, String str) {
        Bundle call;
        try {
            int i = Build.VERSION.SDK_INT;
            if (i >= 17) {
                ContentProviderClient acquireUnstableContentProviderClient = context.getContentResolver().acquireUnstableContentProviderClient(a);
                call = acquireUnstableContentProviderClient.call("getAAID", str, null);
                if (i >= 24) {
                    acquireUnstableContentProviderClient.close();
                } else {
                    acquireUnstableContentProviderClient.release();
                }
            } else {
                call = context.getContentResolver().call(a, "getAAID", str, (Bundle) null);
            }
            if (call.getInt("code", -1) == 0) {
                String string = call.getString("id");
                c.c("NubiaLog succeed:" + string);
                return string;
            }
            String string2 = call.getString("message");
            c.c("NubiaLog failed:" + string2);
            return string2;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String b(Context context) {
        Bundle call;
        try {
            int i = Build.VERSION.SDK_INT;
            if (i >= 17) {
                ContentProviderClient acquireUnstableContentProviderClient = context.getContentResolver().acquireUnstableContentProviderClient(a);
                call = acquireUnstableContentProviderClient.call("getOAID", null, null);
                if (i >= 24) {
                    acquireUnstableContentProviderClient.close();
                } else {
                    acquireUnstableContentProviderClient.release();
                }
            } else {
                call = context.getContentResolver().call(a, "getOAID", (String) null, (Bundle) null);
            }
            if (call.getInt("code", -1) == 0) {
                String string = call.getString("id");
                c.c("NubiaLog succeed:" + string);
                return string;
            }
            String string2 = call.getString("message");
            c.c("NubiaLog failed:" + string2);
            return string2;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean a(Context context) {
        Bundle call;
        try {
            int i = Build.VERSION.SDK_INT;
            if (i >= 17) {
                ContentProviderClient acquireUnstableContentProviderClient = context.getContentResolver().acquireUnstableContentProviderClient(a);
                call = acquireUnstableContentProviderClient.call("isSupport", null, null);
                if (i >= 24) {
                    acquireUnstableContentProviderClient.close();
                } else {
                    acquireUnstableContentProviderClient.release();
                }
            } else {
                call = context.getContentResolver().call(a, "isSupport", (String) null, (Bundle) null);
            }
            if (call.getInt("code", -1) == 0) {
                c.c("NubiaLog succeed");
                return call.getBoolean("issupport", true);
            }
            c.c("NubiaLog failed:" + call.getString("message"));
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
