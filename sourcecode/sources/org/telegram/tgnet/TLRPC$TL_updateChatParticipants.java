package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_updateChatParticipants extends TLRPC$Update {
    public static int constructor = 125178264;
    public TLRPC$ChatParticipants participants;

    @Override // org.telegram.tgnet.TLObject
    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.participants = TLRPC$ChatParticipants.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.participants.serializeToStream(abstractSerializedData);
    }
}
