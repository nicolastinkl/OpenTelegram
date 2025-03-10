package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$JMT_jwallet extends TLObject {
    public static int constructor = -1980884226;
    public long balance;
    public long freeze_balance;
    public boolean have_password;
    public long user_id;

    public static TLRPC$JMT_jwallet TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor != i) {
            if (z) {
                throw new RuntimeException(String.format("can't parse magic %x in JMT_messageMediaRedPackage", Integer.valueOf(i)));
            }
            return null;
        }
        TLRPC$JMT_jwallet tLRPC$JMT_jwallet = new TLRPC$JMT_jwallet();
        tLRPC$JMT_jwallet.readParams(abstractSerializedData, z);
        return tLRPC$JMT_jwallet;
    }

    @Override // org.telegram.tgnet.TLObject
    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.user_id = abstractSerializedData.readInt64(z);
        this.balance = abstractSerializedData.readInt64(z);
        this.freeze_balance = abstractSerializedData.readInt64(z);
        this.have_password = abstractSerializedData.readBool(z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt64(this.user_id);
        abstractSerializedData.writeInt64(this.balance);
        abstractSerializedData.writeInt64(this.freeze_balance);
        abstractSerializedData.writeBool(this.have_password);
    }
}
