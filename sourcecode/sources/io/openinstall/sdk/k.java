package io.openinstall.sdk;

import android.content.Context;
import android.os.Build;
import java.io.File;

/* loaded from: classes.dex */
public class k {
    private static k a;
    private static final Object b = new Object();
    private StringBuilder c = new StringBuilder();
    private final String[] d = {"dmJveA==", "Z29sZGZpc2g=", "X3g4Ng==", "YW9zcA==", "c2RrX2dwaG9uZQ==", "c2RrX3Bob25l"};

    private k() {
    }

    public static k a() {
        if (a == null) {
            synchronized (b) {
                if (a == null) {
                    a = new k();
                }
            }
        }
        return a;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x002a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String a(java.lang.String r8) {
        /*
            r7 = this;
            r0 = 0
            java.lang.String r1 = "android.os.SystemProperties"
            java.lang.Class r1 = java.lang.Class.forName(r1)     // Catch: java.lang.Exception -> L22
            java.lang.String r2 = "get"
            r3 = 1
            java.lang.Class[] r4 = new java.lang.Class[r3]     // Catch: java.lang.Exception -> L22
            java.lang.Class<java.lang.String> r5 = java.lang.String.class
            r6 = 0
            r4[r6] = r5     // Catch: java.lang.Exception -> L22
            java.lang.reflect.Method r1 = r1.getMethod(r2, r4)     // Catch: java.lang.Exception -> L22
            java.lang.Object[] r2 = new java.lang.Object[r3]     // Catch: java.lang.Exception -> L22
            r2[r6] = r8     // Catch: java.lang.Exception -> L22
            java.lang.Object r8 = r1.invoke(r0, r2)     // Catch: java.lang.Exception -> L22
            if (r8 == 0) goto L22
            java.lang.String r8 = (java.lang.String) r8     // Catch: java.lang.Exception -> L22
            goto L23
        L22:
            r8 = r0
        L23:
            boolean r1 = android.text.TextUtils.isEmpty(r8)
            if (r1 == 0) goto L2a
            goto L2b
        L2a:
            r0 = r8
        L2b:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.openinstall.sdk.k.a(java.lang.String):java.lang.String");
    }

    private boolean a(Context context, String str) {
        return !context.getPackageManager().hasSystemFeature(fv.a(str));
    }

    private boolean a(String[] strArr) {
        int i = 0;
        while (true) {
            String[] strArr2 = this.d;
            if (i >= strArr2.length) {
                break;
            }
            strArr2[i] = fv.a(strArr2[i]);
            i++;
        }
        for (String str : strArr) {
            String a2 = a(fv.a(str));
            if (a2 != null) {
                String lowerCase = a2.toLowerCase();
                for (String str2 : this.d) {
                    if (lowerCase.contains(str2)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean b(String[] strArr) {
        for (String str : strArr) {
            if (new File(fv.a(str)).exists()) {
                return true;
            }
        }
        return false;
    }

    private boolean c(String[] strArr) {
        for (String str : strArr) {
            if (new File(fv.a(str)).exists()) {
                return false;
            }
        }
        return true;
    }

    public boolean a(Context context) {
        this.c.setLength(0);
        int i = a(new String[]{"cm8uYnVpbGQuZmxhdm9y", "cm8uYm9hcmQucGxhdGZvcm0=", "cm8uaGFyZHdhcmU="}) ? 5 : 0;
        if (a(context, "YW5kcm9pZC5oYXJkd2FyZS5ibHVldG9vdGg=")) {
            i += 5;
        }
        if (a(context, "YW5kcm9pZC5oYXJkd2FyZS5jYW1lcmEuZmxhc2g=")) {
            i += 5;
        }
        if (b(new String[]{"L3N5c3RlbS9mcmFtZXdvcmsveDg2", "L3N5c3RlbS9mcmFtZXdvcmsveDg2XzY0", "L3N5c3RlbS9saWIvbGliY2xjb3JlX3g4Ni5iYw==", "L3N5c3RlbS9saWI2NC9saWJjbGNvcmVfeDg2LmJj", "L3N5c3RlbS9iaW4vbm94LXByb3A=", "L3N5c3RlbS9iaW4vZHJvaWQ0eC1wcm9w", "L3N5c3RlbS9iaW4vdHRWTS1wcm9w", "L3N5c3RlbS9iaW4vbWljcm92aXJ0LXByb3A=", "L3N5c3RlbS9iaW4vbmVtdVZNLXByb3A=", "L3N5c3RlbS9iaW4vYW5kcm9WTS1wcm9w", "L3N5c3RlbS9iaW4vZ2VueW1vdGlvbi12Ym94LXNm", "L3N5c3RlbS9ldGMvaW5pdC5hbmRyb1ZNLnNo", "L3N5c3RlbS9ldGMvbXVtdS1jb25maWdz", "L3N5c3RlbS9hcHAvS2V5Q2hhaW4vb2F0L3g4Ng==", "L3N5c3RlbS9hcHAvS2V5Q2hhaW4vb2F0L3g4Nl82NA==", "L3N5c3RlbS9mcmFtZXdvcmsvb2F0L3g4Ng==", "L3N5c3RlbS9mcmFtZXdvcmsvb2F0L3g4Nl82NA=="})) {
            i += 10;
        }
        if (b(new String[]{"L3N5c3RlbS9ldGMvZXhjbHVkZWQtaW5wdXQtZGV2aWNlcy54bWw="})) {
            i = Build.VERSION.SDK_INT < 26 ? i + 5 : i + 3;
        }
        if (c(new String[]{"L3N5c3RlbS9mcmFtZXdvcmsvYXJt", "L3N5c3RlbS9mcmFtZXdvcmsvYXJtNjQ="})) {
            i += 7;
        }
        return i >= 10;
    }
}
