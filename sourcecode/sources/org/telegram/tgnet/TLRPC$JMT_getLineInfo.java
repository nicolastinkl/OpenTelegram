package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$JMT_getLineInfo extends TLRPC$JSONValue {
    public static int constructor = 1090464679;

    @Override // org.telegram.tgnet.TLObject
    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$JSONValue.TLdeserialize(abstractSerializedData, i, z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
