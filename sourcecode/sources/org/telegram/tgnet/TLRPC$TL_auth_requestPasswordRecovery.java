package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_auth_requestPasswordRecovery extends TLObject {
    public static int constructor = -661144474;

    @Override // org.telegram.tgnet.TLObject
    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$TL_auth_passwordRecovery.TLdeserialize(abstractSerializedData, i, z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
