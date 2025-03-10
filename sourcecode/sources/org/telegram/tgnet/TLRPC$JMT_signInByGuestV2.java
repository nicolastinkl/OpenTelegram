package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$JMT_signInByGuestV2 extends TLObject {
    public static int constructor = 679267571;
    public String channel_code;
    public String device_id;

    @Override // org.telegram.tgnet.TLObject
    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$auth_Authorization.TLdeserialize(abstractSerializedData, i, z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.device_id);
        abstractSerializedData.writeString(this.channel_code);
    }
}
