package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_channelAdminLogEventActionToggleInvites extends TLRPC$ChannelAdminLogEventAction {
    public static int constructor = 460916654;
    public boolean new_value;

    @Override // org.telegram.tgnet.TLObject
    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.new_value = abstractSerializedData.readBool(z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeBool(this.new_value);
    }
}
