package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_channelAdminLogEventActionDeleteTopic extends TLRPC$ChannelAdminLogEventAction {
    public static int constructor = -1374254839;
    public TLRPC$ForumTopic topic;

    @Override // org.telegram.tgnet.TLObject
    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.topic = TLRPC$ForumTopic.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.topic.serializeToStream(abstractSerializedData);
    }
}
