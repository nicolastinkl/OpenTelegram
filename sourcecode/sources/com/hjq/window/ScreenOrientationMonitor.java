package com.hjq.window;

import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;

/* loaded from: classes.dex */
final class ScreenOrientationMonitor implements ComponentCallbacks {
    private OnScreenOrientationCallback mCallback;
    private int mScreenOrientation;

    interface OnScreenOrientationCallback {

        /* renamed from: com.hjq.window.ScreenOrientationMonitor$OnScreenOrientationCallback$-CC, reason: invalid class name */
        public final /* synthetic */ class CC {
            public static void $default$onScreenOrientationChange(OnScreenOrientationCallback onScreenOrientationCallback, int i) {
            }
        }

        void onScreenOrientationChange(int i);
    }

    @Override // android.content.ComponentCallbacks
    public void onLowMemory() {
    }

    public ScreenOrientationMonitor(Configuration configuration) {
        this.mScreenOrientation = configuration.orientation;
    }

    void registerCallback(Context context, OnScreenOrientationCallback onScreenOrientationCallback) {
        context.getApplicationContext().registerComponentCallbacks(this);
        this.mCallback = onScreenOrientationCallback;
    }

    void unregisterCallback(Context context) {
        context.getApplicationContext().unregisterComponentCallbacks(this);
        this.mCallback = null;
    }

    @Override // android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        int i = this.mScreenOrientation;
        int i2 = configuration.orientation;
        if (i == i2) {
            return;
        }
        this.mScreenOrientation = i2;
        OnScreenOrientationCallback onScreenOrientationCallback = this.mCallback;
        if (onScreenOrientationCallback == null) {
            return;
        }
        onScreenOrientationCallback.onScreenOrientationChange(i2);
    }
}
