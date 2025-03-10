package com.shubao.xinstall.a.a.a;

import android.content.ClipData;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import com.shubao.xinstall.a.a.b;
import com.shubao.xinstall.a.f.o;
import com.xinstall.OnePXActivity;
import com.xinstall.XINConfiguration;
import java.io.File;
import java.util.IdentityHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public final class d implements Runnable {
    com.shubao.xinstall.a.a.b a;
    private boolean b = true;

    public d(com.shubao.xinstall.a.a.b bVar) {
        this.a = bVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        com.shubao.xinstall.a.b.d a;
        com.shubao.xinstall.a.e.b bVar;
        com.shubao.xinstall.a.e.b bVar2;
        com.shubao.xinstall.a.a.c cVar;
        ClipData.Item itemAt;
        com.shubao.xinstall.a.a.h c = this.a.c();
        com.shubao.xinstall.a.a.g f = this.a.f();
        String e = this.a.e();
        com.shubao.xinstall.a.a.c cVar2 = c.a;
        if (cVar2 == null && (cVar2 = f.a(e)) == com.shubao.xinstall.a.a.c.b) {
            try {
                SharedPreferences.Editor edit = f.a.edit();
                edit.clear();
                edit.apply();
            } catch (Exception unused) {
            }
        }
        if (cVar2 == com.shubao.xinstall.a.a.c.a) {
            this.a.d().a(f.h(), true);
            c.a = cVar2;
            c.b.countDown();
            this.a.d().d();
            return;
        }
        if (!com.shubao.xinstall.a.e.a.a()) {
            com.shubao.xinstall.a.a.c cVar3 = com.shubao.xinstall.a.a.c.e;
            c.a = cVar3;
            f.a(e, cVar3);
            f.b("当前网络不可用");
            return;
        }
        XINConfiguration xINConfiguration = com.shubao.xinstall.a.b.a;
        int i = (xINConfiguration == null || xINConfiguration.isOutSettingGaid.booleanValue()) ? 1 : 2;
        XINConfiguration xINConfiguration2 = com.shubao.xinstall.a.b.a;
        if (xINConfiguration2 != null && !xINConfiguration2.isOutSettingOaid.booleanValue()) {
            i++;
        }
        final CountDownLatch countDownLatch = new CountDownLatch(i);
        this.a.i().execute(new Thread(new Runnable() { // from class: com.shubao.xinstall.a.a.a.d.1
            @Override // java.lang.Runnable
            public final void run() {
                OnePXActivity onePXActivity;
                b bVar3 = new b(d.this.a.b(), d.this.a);
                d.this.a.i().execute(new Thread(bVar3));
                o.a("gpuinfo".concat(String.valueOf(bVar3.a())));
                b.a aVar = bVar3.a.j;
                if (aVar != null && (onePXActivity = aVar.b) != null && !onePXActivity.isFinishing()) {
                    aVar.b.finish();
                    aVar.b = null;
                }
                countDownLatch.countDown();
            }
        }));
        XINConfiguration xINConfiguration3 = com.shubao.xinstall.a.b.a;
        if (xINConfiguration3 != null && !xINConfiguration3.isOutSettingGaid.booleanValue()) {
            this.a.i().execute(new Thread(new Runnable() { // from class: com.shubao.xinstall.a.a.a.d.2
                @Override // java.lang.Runnable
                public final void run() {
                    d.this.a.i().execute(new Thread(new a(d.this.a.b())));
                    o.a("gaidinfo".concat(String.valueOf(a.a())));
                    countDownLatch.countDown();
                }
            }));
        }
        XINConfiguration xINConfiguration4 = com.shubao.xinstall.a.b.a;
        if (xINConfiguration4 != null && !xINConfiguration4.isOutSettingOaid.booleanValue()) {
            this.a.i().execute(new Thread(new Runnable() { // from class: com.shubao.xinstall.a.a.a.d.3
                @Override // java.lang.Runnable
                public final void run() {
                    d.this.a.i().execute(new Thread(new f(d.this.a.b())));
                    o.a("oaidInfo".concat(String.valueOf(f.a())));
                    countDownLatch.countDown();
                }
            }));
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        }
        c.a = com.shubao.xinstall.a.a.c.c;
        com.shubao.xinstall.a.a k = this.a.k();
        IdentityHashMap identityHashMap = new IdentityHashMap();
        com.shubao.xinstall.a.f.i g = this.a.g();
        identityHashMap.put("buildId", g.c);
        identityHashMap.put("buildDisplay", g.d);
        identityHashMap.putAll(g.g);
        com.shubao.xinstall.a.f.j.a();
        identityHashMap.put("simulator", String.valueOf(com.shubao.xinstall.a.f.j.a(this.a.b())));
        if (k != null) {
            identityHashMap.put("gReferrer", k.a());
        }
        com.shubao.xinstall.a.f.h h = this.a.h();
        com.shubao.xinstall.a.b.e eVar = new com.shubao.xinstall.a.b.e();
        ClipData b = h.b();
        if (b != null && b.getItemCount() > 0 && (itemAt = b.getItemAt(0)) != null) {
            eVar = com.shubao.xinstall.a.f.h.a(Build.VERSION.SDK_INT >= 16 ? itemAt.getHtmlText() : null, itemAt.getText() != null ? itemAt.getText().toString() : null);
        }
        this.a.h().a();
        com.shubao.xinstall.a.a.g f2 = this.a.f();
        com.shubao.xinstall.a.b.e i2 = f2.i();
        if (i2 != null) {
            eVar = i2;
        } else {
            try {
                SharedPreferences.Editor edit2 = f2.a.edit();
                edit2.putString("xk_data", com.shubao.xinstall.a.b.e.a(eVar));
                edit2.apply();
            } catch (Exception unused2) {
            }
        }
        if (eVar != null) {
            if (eVar.b(2)) {
                identityHashMap.put("xk", eVar.b);
            }
            if (eVar.b(1)) {
                identityHashMap.put("xk", eVar.a);
            }
        }
        o.a("xk:" + ((String) identityHashMap.get("xk")));
        Context context = g.a;
        if (com.shubao.xinstall.a.c.d.a == null) {
            File file = new File(com.shubao.xinstall.a.c.d.a(context));
            System.out.println("try to read channel info from apk : " + file.getAbsolutePath());
            String a2 = com.shubao.xinstall.a.c.e.a(file);
            Log.i("ChannelReaderUtil", "getChannelByV2 , channel = ".concat(String.valueOf(a2)));
            if (a2 == null) {
                a2 = com.shubao.xinstall.a.c.c.a(new File(com.shubao.xinstall.a.c.d.a(context)));
                Log.i("ChannelReaderUtil", "getChannelByV1 , channel = ".concat(String.valueOf(a2)));
            }
            com.shubao.xinstall.a.c.d.a = a2;
        }
        identityHashMap.put("apkXk", com.shubao.xinstall.a.c.d.a);
        int i3 = 0;
        do {
            try {
                com.shubao.xinstall.a.e.a.a(false);
                a = com.shubao.xinstall.a.e.a.a(this.a.a("init2"), this.a.a(), identityHashMap);
                if (a.a.equals(com.shubao.xinstall.a.e.b.RESPUNCONNECT)) {
                    com.shubao.xinstall.a.a.c cVar4 = com.shubao.xinstall.a.a.c.e;
                    c.a = cVar4;
                    f.a(e, cVar4);
                    f.b("当前网络不可用");
                    return;
                }
                if (a.a.equals(com.shubao.xinstall.a.e.b.SERVICEDOWN)) {
                    a.a = com.shubao.xinstall.a.e.b.FAIL;
                }
                try {
                    if (c.c.poll(i3 < 3 ? 1L : i3 < 8 ? 6L : 12L, TimeUnit.SECONDS) != null) {
                        i3 = 0;
                    }
                } catch (InterruptedException unused3) {
                }
                i3++;
                bVar = a.a;
                bVar2 = com.shubao.xinstall.a.e.b.FAIL;
            } catch (Exception unused4) {
                return;
            }
        } while (bVar == bVar2);
        this.a.c(a.e);
        com.shubao.xinstall.a.e.b bVar3 = a.a;
        if (bVar3 != com.shubao.xinstall.a.e.b.SUCCESS) {
            if (bVar3 == com.shubao.xinstall.a.e.b.RESP588) {
                o.c("初始化失败: " + a.d);
                this.a.j().a();
            } else if (bVar3 == bVar2) {
                f.b("初始化失败: " + a.d);
                cVar = com.shubao.xinstall.a.a.c.d;
            }
            c.b.countDown();
            f.a(e, c.a);
        }
        this.b = false;
        String str = a.c;
        try {
            SharedPreferences.Editor edit3 = f.a.edit();
            edit3.putString("init_data", str);
            edit3.apply();
        } catch (Exception unused5) {
        }
        cVar = com.shubao.xinstall.a.a.c.a;
        c.a = cVar;
        c.b.countDown();
        f.a(e, c.a);
    }
}
