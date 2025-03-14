package org.telegram.tgnet;

import org.telegram.messenger.LiteMode;

/* loaded from: classes3.dex */
public class TLRPC$TL_updateShortMessage extends TLRPC$Updates {
    public static int constructor = 826001400;

    @Override // org.telegram.tgnet.TLObject
    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        int readInt32 = abstractSerializedData.readInt32(z);
        this.flags = readInt32;
        this.out = (readInt32 & 2) != 0;
        this.mentioned = (readInt32 & 16) != 0;
        this.media_unread = (readInt32 & 32) != 0;
        this.silent = (readInt32 & LiteMode.FLAG_ANIMATED_EMOJI_REACTIONS_NOT_PREMIUM) != 0;
        this.id = abstractSerializedData.readInt32(z);
        this.user_id = abstractSerializedData.readInt64(z);
        this.message = abstractSerializedData.readString(z);
        this.pts = abstractSerializedData.readInt32(z);
        this.pts_count = abstractSerializedData.readInt32(z);
        this.date = abstractSerializedData.readInt32(z);
        if ((this.flags & 4) != 0) {
            this.fwd_from = TLRPC$MessageFwdHeader.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        }
        if ((this.flags & LiteMode.FLAG_AUTOPLAY_GIFS) != 0) {
            this.via_bot_id = abstractSerializedData.readInt64(z);
        }
        if ((this.flags & 8) != 0) {
            this.reply_to = TLRPC$TL_messageReplyHeader.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        }
        if ((this.flags & 128) != 0) {
            int readInt322 = abstractSerializedData.readInt32(z);
            if (readInt322 != 481674261) {
                if (z) {
                    throw new RuntimeException(String.format("wrong Vector magic, got %x", Integer.valueOf(readInt322)));
                }
                return;
            }
            int readInt323 = abstractSerializedData.readInt32(z);
            for (int i = 0; i < readInt323; i++) {
                TLRPC$MessageEntity TLdeserialize = TLRPC$MessageEntity.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                if (TLdeserialize == null) {
                    return;
                }
                this.entities.add(TLdeserialize);
            }
        }
        if ((this.flags & ConnectionsManager.FileTypeVideo) != 0) {
            this.ttl_period = abstractSerializedData.readInt32(z);
        }
    }
}
