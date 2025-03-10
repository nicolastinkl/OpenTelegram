package com.tencent.beacon.event.immediate;

/* loaded from: classes.dex */
public class Beacon2MsfTransferArgs extends BeaconTransferArgs {
    private String d;

    public Beacon2MsfTransferArgs(byte[] bArr) {
        super(bArr);
        this.d = "trpc.Beacon.BeaconLogServerLC.blslongconnection.SsoProcess";
    }

    @Override // com.tencent.beacon.event.immediate.BeaconTransferArgs
    public String getCommand() {
        return this.d;
    }

    @Override // com.tencent.beacon.event.immediate.BeaconTransferArgs
    public void setCommand(String str) {
        this.d = str;
    }
}
