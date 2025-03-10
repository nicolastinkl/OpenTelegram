package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$JMT_applyList_res extends TLObject {
    public static int constructor = -1713725838;
    public long apply_id;
    public String reason;
    public int status;
    public TLRPC$User user;

    public static TLRPC$JMT_applyList_res TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor != i) {
            if (z) {
                throw new RuntimeException(String.format("can't parse magic %x in JMT_applyList_res", Integer.valueOf(i)));
            }
            return null;
        }
        TLRPC$JMT_applyList_res tLRPC$JMT_applyList_res = new TLRPC$JMT_applyList_res();
        tLRPC$JMT_applyList_res.readParams(abstractSerializedData, z);
        return tLRPC$JMT_applyList_res;
    }

    @Override // org.telegram.tgnet.TLObject
    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.user = TLRPC$User.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        this.status = abstractSerializedData.readInt32(z);
        this.reason = abstractSerializedData.readString(z);
        abstractSerializedData.readInt64(z);
        abstractSerializedData.readInt32(z);
        abstractSerializedData.readInt32(z);
        this.apply_id = abstractSerializedData.readInt64(z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
