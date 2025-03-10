package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_payments_canPurchasePremium extends TLObject {
    public static int constructor = -1614700874;
    public TLRPC$InputStorePaymentPurpose purpose;

    @Override // org.telegram.tgnet.TLObject
    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$Bool.TLdeserialize(abstractSerializedData, i, z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.purpose.serializeToStream(abstractSerializedData);
    }
}
