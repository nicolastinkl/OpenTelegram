package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_inputGeoPointEmpty extends TLRPC$InputGeoPoint {
    public static int constructor = -457104426;

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
