package com.google.mlkit.common.sdkinternal;

import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.mlkit.common.MlKitException;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: com.google.mlkit:common@@17.0.0 */
/* loaded from: classes.dex */
public abstract class ModelResource {
    private final AtomicInteger zza = new AtomicInteger(0);
    protected final TaskQueue taskQueue = new TaskQueue();
    private final AtomicBoolean zzb = new AtomicBoolean(false);

    public abstract void load() throws MlKitException;

    protected abstract void release();

    public void pin() {
        this.zza.incrementAndGet();
    }

    public void unpin(Executor executor) {
        Preconditions.checkState(this.zza.get() > 0);
        this.taskQueue.submit(executor, new Runnable(this) { // from class: com.google.mlkit.common.sdkinternal.zzk
            private final ModelResource zza;

            {
                this.zza = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                this.zza.zza();
            }
        });
    }

    public <T> Task<T> callAfterLoad(final Executor executor, final Callable<T> callable, final CancellationToken cancellationToken) {
        Preconditions.checkState(this.zza.get() > 0);
        if (cancellationToken.isCancellationRequested()) {
            return Tasks.forCanceled();
        }
        final CancellationTokenSource cancellationTokenSource = new CancellationTokenSource();
        final TaskCompletionSource taskCompletionSource = new TaskCompletionSource(cancellationTokenSource.getToken());
        this.taskQueue.submit(new Executor(executor, cancellationToken, cancellationTokenSource, taskCompletionSource) { // from class: com.google.mlkit.common.sdkinternal.zzm
            private final Executor zza;
            private final CancellationToken zzb;
            private final CancellationTokenSource zzc;
            private final TaskCompletionSource zzd;

            {
                this.zza = executor;
                this.zzb = cancellationToken;
                this.zzc = cancellationTokenSource;
                this.zzd = taskCompletionSource;
            }

            @Override // java.util.concurrent.Executor
            public final void execute(Runnable runnable) {
                Executor executor2 = this.zza;
                CancellationToken cancellationToken2 = this.zzb;
                CancellationTokenSource cancellationTokenSource2 = this.zzc;
                TaskCompletionSource taskCompletionSource2 = this.zzd;
                try {
                    executor2.execute(runnable);
                } catch (RuntimeException e) {
                    if (cancellationToken2.isCancellationRequested()) {
                        cancellationTokenSource2.cancel();
                    } else {
                        taskCompletionSource2.setException(e);
                    }
                    throw e;
                }
            }
        }, new Runnable(this, cancellationToken, cancellationTokenSource, callable, taskCompletionSource) { // from class: com.google.mlkit.common.sdkinternal.zzl
            private final ModelResource zza;
            private final CancellationToken zzb;
            private final CancellationTokenSource zzc;
            private final Callable zzd;
            private final TaskCompletionSource zze;

            {
                this.zza = this;
                this.zzb = cancellationToken;
                this.zzc = cancellationTokenSource;
                this.zzd = callable;
                this.zze = taskCompletionSource;
            }

            @Override // java.lang.Runnable
            public final void run() {
                this.zza.zza(this.zzb, this.zzc, this.zzd, this.zze);
            }
        });
        return taskCompletionSource.getTask();
    }

    public boolean isLoaded() {
        return this.zzb.get();
    }

    final /* synthetic */ void zza(CancellationToken cancellationToken, CancellationTokenSource cancellationTokenSource, Callable callable, TaskCompletionSource taskCompletionSource) {
        try {
            if (cancellationToken.isCancellationRequested()) {
                cancellationTokenSource.cancel();
                return;
            }
            try {
                if (!this.zzb.get()) {
                    load();
                    this.zzb.set(true);
                }
                if (cancellationToken.isCancellationRequested()) {
                    cancellationTokenSource.cancel();
                    return;
                }
                Object call = callable.call();
                if (cancellationToken.isCancellationRequested()) {
                    cancellationTokenSource.cancel();
                } else {
                    taskCompletionSource.setResult(call);
                }
            } catch (RuntimeException e) {
                throw new MlKitException("Internal error has occurred when executing ML Kit tasks", 13, e);
            }
        } catch (Exception e2) {
            if (cancellationToken.isCancellationRequested()) {
                cancellationTokenSource.cancel();
            } else {
                taskCompletionSource.setException(e2);
            }
        }
    }

    final /* synthetic */ void zza() {
        int decrementAndGet = this.zza.decrementAndGet();
        Preconditions.checkState(decrementAndGet >= 0);
        if (decrementAndGet == 0) {
            release();
            this.zzb.set(false);
        }
    }
}
