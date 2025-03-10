package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$JMT_getNewLineInfo extends TLRPC$JSONValue {
    public static int constructor = -1284639294;

    @Override // org.telegram.tgnet.TLObject
    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$JSONValue.TLdeserialize(abstractSerializedData, i, z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
