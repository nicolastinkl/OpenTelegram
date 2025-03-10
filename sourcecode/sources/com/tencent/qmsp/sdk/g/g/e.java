package com.tencent.qmsp.sdk.g.g;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.IBinder;
import android.text.TextUtils;
import com.tencent.qmsp.sdk.g.g.d;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes.dex */
public class e {
    public static final e f = new e();
    public d a;
    public String b;
    public String c;
    public final Object d = new Object();
    public ServiceConnection e = new a();

    class a implements ServiceConnection {
        a() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            e.this.a = d.a.a(iBinder);
            synchronized (e.this.d) {
                e.this.d.notify();
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            e.this.a = null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x0045, code lost:
    
        if (r4.a == null) goto L32;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String a(android.content.Context r5, java.lang.String r6) {
        /*
            r4 = this;
            monitor-enter(r4)
            android.os.Looper r0 = android.os.Looper.myLooper()     // Catch: java.lang.Throwable -> L5d
            android.os.Looper r1 = android.os.Looper.getMainLooper()     // Catch: java.lang.Throwable -> L5d
            if (r0 == r1) goto L55
            com.tencent.qmsp.sdk.g.g.d r0 = r4.a     // Catch: java.lang.Throwable -> L5d
            if (r0 != 0) goto L48
            android.content.Intent r0 = new android.content.Intent     // Catch: java.lang.Throwable -> L5d
            r0.<init>()     // Catch: java.lang.Throwable -> L5d
            android.content.ComponentName r1 = new android.content.ComponentName     // Catch: java.lang.Throwable -> L5d
            java.lang.String r2 = "com.heytap.openid"
            java.lang.String r3 = "com.heytap.openid.IdentifyService"
            r1.<init>(r2, r3)     // Catch: java.lang.Throwable -> L5d
            r0.setComponent(r1)     // Catch: java.lang.Throwable -> L5d
            java.lang.String r1 = "action.com.heytap.openid.OPEN_ID_SERVICE"
            r0.setAction(r1)     // Catch: java.lang.Throwable -> L5d
            android.content.ServiceConnection r1 = r4.e     // Catch: java.lang.Throwable -> L5d
            r2 = 1
            boolean r0 = r5.bindService(r0, r1, r2)     // Catch: java.lang.Throwable -> L5d
            if (r0 == 0) goto L43
            java.lang.Object r0 = r4.d     // Catch: java.lang.Throwable -> L5d
            monitor-enter(r0)     // Catch: java.lang.Throwable -> L5d
            java.lang.Object r1 = r4.d     // Catch: java.lang.Throwable -> L39 java.lang.InterruptedException -> L3b
            r2 = 3000(0xbb8, double:1.482E-320)
            r1.wait(r2)     // Catch: java.lang.Throwable -> L39 java.lang.InterruptedException -> L3b
            goto L3f
        L39:
            r5 = move-exception
            goto L41
        L3b:
            r1 = move-exception
            r1.printStackTrace()     // Catch: java.lang.Throwable -> L39
        L3f:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L39
            goto L43
        L41:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L39
            throw r5     // Catch: java.lang.Throwable -> L5d
        L43:
            com.tencent.qmsp.sdk.g.g.d r0 = r4.a     // Catch: java.lang.Throwable -> L5d
            if (r0 != 0) goto L48
            goto L51
        L48:
            java.lang.String r5 = r4.b(r5, r6)     // Catch: java.lang.Exception -> L4d java.lang.Throwable -> L5d
            goto L53
        L4d:
            r5 = move-exception
            r5.printStackTrace()     // Catch: java.lang.Throwable -> L5d
        L51:
            java.lang.String r5 = ""
        L53:
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L5d
            return r5
        L55:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException     // Catch: java.lang.Throwable -> L5d
            java.lang.String r6 = "Cannot run on MainThread"
            r5.<init>(r6)     // Catch: java.lang.Throwable -> L5d
            throw r5     // Catch: java.lang.Throwable -> L5d
        L5d:
            r5 = move-exception
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L5d
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.qmsp.sdk.g.g.e.a(android.content.Context, java.lang.String):java.lang.String");
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:20:0x0027 -> B:15:0x002a). Please report as a decompilation issue!!! */
    public boolean a(Context context) {
        boolean z = true;
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo("com.heytap.openid", 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        z = Build.VERSION.SDK_INT < 28 ? false : false;
        return z;
    }

    public final String b(Context context, String str) {
        Signature[] signatureArr;
        if (TextUtils.isEmpty(this.b)) {
            this.b = context.getPackageName();
        }
        if (TextUtils.isEmpty(this.c)) {
            String str2 = null;
            try {
                signatureArr = context.getPackageManager().getPackageInfo(this.b, 64).signatures;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                signatureArr = null;
            }
            if (signatureArr != null && signatureArr.length > 0) {
                byte[] byteArray = signatureArr[0].toByteArray();
                try {
                    MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
                    if (messageDigest != null) {
                        byte[] digest = messageDigest.digest(byteArray);
                        StringBuilder sb = new StringBuilder();
                        for (byte b : digest) {
                            sb.append(Integer.toHexString((b & 255) | 256).substring(1, 3));
                        }
                        str2 = sb.toString();
                    }
                } catch (NoSuchAlgorithmException e2) {
                    e2.printStackTrace();
                }
            }
            this.c = str2;
        }
        String a2 = ((d.a.C0045a) this.a).a(this.b, this.c, str);
        return TextUtils.isEmpty(a2) ? "" : a2;
    }
}
