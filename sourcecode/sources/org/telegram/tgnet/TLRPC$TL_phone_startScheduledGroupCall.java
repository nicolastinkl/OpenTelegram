package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_phone_startScheduledGroupCall extends TLObject {
    public static int constructor = 1451287362;
    public TLRPC$TL_inputGroupCall call;

    @Override // org.telegram.tgnet.TLObject
    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$Updates.TLdeserialize(abstractSerializedData, i, z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.call.serializeToStream(abstractSerializedData);
    }
}
