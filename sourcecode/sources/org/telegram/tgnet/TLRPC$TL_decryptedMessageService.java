package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_decryptedMessageService extends TLRPC$DecryptedMessage {
    public static int constructor = 1930838368;

    @Override // org.telegram.tgnet.TLObject
    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.random_id = abstractSerializedData.readInt64(z);
        this.action = TLRPC$DecryptedMessageAction.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt64(this.random_id);
        this.action.serializeToStream(abstractSerializedData);
    }
}
