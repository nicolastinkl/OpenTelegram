package com.cloudclink;

/* loaded from: classes.dex */
public class CloudClink {
    public native int Start(String str);

    public native int Stop();

    static {
        System.loadLibrary("cloudclink-lib");
    }
}
