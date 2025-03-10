package com.tencent.qcloud.core.task;

import bolts.CancellationToken;
import bolts.CancellationTokenSource;
import bolts.Continuation;
import bolts.ExecutorException;
import bolts.Task;
import bolts.TaskCompletionSource;
import com.tencent.qcloud.core.common.QCloudClientException;
import com.tencent.qcloud.core.common.QCloudProgressListener;
import com.tencent.qcloud.core.common.QCloudResultListener;
import com.tencent.qcloud.core.common.QCloudServiceException;
import com.tencent.qcloud.core.common.QCloudTaskStateListener;
import com.tencent.qcloud.core.logger.QCloudLogger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes.dex */
public abstract class QCloudTask<T> implements Callable<T> {
    private final String identifier;
    private CancellationTokenSource mCancellationTokenSource;
    private int mState;
    private Task<T> mTask;
    private Executor observerExecutor;
    private OnRequestWeightListener onRequestWeightListener;
    private final Object tag;
    private boolean enableTraffic = true;
    private Set<QCloudResultListener<T>> mResultListeners = new HashSet(2);
    private Set<QCloudProgressListener> mProgressListeners = new HashSet(2);
    private Set<QCloudTaskStateListener> mStateListeners = new HashSet(2);
    private TaskManager taskManager = TaskManager.getInstance();

    public interface OnRequestWeightListener {
        int onWeight();
    }

    protected abstract T execute() throws QCloudClientException, QCloudServiceException;

    public QCloudTask(String str, Object obj) {
        this.identifier = str;
        this.tag = obj;
    }

    public final T executeNow() throws QCloudClientException, QCloudServiceException {
        executeNowSilently();
        Exception exception = getException();
        if (exception != null) {
            if (exception instanceof QCloudClientException) {
                throw ((QCloudClientException) exception);
            }
            if (exception instanceof QCloudServiceException) {
                throw ((QCloudServiceException) exception);
            }
            throw new QCloudClientException(exception);
        }
        return getResult();
    }

    public final void executeNowSilently() {
        this.taskManager.add(this);
        onStateChanged(1);
        this.mTask = Task.call(this);
    }

    protected QCloudTask<T> scheduleOn(Executor executor, CancellationTokenSource cancellationTokenSource, int i) {
        this.taskManager.add(this);
        onStateChanged(1);
        this.mCancellationTokenSource = cancellationTokenSource;
        if (i <= 0) {
            i = 2;
        }
        Task<T> callTask = callTask(this, executor, cancellationTokenSource != null ? cancellationTokenSource.getToken() : null, i);
        this.mTask = callTask;
        callTask.continueWithTask(new Continuation<T, Task<Void>>() { // from class: com.tencent.qcloud.core.task.QCloudTask.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // bolts.Continuation
            public Task<Void> then(Task<T> task) throws Exception {
                if (task.isFaulted() || task.isCancelled()) {
                    if (QCloudTask.this.observerExecutor == null) {
                        try {
                            QCloudTask.this.onFailure();
                            return null;
                        } catch (Exception e) {
                            e.printStackTrace();
                            throw new Error(e);
                        }
                    }
                    return Task.call(new Callable<Void>() { // from class: com.tencent.qcloud.core.task.QCloudTask.1.1
                        @Override // java.util.concurrent.Callable
                        public Void call() throws Exception {
                            try {
                                QCloudTask.this.onFailure();
                                return null;
                            } catch (Exception e2) {
                                e2.printStackTrace();
                                throw new Error(e2);
                            }
                        }
                    }, QCloudTask.this.observerExecutor);
                }
                if (QCloudTask.this.observerExecutor == null) {
                    try {
                        QCloudTask.this.onSuccess();
                        return null;
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        throw new Error(e2);
                    }
                }
                return Task.call(new Callable<Void>() { // from class: com.tencent.qcloud.core.task.QCloudTask.1.2
                    @Override // java.util.concurrent.Callable
                    public Void call() throws Exception {
                        try {
                            QCloudTask.this.onSuccess();
                            return null;
                        } catch (Exception e3) {
                            e3.printStackTrace();
                            throw new Error(e3);
                        }
                    }
                }, QCloudTask.this.observerExecutor);
            }
        });
        return this;
    }

    private static <TResult> Task<TResult> callTask(Callable<TResult> callable, Executor executor, CancellationToken cancellationToken, int i) {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        try {
            executor.execute(new AtomTask(taskCompletionSource, cancellationToken, callable, i));
        } catch (Exception e) {
            taskCompletionSource.setError(new ExecutorException(e));
        }
        return taskCompletionSource.getTask();
    }

    public void cancel() {
        QCloudLogger.d("QCloudTask", "[Call] %s cancel", this);
        CancellationTokenSource cancellationTokenSource = this.mCancellationTokenSource;
        if (cancellationTokenSource != null) {
            cancellationTokenSource.cancel();
        }
    }

    public void setTransferThreadControl(boolean z) {
        this.enableTraffic = z;
    }

    public boolean isEnableTraffic() {
        return this.enableTraffic;
    }

    public final boolean isCanceled() {
        CancellationTokenSource cancellationTokenSource = this.mCancellationTokenSource;
        return cancellationTokenSource != null && cancellationTokenSource.isCancellationRequested();
    }

