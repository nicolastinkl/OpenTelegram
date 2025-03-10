package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$JMT_searchUser_res extends TLObject {
    public static int constructor = 2084614038;
    public boolean is_friend;
    public TLRPC$User user;

    public static TLRPC$JMT_searchUser_res TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor != i) {
            if (z) {
                throw new RuntimeException(String.format("can't parse magic %x in JMT_searchUser_response", Integer.valueOf(i)));
            }
            return null;
        }
        TLRPC$JMT_searchUser_res tLRPC$JMT_searchUser_res = new TLRPC$JMT_searchUser_res();
        tLRPC$JMT_searchUser_res.readParams(abstractSerializedData, z);
        return tLRPC$JMT_searchUser_res;
    }

    @Override // org.telegram.tgnet.TLObject
    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.user = TLRPC$User.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        this.is_friend = abstractSerializedData.readBool(z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
