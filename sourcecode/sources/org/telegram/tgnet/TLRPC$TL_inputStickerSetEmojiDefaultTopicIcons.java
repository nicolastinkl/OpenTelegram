package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_inputStickerSetEmojiDefaultTopicIcons extends TLRPC$InputStickerSet {
    public static int constructor = 1153562857;

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
