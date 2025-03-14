package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_dialog extends TLRPC$Dialog {
    public static int constructor = -712374074;

    @Override // org.telegram.tgnet.TLObject
    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        int readInt32 = abstractSerializedData.readInt32(z);
        this.flags = readInt32;
        this.pinned = (readInt32 & 4) != 0;
        this.unread_mark = (readInt32 & 8) != 0;
        this.peer = TLRPC$Peer.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        this.top_message = abstractSerializedData.readInt32(z);
        this.read_inbox_max_id = abstractSerializedData.readInt32(z);
        this.read_outbox_max_id = abstractSerializedData.readInt32(z);
        this.unread_count = abstractSerializedData.readInt32(z);
        this.unread_mentions_count = abstractSerializedData.readInt32(z);
        this.unread_reactions_count = abstractSerializedData.readInt32(z);
        this.notify_settings = TLRPC$PeerNotifySettings.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        if ((this.flags & 1) != 0) {
            this.pts = abstractSerializedData.readInt32(z);
        }
        if ((this.flags & 2) != 0) {
            this.draft = TLRPC$DraftMessage.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        }
        if ((this.flags & 16) != 0) {
            this.folder_id = abstractSerializedData.readInt32(z);
        }
        if ((this.flags & 32) != 0) {
            this.ttl_period = abstractSerializedData.readInt32(z);
        }
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        int i = this.pinned ? this.flags | 4 : this.flags & (-5);
        this.flags = i;
        int i2 = this.unread_mark ? i | 8 : i & (-9);
        this.flags = i2;
        abstractSerializedData.writeInt32(i2);
        this.peer.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt32(this.top_message);
        abstractSerializedData.writeInt32(this.read_inbox_max_id);
        abstractSerializedData.writeInt32(this.read_outbox_max_id);
        abstractSerializedData.writeInt32(this.unread_count);
        abstractSerializedData.writeInt32(this.unread_mentions_count);
        abstractSerializedData.writeInt32(this.unread_reactions_count);
        this.notify_settings.serializeToStream(abstractSerializedData);
        if ((this.flags & 1) != 0) {
            abstractSerializedData.writeInt32(this.pts);
        }
        if ((this.flags & 2) != 0) {
            this.draft.serializeToStream(abstractSerializedData);
        }
        if ((this.flags & 16) != 0) {
            abstractSerializedData.writeInt32(this.folder_id);
        }
        if ((this.flags & 32) != 0) {
            abstractSerializedData.writeInt32(this.ttl_period);
        }
    }
}
