package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_messages_toggleBotInAttachMenu extends TLObject {
    public static int constructor = 1777704297;
    public TLRPC$InputUser bot;
    public boolean enabled;
    public int flags;
    public boolean write_allowed;

    @Override // org.telegram.tgnet.TLObject
    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$Bool.TLdeserialize(abstractSerializedData, i, z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        int i = this.write_allowed ? this.flags | 1 : this.flags & (-2);
        this.flags = i;
        abstractSerializedData.writeInt32(i);
        this.bot.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeBool(this.enabled);
    }
}
