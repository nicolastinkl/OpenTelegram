package com.google.android.datatransport.runtime.backends;

import com.google.auto.value.AutoValue;

@AutoValue
/* loaded from: classes.dex */
public abstract class BackendResponse {

    public enum Status {
        OK,
        TRANSIENT_ERROR,
        FATAL_ERROR,
        INVALID_PAYLOAD
    }

    public abstract long getNextRequestWaitMillis();

    public abstract Status getStatus();

    public static BackendResponse transientError() {
        return new AutoValue_BackendResponse(Status.TRANSIENT_ERROR, -1L);
    }

    public static BackendResponse fatalError() {
        return new AutoValue_BackendResponse(Status.FATAL_ERROR, -1L);
    }

    public static BackendResponse invalidPayload() {
        return new AutoValue_BackendResponse(Status.INVALID_PAYLOAD, -1L);
    }

    public static BackendResponse ok(long j) {
        return new AutoValue_BackendResponse(Status.OK, j);
    }
}
