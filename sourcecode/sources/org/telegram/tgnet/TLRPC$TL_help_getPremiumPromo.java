package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_help_getPremiumPromo extends TLObject {
    public static int constructor = -1206152236;

    @Override // org.telegram.tgnet.TLObject
    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$TL_help_premiumPromo.TLdeserialize(abstractSerializedData, i, z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
