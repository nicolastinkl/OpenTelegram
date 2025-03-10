package com.tencent.cos.xml.common;

/* loaded from: classes.dex */
public class VersionInfo {
    public static final String platform = "cos-android-sdk-5.9.13";
    public static final String platformQuic = "cos-android-quic-sdk-5.9.13";
    public static final int version = 50913;

    public static String getQuicUserAgent() {
        return platformQuic;
    }

    public static String getUserAgent() {
        return platform;
    }
}
