package com.tencent.qcloud.core.logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public final class QCloudLogger {
    private static final List<LogAdapter> logAdapters = new ArrayList();

    public static void addAdapter(LogAdapter logAdapter) {
        if (logAdapter != null) {
            synchronized (LogAdapter.class) {
                boolean z = false;
                Iterator<LogAdapter> it = logAdapters.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    } else if (it.next().getClass().equals(logAdapter.getClass())) {
                        z = true;
                        break;
                    }
                }
                if (!z) {
                    logAdapters.add(logAdapter);
                }
            }
        }
    }

    public static <T extends LogAdapter> T getAdapter(Class<T> cls) {
        synchronized (LogAdapter.class) {
            Iterator<LogAdapter> it = logAdapters.iterator();
            while (it.hasNext()) {
                T t = (T) it.next();
                if (t.getClass().equals(cls)) {
                    return t;
                }
            }
            return null;
        }
    }

    public static void d(String str, String str2, Object... objArr) {
        print(3, str, null, str2, objArr);
    }

    public static void i(String str, String str2, Object... objArr) {
        print(4, str, null, str2, objArr);
    }

    public static void w(String str, String str2, Object... objArr) {
        print(5, str, null, str2, objArr);
    }

    private static void print(int i, String str, Throwable th, String str2, Object... objArr) {
        if (objArr != null) {
            try {
                if (objArr.length > 0) {
                    str2 = String.format(str2, objArr);
                }
            } catch (Exception unused) {
                str2 = str2 + ": !!!! Log format exception: ";
            }
        }
        synchronized (LogAdapter.class) {
            for (LogAdapter logAdapter : logAdapters) {
                if (logAdapter.isLoggable(i, str)) {
                    logAdapter.log(i, str, str2, th);
                }
            }
        }
    }
}
