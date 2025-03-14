package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_messages_checkChatInvite extends TLObject {
    public static int constructor = 1051570619;
    public String hash;

    @Override // org.telegram.tgnet.TLObject
    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$ChatInvite.TLdeserialize(abstractSerializedData, i, z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.hash);
    }
}
