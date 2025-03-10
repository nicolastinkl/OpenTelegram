package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_defaultHistoryTTL extends TLObject {
    public static int constructor = 1135897376;
    public int period;

    public static TLRPC$TL_defaultHistoryTTL TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor != i) {
            if (z) {
                throw new RuntimeException(String.format("can't parse magic %x in TL_defaultHistoryTTL", Integer.valueOf(i)));
            }
            return null;
        }
        TLRPC$TL_defaultHistoryTTL tLRPC$TL_defaultHistoryTTL = new TLRPC$TL_defaultHistoryTTL();
        tLRPC$TL_defaultHistoryTTL.readParams(abstractSerializedData, z);
        return tLRPC$TL_defaultHistoryTTL;
    }

    @Override // org.telegram.tgnet.TLObject
    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.period = abstractSerializedData.readInt32(z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.period);
    }
}
