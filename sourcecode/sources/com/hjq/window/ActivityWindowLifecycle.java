package com.hjq.window;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;

/* loaded from: classes.dex */
final class ActivityWindowLifecycle implements Application.ActivityLifecycleCallbacks {
    private Activity mActivity;
    private EasyWindow<?> mWindow;

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
    }

    ActivityWindowLifecycle(EasyWindow<?> easyWindow, Activity activity) {
        this.mActivity = activity;
        this.mWindow = easyWindow;
    }

    void register() {
        Activity activity = this.mActivity;
        if (activity == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= 29) {
            activity.registerActivityLifecycleCallbacks(this);
        } else {
            activity.getApplication().registerActivityLifecycleCallbacks(this);
        }
    }

    void unregister() {
        Activity activity = this.mActivity;
        if (activity == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= 29) {
            activity.unregisterActivityLifecycleCallbacks(this);
        } else {
            activity.getApplication().unregisterActivityLifecycleCallbacks(this);
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
        EasyWindow<?> easyWindow;
        Activity activity2 = this.mActivity;
        if (activity2 == activity && activity2.isFinishing() && (easyWindow = this.mWindow) != null && easyWindow.isShowing()) {
            this.mWindow.cancel();
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(Activity activity) {
        if (this.mActivity != activity) {
            return;
        }
        this.mActivity = null;
        EasyWindow<?> easyWindow = this.mWindow;
        if (easyWindow == null) {
            return;
        }
        easyWindow.recycle();
        this.mWindow = null;
    }
}
