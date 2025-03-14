package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_pageBlockChannel extends TLRPC$PageBlock {
    public static int constructor = -283684427;
    public TLRPC$Chat channel;

    @Override // org.telegram.tgnet.TLObject
    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.channel = TLRPC$Chat.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.channel.serializeToStream(abstractSerializedData);
    }
}
