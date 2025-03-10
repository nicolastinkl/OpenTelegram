package org.slf4j.event;

import java.util.Queue;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.helpers.MessageFormatter;
import org.slf4j.helpers.SubstituteLogger;

/* loaded from: classes3.dex */
public class EventRecodingLogger implements Logger {
    Queue<SubstituteLoggingEvent> eventQueue;
    SubstituteLogger logger;
    String name;

    @Override // org.slf4j.Logger
    public boolean isDebugEnabled() {
        return true;
    }

    @Override // org.slf4j.Logger
    public boolean isTraceEnabled() {
        return true;
    }

    public EventRecodingLogger(SubstituteLogger substituteLogger, Queue<SubstituteLoggingEvent> queue) {
        this.logger = substituteLogger;
        this.name = substituteLogger.getName();
        this.eventQueue = queue;
    }

    @Override // org.slf4j.Logger
    public String getName() {
        return this.name;
    }

    @Override // org.slf4j.Logger
    public void trace(String str) {
        recordEvent_0Args(Level.TRACE, null, str, null);
    }

    @Override // org.slf4j.Logger
    public void trace(String str, Object obj) {
        recordEvent_1Args(Level.TRACE, null, str, obj);
    }

    @Override // org.slf4j.Logger
    public void trace(String str, Object obj, Object obj2) {
        recordEvent2Args(Level.TRACE, null, str, obj, obj2);
    }

    @Override // org.slf4j.Logger
    public void trace(String str, Object... objArr) {
        recordEventArgArray(Level.TRACE, null, str, objArr);
    }

    @Override // org.slf4j.Logger
    public void debug(String str) {
        recordEvent_0Args(Level.DEBUG, null, str, null);
    }

    @Override // org.slf4j.Logger
    public void debug(String str, Object obj) {
        recordEvent_1Args(Level.DEBUG, null, str, obj);
    }

    @Override // org.slf4j.Logger
    public void debug(String str, Object obj, Object obj2) {
        recordEvent2Args(Level.DEBUG, null, str, obj, obj2);
    }

    @Override // org.slf4j.Logger
    public void debug(String str, Object... objArr) {
        recordEventArgArray(Level.DEBUG, null, str, objArr);
    }

    @Override // org.slf4j.Logger
    public void debug(String str, Throwable th) {
        recordEvent_0Args(Level.DEBUG, null, str, th);
    }

    @Override // org.slf4j.Logger
    public void info(String str, Object obj) {
        recordEvent_1Args(Level.INFO, null, str, obj);
    }

    @Override // org.slf4j.Logger
    public void warn(String str) {
        recordEvent_0Args(Level.WARN, null, str, null);
    }

    @Override // org.slf4j.Logger
    public void warn(String str, Object obj) {
        recordEvent_1Args(Level.WARN, null, str, obj);
    }

    @Override // org.slf4j.Logger
    public void warn(String str, Object... objArr) {
        recordEventArgArray(Level.WARN, null, str, objArr);
    }

    @Override // org.slf4j.Logger
    public void warn(String str, Throwable th) {
        recordEvent_0Args(Level.WARN, null, str, th);
    }

    @Override // org.slf4j.Logger
    public void error(String str, Throwable th) {
        recordEvent_0Args(Level.ERROR, null, str, th);
    }

    private void recordEvent_0Args(Level level, Marker marker, String str, Throwable th) {
        recordEvent(level, marker, str, null, th);
    }

    private void recordEvent_1Args(Level level, Marker marker, String str, Object obj) {
        recordEvent(level, marker, str, new Object[]{obj}, null);
    }

    private void recordEvent2Args(Level level, Marker marker, String str, Object obj, Object obj2) {
        if (obj2 instanceof Throwable) {
            recordEvent(level, marker, str, new Object[]{obj}, (Throwable) obj2);
        } else {
            recordEvent(level, marker, str, new Object[]{obj, obj2}, null);
        }
    }

    private void recordEventArgArray(Level level, Marker marker, String str, Object[] objArr) {
        Throwable throwableCandidate = MessageFormatter.getThrowableCandidate(objArr);
        if (throwableCandidate != null) {
            recordEvent(level, marker, str, MessageFormatter.trimmedCopy(objArr), throwableCandidate);
        } else {
            recordEvent(level, marker, str, objArr, null);
        }
    }

    private void recordEvent(Level level, Marker marker, String str, Object[] objArr, Throwable th) {
        SubstituteLoggingEvent substituteLoggingEvent = new SubstituteLoggingEvent();
        substituteLoggingEvent.setTimeStamp(System.currentTimeMillis());
        substituteLoggingEvent.setLevel(level);
        substituteLoggingEvent.setLogger(this.logger);
        substituteLoggingEvent.setLoggerName(this.name);
        substituteLoggingEvent.setMarker(marker);
        substituteLoggingEvent.setMessage(str);
        substituteLoggingEvent.setThreadName(Thread.currentThread().getName());
        substituteLoggingEvent.setArgumentArray(objArr);
        substituteLoggingEvent.setThrowable(th);
        this.eventQueue.add(substituteLoggingEvent);
    }
}
