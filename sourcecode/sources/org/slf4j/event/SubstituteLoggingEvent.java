package org.slf4j.event;

import org.slf4j.Marker;
import org.slf4j.helpers.SubstituteLogger;

/* loaded from: classes3.dex */
public class SubstituteLoggingEvent implements LoggingEvent {
    Object[] argArray;
    SubstituteLogger logger;

    public void setLevel(Level level) {
    }

    public void setLoggerName(String str) {
    }

    public void setMarker(Marker marker) {
    }

    public void setMessage(String str) {
    }

    public void setThreadName(String str) {
    }

    public void setThrowable(Throwable th) {
    }

    public void setTimeStamp(long j) {
    }

    public SubstituteLogger getLogger() {
        return this.logger;
    }

    public void setLogger(SubstituteLogger substituteLogger) {
        this.logger = substituteLogger;
    }

    public void setArgumentArray(Object[] objArr) {
        this.argArray = objArr;
    }
}
