package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_inputMediaDocument extends TLRPC$InputMedia {
    public static int constructor = 860303448;
    public TLRPC$InputDocument id;
    public String query;

    @Override // org.telegram.tgnet.TLObject
    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        int readInt32 = abstractSerializedData.readInt32(z);
        this.flags = readInt32;
        this.spoiler = (readInt32 & 4) != 0;
        this.id = TLRPC$InputDocument.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        if ((this.flags & 1) != 0) {
            this.ttl_seconds = abstractSerializedData.readInt32(z);
        }
        if ((this.flags & 2) != 0) {
            this.query = abstractSerializedData.readString(z);
        }
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        int i = this.spoiler ? this.flags | 4 : this.flags & (-5);
        this.flags = i;
        abstractSerializedData.writeInt32(i);
        this.id.serializeToStream(abstractSerializedData);
        if ((this.flags & 1) != 0) {
            abstractSerializedData.writeInt32(this.ttl_seconds);
        }
        if ((this.flags & 2) != 0) {
            abstractSerializedData.writeString(this.query);
        }
    }
}