    @Override // java.util.concurrent.Callable
    public T call() throws Exception {
        try {
            QCloudLogger.d("QCloudTask", "[Task] %s start testExecute", getIdentifier());
            onStateChanged(2);
            T execute = execute();
            QCloudLogger.d("QCloudTask", "[Task] %s complete", getIdentifier());
            onStateChanged(3);
            this.taskManager.remove(this);
            return execute;
        } catch (Throwable th) {
            QCloudLogger.d("QCloudTask", "[Task] %s complete", getIdentifier());
            onStateChanged(3);
            this.taskManager.remove(this);
            throw th;
        }
    }

    public final QCloudTask<T> observeOn(Executor executor) {
        this.observerExecutor = executor;
        return this;
    }

    public final QCloudTask<T> addResultListener(QCloudResultListener<T> qCloudResultListener) {
        if (qCloudResultListener != null) {
            this.mResultListeners.add(qCloudResultListener);
        }
        return this;
    }

    public final QCloudTask<T> addProgressListener(QCloudProgressListener qCloudProgressListener) {
        if (qCloudProgressListener != null) {
            this.mProgressListeners.add(qCloudProgressListener);
        }
        return this;
    }

    public final QCloudTask<T> addStateListener(QCloudTaskStateListener qCloudTaskStateListener) {
        if (qCloudTaskStateListener != null) {
            this.mStateListeners.add(qCloudTaskStateListener);
        }
        return this;
    }

    public T getResult() {
        return this.mTask.getResult();
    }

    public Exception getException() {
        if (this.mTask.isFaulted()) {
            return this.mTask.getError();
        }
        if (this.mTask.isCancelled()) {
            return new QCloudClientException("canceled");
        }
        return null;
    }

    protected void onSuccess() {
        if (this.mResultListeners.size() > 0) {
            Iterator it = new ArrayList(this.mResultListeners).iterator();
            while (it.hasNext()) {
                ((QCloudResultListener) it.next()).onSuccess(getResult());
            }
        }
    }

    protected void onFailure() {
        Exception exception = getException();
        if (exception == null || this.mResultListeners.size() <= 0) {
            return;
        }
        for (QCloudResultListener qCloudResultListener : new ArrayList(this.mResultListeners)) {
            if (exception instanceof QCloudClientException) {
                qCloudResultListener.onFailure((QCloudClientException) exception, null);
            } else if (exception instanceof QCloudServiceException) {
                qCloudResultListener.onFailure(null, (QCloudServiceException) exception);
            } else {
                qCloudResultListener.onFailure(new QCloudClientException(exception.getCause() == null ? exception : exception.getCause()), null);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onProgress(final long j, final long j2) {
        if (this.mProgressListeners.size() > 0) {
            executeListener(new Runnable() { // from class: com.tencent.qcloud.core.task.QCloudTask.2
                @Override // java.lang.Runnable
                public void run() {
                    Iterator it = new ArrayList(QCloudTask.this.mProgressListeners).iterator();
                    while (it.hasNext()) {
                        ((QCloudProgressListener) it.next()).onProgress(j, j2);
                    }
                }
            });
        }
    }

    protected void onStateChanged(int i) {
        setState(i);
        if (this.mStateListeners.size() > 0) {
            executeListener(new Runnable() { // from class: com.tencent.qcloud.core.task.QCloudTask.3
                @Override // java.lang.Runnable
                public void run() {
                    Iterator it = new ArrayList(QCloudTask.this.mStateListeners).iterator();
                    while (it.hasNext()) {
                        ((QCloudTaskStateListener) it.next()).onStateChanged(QCloudTask.this.identifier, QCloudTask.this.mState);
                    }
                }
            });
        }
    }

    private synchronized void setState(int i) {
        this.mState = i;
    }

    private void executeListener(Runnable runnable) {
        Executor executor = this.observerExecutor;
        if (executor != null) {
            executor.execute(runnable);
        } else {
            runnable.run();
        }
    }

    public final String getIdentifier() {
        return this.identifier;
    }

    public final Object getTag() {
        return this.tag;
    }

    public int getWeight() {
        OnRequestWeightListener onRequestWeightListener = this.onRequestWeightListener;
        if (onRequestWeightListener != null) {
            return onRequestWeightListener.onWeight();
        }
        return 0;
    }

    public void setOnRequestWeightListener(OnRequestWeightListener onRequestWeightListener) {
        this.onRequestWeightListener = onRequestWeightListener;
    }

    private static class AtomTask<TResult> implements Runnable, Comparable<Runnable> {
        private static AtomicInteger increment = new AtomicInteger(0);
        private Callable<TResult> callable;
        private CancellationToken ct;
        private int priority;
        private int taskIdentifier = increment.addAndGet(1);
        private TaskCompletionSource<TResult> tcs;

        public AtomTask(TaskCompletionSource<TResult> taskCompletionSource, CancellationToken cancellationToken, Callable<TResult> callable, int i) {
            this.tcs = taskCompletionSource;
            this.ct = cancellationToken;
            this.callable = callable;
            this.priority = i;
        }

        @Override // java.lang.Runnable
        public void run() {
            CancellationToken cancellationToken = this.ct;
            if (cancellationToken != null && cancellationToken.isCancellationRequested()) {
                this.tcs.setCancelled();
                return;
            }
            try {
                this.tcs.setResult(this.callable.call());
            } catch (CancellationException unused) {
                this.tcs.setCancelled();
            } catch (Exception e) {
                this.tcs.setError(e);
            }
        }

        @Override // java.lang.Comparable
        public int compareTo(Runnable runnable) {
            if (!(runnable instanceof AtomTask)) {
                return 0;
            }
            AtomTask atomTask = (AtomTask) runnable;
            int i = atomTask.priority - this.priority;
            return i != 0 ? i : this.taskIdentifier - atomTask.taskIdentifier;
        }
    }
}
