package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_inputCheckPasswordEmpty extends TLRPC$InputCheckPasswordSRP {
    public static int constructor = -1736378792;

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
