package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_messages_getReplies extends TLObject {
    public static int constructor = 584962828;
    public int add_offset;
    public long hash;
    public int limit;
    public int max_id;
    public int min_id;
    public int msg_id;
    public int offset_date;
    public int offset_id;
    public TLRPC$InputPeer peer;

    @Override // org.telegram.tgnet.TLObject
    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$messages_Messages.TLdeserialize(abstractSerializedData, i, z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.peer.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt32(this.msg_id);
        abstractSerializedData.writeInt32(this.offset_id);
        abstractSerializedData.writeInt32(this.offset_date);
        abstractSerializedData.writeInt32(this.add_offset);
        abstractSerializedData.writeInt32(this.limit);
        abstractSerializedData.writeInt32(this.max_id);
        abstractSerializedData.writeInt32(this.min_id);
        abstractSerializedData.writeInt64(this.hash);
    }
}
