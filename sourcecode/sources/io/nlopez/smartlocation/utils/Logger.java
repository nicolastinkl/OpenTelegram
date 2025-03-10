package io.nlopez.smartlocation.utils;

/* loaded from: classes.dex */
public interface Logger {
    void d(String str, Object... objArr);

    void e(String str, Object... objArr);

    void e(Throwable th, String str, Object... objArr);

    void i(String str, Object... objArr);

    void w(String str, Object... objArr);
}
