package com.tencent.qcloud.core.task;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
final class UIThreadExecutor implements Executor {
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    UIThreadExecutor() {
    }

    @Override // java.util.concurrent.Executor
    public final void execute(Runnable runnable) {
        this.mHandler.post(runnable);
    }
}
