package com.fm.openinstall.model;

import android.text.TextUtils;
import java.io.Serializable;

/* loaded from: classes.dex */
public final class AppData implements Serializable {
    public String channel = "";
    public String data = "";

    public String getChannel() {
        return this.channel;
    }

    public String getData() {
        return this.data;
    }

    public boolean isEmpty() {
        return TextUtils.isEmpty(getChannel()) && TextUtils.isEmpty(getData());
    }

    public void setChannel(String str) {
        this.channel = str;
    }

    public void setData(String str) {
        this.data = str;
    }

    public String toString() {
        return "AppData{channel='" + this.channel + "', data='" + this.data + "'}";
    }
}
