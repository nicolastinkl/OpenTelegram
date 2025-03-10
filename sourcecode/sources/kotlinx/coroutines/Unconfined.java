package kotlinx.coroutines;

import kotlin.coroutines.CoroutineContext;

/* compiled from: Unconfined.kt */
/* loaded from: classes.dex */
public final class Unconfined extends CoroutineDispatcher {
    public static final Unconfined INSTANCE = new Unconfined();

    @Override // kotlinx.coroutines.CoroutineDispatcher
    public boolean isDispatchNeeded(CoroutineContext coroutineContext) {
        return false;
    }

    @Override // kotlinx.coroutines.CoroutineDispatcher
    public String toString() {
        return "Dispatchers.Unconfined";
    }

    private Unconfined() {
    }

    @Override // kotlinx.coroutines.CoroutineDispatcher
    /* renamed from: dispatch */
    public void mo168dispatch(CoroutineContext coroutineContext, Runnable runnable) {
        if (((YieldContext) coroutineContext.get(YieldContext.Key)) == null) {
            throw new UnsupportedOperationException("Dispatchers.Unconfined.dispatch function can only be used by the yield function. If you wrap Unconfined dispatcher in your code, make sure you properly delegate isDispatchNeeded and dispatch calls.");
        }
    }
}
