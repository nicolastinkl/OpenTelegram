package io.openinstall.sdk;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class fc {
    private static final ThreadFactory a;
    private static final RejectedExecutionHandler b;
    private static final ThreadPoolExecutor c;
    private static final ThreadPoolExecutor d;

    static {
        fd fdVar = new fd();
        a = fdVar;
        fe feVar = new fe();
        b = feVar;
        TimeUnit timeUnit = TimeUnit.SECONDS;
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 10L, timeUnit, new LinkedBlockingQueue(30), fdVar, feVar);
        c = threadPoolExecutor;
        ThreadPoolExecutor threadPoolExecutor2 = new ThreadPoolExecutor(3, 10, 10L, timeUnit, new LinkedBlockingQueue(30), fdVar, feVar);
        d = threadPoolExecutor2;
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        threadPoolExecutor2.allowCoreThreadTimeOut(true);
    }

    public static ThreadPoolExecutor a() {
        return d;
    }

    public static ThreadPoolExecutor b() {
        return c;
    }
}
