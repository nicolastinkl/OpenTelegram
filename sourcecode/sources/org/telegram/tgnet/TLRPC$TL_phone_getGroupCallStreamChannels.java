package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_phone_getGroupCallStreamChannels extends TLObject {
    public static int constructor = 447879488;
    public TLRPC$TL_inputGroupCall call;

    @Override // org.telegram.tgnet.TLObject
    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$TL_phone_groupCallStreamChannels.TLdeserialize(abstractSerializedData, i, z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.call.serializeToStream(abstractSerializedData);
    }
}
