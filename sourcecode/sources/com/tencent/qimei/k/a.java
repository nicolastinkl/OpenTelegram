package com.tencent.qimei.k;

import android.util.Log;
import com.tencent.qimei.log.IObservableLog;
import java.util.Locale;

/* compiled from: ELog.java */
/* loaded from: classes.dex */
public class a {
    public static boolean a = false;
    public static boolean b = true;
    public static IObservableLog c;

    public static synchronized void a(boolean z) {
        synchronized (a.class) {
            Log.i("qm", "beacon logAble: " + z);
            a = z;
        }
    }

    public static synchronized void b(boolean z) {
        synchronized (a.class) {
            b = z;
        }
    }

    public static synchronized boolean b() {
        boolean z;
        synchronized (a.class) {
            z = b;
        }
        return z;
    }

    public static synchronized boolean a() {
        boolean z;
        synchronized (a.class) {
            z = a;
        }
        return z;
    }

    public static void b(String str, String str2, Object... objArr) {
        if (a()) {
            Log.i("qm", a("Qm-Core-Info: " + str + " " + str2, objArr));
        }
    }

    public static void a(String str, String str2, Object... objArr) {
        if (a()) {
            Log.e("qm", a("Qm-Core-Error: " + str + " " + str2, objArr));
        }
    }

    public static void a(Throwable th) {
        if (th != null) {
            if (a()) {
                th.printStackTrace();
            } else {
                th.getMessage();
            }
        }
    }

    public static int a(StackTraceElement[] stackTraceElementArr, Class cls) {
        for (int i = 5; i < stackTraceElementArr.length; i++) {
            String className = stackTraceElementArr[i].getClassName();
            if (!(cls.equals(Log.class) && i < stackTraceElementArr.length - 1 && stackTraceElementArr[i + 1].getClassName().equals(Log.class.getName())) && className.equals(cls.getName())) {
                return i + 1;
            }
        }
        return -1;
    }

    public static String a(String str, Object... objArr) {
        String str2;
        String str3;
        str2 = "";
        if (b()) {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            int a2 = a(stackTrace, a.class);
            StackTraceElement stackTraceElement = (a2 == -1 && (a2 = a(stackTrace, Log.class)) == -1) ? null : stackTrace[a2];
            if (stackTraceElement != null) {
                String fileName = stackTraceElement.getFileName();
                str2 = fileName != null ? fileName : "";
                String methodName = stackTraceElement.getMethodName();
                if (methodName.contains("$")) {
                    methodName = methodName.substring(methodName.indexOf("$") + 1, methodName.lastIndexOf("$") - 2);
                }
                str2 = "(" + str2 + ":" + stackTraceElement.getLineNumber() + ")" + methodName + " ";
            }
        }
        if (str == null) {
            str3 = str2 + "msg is null";
        } else if (objArr != null && objArr.length != 0) {
            str3 = str2 + String.format(Locale.US, str, objArr);
        } else {
            str3 = str2 + str;
        }
        IObservableLog iObservableLog = c;
        if (iObservableLog != null) {
            iObservableLog.onLog(str3);
        }
        return str3;
    }
}
