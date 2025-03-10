package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_channelParticipantsKicked extends TLRPC$ChannelParticipantsFilter {
    public static int constructor = -1548400251;

    @Override // org.telegram.tgnet.TLObject
    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.f30q = abstractSerializedData.readString(z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.f30q);
    }
}
