package com.google.firebase.remoteconfig.internal;

import android.content.SharedPreferences;
import java.util.Date;

/* loaded from: classes.dex */
public class ConfigMetadataClient {
    private final SharedPreferences frcMetadata;

    static {
        new Date(-1L);
        new Date(-1L);
    }

    public ConfigMetadataClient(SharedPreferences sharedPreferences) {
        this.frcMetadata = sharedPreferences;
    }

    public long getFetchTimeoutInSeconds() {
        return this.frcMetadata.getLong("fetch_timeout_in_seconds", 60L);
    }
}
