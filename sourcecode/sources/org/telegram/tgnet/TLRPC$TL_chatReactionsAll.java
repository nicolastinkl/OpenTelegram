package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_chatReactionsAll extends TLRPC$ChatReactions {
    public static int constructor = 1385335754;
    public boolean allow_custom;
    public int flags;

    @Override // org.telegram.tgnet.TLObject
    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        int readInt32 = abstractSerializedData.readInt32(z);
        this.flags = readInt32;
        this.allow_custom = (readInt32 & 1) != 0;
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        int i = this.allow_custom ? this.flags | 1 : this.flags & (-2);
        this.flags = i;
        abstractSerializedData.writeInt32(i);
    }
}
