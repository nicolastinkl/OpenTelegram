package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$JMT_auth_authorizationV2 extends TLRPC$auth_Authorization {
    public static int constructor = -1283734134;
    public int new_user;
    public TLRPC$TL_auth_authorization v1;

    @Override // org.telegram.tgnet.TLObject
    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.v1 = (TLRPC$TL_auth_authorization) TLRPC$auth_Authorization.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        this.new_user = abstractSerializedData.readInt32(z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.v1.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt32(this.new_user);
    }
}
