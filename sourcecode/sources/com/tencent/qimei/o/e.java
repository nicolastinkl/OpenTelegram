package com.tencent.qimei.o;

import android.content.Context;
import android.content.SharedPreferences;
import com.tencent.qimei.upload.BuildConfig;

/* compiled from: Bugly.java */
/* loaded from: classes.dex */
public class e {
    public static e a;
    public boolean b = false;

    public static e a() {
        if (a == null) {
            synchronized (e.class) {
                if (a == null) {
                    a = new e();
                }
            }
        }
        return a;
    }

    public synchronized void a(Context context) {
        if (!this.b && context != null) {
            if (!com.tencent.qimei.c.a.i()) {
                this.b = true;
                return;
            }
            SharedPreferences sharedPreferences = context.getSharedPreferences("BuglySdkInfos", 0);
            String string = sharedPreferences.getString("4ef223fde6", "");
            if (string == null || !string.equals(BuildConfig.SDK_VERSION)) {
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putString("4ef223fde6", BuildConfig.SDK_VERSION);
                edit.apply();
            }
            this.b = true;
        }
    }
}
