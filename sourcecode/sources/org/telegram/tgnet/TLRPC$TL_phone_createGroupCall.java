package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_phone_createGroupCall extends TLObject {
    public static int constructor = 1221445336;
    public int flags;
    public TLRPC$InputPeer peer;
    public int random_id;
    public int schedule_date;
    public String title;

    @Override // org.telegram.tgnet.TLObject
    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$Updates.TLdeserialize(abstractSerializedData, i, z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.flags);
        this.peer.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt32(this.random_id);
        if ((this.flags & 1) != 0) {
            abstractSerializedData.writeString(this.title);
        }
        if ((this.flags & 2) != 0) {
            abstractSerializedData.writeInt32(this.schedule_date);
        }
    }
}
