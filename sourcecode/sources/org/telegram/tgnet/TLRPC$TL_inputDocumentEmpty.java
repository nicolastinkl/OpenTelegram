package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_inputDocumentEmpty extends TLRPC$InputDocument {
    public static int constructor = 1928391342;

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
