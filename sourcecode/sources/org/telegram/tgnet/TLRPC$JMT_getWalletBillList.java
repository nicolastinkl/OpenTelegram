package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$JMT_getWalletBillList extends TLObject {
    public static int constructor = 337903414;
    public int date_gte;
    public int date_lte;
    public int limit;
    public int offset;
    public int type;

    @Override // org.telegram.tgnet.TLObject
    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        TLRPC$Vector tLRPC$Vector = new TLRPC$Vector();
        int readInt32 = abstractSerializedData.readInt32(z);
        for (int i2 = 0; i2 < readInt32; i2++) {
            TLRPC$JMT_WalletBill TLdeserialize = TLRPC$JMT_WalletBill.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            if (TLdeserialize == null) {
                return tLRPC$Vector;
            }
            tLRPC$Vector.objects.add(TLdeserialize);
        }
        return tLRPC$Vector;
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.offset);
        abstractSerializedData.writeInt32(this.limit);
        abstractSerializedData.writeInt32(this.date_lte);
        abstractSerializedData.writeInt32(this.date_gte);
        abstractSerializedData.writeInt32(this.type);
    }
}
