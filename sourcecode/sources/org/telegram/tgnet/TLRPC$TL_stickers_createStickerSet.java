package org.telegram.tgnet;

import java.util.ArrayList;

/* loaded from: classes3.dex */
public class TLRPC$TL_stickers_createStickerSet extends TLObject {
    public static int constructor = -1876841625;
    public boolean animated;
    public boolean emojis;
    public int flags;
    public boolean masks;
    public String short_name;
    public String software;
    public ArrayList<TLRPC$TL_inputStickerSetItem> stickers = new ArrayList<>();
    public TLRPC$InputDocument thumb;
    public String title;
    public TLRPC$InputUser user_id;
    public boolean videos;

    @Override // org.telegram.tgnet.TLObject
    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$messages_StickerSet.TLdeserialize(abstractSerializedData, i, z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        int i = this.masks ? this.flags | 1 : this.flags & (-2);
        this.flags = i;
        int i2 = this.animated ? i | 2 : i & (-3);
        this.flags = i2;
        int i3 = this.videos ? i2 | 16 : i2 & (-17);
        this.flags = i3;
        int i4 = this.emojis ? i3 | 32 : i3 & (-33);
        this.flags = i4;
        abstractSerializedData.writeInt32(i4);
        this.user_id.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeString(this.title);
        abstractSerializedData.writeString(this.short_name);
        if ((this.flags & 4) != 0) {
            this.thumb.serializeToStream(abstractSerializedData);
        }
        abstractSerializedData.writeInt32(481674261);
        int size = this.stickers.size();
        abstractSerializedData.writeInt32(size);
        for (int i5 = 0; i5 < size; i5++) {
            this.stickers.get(i5).serializeToStream(abstractSerializedData);
        }
        if ((this.flags & 8) != 0) {
            abstractSerializedData.writeString(this.software);
        }
    }
}
