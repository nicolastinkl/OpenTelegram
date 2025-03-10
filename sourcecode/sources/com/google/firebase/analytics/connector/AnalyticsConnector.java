package com.google.firebase.analytics.connector;

import android.os.Bundle;

/* compiled from: com.google.firebase:firebase-measurement-connector@@19.0.0 */
/* loaded from: classes.dex */
public interface AnalyticsConnector {
    void logEvent(String str, String str2, Bundle bundle);

    void setUserProperty(String str, String str2, Object obj);
}
