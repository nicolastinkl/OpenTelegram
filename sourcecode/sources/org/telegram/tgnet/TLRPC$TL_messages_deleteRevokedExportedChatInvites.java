package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_messages_deleteRevokedExportedChatInvites extends TLObject {
    public static int constructor = 1452833749;
    public TLRPC$InputUser admin_id;
    public TLRPC$InputPeer peer;

    @Override // org.telegram.tgnet.TLObject
    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$Bool.TLdeserialize(abstractSerializedData, i, z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.peer.serializeToStream(abstractSerializedData);
        this.admin_id.serializeToStream(abstractSerializedData);
    }
}
