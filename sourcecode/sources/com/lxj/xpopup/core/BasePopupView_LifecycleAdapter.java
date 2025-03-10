package com.lxj.xpopup.core;

import androidx.lifecycle.GeneratedAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MethodCallsLogger;

/* loaded from: classes.dex */
public class BasePopupView_LifecycleAdapter implements GeneratedAdapter {
    final BasePopupView mReceiver;

    BasePopupView_LifecycleAdapter(BasePopupView receiver) {
        this.mReceiver = receiver;
    }

    @Override // androidx.lifecycle.GeneratedAdapter
    public void callMethods(LifecycleOwner owner, Lifecycle.Event event, boolean onAny, MethodCallsLogger logger) {
        boolean z = logger != null;
        if (!onAny && event == Lifecycle.Event.ON_DESTROY) {
            if (!z || logger.approveCall("onDestroy", 1)) {
                this.mReceiver.onDestroy();
            }
        }
    }
}
