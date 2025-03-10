package com.tencent.qcloud.core.http;

import com.tencent.qcloud.core.logger.QCloudLogger;
import j$.util.DesugarTimeZone;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes.dex */
public class HttpConfiguration {
    private static final AtomicLong GLOBAL_TIME_OFFSET = new AtomicLong(0);
    private static final TimeZone GMT_TIMEZONE = DesugarTimeZone.getTimeZone("GMT");
    private static ThreadLocal<SimpleDateFormat> gmtFormatters = new ThreadLocal<>();

    public static long calculateGlobalTimeOffset(String str, Date date) {
        AtomicLong atomicLong = GLOBAL_TIME_OFFSET;
        long j = atomicLong.get();
        calculateGlobalTimeOffset(str, date, 0);
        return Math.abs(j - atomicLong.get());
    }

    public static void calculateGlobalTimeOffset(String str, Date date, int i) {
        try {
            long time = (getFormatter().parse(str).getTime() - date.getTime()) / 1000;
            if (Math.abs(time) >= i) {
                GLOBAL_TIME_OFFSET.set(time);
                QCloudLogger.i("QCloudHttp", "NEW TIME OFFSET is " + time + "s", new Object[0]);
            }
        } catch (ParseException unused) {
        }
    }

    public static long getDeviceTimeWithOffset() {
        return (System.currentTimeMillis() / 1000) + GLOBAL_TIME_OFFSET.get();
    }

    public static String getGMTDate(Date date) {
        return getFormatter().format(date);
    }

    private static SimpleDateFormat getFormatter() {
        SimpleDateFormat simpleDateFormat = gmtFormatters.get();
        if (simpleDateFormat != null) {
            return simpleDateFormat;
        }
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        simpleDateFormat2.setTimeZone(GMT_TIMEZONE);
        simpleDateFormat2.setLenient(false);
        gmtFormatters.set(simpleDateFormat2);
        return simpleDateFormat2;
    }
}
