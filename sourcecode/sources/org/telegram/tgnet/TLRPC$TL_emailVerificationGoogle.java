package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_emailVerificationGoogle extends TLRPC$EmailVerification {
    public static int constructor = -611279166;
    public String token;

    @Override // org.telegram.tgnet.TLObject
    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.token = abstractSerializedData.readString(z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.token);
    }
}
