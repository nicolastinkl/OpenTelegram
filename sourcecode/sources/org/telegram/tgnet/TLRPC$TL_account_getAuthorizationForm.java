package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_account_getAuthorizationForm extends TLObject {
    public static int constructor = -1456907910;
    public long bot_id;
    public String public_key;
    public String scope;

    @Override // org.telegram.tgnet.TLObject
    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$TL_account_authorizationForm.TLdeserialize(abstractSerializedData, i, z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt64(this.bot_id);
        abstractSerializedData.writeString(this.scope);
        abstractSerializedData.writeString(this.public_key);
    }
}
