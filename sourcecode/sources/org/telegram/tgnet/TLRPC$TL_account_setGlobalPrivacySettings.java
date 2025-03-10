package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_account_setGlobalPrivacySettings extends TLObject {
    public static int constructor = 517647042;
    public TLRPC$TL_globalPrivacySettings settings;

    @Override // org.telegram.tgnet.TLObject
    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$TL_globalPrivacySettings.TLdeserialize(abstractSerializedData, i, z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.settings.serializeToStream(abstractSerializedData);
    }
}
