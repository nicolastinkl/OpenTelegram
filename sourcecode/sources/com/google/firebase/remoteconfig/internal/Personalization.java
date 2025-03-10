package com.google.firebase.remoteconfig.internal;

import com.google.firebase.analytics.connector.AnalyticsConnector;
import com.google.firebase.inject.Provider;
import j$.util.DesugarCollections;
import java.util.HashMap;

/* loaded from: classes.dex */
public class Personalization {
    private final Provider<AnalyticsConnector> analyticsConnector;

    public Personalization(Provider<AnalyticsConnector> provider) {
        DesugarCollections.synchronizedMap(new HashMap());
        this.analyticsConnector = provider;
    }
}
