package com.google.firebase.messaging;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

/* compiled from: com.google.firebase:firebase-messaging@@22.0.0 */
/* loaded from: classes.dex */
class FcmLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
    private final Set<Intent> seenIntents = Collections.newSetFromMap(new WeakHashMap());

    FcmLifecycleCallbacks() {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle bundle) {
        Bundle extras;
        Intent intent = activity.getIntent();
        if (intent == null || !this.seenIntents.add(intent) || (extras = intent.getExtras()) == null) {
            return;
        }
        Bundle bundle2 = extras.getBundle("gcm.n.analytics_data");
        if (MessagingAnalytics.shouldUploadScionMetrics(bundle2)) {
            MessagingAnalytics.logNotificationOpen(bundle2);
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
        if (activity.isFinishing()) {
            this.seenIntents.remove(activity.getIntent());
        }
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
}
