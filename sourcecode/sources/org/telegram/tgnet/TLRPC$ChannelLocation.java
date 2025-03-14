package org.telegram.tgnet;

/* loaded from: classes3.dex */
public abstract class TLRPC$ChannelLocation extends TLObject {
    public static TLRPC$ChannelLocation TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        TLRPC$ChannelLocation tLRPC$ChannelLocation;
        if (i == -1078612597) {
            tLRPC$ChannelLocation = new TLRPC$ChannelLocation() { // from class: org.telegram.tgnet.TLRPC$TL_channelLocationEmpty
                public static int constructor = -1078612597;

                @Override // org.telegram.tgnet.TLObject
                public void serializeToStream(AbstractSerializedData abstractSerializedData2) {
                    abstractSerializedData2.writeInt32(constructor);
                }
            };
        } else {
            tLRPC$ChannelLocation = i != 547062491 ? null : new TLRPC$TL_channelLocation();
        }
        if (tLRPC$ChannelLocation == null && z) {
            throw new RuntimeException(String.format("can't parse magic %x in ChannelLocation", Integer.valueOf(i)));
        }
        if (tLRPC$ChannelLocation != null) {
            tLRPC$ChannelLocation.readParams(abstractSerializedData, z);
        }
        return tLRPC$ChannelLocation;
    }
}
