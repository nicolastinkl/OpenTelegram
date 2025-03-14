package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_documentAttributeImageSize extends TLRPC$DocumentAttribute {
    public static int constructor = 1815593308;

    @Override // org.telegram.tgnet.TLObject
    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.w = abstractSerializedData.readInt32(z);
        this.h = abstractSerializedData.readInt32(z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.w);
        abstractSerializedData.writeInt32(this.h);
    }
}
