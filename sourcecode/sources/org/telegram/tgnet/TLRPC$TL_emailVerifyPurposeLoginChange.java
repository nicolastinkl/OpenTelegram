package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_emailVerifyPurposeLoginChange extends TLRPC$EmailVerifyPurpose {
    public static int constructor = 1383932651;

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
