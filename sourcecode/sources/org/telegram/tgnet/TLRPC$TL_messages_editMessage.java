package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.messenger.LiteMode;

/* loaded from: classes3.dex */
public class TLRPC$TL_messages_editMessage extends TLObject {
    public static int constructor = 1224152952;
    public ArrayList<TLRPC$MessageEntity> entities = new ArrayList<>();
    public int flags;
    public int id;
    public TLRPC$InputMedia media;
    public String message;
    public boolean no_webpage;
    public TLRPC$InputPeer peer;
    public TLRPC$ReplyMarkup reply_markup;
    public int schedule_date;

    @Override // org.telegram.tgnet.TLObject
    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$Updates.TLdeserialize(abstractSerializedData, i, z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        int i = this.no_webpage ? this.flags | 2 : this.flags & (-3);
        this.flags = i;
        abstractSerializedData.writeInt32(i);
        this.peer.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt32(this.id);
        if ((this.flags & LiteMode.FLAG_AUTOPLAY_GIFS) != 0) {
            abstractSerializedData.writeString(this.message);
        }
        if ((this.flags & LiteMode.FLAG_ANIMATED_EMOJI_KEYBOARD_NOT_PREMIUM) != 0) {
            this.media.serializeToStream(abstractSerializedData);
        }
        if ((this.flags & 4) != 0) {
            this.reply_markup.serializeToStream(abstractSerializedData);
        }
        if ((this.flags & 8) != 0) {
            abstractSerializedData.writeInt32(481674261);
            int size = this.entities.size();
            abstractSerializedData.writeInt32(size);
            for (int i2 = 0; i2 < size; i2++) {
                this.entities.get(i2).serializeToStream(abstractSerializedData);
            }
        }
        if ((this.flags & LiteMode.FLAG_CHAT_SCALE) != 0) {
            abstractSerializedData.writeInt32(this.schedule_date);
        }
    }
}
