package com.tencent.beacon.upload;

import com.tencent.beacon.event.c.d;

@Deprecated
/* loaded from: classes.dex */
public class TunnelInfo {
    private String appKey;
    private String channelID;
    private String version;

    public TunnelInfo(String str) {
        this(str, "", "");
    }

    public String getAppKey() {
        return this.appKey;
    }

    public String getChannelID() {
        return this.channelID;
    }

    public String getVersion() {
        return this.version;
    }

    public void setAppKey(String str) {
        this.appKey = str;
    }

    public void setChannelID(String str) {
        this.channelID = str;
    }

    public void setVersion(String str) {
        this.version = str;
    }

    public TunnelInfo(String str, String str2) {
        this(str, "", str2);
    }

    public TunnelInfo(String str, String str2, String str3) {
        this.appKey = str;
        this.version = str2;
        this.channelID = d.a(str3);
    }
}
