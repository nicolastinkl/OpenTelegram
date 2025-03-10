package com.tencent.qmsp.sdk.g.f;

import android.content.ContentProviderClient;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import com.tencent.qmsp.sdk.base.c;

/* loaded from: classes.dex */
public class b {
    private static final Uri a = Uri.parse("content://cn.nubia.identity/identity");

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v14 */
    /* JADX WARN: Type inference failed for: r4v15 */
    /* JADX WARN: Type inference failed for: r4v5, types: [int] */
    /* JADX WARN: Type inference failed for: r4v6 */
    /* JADX WARN: Type inference failed for: r4v9, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r5v11 */
    /* JADX WARN: Type inference failed for: r5v12 */
    /* JADX WARN: Type inference failed for: r5v7, types: [java.lang.StringBuilder] */
    public static String a(Context context, String str) {
        Exception e;
        Bundle call;
        ?? r5;
        String str2 = null;
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
            ?? r4 = call.getInt("code", -1);
            try {
                if (r4 == 0) {
                    String string = call.getString("id");
                    StringBuilder sb = new StringBuilder();
                    sb.append("NubiaLog succeed:");
                    r4 = string;
                    r5 = sb;
                } else {
                    String string2 = call.getString("message");
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("NubiaLog failed:");
                    r4 = string2;
                    r5 = sb2;
                }
                r5.append(r4);
                c.c(r5.toString());
                return r4;
            } catch (Exception e2) {
                e = e2;
                str2 = r4;
                e.printStackTrace();
                return str2;
            }
        } catch (Exception e3) {
            e = e3;
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
            String string = call.getString("message");
            StringBuilder sb = new StringBuilder();
            sb.append("NubiaLog failed:");
            sb.append(string);
            c.c(sb.toString());
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v5, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r0v8 */
    /* JADX WARN: Type inference failed for: r0v9 */
    /* JADX WARN: Type inference failed for: r5v11, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r5v16 */
    /* JADX WARN: Type inference failed for: r5v17 */
    /* JADX WARN: Type inference failed for: r5v2 */
    /* JADX WARN: Type inference failed for: r5v3 */
    /* JADX WARN: Type inference failed for: r5v7, types: [int] */
    /* JADX WARN: Type inference failed for: r5v8 */
    public static String b(Context context) {
        Exception e;
        ?? r5;
        String str;
        Bundle call;
        ?? r0;
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
            r5 = call.getInt("code", -1);
            try {
                if (r5 == 0) {
                    String string = call.getString("id");
                    StringBuilder sb = new StringBuilder();
                    sb.append("NubiaLog succeed:");
                    r0 = sb;
                    r5 = string;
                } else {
                    String string2 = call.getString("message");
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("NubiaLog failed:");
                    r0 = sb2;
                    r5 = string2;
                }
                r0.append(r5);
                c.c(r0.toString());
                str = r5;
            } catch (Exception e2) {
                e = e2;
                e.printStackTrace();
                str = r5;
                return str;
            }
        } catch (Exception e3) {
            e = e3;
            r5 = 0;
        }
        return str;
    }
}
