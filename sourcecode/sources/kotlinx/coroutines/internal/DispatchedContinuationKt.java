package kotlinx.coroutines.internal;

import java.util.concurrent.CancellationException;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.CompletionStateKt;
import kotlinx.coroutines.CoroutineContextKt;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.EventLoop;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.ThreadLocalEventLoop;
import kotlinx.coroutines.UndispatchedCoroutine;

/* compiled from: DispatchedContinuation.kt */
/* loaded from: classes3.dex */
public final class DispatchedContinuationKt {
    private static final Symbol UNDEFINED = new Symbol("UNDEFINED");
    public static final Symbol REUSABLE_CLAIMED = new Symbol("REUSABLE_CLAIMED");

    public static /* synthetic */ void resumeCancellableWith$default(Continuation continuation, Object obj, Function1 function1, int i, Object obj2) {
        if ((i & 2) != 0) {
            function1 = null;
        }
        resumeCancellableWith(continuation, obj, function1);
    }

    /* JADX WARN: Finally extract failed */
    public static final <T> void resumeCancellableWith(Continuation<? super T> continuation, Object obj, Function1<? super Throwable, Unit> function1) {
        boolean z;
        if (continuation instanceof DispatchedContinuation) {
            DispatchedContinuation dispatchedContinuation = (DispatchedContinuation) continuation;
            Object state = CompletionStateKt.toState(obj, function1);
            if (dispatchedContinuation.dispatcher.isDispatchNeeded(dispatchedContinuation.getContext())) {
                dispatchedContinuation._state = state;
                dispatchedContinuation.resumeMode = 1;
                dispatchedContinuation.dispatcher.mo168dispatch(dispatchedContinuation.getContext(), dispatchedContinuation);
                return;
            }
            DebugKt.getASSERTIONS_ENABLED();
            EventLoop eventLoop$kotlinx_coroutines_core = ThreadLocalEventLoop.INSTANCE.getEventLoop$kotlinx_coroutines_core();
            if (!eventLoop$kotlinx_coroutines_core.isUnconfinedLoopActive()) {
                eventLoop$kotlinx_coroutines_core.incrementUseCount(true);
                try {
                    Job job = (Job) dispatchedContinuation.getContext().get(Job.Key);
                    if (job == null || job.isActive()) {
                        z = false;
                    } else {
                        CancellationException cancellationException = job.getCancellationException();
                        dispatchedContinuation.cancelCompletedResult$kotlinx_coroutines_core(state, cancellationException);
                        Result.Companion companion = Result.Companion;
                        dispatchedContinuation.resumeWith(Result.m164constructorimpl(ResultKt.createFailure(cancellationException)));
                        z = true;
                    }
                    if (!z) {
                        Continuation<T> continuation2 = dispatchedContinuation.continuation;
                        Object obj2 = dispatchedContinuation.countOrElement;
                        CoroutineContext context = continuation2.getContext();
                        Object updateThreadContext = ThreadContextKt.updateThreadContext(context, obj2);
                        UndispatchedCoroutine<?> updateUndispatchedCompletion = updateThreadContext != ThreadContextKt.NO_THREAD_ELEMENTS ? CoroutineContextKt.updateUndispatchedCompletion(continuation2, context, updateThreadContext) : null;
                        try {
                            dispatchedContinuation.continuation.resumeWith(obj);
                            Unit unit = Unit.INSTANCE;
                            if (updateUndispatchedCompletion == null || updateUndispatchedCompletion.clearThreadContext()) {
                                ThreadContextKt.restoreThreadContext(context, updateThreadContext);
                            }
                        } catch (Throwable th) {
                            if (updateUndispatchedCompletion == null || updateUndispatchedCompletion.clearThreadContext()) {
                                ThreadContextKt.restoreThreadContext(context, updateThreadContext);
                            }
                            throw th;
                        }
                    }
                    while (eventLoop$kotlinx_coroutines_core.processUnconfinedEvent()) {
                    }
                } finally {
                    try {
                        return;
                    } finally {
                    }
                }
                return;
            }
            dispatchedContinuation._state = state;
            dispatchedContinuation.resumeMode = 1;
            eventLoop$kotlinx_coroutines_core.dispatchUnconfined(dispatchedContinuation);
            return;
        }
        continuation.resumeWith(obj);
    }
}
