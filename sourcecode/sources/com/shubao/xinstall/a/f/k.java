package com.shubao.xinstall.a.f;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public final class k {
    private static final ThreadPoolExecutor a = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), Runtime.getRuntime().availableProcessors() * 4, 60, TimeUnit.SECONDS, new LinkedBlockingQueue());
    private static final ThreadPoolExecutor b = new ThreadPoolExecutor(1, 1, 60, TimeUnit.MILLISECONDS, new LinkedBlockingQueue());
    private static final Executor c = new a();
    private static final ScheduledExecutorService d = Executors.newSingleThreadScheduledExecutor();

    static class a implements Executor {
        private Handler a = new Handler(Looper.getMainLooper());

        a() {
        }

        @Override // java.util.concurrent.Executor
        public final void execute(Runnable runnable) {
            this.a.post(runnable);
        }
    }

    public static ThreadPoolExecutor a() {
        return a;
    }

    public static Executor b() {
        return c;
    }
}
