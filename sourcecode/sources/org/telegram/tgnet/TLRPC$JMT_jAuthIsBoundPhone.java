package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$JMT_jAuthIsBoundPhone extends TLObject {
    public static int constructor = 255433246;
    public int bound;
    public String ext;
    public int require_level;

    public static TLRPC$JMT_jAuthIsBoundPhone TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor != i) {
            if (z) {
                throw new RuntimeException(String.format("can't parse magic %x in JMT_jAuthIsBoundPhone", Integer.valueOf(i)));
            }
            return null;
        }
        TLRPC$JMT_jAuthIsBoundPhone tLRPC$JMT_jAuthIsBoundPhone = new TLRPC$JMT_jAuthIsBoundPhone();
        tLRPC$JMT_jAuthIsBoundPhone.readParams(abstractSerializedData, z);
        return tLRPC$JMT_jAuthIsBoundPhone;
    }

    @Override // org.telegram.tgnet.TLObject
    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.bound = abstractSerializedData.readInt32(z);
        this.require_level = abstractSerializedData.readInt32(z);
        this.ext = abstractSerializedData.readString(z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.bound);
        abstractSerializedData.writeInt32(this.require_level);
        abstractSerializedData.writeString(this.ext);
    }
}
