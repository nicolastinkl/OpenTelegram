package com.tencent.beacon.event.immediate;

/* loaded from: classes.dex */
public class BeaconTransferResult {
    private int a;
    private int b;
    private byte[] c;
    private String d;

    public byte[] getBizBuffer() {
        return this.c;
    }

    public int getBizCode() {
        return this.b;
    }

    public String getBizMsg() {
        return this.d;
    }

    public int getCode() {
        return this.a;
    }

    public void setBizBuffer(byte[] bArr) {
        this.c = bArr;
    }

    public void setBizCode(int i) {
        this.b = i;
    }

    public void setBizMsg(String str) {
        this.d = str;
    }

    public void setCode(int i) {
        this.a = i;
    }

    public String toString() {
        return "BeaconTransferResult{returnCode=" + this.a + ", bizReturnCode=" + this.b + ", bizMsg='" + this.d + "'}";
    }
}
