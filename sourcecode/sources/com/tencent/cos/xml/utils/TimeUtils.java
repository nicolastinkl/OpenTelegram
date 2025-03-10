package com.tencent.cos.xml.utils;

import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class TimeUtils {
    private static final String TAG = "TimeUtil";

    public static long getTookTime(long j) {
        return TimeUnit.MILLISECONDS.convert(System.nanoTime() - j, TimeUnit.NANOSECONDS);
    }
}
