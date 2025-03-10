package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$JMT_signInByUsernameV2 extends TLObject {
    public static int constructor = 1379400047;
    public String ext;
    public String password;
    public String username;

    @Override // org.telegram.tgnet.TLObject
    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$auth_Authorization.TLdeserialize(abstractSerializedData, i, z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.username);
        abstractSerializedData.writeString(this.password);
        abstractSerializedData.writeString(this.ext);
    }
}
