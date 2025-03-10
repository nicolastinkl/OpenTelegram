package com.tencent.qmsp.sdk.g.b;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.telegram.messenger.LiteMode;

/* loaded from: classes.dex */
public final class b implements ServiceConnection {
    public static final ThreadPoolExecutor c = new ThreadPoolExecutor(0, 3, 60, TimeUnit.SECONDS, new LinkedBlockingQueue(LiteMode.FLAG_AUTOPLAY_GIFS), new ThreadPoolExecutor.DiscardPolicy());
    public final LinkedBlockingQueue<IBinder> b = new LinkedBlockingQueue<>(1);
    public boolean a = false;

    public class a implements Runnable {
        public final IBinder a;

        public a(IBinder iBinder) {
            this.a = iBinder;
        }

        @Override // java.lang.Runnable
        public final void run() {
            try {
                b.this.b.offer(this.a);
            } catch (Throwable unused) {
            }
        }
    }

    @Override // android.content.ServiceConnection
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("onServiceConnected ");
            sb.append(System.currentTimeMillis());
            Log.d("PPSSerivceConnection", sb.toString());
            c.execute(new a(iBinder));
        } catch (Throwable unused) {
            Log.w("PPSSerivceConnection", "onServiceConnected InterruptedException " + System.currentTimeMillis());
        }
    }

    @Override // android.content.ServiceConnection
    public final void onServiceDisconnected(ComponentName componentName) {
        Log.d("PPSSerivceConnection", "onServiceDisconnected " + System.currentTimeMillis());
    }
}
