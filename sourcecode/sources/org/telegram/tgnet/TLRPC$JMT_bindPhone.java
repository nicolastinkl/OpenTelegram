package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$JMT_bindPhone extends TLObject {
    public static int constructor = 1904768369;
    public String code;
    public String ext;
    public String phone;

    @Override // org.telegram.tgnet.TLObject
    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$Bool.TLdeserialize(abstractSerializedData, i, z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.phone);
        abstractSerializedData.writeString(this.code);
        abstractSerializedData.writeString(this.ext);
    }
}
