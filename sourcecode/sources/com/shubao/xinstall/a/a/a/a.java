package com.shubao.xinstall.a.a.a;

import android.content.Context;
import com.shubao.xinstall.a.c.a.a;
import com.xinstall.XINConfiguration;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public final class a implements Runnable {
    public static final BlockingQueue a = new LinkedBlockingQueue(1);
    private final Context b;

    public a(Context context) {
        this.b = context;
    }

    public static String a() {
        String str;
        try {
            str = (String) a.poll(3000L, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            str = "";
        }
        XINConfiguration xINConfiguration = com.shubao.xinstall.a.b.a;
        if (xINConfiguration != null) {
            xINConfiguration.changeGaid(str);
        }
        return str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        a.c cVar;
        try {
            cVar = com.shubao.xinstall.a.c.a.a.a(this.b);
        } catch (Exception e) {
            e.printStackTrace();
            cVar = null;
        }
        if (cVar == null) {
            a.offer("");
            XINConfiguration xINConfiguration = com.shubao.xinstall.a.b.a;
            if (xINConfiguration != null) {
                xINConfiguration.changeGaid("");
                return;
            }
            return;
        }
        if (cVar.a() != null) {
            a.offer(cVar.a());
            XINConfiguration xINConfiguration2 = com.shubao.xinstall.a.b.a;
            if (xINConfiguration2 != null) {
                xINConfiguration2.changeGaid(cVar.a());
            }
        }
    }
}
