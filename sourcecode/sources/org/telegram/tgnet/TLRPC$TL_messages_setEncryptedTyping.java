package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_messages_setEncryptedTyping extends TLObject {
    public static int constructor = 2031374829;
    public TLRPC$TL_inputEncryptedChat peer;
    public boolean typing;

    @Override // org.telegram.tgnet.TLObject
    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$Bool.TLdeserialize(abstractSerializedData, i, z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.peer.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeBool(this.typing);
    }
}
