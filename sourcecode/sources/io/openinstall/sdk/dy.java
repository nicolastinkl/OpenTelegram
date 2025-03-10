package io.openinstall.sdk;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import com.tencent.cos.xml.common.COSRequestHeaderKey;
import java.lang.ref.WeakReference;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class dy {
    private ClipboardManager b;
    private final DelayQueue<dx> c = new DelayQueue<>();
    private WeakReference<Activity> d = null;
    private int e = 0;
    private final boolean a = aw.a().i().booleanValue();

    public dy(Context context) {
        try {
            this.b = (ClipboardManager) context.getSystemService("clipboard");
        } catch (Throwable unused) {
        }
    }

    private ClipData f() {
        ClipDescription clipDescription;
        ClipData clipData = null;
        try {
            clipDescription = this.b.getPrimaryClipDescription();
        } catch (Throwable unused) {
            clipDescription = null;
        }
        if (clipDescription == null) {
            return g();
        }
        boolean hasMimeType = clipDescription.hasMimeType(COSRequestHeaderKey.TEXT_PLAIN);
        if (Build.VERSION.SDK_INT >= 16) {
            hasMimeType |= clipDescription.hasMimeType("text/html");
        }
        if (!hasMimeType) {
            return ClipData.newPlainText("custom", "don't match");
        }
        try {
            clipData = this.b.getPrimaryClip();
        } catch (Throwable unused2) {
        }
        return clipData == null ? g() : clipData;
    }

    private ClipData g() {
        if (!c()) {
            return null;
        }
        int i = this.e + 1;
        this.e = i;
        if (i < 3) {
            return null;
        }
        this.e = 0;
        return ClipData.newPlainText("custom", "app focus");
    }

    public void a() {
        if (this.a) {
            this.c.offer((DelayQueue<dx>) dx.a());
        }
    }

    public void a(WeakReference<Activity> weakReference) {
        this.d = weakReference;
    }

    public void b() {
        if (this.a) {
            this.c.offer((DelayQueue<dx>) dx.a());
            this.c.offer((DelayQueue<dx>) dx.b());
        }
    }

    public boolean c() {
        Activity activity;
        WeakReference<Activity> weakReference = this.d;
        if (weakReference == null || (activity = weakReference.get()) == null) {
            return false;
        }
        return activity.hasWindowFocus();
    }

    public ClipData d() {
        if (this.b == null) {
            return null;
        }
        return f();
    }

    public ClipData e() {
        ClipData clipData;
        int i;
        dx dxVar;
        if (this.b == null) {
            return null;
        }
        if (this.a) {
            clipData = f();
            i = 2;
        } else {
            clipData = null;
            i = 1;
        }
        while (clipData == null) {
            try {
                dxVar = this.c.poll(1000L, TimeUnit.MILLISECONDS);
            } catch (InterruptedException unused) {
                dxVar = null;
            }
            ClipData f = f();
            i++;
            if (dxVar == null || !dxVar.c()) {
                if (this.a || i < 3) {
                    clipData = f;
                }
            } else if (f == null && ga.a) {
                ga.b(fx.init_background.a(), new Object[0]);
            }
            clipData = f;
            break;
        }
        this.c.clear();
        return clipData;
    }
}
