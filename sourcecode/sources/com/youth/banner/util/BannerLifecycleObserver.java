package com.youth.banner.util;

import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

/* loaded from: classes.dex */
public interface BannerLifecycleObserver extends LifecycleObserver {
    void onDestroy(LifecycleOwner lifecycleOwner);

    void onStart(LifecycleOwner lifecycleOwner);

    void onStop(LifecycleOwner lifecycleOwner);
}
