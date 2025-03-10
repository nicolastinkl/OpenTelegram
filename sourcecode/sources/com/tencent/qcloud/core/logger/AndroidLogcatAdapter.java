package com.tencent.qcloud.core.logger;

import android.text.TextUtils;
import android.util.Log;

/* loaded from: classes.dex */
public final class AndroidLogcatAdapter implements LogAdapter {
    @Override // com.tencent.qcloud.core.logger.LogAdapter
    public boolean isLoggable(int i, String str) {
        if (TextUtils.isEmpty(str) || str.length() >= 23) {
            return false;
        }
        return Log.isLoggable(str, i);
    }

    @Override // com.tencent.qcloud.core.logger.LogAdapter
    public void log(int i, String str, String str2, Throwable th) {
        if (i == 2) {
            v(str, str2, th);
            return;
        }
        if (i == 3) {
            d(str, str2, th);
            return;
        }
        if (i == 4) {
            i(str, str2, th);
        } else if (i == 5) {
            w(str, str2, th);
        } else {
            if (i != 6) {
                return;
            }
            e(str, str2, th);
        }
    }

    private int v(String str, String str2, Throwable th) {
        if (th == null) {
            return Log.v(str, str2);
        }
        return Log.v(str, str2, th);
    }

    private int d(String str, String str2, Throwable th) {
        if (th == null) {
            return Log.d(str, str2);
        }
        return Log.d(str, str2, th);
    }

    private int i(String str, String str2, Throwable th) {
        if (th == null) {
            return Log.i(str, str2);
        }
        return Log.i(str, str2, th);
    }

    private int w(String str, String str2, Throwable th) {
        if (th == null) {
            return Log.w(str, str2);
        }
        return Log.w(str, str2, th);
    }

    private int e(String str, String str2, Throwable th) {
        if (th == null) {
            return Log.e(str, str2);
        }
        return Log.e(str, str2, th);
    }
}
