package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_account_sendConfirmPhoneCode extends TLObject {
    public static int constructor = 457157256;
    public String hash;
    public TLRPC$TL_codeSettings settings;

    @Override // org.telegram.tgnet.TLObject
    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$auth_SentCode.TLdeserialize(abstractSerializedData, i, z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.hash);
        this.settings.serializeToStream(abstractSerializedData);
    }
}
