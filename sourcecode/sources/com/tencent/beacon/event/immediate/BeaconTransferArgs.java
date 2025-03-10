package com.tencent.beacon.event.immediate;

/* loaded from: classes.dex */
public abstract class BeaconTransferArgs {
    private byte[] a;
    private String b = "";
    private String c = "";

    public BeaconTransferArgs(byte[] bArr) {
        this.a = bArr;
    }

    public String getAppkey() {
        return this.b;
    }

    public abstract String getCommand();

    public byte[] getData() {
        return this.a;
    }

    public String getEventCode() {
        return this.c;
    }

    public void setAppkey(String str) {
        this.b = str;
    }

    public abstract void setCommand(String str);

    public void setData(byte[] bArr) {
        this.a = bArr;
    }

    public void setEventCode(String str) {
        this.c = str;
    }
}
