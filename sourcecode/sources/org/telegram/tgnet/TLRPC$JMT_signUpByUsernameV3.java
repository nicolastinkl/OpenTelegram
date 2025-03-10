package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$JMT_signUpByUsernameV3 extends TLObject {
    public static int constructor = -1409507056;
    public String channel_code;
    public String first_name;
    public String invite_code;
    public String last_name;
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
        abstractSerializedData.writeString(this.first_name);
        abstractSerializedData.writeString(this.last_name);
        abstractSerializedData.writeString(this.invite_code);
        abstractSerializedData.writeString(this.channel_code);
    }
}
