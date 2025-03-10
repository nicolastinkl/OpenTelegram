package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_channelParticipantsContacts extends TLRPC$ChannelParticipantsFilter {
    public static int constructor = -1150621555;

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
