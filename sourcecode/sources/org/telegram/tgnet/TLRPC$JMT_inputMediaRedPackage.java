package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$JMT_inputMediaRedPackage extends TLRPC$InputMedia {
    public static int constructor = 2016190218;
    public TLRPC$JMT_RedPackage red_package;

    @Override // org.telegram.tgnet.TLObject
    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$JMT_messageMediaRedPackage.TLdeserialize(abstractSerializedData, i, z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.red_package = TLRPC$JMT_RedPackage.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.red_package.serializeToStream(abstractSerializedData);
    }
}
