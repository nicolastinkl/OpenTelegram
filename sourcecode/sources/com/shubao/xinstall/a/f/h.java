package com.shubao.xinstall.a.f;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class h {
    private static h f;
    private ClipboardManager a;
    private Context b;
    private CountDownLatch c = new CountDownLatch(1);
    private Runnable e = new a();
    private Handler d = new Handler();

    class a implements Runnable {
        a() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            h hVar;
            ClipData newPlainText;
            if (Build.VERSION.SDK_INT >= 16) {
                hVar = h.this;
                newPlainText = ClipData.newHtmlText(null, null, null);
            } else {
                hVar = h.this;
                newPlainText = ClipData.newPlainText(null, null);
            }
            h.a(hVar, newPlainText);
        }
    }

    private h(Context context) {
        this.b = context;
    }

    public static com.shubao.xinstall.a.b.e a(String str, String str2) {
        com.shubao.xinstall.a.b.e eVar = new com.shubao.xinstall.a.b.e();
        if (str != null && str.contains(f.n)) {
            eVar.b = str;
            eVar.a(2);
        }
        if (str2 != null && g.b(str2).contains(f.n)) {
            eVar.a = str2;
            eVar.a(1);
        }
        return eVar;
    }

    public static h a(Context context) {
        if (f == null) {
            synchronized (h.class) {
                if (f == null) {
                    f = new h(context);
                }
            }
        }
        return f;
    }

    static /* synthetic */ void a(h hVar, ClipData clipData) {
        try {
            ClipboardManager clipboardManager = hVar.a;
            if (clipboardManager != null) {
                clipboardManager.setPrimaryClip(clipData);
            }
        } catch (Exception unused) {
        }
    }

    public final void a() {
        this.d.postDelayed(this.e, 2000L);
    }

    public final ClipData b() {
        try {
            if (Build.VERSION.SDK_INT >= 29) {
                this.c.await(1500L, TimeUnit.MILLISECONDS);
            }
            ClipboardManager clipboardManager = this.a;
            if (clipboardManager != null) {
                return clipboardManager.getPrimaryClip();
            }
            return null;
        } catch (Exception unused) {
            return null;
        }
    }
}
