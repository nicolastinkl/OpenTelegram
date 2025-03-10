package com.google.android.exoplayer2.util;

import android.text.TextUtils;
import java.net.UnknownHostException;

/* loaded from: classes.dex */
public final class Log {
    private static int logLevel = 0;
    private static boolean logStackTraces = true;
    private static final Object lock = new Object();
    private static Logger logger = Logger.DEFAULT;

    public interface Logger {
        public static final Logger DEFAULT = new Logger() { // from class: com.google.android.exoplayer2.util.Log.Logger.1
            @Override // com.google.android.exoplayer2.util.Log.Logger
            public void d(String str, String str2) {
                android.util.Log.d(str, str2);
            }

            @Override // com.google.android.exoplayer2.util.Log.Logger
            public void i(String str, String str2) {
                android.util.Log.i(str, str2);
            }

            @Override // com.google.android.exoplayer2.util.Log.Logger
            public void w(String str, String str2) {
                android.util.Log.w(str, str2);
            }

            @Override // com.google.android.exoplayer2.util.Log.Logger
            public void e(String str, String str2) {
                android.util.Log.e(str, str2);
            }
        };

        void d(String str, String str2);

        void e(String str, String str2);

        void i(String str, String str2);

        void w(String str, String str2);
    }

    public static void d(String str, String str2) {
        synchronized (lock) {
            if (logLevel == 0) {
                logger.d(str, str2);
            }
        }
    }

    public static void i(String str, String str2) {
        synchronized (lock) {
            if (logLevel <= 1) {
                logger.i(str, str2);
            }
        }
    }

    public static void i(String str, String str2, Throwable th) {
        i(str, appendThrowableString(str2, th));
    }

    public static void w(String str, String str2) {
        synchronized (lock) {
            if (logLevel <= 2) {
                logger.w(str, str2);
            }
        }
    }

    public static void w(String str, String str2, Throwable th) {
        w(str, appendThrowableString(str2, th));
    }

    public static void e(String str, String str2) {
        synchronized (lock) {
            if (logLevel <= 3) {
                logger.e(str, str2);
            }
        }
    }

    public static void e(String str, String str2, Throwable th) {
        e(str, appendThrowableString(str2, th));
    }

    public static String getThrowableString(Throwable th) {
        synchronized (lock) {
            if (th == null) {
                return null;
            }
            if (isCausedByUnknownHostException(th)) {
                return "UnknownHostException (no network)";
            }
            if (!logStackTraces) {
                return th.getMessage();
            }
            return android.util.Log.getStackTraceString(th).trim().replace("\t", "    ");
        }
    }

    private static String appendThrowableString(String str, Throwable th) {
        String throwableString = getThrowableString(th);
        if (TextUtils.isEmpty(throwableString)) {
            return str;
        }
        return str + "\n  " + throwableString.replace("\n", "\n  ") + '\n';
    }

    private static boolean isCausedByUnknownHostException(Throwable th) {
        while (th != null) {
            if (th instanceof UnknownHostException) {
                return true;
            }
            th = th.getCause();
        }
        return false;
    }
}
