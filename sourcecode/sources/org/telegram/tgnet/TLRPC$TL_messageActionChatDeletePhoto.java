package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_messageActionChatDeletePhoto extends TLRPC$MessageAction {
    public static int constructor = -1780220945;

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
