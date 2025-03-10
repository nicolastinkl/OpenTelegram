package com.google.android.datatransport.cct.internal;

import java.util.List;
import java.util.Objects;

/* loaded from: classes.dex */
final class AutoValue_BatchedLogRequest extends BatchedLogRequest {
    private final List<LogRequest> logRequests;

    AutoValue_BatchedLogRequest(List<LogRequest> list) {
        Objects.requireNonNull(list, "Null logRequests");
        this.logRequests = list;
    }

    @Override // com.google.android.datatransport.cct.internal.BatchedLogRequest
    public List<LogRequest> getLogRequests() {
        return this.logRequests;
    }

    public String toString() {
        return "BatchedLogRequest{logRequests=" + this.logRequests + "}";
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof BatchedLogRequest) {
            return this.logRequests.equals(((BatchedLogRequest) obj).getLogRequests());
        }
        return false;
    }

    public int hashCode() {
        return this.logRequests.hashCode() ^ 1000003;
    }
}
