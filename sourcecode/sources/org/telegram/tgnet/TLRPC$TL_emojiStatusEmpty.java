package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_emojiStatusEmpty extends TLRPC$EmojiStatus {
    public static int constructor = 769727150;

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
