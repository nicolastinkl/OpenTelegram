package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_notificationSoundNone extends TLRPC$NotificationSound {
    public static int constructor = 1863070943;

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
