package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_channels_editAdmin extends TLObject {
    public static int constructor = -751007486;
    public TLRPC$TL_chatAdminRights admin_rights;
    public TLRPC$InputChannel channel;
    public String rank;
    public TLRPC$InputUser user_id;

    @Override // org.telegram.tgnet.TLObject
    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$Updates.TLdeserialize(abstractSerializedData, i, z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.channel.serializeToStream(abstractSerializedData);
        this.user_id.serializeToStream(abstractSerializedData);
        this.admin_rights.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeString(this.rank);
    }
}
