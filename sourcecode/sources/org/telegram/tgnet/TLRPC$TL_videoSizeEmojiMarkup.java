package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_videoSizeEmojiMarkup extends TLRPC$VideoSize {
    public static int constructor = -128171716;
    public long emoji_id;

    @Override // org.telegram.tgnet.TLObject
    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.emoji_id = abstractSerializedData.readInt64(z);
        int readInt32 = abstractSerializedData.readInt32(z);
        if (readInt32 != 481674261) {
            if (z) {
                throw new RuntimeException(String.format("wrong Vector magic, got %x", Integer.valueOf(readInt32)));
            }
        } else {
            int readInt322 = abstractSerializedData.readInt32(z);
            for (int i = 0; i < readInt322; i++) {
                this.background_colors.add(Integer.valueOf(abstractSerializedData.readInt32(z)));
            }
        }
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt64(this.emoji_id);
        abstractSerializedData.writeInt32(481674261);
        int size = this.background_colors.size();
        abstractSerializedData.writeInt32(size);
        for (int i = 0; i < size; i++) {
            abstractSerializedData.writeInt32(this.background_colors.get(i).intValue());
        }
    }
}
