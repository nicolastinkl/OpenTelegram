package com.shubao.xinstall.a;

import com.android.installreferrer.api.InstallReferrerClient;
import com.android.installreferrer.api.InstallReferrerStateListener;
import com.android.installreferrer.api.ReferrerDetails;
import com.shubao.xinstall.a.f.o;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public final class a {
    CountDownLatch a = new CountDownLatch(1);
    public InstallReferrerClient b;
    String c;

    /* renamed from: com.shubao.xinstall.a.a$a, reason: collision with other inner class name */
    public class C0010a implements InstallReferrerStateListener {
        public C0010a() {
        }

        public final void onInstallReferrerServiceDisconnected() {
        }

        public final void onInstallReferrerSetupFinished(int i) {
            String str;
            if (i == 0) {
                if (o.a) {
                    o.a("PlayInstallReferrer Connection established");
                }
                try {
                    ReferrerDetails installReferrer = a.this.b.getInstallReferrer();
                    a.this.c = installReferrer.getInstallReferrer();
                } catch (Exception unused) {
                }
                a.this.b.endConnection();
            } else if (i != 1) {
                if (i == 2 && o.a) {
                    str = "API not available on the current Play Store app";
                    o.b(str);
                }
            } else if (o.a) {
                str = "Connection could not be established";
                o.b(str);
            }
            a.this.a.countDown();
        }
    }

    public final String a() {
        try {
            this.a.await(5L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception unused) {
        }
        if (o.a) {
            o.a("PlayInstallReferrer :" + this.c);
        }
        return this.c;
    }
}
