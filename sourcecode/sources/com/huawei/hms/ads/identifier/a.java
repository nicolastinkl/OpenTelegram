package com.huawei.hms.ads.identifier;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.telegram.messenger.LiteMode;

/* loaded from: classes.dex */
public final class a implements ServiceConnection {
    public static final ThreadPoolExecutor a = new ThreadPoolExecutor(0, 3, 60, TimeUnit.SECONDS, new LinkedBlockingQueue(LiteMode.FLAG_AUTOPLAY_GIFS), new ThreadPoolExecutor.DiscardPolicy());
    boolean b = false;
    private final LinkedBlockingQueue<IBinder> c = new LinkedBlockingQueue<>(1);

    public IBinder a() throws InterruptedException {
        if (this.b) {
            throw new IllegalStateException();
        }
        this.b = true;
        return this.c.take();
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, final IBinder iBinder) {
        Log.d("PPSSerivceConnection", "onServiceConnected");
        a.execute(new Runnable() { // from class: com.huawei.hms.ads.identifier.a.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    Log.d("PPSSerivceConnection", "onServiceConnected " + System.currentTimeMillis());
                    a.this.c.offer(iBinder);
                } catch (Throwable th) {
                    Log.w("PPSSerivceConnection", "onServiceConnected  " + th.getClass().getSimpleName());
                }
            }
        });
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
        Log.d("PPSSerivceConnection", "onServiceDisconnected " + System.currentTimeMillis());
    }
}
