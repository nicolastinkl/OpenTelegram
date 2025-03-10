package com.xinstall;

import com.shubao.xinstall.a.f.s;

/* loaded from: classes.dex */
public class XINConfiguration {
    private String androidId;
    private String gaid;
    private boolean isAdEnable = false;
    private boolean isCanClip = true;
    private Boolean isImeiEnable;
    public Boolean isOutSettingAndroidId;
    public Boolean isOutSettingGaid;
    public Boolean isOutSettingOaid;
    public Boolean isOutSettingSerial;
    private String oaid;
    private String serial;

    public XINConfiguration() {
        Boolean bool = Boolean.FALSE;
        this.isOutSettingOaid = bool;
        this.isOutSettingAndroidId = bool;
        this.isOutSettingSerial = bool;
        this.isOutSettingGaid = bool;
    }

    public static XINConfiguration Builder() {
        return new XINConfiguration();
    }

    public XINConfiguration adEnable(boolean z) {
        this.isAdEnable = z;
        return this;
    }

    public XINConfiguration androidId(String str) {
        if (str != null) {
            this.androidId = str;
            this.isOutSettingAndroidId = Boolean.TRUE;
        }
        return this;
    }

    public XINConfiguration canClip(Boolean bool) {
        this.isCanClip = bool.booleanValue();
        return this;
    }

    public void changeAndroidId(String str) {
        if (this.isOutSettingAndroidId.booleanValue()) {
            return;
        }
        if (!s.a(str) || s.a(this.androidId)) {
            this.androidId = str;
        }
    }

    public void changeGaid(String str) {
        if (this.isOutSettingGaid.booleanValue()) {
            return;
        }
        if (!s.a(str) || s.a(this.gaid)) {
            this.gaid = str;
        }
    }

    public void changeOaid(String str) {
        if (this.isOutSettingOaid.booleanValue()) {
            return;
        }
        if (!s.a(str) || s.a(this.oaid)) {
            this.oaid = str;
        }
    }

    public void changeSerial(String str) {
        if (this.isOutSettingSerial.booleanValue()) {
            return;
        }
        if (!s.a(str) || s.a(this.serial)) {
            this.serial = str;
        }
    }

    public XINConfiguration gaid(String str) {
        this.gaid = str;
        this.isOutSettingGaid = Boolean.TRUE;
        return this;
    }

    public String getAndroidId() {
        String str = this.androidId;
        return str == null ? "" : str;
    }

    public String getGaid() {
        String str = this.gaid;
        return str == null ? "" : str;
    }

    public String getOaid() {
        String str = this.oaid;
        return str == null ? "" : str;
    }

    public String getSerial() {
        String str = this.serial;
        return str == null ? "" : str;
    }

    public boolean isAdEnable() {
        return this.isAdEnable;
    }

    public boolean isCanClip() {
        return this.isCanClip;
    }

    public Boolean isImeiEnable() {
        return this.isImeiEnable;
    }

    public XINConfiguration oaid(String str) {
        this.oaid = str;
        this.isOutSettingOaid = Boolean.TRUE;
        return this;
    }

    public XINConfiguration serial(String str) {
        if (str != null) {
            this.serial = str;
            this.isOutSettingSerial = Boolean.TRUE;
        }
        return this;
    }

    public void setImeiEnable(Boolean bool) {
        this.isImeiEnable = bool;
    }
}
