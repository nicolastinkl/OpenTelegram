package com.google.mlkit.common.sdkinternal;

import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.mlkit_common.zzan;
import com.google.mlkit.common.sdkinternal.TaskQueue;
import java.io.Closeable;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: com.google.mlkit:common@@17.0.0 */
/* loaded from: classes.dex */
public class TaskQueue {
    private boolean zzb;
    private final Object zza = new Object();
    private final Queue<zzb> zzc = new ArrayDeque();
    private final AtomicReference<Thread> zzd = new AtomicReference<>();

    /* compiled from: com.google.mlkit:common@@17.0.0 */
    private static class zzb {
        final Executor zza;
        final Runnable zzb;

        private zzb(Executor executor, Runnable runnable) {
            this.zza = executor;
            this.zzb = runnable;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: com.google.mlkit:common@@17.0.0 */
    class zza implements Closeable {
        private zza() {
            Preconditions.checkState(((Thread) TaskQueue.this.zzd.getAndSet(Thread.currentThread())) == null);
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public final void close() {
            TaskQueue.this.zzd.set(null);
            TaskQueue.this.zza();
        }
    }

    public void submit(Executor executor, Runnable runnable) {
        synchronized (this.zza) {
            if (this.zzb) {
                this.zzc.add(new zzb(executor, runnable));
            } else {
                this.zzb = true;
                zza(executor, runnable);
            }
        }
    }

    private final void zza(Executor executor, final Runnable runnable) {
        try {
            executor.execute(new Runnable(this, runnable) { // from class: com.google.mlkit.common.sdkinternal.zzp
                private final TaskQueue zza;
                private final Runnable zzb;

                {
                    this.zza = this;
                    this.zzb = runnable;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    TaskQueue taskQueue = this.zza;
                    Runnable runnable2 = this.zzb;
                    TaskQueue.zza zzaVar = new TaskQueue.zza();
                    try {
                        runnable2.run();
                        zzaVar.close();
                    } catch (Throwable th) {
                        try {
                            zzaVar.close();
                        } catch (Throwable th2) {
                            zzan.zza(th, th2);
                        }
                        throw th;
                    }
                }
            });
        } catch (RejectedExecutionException unused) {
            zza();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zza() {
        synchronized (this.zza) {
            if (this.zzc.isEmpty()) {
                this.zzb = false;
            } else {
                zzb remove = this.zzc.remove();
                zza(remove.zza, remove.zzb);
            }
        }
    }

    public void checkIsRunningOnCurrentThread() {
        Preconditions.checkState(Thread.currentThread().equals(this.zzd.get()));
    }
}
