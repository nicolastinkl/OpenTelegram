package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_channelAdminLogEventActionExportedInviteRevoke extends TLRPC$ChannelAdminLogEventAction {
    public static int constructor = 1091179342;
    public TLRPC$TL_chatInviteExported invite;

    @Override // org.telegram.tgnet.TLObject
    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.invite = TLRPC$ExportedChatInvite.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.invite.serializeToStream(abstractSerializedData);
    }
}
