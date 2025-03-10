package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$JMT_isBoundPhone extends TLObject {
    public static int constructor = 618185870;
    public String ext;

    @Override // org.telegram.tgnet.TLObject
    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$JMT_jAuthIsBoundPhone.TLdeserialize(abstractSerializedData, i, z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.ext);
    }
}
