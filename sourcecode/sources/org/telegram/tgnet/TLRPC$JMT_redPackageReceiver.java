package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$JMT_redPackageReceiver extends TLObject {
    public static int constructor = 707155244;
    public int amount;
    public int date;
    public TLRPC$User user;

    public static TLRPC$JMT_redPackageReceiver TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor != i) {
            if (z) {
                throw new RuntimeException(String.format("can't parse magic %x in JMT_redPackageReceiver", Integer.valueOf(i)));
            }
            return null;
        }
        TLRPC$JMT_redPackageReceiver tLRPC$JMT_redPackageReceiver = new TLRPC$JMT_redPackageReceiver();
        tLRPC$JMT_redPackageReceiver.readParams(abstractSerializedData, z);
        return tLRPC$JMT_redPackageReceiver;
    }

    @Override // org.telegram.tgnet.TLObject
    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.user = TLRPC$User.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        this.amount = abstractSerializedData.readInt32(z);
        this.date = abstractSerializedData.readInt32(z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.user.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt32(this.amount);
        abstractSerializedData.writeInt32(this.date);
    }
}
