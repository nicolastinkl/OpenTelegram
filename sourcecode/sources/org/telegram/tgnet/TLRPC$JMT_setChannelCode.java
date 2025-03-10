package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$JMT_setChannelCode extends TLObject {
    public static int constructor = 1236833078;
    public String channel_code;

    @Override // org.telegram.tgnet.TLObject
    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$Bool.TLdeserialize(abstractSerializedData, i, z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.channel_code);
    }
}
