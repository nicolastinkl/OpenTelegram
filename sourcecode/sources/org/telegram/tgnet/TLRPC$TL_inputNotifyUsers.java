package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_inputNotifyUsers extends TLRPC$InputNotifyPeer {
    public static int constructor = 423314455;

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
