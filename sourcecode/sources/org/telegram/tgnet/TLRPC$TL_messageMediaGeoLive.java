package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_messageMediaGeoLive extends TLRPC$MessageMedia {
    public static int constructor = -1186937242;

    @Override // org.telegram.tgnet.TLObject
    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.flags = abstractSerializedData.readInt32(z);
        this.geo = TLRPC$GeoPoint.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        if ((this.flags & 1) != 0) {
            this.heading = abstractSerializedData.readInt32(z);
        }
        this.period = abstractSerializedData.readInt32(z);
        if ((this.flags & 2) != 0) {
            this.proximity_notification_radius = abstractSerializedData.readInt32(z);
        }
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.flags);
        this.geo.serializeToStream(abstractSerializedData);
        if ((this.flags & 1) != 0) {
            abstractSerializedData.writeInt32(this.heading);
        }
        abstractSerializedData.writeInt32(this.period);
        if ((this.flags & 2) != 0) {
            abstractSerializedData.writeInt32(this.proximity_notification_radius);
        }
    }
}
