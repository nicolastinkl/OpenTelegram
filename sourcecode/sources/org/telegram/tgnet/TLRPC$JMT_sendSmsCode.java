package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$JMT_sendSmsCode extends TLObject {
    public static int constructor = -1618608891;
    public String ext;
    public String phone;
    public String type;

    @Override // org.telegram.tgnet.TLObject
    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$Bool.TLdeserialize(abstractSerializedData, i, z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.phone);
        abstractSerializedData.writeString(this.type);
        abstractSerializedData.writeString(this.ext);
    }
}
