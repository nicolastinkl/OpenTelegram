package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_messages_editChatTitle extends TLObject {
    public static int constructor = 1937260541;
    public long chat_id;
    public String title;

    @Override // org.telegram.tgnet.TLObject
    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$Updates.TLdeserialize(abstractSerializedData, i, z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt64(this.chat_id);
        abstractSerializedData.writeString(this.title);
    }
}
