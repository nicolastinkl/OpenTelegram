package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$JMT_applyFriend_req extends TLObject {
    public static int constructor = 647807255;
    public String reason;
    public long sourceId;
    public int sourceType;
    public long user_id;

    @Override // org.telegram.tgnet.TLObject
    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$Bool.TLdeserialize(abstractSerializedData, i, z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt64(this.user_id);
        abstractSerializedData.writeString(this.reason);
        abstractSerializedData.writeInt32(this.sourceType);
        abstractSerializedData.writeInt64(this.sourceId);
    }
}
