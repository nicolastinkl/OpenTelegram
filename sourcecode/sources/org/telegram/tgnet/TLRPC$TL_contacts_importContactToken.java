package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_contacts_importContactToken extends TLObject {
    public static int constructor = 318789512;
    public String token;

    @Override // org.telegram.tgnet.TLObject
    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$User.TLdeserialize(abstractSerializedData, i, z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.token);
    }
}
