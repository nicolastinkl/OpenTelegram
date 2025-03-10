package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$JMT_deleteFriend_req extends TLObject {
    public static int constructor = 1460976183;
    public long friend_id;

    @Override // org.telegram.tgnet.TLObject
    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$Bool.TLdeserialize(abstractSerializedData, i, z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt64(this.friend_id);
    }
}
