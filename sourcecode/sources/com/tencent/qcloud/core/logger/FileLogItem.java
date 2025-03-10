package com.tencent.qcloud.core.logger;

import android.util.Log;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/* loaded from: classes.dex */
class FileLogItem {
    private String msg;
    private int priority;
    private String tag;
    private String threadName;
    private Throwable throwable;
    private long timestamp = System.currentTimeMillis();
    private long threadId = Thread.currentThread().getId();

    private static String getPriorityString(int i) {
        return i != 2 ? i != 3 ? i != 4 ? i != 5 ? i != 6 ? "UNKNOWN" : "ERROR" : "WARN" : "INFO" : "DEBUG" : "VERBOSE";
    }

    public FileLogItem(String str, int i, String str2, Throwable th) {
        this.tag = null;
        this.msg = null;
        this.throwable = null;
        this.priority = 0;
        this.threadName = null;
        this.priority = i;
        this.tag = str;
        this.msg = str2;
        this.throwable = th;
        this.threadName = Thread.currentThread().getName();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getPriorityString(this.priority));
        sb.append("/");
        sb.append(timeUtils(this.timestamp, "yyyy-MM-dd HH:mm:ss"));
        sb.append("[");
        sb.append(this.threadName);
        sb.append(" ");
        sb.append(this.threadId);
        sb.append("]");
        sb.append("[");
        sb.append(this.tag);
        sb.append("]");
        sb.append("[");
        sb.append(this.msg);
        sb.append("]");
        if (this.throwable != null) {
            sb.append(" * Exception :\n");
            sb.append(Log.getStackTraceString(this.throwable));
        }
        sb.append("\n");
        return sb.toString();
    }

    private static String timeUtils(long j, String str) {
        Date date = new Date(j);
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        return new SimpleDateFormat(str, Locale.CHINA).format(gregorianCalendar.getTime());
    }

    public long getLength() {
        return (this.msg != null ? r0.length() : 0) + 40;
    }
}
