package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_inputPhotoEmpty extends TLRPC$InputPhoto {
    public static int constructor = 483901197;

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
