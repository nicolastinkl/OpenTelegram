package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_account_emailVerifiedLogin extends TLRPC$account_EmailVerified {
    public static int constructor = -507835039;
    public String email;
    public TLRPC$auth_SentCode sent_code;

    @Override // org.telegram.tgnet.TLObject
    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.email = abstractSerializedData.readString(z);
        this.sent_code = TLRPC$auth_SentCode.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.email);
        this.sent_code.serializeToStream(abstractSerializedData);
    }
}
