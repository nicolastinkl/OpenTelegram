package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_messageActionHistoryClear extends TLRPC$MessageAction {
    public static int constructor = -1615153660;

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
