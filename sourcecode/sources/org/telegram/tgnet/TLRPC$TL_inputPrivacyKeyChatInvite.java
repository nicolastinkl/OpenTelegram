package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_inputPrivacyKeyChatInvite extends TLRPC$InputPrivacyKey {
    public static int constructor = -1107622874;

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
