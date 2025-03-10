package com.shubao.xinstall.a.a.a;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import com.xinstall.OnePXActivity;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public final class b implements Runnable {
    public static final BlockingQueue b = new LinkedBlockingQueue(1);
    private static final Handler d = new Handler(Looper.getMainLooper());
    final com.shubao.xinstall.a.a.b a;
    private final Context c;

    public b(Context context, com.shubao.xinstall.a.a.b bVar) {
        this.c = context;
        this.a = bVar;
    }

    public final String a() {
        String str;
        try {
            str = (String) b.poll(3000L, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            str = "";
        }
        this.a.g().f = str;
        return str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            d.post(new Runnable() { // from class: com.shubao.xinstall.a.a.a.b.1
                @Override // java.lang.Runnable
                public final void run() {
                    Intent intent = new Intent(b.this.c, (Class<?>) OnePXActivity.class);
                    intent.addFlags(268435456);
                    b.this.c.startActivity(intent);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            b.offer("");
            this.a.g().f = "";
        }
    }
}
