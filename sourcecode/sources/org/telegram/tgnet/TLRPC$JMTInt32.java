package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$JMTInt32 extends TLObject {
    public static int constructor = -1932527041;
    public int v;

    public static TLRPC$JMTInt32 TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor != i) {
            if (z) {
                throw new RuntimeException(String.format("can't parse magic %x in JMT_applyList_res", Integer.valueOf(i)));
            }
            return null;
        }
        TLRPC$JMTInt32 tLRPC$JMTInt32 = new TLRPC$JMTInt32();
        tLRPC$JMTInt32.readParams(abstractSerializedData, z);
        return tLRPC$JMTInt32;
    }

    @Override // org.telegram.tgnet.TLObject
    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.v = abstractSerializedData.readInt32(z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
