package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_inputUserSelf extends TLRPC$InputUser {
    public static int constructor = -138301121;

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
