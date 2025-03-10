package com.tencent.beacon.module;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.beacon.a.a.b;
import com.tencent.beacon.a.a.d;
import com.tencent.beacon.a.b.a;
import com.tencent.beacon.a.c.j;
import com.tencent.beacon.base.util.c;
import com.tencent.qmsp.sdk.u.U;

/* loaded from: classes.dex */
public class QmspModule implements BeaconModule, d {
    private Context a;
    private boolean b;
    private final Runnable c = new Runnable() { // from class: com.tencent.beacon.module.QmspModule.1
        @Override // java.lang.Runnable
        public void run() {
            String str;
            if (!QmspModule.this.b) {
                QmspModule.this.a(j.c());
            }
            try {
            } catch (Throwable th) {
                c.b("[qmsp] getSDKIsAlive error! exception msg", th.getMessage());
                c.a(th);
            }
            if (U.getSDKIsAlive()) {
                str = "Y";
                c.a("[qmsp] current qmsp is alive:%s", str);
                com.tencent.beacon.a.c.c.d().c(str);
            }
            str = "N";
            c.a("[qmsp] current qmsp is alive:%s", str);
            com.tencent.beacon.a.c.c.d().c(str);
        }
    };

    @Override // com.tencent.beacon.module.BeaconModule
    public void a(Context context) {
        if (!com.tencent.beacon.a.c.c.d().m()) {
            c.a("qmsp disable by user", new Object[0]);
        } else {
            this.a = context;
            b.a().a(13, this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void a(String str) {
        Throwable th;
        int i;
        if (this.b) {
            return;
        }
        c.a("[qmsp] init qmsp qimei: %s", str);
        if (TextUtils.isEmpty(str)) {
            return;
        }
        com.tencent.beacon.a.c.c d = com.tencent.beacon.a.c.c.d();
        String l = com.tencent.beacon.a.c.c.d().l();
        String f = com.tencent.beacon.a.c.c.d().f();
        try {
            c.a("[qmsp] startQ: userId: %s, qimei:%s, appKey:%s, sdkVersion: %s", l, str, f, d.j());
            i = U.startQ(this.a, l, str, f, d.j(), c.b());
        } catch (Throwable th2) {
            th = th2;
            i = 0;
        }
        try {
            a.a().a(114, 0L, 300000L, this.c);
            this.b = true;
        } catch (Throwable th3) {
            th = th3;
            c.b("[qmsp] qmspSDk start error! result:%d, exception msg: %s", Integer.valueOf(i), th.getMessage());
            c.a(th);
            U.stopQ();
            this.b = false;
            c.a("[qmsp] qmspSDK start result:%d", Integer.valueOf(i));
        }
        c.a("[qmsp] qmspSDK start result:%d", Integer.valueOf(i));
    }

    @Override // com.tencent.beacon.a.a.d
    public void a(com.tencent.beacon.a.a.c cVar) {
        if (cVar.a != 13) {
            return;
        }
        a(j.c());
    }
}
