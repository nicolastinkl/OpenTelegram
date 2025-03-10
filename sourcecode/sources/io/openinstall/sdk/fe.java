package io.openinstall.sdk;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/* loaded from: classes.dex */
class fe implements RejectedExecutionHandler {
    fe() {
    }

    @Override // java.util.concurrent.RejectedExecutionHandler
    public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
        if (ga.a) {
            ga.b("Task rejected by " + threadPoolExecutor.toString(), new Object[0]);
        }
    }
}
