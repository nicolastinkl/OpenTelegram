package org.slf4j;

/* loaded from: classes3.dex */
public interface Logger {
    void debug(String str);

    void debug(String str, Object obj);

    void debug(String str, Object obj, Object obj2);

    void debug(String str, Throwable th);

    void debug(String str, Object... objArr);

    void error(String str, Throwable th);

    String getName();

    void info(String str, Object obj);

    boolean isDebugEnabled();

    boolean isTraceEnabled();

    void trace(String str);

    void trace(String str, Object obj);

    void trace(String str, Object obj, Object obj2);

    void trace(String str, Object... objArr);

    void warn(String str);

    void warn(String str, Object obj);

    void warn(String str, Throwable th);

    void warn(String str, Object... objArr);
}
