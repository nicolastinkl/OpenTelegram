package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$JMT_messageActionRedPackageAddReceiver extends TLRPC$MessageAction {
    public static int constructor = -675480596;
    public int date;
    public int receive_amount;
    public TLRPC$JMT_RedPackage red_package;
    public long user_id;

    @Override // org.telegram.tgnet.TLObject
    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.user_id = abstractSerializedData.readInt64(z);
        this.receive_amount = abstractSerializedData.readInt32(z);
        this.date = abstractSerializedData.readInt32(z);
        this.red_package = TLRPC$JMT_RedPackage.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt64(this.user_id);
        abstractSerializedData.writeInt32(this.receive_amount);
        abstractSerializedData.writeInt32(this.date);
        this.red_package.serializeToStream(abstractSerializedData);
    }
}
