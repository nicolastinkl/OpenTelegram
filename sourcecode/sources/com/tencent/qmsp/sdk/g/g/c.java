package com.tencent.qmsp.sdk.g.g;

import android.content.Context;
import com.tencent.qmsp.sdk.base.IVendorCallback;

/* loaded from: classes.dex */
public class c implements com.tencent.qmsp.sdk.base.b {
    private Context a = null;
    private IVendorCallback b = null;

    class a implements Runnable {
        a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                Thread.sleep(1000L);
                if (c.this.b != null) {
                    c.this.b.onResult(b.a(), b.a(c.this.a), b.b(c.this.a));
                }
            } catch (Exception e) {
                if (c.this.b != null) {
                    c.this.b.onResult(false, null, null);
                }
                e.printStackTrace();
            }
        }
    }

    @Override // com.tencent.qmsp.sdk.base.b
    public String a() {
        return "";
    }

    @Override // com.tencent.qmsp.sdk.base.b
    public void a(Context context, IVendorCallback iVendorCallback) {
        this.a = context;
        this.b = iVendorCallback;
        b.c(context);
    }

    @Override // com.tencent.qmsp.sdk.base.b
    public String b() {
        return "";
    }

    @Override // com.tencent.qmsp.sdk.base.b
    public void c() {
        new Thread(new a()).start();
    }

    @Override // com.tencent.qmsp.sdk.base.b
    public boolean d() {
        return false;
    }

    @Override // com.tencent.qmsp.sdk.base.b
    public boolean e() {
        return b.a();
    }

    @Override // com.tencent.qmsp.sdk.base.b
    public void f() {
    }
}
