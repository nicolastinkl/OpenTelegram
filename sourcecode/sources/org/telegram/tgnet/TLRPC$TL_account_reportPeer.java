package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_account_reportPeer extends TLObject {
    public static int constructor = -977650298;
    public String message;
    public TLRPC$InputPeer peer;
    public TLRPC$ReportReason reason;

    @Override // org.telegram.tgnet.TLObject
    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$Bool.TLdeserialize(abstractSerializedData, i, z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.peer.serializeToStream(abstractSerializedData);
        this.reason.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeString(this.message);
    }
}
