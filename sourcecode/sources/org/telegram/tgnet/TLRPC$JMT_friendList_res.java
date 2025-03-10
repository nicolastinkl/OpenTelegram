package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$JMT_friendList_res extends TLObject {
    public static int constructor = -408337193;
    public TLRPC$User user;

    public static TLRPC$JMT_friendList_res TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor != i) {
            if (z) {
                throw new RuntimeException(String.format("can't parse magic %x in JMT_friendList_res", Integer.valueOf(i)));
            }
            return null;
        }
        TLRPC$JMT_friendList_res tLRPC$JMT_friendList_res = new TLRPC$JMT_friendList_res();
        tLRPC$JMT_friendList_res.readParams(abstractSerializedData, z);
        return tLRPC$JMT_friendList_res;
    }

    @Override // org.telegram.tgnet.TLObject
    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.user = TLRPC$User.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
