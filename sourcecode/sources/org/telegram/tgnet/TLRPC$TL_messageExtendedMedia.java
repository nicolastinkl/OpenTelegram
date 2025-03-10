package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_messageExtendedMedia extends TLRPC$MessageExtendedMedia {
    public static int constructor = -297296796;
    public TLRPC$MessageMedia media;

    @Override // org.telegram.tgnet.TLObject
    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.media = TLRPC$MessageMedia.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.media.serializeToStream(abstractSerializedData);
    }
}
