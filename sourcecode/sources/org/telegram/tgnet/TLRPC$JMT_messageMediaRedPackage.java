package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$JMT_messageMediaRedPackage extends TLRPC$MessageMedia {
    public static int constructor = -48153248;
    public TLRPC$JMT_RedPackage red_package;

    public static TLRPC$JMT_messageMediaRedPackage TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor != i) {
            if (z) {
                throw new RuntimeException(String.format("can't parse magic %x in JMT_messageMediaRedPackage", Integer.valueOf(i)));
            }
            return null;
        }
        TLRPC$JMT_messageMediaRedPackage tLRPC$JMT_messageMediaRedPackage = new TLRPC$JMT_messageMediaRedPackage();
        tLRPC$JMT_messageMediaRedPackage.readParams(abstractSerializedData, z);
        return tLRPC$JMT_messageMediaRedPackage;
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
