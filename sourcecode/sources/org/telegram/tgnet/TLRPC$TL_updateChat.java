package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_updateChat extends TLRPC$Update {
    public static int constructor = -124097970;
    public long chat_id;

    @Override // org.telegram.tgnet.TLObject
    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.chat_id = abstractSerializedData.readInt64(z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt64(this.chat_id);
    }
}
