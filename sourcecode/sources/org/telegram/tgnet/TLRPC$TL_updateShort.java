package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_updateShort extends TLRPC$Updates {
    public static int constructor = 2027216577;

    @Override // org.telegram.tgnet.TLObject
    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.update = TLRPC$Update.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        this.date = abstractSerializedData.readInt32(z);
    }
}
