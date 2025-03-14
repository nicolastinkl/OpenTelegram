package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_replyKeyboardForceReply extends TLRPC$ReplyMarkup {
    public static int constructor = -2035021048;

    @Override // org.telegram.tgnet.TLObject
    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        int readInt32 = abstractSerializedData.readInt32(z);
        this.flags = readInt32;
        this.single_use = (readInt32 & 2) != 0;
        this.selective = (readInt32 & 4) != 0;
        if ((readInt32 & 8) != 0) {
            this.placeholder = abstractSerializedData.readString(z);
        }
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        int i = this.single_use ? this.flags | 2 : this.flags & (-3);
        this.flags = i;
        int i2 = this.selective ? i | 4 : i & (-5);
        this.flags = i2;
        abstractSerializedData.writeInt32(i2);
        if ((this.flags & 8) != 0) {
            abstractSerializedData.writeString(this.placeholder);
        }
    }
}
