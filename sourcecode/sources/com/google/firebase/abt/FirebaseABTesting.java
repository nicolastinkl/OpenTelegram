package com.google.firebase.abt;

import android.content.Context;
import com.google.firebase.analytics.connector.AnalyticsConnector;
import com.google.firebase.inject.Provider;

/* loaded from: classes.dex */
public class FirebaseABTesting {
    private final Provider<AnalyticsConnector> analyticsConnector;

    public FirebaseABTesting(Context context, Provider<AnalyticsConnector> provider, String str) {
        this.analyticsConnector = provider;
    }
}
