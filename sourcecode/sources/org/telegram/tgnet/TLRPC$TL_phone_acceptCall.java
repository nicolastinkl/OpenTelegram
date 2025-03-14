package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_phone_acceptCall extends TLObject {
    public static int constructor = 1003664544;
    public byte[] g_b;
    public TLRPC$TL_inputPhoneCall peer;
    public TLRPC$TL_phoneCallProtocol protocol;

    @Override // org.telegram.tgnet.TLObject
    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$TL_phone_phoneCall.TLdeserialize(abstractSerializedData, i, z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.peer.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeByteArray(this.g_b);
        this.protocol.serializeToStream(abstractSerializedData);
    }
}
