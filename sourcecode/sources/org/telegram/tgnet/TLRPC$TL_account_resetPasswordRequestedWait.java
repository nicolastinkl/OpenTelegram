package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_account_resetPasswordRequestedWait extends TLRPC$account_ResetPasswordResult {
    public static int constructor = -370148227;
    public int until_date;

    @Override // org.telegram.tgnet.TLObject
    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.until_date = abstractSerializedData.readInt32(z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.until_date);
    }
}
