package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$JMT_WalletBill extends TLObject {
    public static int constructor = -609846850;
    public long amount;
    public long balance;
    public String chat_title;
    public int date;
    public int flags;
    public int type;

    public static TLRPC$JMT_WalletBill TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor != i) {
            if (z) {
                throw new RuntimeException(String.format("can't parse magic %x in JMT_WalletBill", Integer.valueOf(i)));
            }
            return null;
        }
        TLRPC$JMT_WalletBill tLRPC$JMT_WalletBill = new TLRPC$JMT_WalletBill();
        tLRPC$JMT_WalletBill.readParams(abstractSerializedData, z);
        return tLRPC$JMT_WalletBill;
    }

    @Override // org.telegram.tgnet.TLObject
    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.flags = abstractSerializedData.readInt32(z);
        this.type = abstractSerializedData.readInt32(z);
        this.amount = abstractSerializedData.readInt64(z);
        this.balance = abstractSerializedData.readInt64(z);
        abstractSerializedData.readInt64(z);
        this.date = abstractSerializedData.readInt32(z);
        if ((this.flags & 1) != 0) {
            TLRPC$Peer.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            this.chat_title = abstractSerializedData.readString(z);
        }
    }
}
